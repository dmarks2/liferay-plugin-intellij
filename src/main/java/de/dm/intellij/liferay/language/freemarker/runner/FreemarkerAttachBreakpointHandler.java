package de.dm.intellij.liferay.language.freemarker.runner;

import com.intellij.freemarker.psi.FtlElementTypes;
import com.intellij.icons.AllIcons;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.DocumentUtil;
import com.intellij.xdebugger.XSourcePosition;
import com.intellij.xdebugger.breakpoints.XBreakpointHandler;
import com.intellij.xdebugger.breakpoints.XLineBreakpoint;
import de.dm.intellij.liferay.client.Constants;
import de.dm.intellij.liferay.client.GroupConstants;
import de.dm.intellij.liferay.client.LiferayServicesUtil;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import freemarker.debug.Breakpoint;
import freemarker.debug.Debugger;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class FreemarkerAttachBreakpointHandler extends XBreakpointHandler<XLineBreakpoint<FreemarkerAttachBreakpointProperties>> {

    private final static Logger log = Logger.getInstance(FreemarkerAttachBreakpointHandler.class);

    public static final String SERVLET_CONTEXT = "_SERVLET_CONTEXT_";

    private Debugger debugger;
    private FreemarkerAttachDebugProcess debugProcess;

    private LiferayServicesUtil liferayServicesUtil;

    private Map<AbstractMap.SimpleImmutableEntry<String, Integer>, Breakpoint> breakpoints = new HashMap<>();
    private Map<AbstractMap.SimpleImmutableEntry<String, Integer>, XLineBreakpoint<FreemarkerAttachBreakpointProperties>> xlineBreakpoints = new HashMap<>();

    public FreemarkerAttachBreakpointHandler(Debugger debugger, FreemarkerAttachDebugProcess debugProcess) throws URISyntaxException, IOException, JSONException {
        super(FreemarkerAttachBreakpointType.class);

        this.debugger = debugger;
        this.debugProcess = debugProcess;

        URI uri = new URI(debugProcess.getFreemarkerAttachDebugConfiguration().getLiferayURL() + Constants.ENDPOINT_JSONWS);
        liferayServicesUtil = new LiferayServicesUtil(uri, debugProcess.getFreemarkerAttachDebugConfiguration().getLiferayUsername(), debugProcess.getFreemarkerAttachDebugConfiguration().getLiferayPassword());

        String result = liferayServicesUtil.getVersion();

        if (result != null) {
            if (result.contains("\"exception\"")) {
                throw new IOException(result);
            }
        }
    }

    @Override
    public void registerBreakpoint(@NotNull XLineBreakpoint<FreemarkerAttachBreakpointProperties> breakpoint) {
        XSourcePosition sourcePosition = breakpoint.getSourcePosition();

        if (sourcePosition != null) {
            VirtualFile virtualFile = sourcePosition.getFile();
            Project project = debugProcess.getSession().getProject();
            int line = (sourcePosition.getLine() + 1);

            boolean isValidFreemarkerLocation = isValidFreemarkerLocation(project, virtualFile, line);

            if (isValidFreemarkerLocation) {

                String templateName = getTemplateName(virtualFile);

                if (templateName != null) {
                    Breakpoint registeredBreakpoint = new Breakpoint(templateName, line);

                    AbstractMap.SimpleImmutableEntry<String, Integer> key = new AbstractMap.SimpleImmutableEntry<>(templateName, line);

                    breakpoints.put(key, registeredBreakpoint);
                    xlineBreakpoints.put(key, breakpoint);

                    try {
                        debugger.addBreakpoint(registeredBreakpoint);

                        if (log.isDebugEnabled()) {
                            log.debug("Successfully registered breakpoint at line " + line + " in template " + templateName);
                        }

                        debugProcess.getSession().updateBreakpointPresentation(
                            breakpoint,
                            AllIcons.Debugger.Db_verified_breakpoint,
                            null
                        );
                    } catch (RemoteException e) {
                        debugProcess.getSession().updateBreakpointPresentation(
                            breakpoint,
                            AllIcons.Debugger.Db_invalid_breakpoint,
                            e.getMessage()
                        );
                        log.error(e.getMessage(), e);
                    }
                } else {
                    debugProcess.getSession().updateBreakpointPresentation(
                        breakpoint,
                        AllIcons.Debugger.Db_invalid_breakpoint,
                        ""
                    );
                }
            } else {
                debugProcess.getSession().updateBreakpointPresentation(
                    breakpoint,
                    AllIcons.Debugger.Db_invalid_breakpoint,
                    ""
                );
            }
        }
    }

    @Override
    public void unregisterBreakpoint(@NotNull XLineBreakpoint<FreemarkerAttachBreakpointProperties> breakpoint, boolean temporary) {
        XSourcePosition sourcePosition = breakpoint.getSourcePosition();

        VirtualFile virtualFile = sourcePosition.getFile();

        String templateName = getTemplateName(virtualFile);

        if (templateName != null) {
            int line = (sourcePosition.getLine() + 1);

            AbstractMap.SimpleImmutableEntry<String, Integer> key = new AbstractMap.SimpleImmutableEntry<>(templateName, line);

            Breakpoint registeredBreakpoint = breakpoints.get(key);
            if (registeredBreakpoint != null) {
                breakpoints.remove(key);
                xlineBreakpoints.remove(key);

                try {
                    debugger.removeBreakpoint(registeredBreakpoint);

                    if (log.isDebugEnabled()) {
                        log.debug("Successfully unregistered breakpoint at line " + line + " in template " + templateName);
                    }
                } catch (RemoteException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    public XLineBreakpoint<FreemarkerAttachBreakpointProperties> findXlineBreakpoint(String templateName, int line) {
        AbstractMap.SimpleImmutableEntry<String, Integer> key = new AbstractMap.SimpleImmutableEntry<>(templateName, line);

        return xlineBreakpoints.get(key);
    }

    public VirtualFile getVirtualFileByTemplateName(String templateName) {
        Map.Entry<AbstractMap.SimpleImmutableEntry<String, Integer>, XLineBreakpoint<FreemarkerAttachBreakpointProperties>> entry =
            xlineBreakpoints.entrySet().stream().
                filter(e -> templateName.equals(e.getKey().getKey())).
                findFirst().orElse(null);

        if (entry != null) {
            XLineBreakpoint<FreemarkerAttachBreakpointProperties> xLineBreakpoint = entry.getValue();
            XSourcePosition sourcePosition = xLineBreakpoint.getSourcePosition();
            if (sourcePosition != null) {
                return sourcePosition.getFile();
            }
        }

        return null;
    }

    public String getTemplateName(VirtualFile virtualFile) {
        PsiFile psiFile = PsiManager.getInstance(debugProcess.getSession().getProject()).findFile(virtualFile);

        if (psiFile != null) {

            if (
                (LiferayFileUtil.isLayoutTemplateFile(psiFile)) ||
                    (LiferayFileUtil.isThemeTemplateFile(psiFile))
            ) {
                String servletContextTemplateName = getServletContextTemplateName(virtualFile);

                if (log.isDebugEnabled()) {
                    log.debug("Found template name for file " + virtualFile.getName() + " based on theme or layout as " + servletContextTemplateName);
                }

                return servletContextTemplateName;
            } else if (LiferayFileUtil.isJournalTemplateFile(psiFile)) {
                VirtualFile journalStructureFile = LiferayFileUtil.getJournalStructureFile(psiFile);

                if (journalStructureFile != null) {
                    String groupName = GroupConstants.GLOBAL;

                    final Module module = ModuleUtil.findModuleForFile(virtualFile, debugProcess.getSession().getProject());

                    if (module != null) {
                        if (LiferayModuleComponent.getResourcesImporterGroupName(module) != null) {
                            groupName = LiferayModuleComponent.getResourcesImporterGroupName(module);
                        }
                    }

                    String structureKey = getKey(journalStructureFile.getNameWithoutExtension());
                    String templateKey = getKey(virtualFile.getNameWithoutExtension());

                    try {
                        if (log.isDebugEnabled()) {
                            log.debug("Trying to find Journal Structure Template for file " + virtualFile.getName() + " with Structure Key " + structureKey + " and Template Key " + templateKey + " in Group " + groupName + " ...");
                        }

                        String result = liferayServicesUtil.getFreemarkerTemplateName(structureKey, templateKey, groupName);

                        return result;
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (LiferayFileUtil.isApplicationDisplayTemplateFile(psiFile)) {
                String templateName = virtualFile.getNameWithoutExtension();
                String type = LiferayFileUtil.getApplicationDisplayTemplateType(psiFile);

                String groupName = GroupConstants.GLOBAL;

                final Module module = ModuleUtil.findModuleForFile(virtualFile, debugProcess.getSession().getProject());

                if (module != null) {
                    if (LiferayModuleComponent.getResourcesImporterGroupName(module) != null) {
                        groupName = LiferayModuleComponent.getResourcesImporterGroupName(module);
                    }
                }

                try {
                    if (log.isDebugEnabled()) {
                        log.debug("Trying to find Application Display Template for file " + virtualFile.getName() + " with Template name " + templateName + " and Type " + type + " in Group " + groupName + " ...");
                    }

                    String result = liferayServicesUtil.getFreemarkerApplicationDisplayTemplateName(type, templateName, groupName);

                    return result;
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    private String getServletContextTemplateName(VirtualFile virtualFile) {
        Project project = debugProcess.getSession().getProject();

        Module module = ModuleUtil.findModuleForFile(virtualFile, project);

        String name = module.getName(); //??

        ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(module);
        for (VirtualFile sourceRoot : moduleRootManager.getSourceRoots()) {
            String relativePath = VfsUtilCore.getRelativePath(virtualFile, sourceRoot);
            if (relativePath != null) {
                String templateFilename = name + SERVLET_CONTEXT + "/" + relativePath;

                return templateFilename;
            }
        }

        return null;
    }

    public boolean isValidFreemarkerLocation(Project project, VirtualFile virtualFile, int line) {
        boolean isValidFreemarkerLocation = false;

        PsiFile psiFile = PsiManager.getInstance(project).findFile(virtualFile);

        if (psiFile != null) {
            Document document = PsiDocumentManager.getInstance(psiFile.getProject()).getDocument(psiFile);
            if (document != null) {
                TextRange lineTextRange = DocumentUtil.getLineTextRange(document, (line-1));
                for (int i = lineTextRange.getStartOffset(); i < lineTextRange.getEndOffset(); i++) {
                    PsiElement psiElement = psiFile.findElementAt(i);
                    if (psiElement != null) {
                        ASTNode astNode = psiElement.getNode();
                        IElementType elementType = astNode.getElementType();
                        if (
                            (elementType.equals(FtlElementTypes.START_DIRECTIVE_START)) ||
                            (elementType.equals(FtlElementTypes.START_MACRO_START)) ||
                            (elementType.equals(FtlElementTypes.EL_START))
                        ) {
                            isValidFreemarkerLocation = true;

                            break;
                        }
                    }
                }

            }
        }

        return isValidFreemarkerLocation;
    }

    private String getKey(String name) {
        name = StringUtil.replace(name, " ", "-");

        name = StringUtil.toUpperCase(name);

        return name;
    }
}
