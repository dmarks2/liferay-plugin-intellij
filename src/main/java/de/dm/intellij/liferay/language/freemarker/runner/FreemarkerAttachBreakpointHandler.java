package de.dm.intellij.liferay.language.freemarker.runner;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.xdebugger.XSourcePosition;
import com.intellij.xdebugger.breakpoints.XBreakpoint;
import com.intellij.xdebugger.breakpoints.XBreakpointHandler;
import com.intellij.xdebugger.breakpoints.XLineBreakpoint;
import freemarker.debug.Breakpoint;
import freemarker.debug.Debugger;
import org.jetbrains.annotations.NotNull;

import java.rmi.RemoteException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class FreemarkerAttachBreakpointHandler extends XBreakpointHandler<XLineBreakpoint<FreemarkerAttachBreakpointProperties>> {

    public static final String SERVLET_CONTEXT = "_SERVLET_CONTEXT_";

    private Debugger debugger;
    private FreemarkerAttachDebugProcess debugProcess;

    private Map<AbstractMap.SimpleImmutableEntry<String, Integer>, Breakpoint> breakpoints = new HashMap<>();
    private Map<AbstractMap.SimpleImmutableEntry<String, Integer>, XLineBreakpoint<FreemarkerAttachBreakpointProperties>> xlineBreakpoints = new HashMap<>();

    public FreemarkerAttachBreakpointHandler(Debugger debugger, FreemarkerAttachDebugProcess debugProcess) {
        super(FreemarkerAttachBreakpointType.class);

        this.debugger = debugger;
        this.debugProcess = debugProcess;
    }

    @Override
    public void registerBreakpoint(@NotNull XLineBreakpoint<FreemarkerAttachBreakpointProperties> breakpoint) {
        XSourcePosition sourcePosition = breakpoint.getSourcePosition();

        VirtualFile virtualFile = sourcePosition.getFile();

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

    private String getTemplateName(VirtualFile virtualFile) {
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
