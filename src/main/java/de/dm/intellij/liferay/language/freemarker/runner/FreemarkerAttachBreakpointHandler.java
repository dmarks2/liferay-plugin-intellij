package de.dm.intellij.liferay.language.freemarker.runner;

import com.intellij.freemarker.psi.FtlTokenType;
import com.intellij.icons.AllIcons;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.util.TextRange;
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
import de.dm.intellij.liferay.client.LiferayServicesUtil;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import freemarker.debug.Breakpoint;
import freemarker.debug.Debugger;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class FreemarkerAttachBreakpointHandler extends XBreakpointHandler<XLineBreakpoint<FreemarkerAttachBreakpointProperties>> {

    public static final String SERVLET_CONTEXT = "_SERVLET_CONTEXT_";

    private Debugger debugger;
    private FreemarkerAttachDebugProcess debugProcess;

    private LiferayServicesUtil liferayServicesUtil;

    private Map<AbstractMap.SimpleImmutableEntry<String, Integer>, Breakpoint> breakpoints = new HashMap<>();
    private Map<AbstractMap.SimpleImmutableEntry<String, Integer>, XLineBreakpoint<FreemarkerAttachBreakpointProperties>> xlineBreakpoints = new HashMap<>();

    public FreemarkerAttachBreakpointHandler(Debugger debugger, FreemarkerAttachDebugProcess debugProcess) {
        super(FreemarkerAttachBreakpointType.class);

        this.debugger = debugger;
        this.debugProcess = debugProcess;

        try {
            URI uri = new URI(debugProcess.getFreemarkerAttachDebugConfiguration().getLiferayURL() + Constants.ENDPOINT_JSONWS);
            liferayServicesUtil = new LiferayServicesUtil(uri, debugProcess.getFreemarkerAttachDebugConfiguration().getLiferayUsername(), debugProcess.getFreemarkerAttachDebugConfiguration().getLiferayPassword());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void registerBreakpoint(@NotNull XLineBreakpoint<FreemarkerAttachBreakpointProperties> breakpoint) {
        XSourcePosition sourcePosition = breakpoint.getSourcePosition();

        if (sourcePosition != null) {
            VirtualFile virtualFile = sourcePosition.getFile();

            boolean isValidFreemarkerLocation = false;

            PsiFile psiFile = PsiManager.getInstance(debugProcess.getSession().getProject()).findFile(virtualFile);
            if (psiFile != null) {
                Document document = PsiDocumentManager.getInstance(psiFile.getProject()).getDocument(psiFile);
                if (document != null) {
                    TextRange lineTextRange = DocumentUtil.getLineTextRange(document, sourcePosition.getLine());
                    for (int i = lineTextRange.getStartOffset(); i < lineTextRange.getEndOffset(); i++) {
                        PsiElement psiElement = psiFile.findElementAt(i);
                        if (psiElement != null) {
                            ASTNode astNode = psiElement.getNode();
                            IElementType elementType = astNode.getElementType();
                            if (elementType instanceof FtlTokenType) {
                                isValidFreemarkerLocation = true;

                                break;
                            }
                        }
                    }

                }
            }

            if (isValidFreemarkerLocation) {

                String templateName = getTemplateName(virtualFile);

                if (templateName != null) {
                    Breakpoint registeredBreakpoint = new Breakpoint(templateName, sourcePosition.getLine());

                    AbstractMap.SimpleImmutableEntry<String, Integer> key = new AbstractMap.SimpleImmutableEntry<>(templateName, sourcePosition.getLine());

                    breakpoints.put(key, registeredBreakpoint);
                    xlineBreakpoints.put(key, breakpoint);

                    try {
                        debugger.addBreakpoint(registeredBreakpoint);

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
                        e.printStackTrace();
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
            AbstractMap.SimpleImmutableEntry<String, Integer> key = new AbstractMap.SimpleImmutableEntry<>(templateName, sourcePosition.getLine());

            Breakpoint registeredBreakpoint = breakpoints.get(key);
            if (registeredBreakpoint != null) {
                breakpoints.remove(key);
                xlineBreakpoints.remove(key);

                try {
                    debugger.removeBreakpoint(registeredBreakpoint);
                } catch (RemoteException e) {
                    e.printStackTrace();
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

    private String getTemplateName(VirtualFile virtualFile) {
        PsiFile psiFile = PsiManager.getInstance(debugProcess.getSession().getProject()).findFile(virtualFile);
        if (psiFile != null) {
            if (
                (LiferayFileUtil.isLayoutTemplateFile(psiFile)) ||
                (LiferayFileUtil.isThemeTemplateFile(psiFile))
            ) {
                return getServletContextTemplateName(virtualFile);
            } else if (LiferayFileUtil.isJournalTemplateFile(psiFile)) {
                VirtualFile journalStructureFile = LiferayFileUtil.getJournalStructureFile(psiFile);
                if (journalStructureFile != null) {
                    String structureName = journalStructureFile.getNameWithoutExtension();
                    String templateName = virtualFile.getNameWithoutExtension();

                    try {
                        String result = liferayServicesUtil.getFreemarkerTemplateName(structureName, templateName);

                        return result;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (LiferayFileUtil.isApplicationDisplayTemplateFile(psiFile)) {
                String templateName = virtualFile.getNameWithoutExtension();
                String type = LiferayFileUtil.getApplicationDisplayTemplateType(psiFile);

                try {
                    String result = liferayServicesUtil.getFreemarkerApplicationDisplayTemplateName(type,templateName);

                    return result;
                } catch (IOException e) {
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
}
