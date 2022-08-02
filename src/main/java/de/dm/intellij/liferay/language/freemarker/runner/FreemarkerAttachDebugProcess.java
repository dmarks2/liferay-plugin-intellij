package de.dm.intellij.liferay.language.freemarker.runner;

import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ExecutionConsole;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.xdebugger.XDebugProcess;
import com.intellij.xdebugger.XDebugSession;
import com.intellij.xdebugger.XDebuggerUtil;
import com.intellij.xdebugger.XSourcePosition;
import com.intellij.xdebugger.breakpoints.XBreakpointHandler;
import com.intellij.xdebugger.breakpoints.XLineBreakpoint;
import com.intellij.xdebugger.evaluation.XDebuggerEditorsProvider;
import com.intellij.xdebugger.frame.XSuspendContext;
import freemarker.debug.Breakpoint;
import freemarker.debug.DebuggedEnvironment;
import freemarker.debug.Debugger;
import freemarker.debug.DebuggerClient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.util.Collection;

public class FreemarkerAttachDebugProcess extends XDebugProcess {

    private final static Logger log = Logger.getInstance(FreemarkerAttachDebugProcess.class);

    private FreemarkerAttachDebugConfiguration freemarkerAttachDebugConfiguration;
    private Debugger debugger;
    private Object debuggerListenerId;

    private FreemarkerAttachBreakpointHandler freemarkerAttachBreakpointHandler;

    private ConsoleView executionConsole;

    private XDebuggerEditorsProvider editorsProvider = new FreemarkerAttachDebugEditorsProvider();

    private Breakpoint steppingBreakpoint;

    public FreemarkerAttachDebugProcess(XDebugSession session, FreemarkerAttachExecutionResult freemarkerAttachExecutionResult) throws IOException, JSONException, URISyntaxException {
        super(session);

        this.freemarkerAttachDebugConfiguration = freemarkerAttachExecutionResult.getFreemarkerAttachDebugConfiguration();
        this.executionConsole = (ConsoleView) freemarkerAttachExecutionResult.getExecutionConsole();

        InetAddress inetAddress = InetAddress.getByName(freemarkerAttachDebugConfiguration.getHost());

        this.debugger = DebuggerClient.getDebugger(inetAddress, freemarkerAttachDebugConfiguration.getPort(), freemarkerAttachDebugConfiguration.getSecret());

        this.freemarkerAttachBreakpointHandler = new FreemarkerAttachBreakpointHandler(debugger, this);

        this.debugger.removeBreakpoints();

        this.debuggerListenerId = this.debugger.addDebuggerListener(e -> {
            DebuggedEnvironment debuggedEnvironment = e.getEnvironment();

            String templateName = e.getName();
            int line = e.getLine();

            if (log.isDebugEnabled()) {
                log.debug("Reached Breakpoint at line " + line + " in template " + templateName);
            }

            XLineBreakpoint<FreemarkerAttachBreakpointProperties> xlineBreakpoint = freemarkerAttachBreakpointHandler.findXlineBreakpoint(templateName, line);

            if (xlineBreakpoint != null) {
                FreemarkerAttachSuspendContext freemarkerAttachSuspendContext = new FreemarkerAttachSuspendContext(debuggedEnvironment, xlineBreakpoint);

                boolean suspendProcess = getSession().breakpointReached(xlineBreakpoint, null, freemarkerAttachSuspendContext);

                if (!suspendProcess) {
                    ApplicationManager.getApplication().invokeLater(
                        () -> {
                            try {
                                debuggedEnvironment.resume();
                            } catch (RemoteException e1) {
                                e1.printStackTrace();
                            }
                        }
                    );
                }
            } else {
                VirtualFile virtualFile = getVirtualFileFromTemplateName(templateName);
                if (virtualFile != null) {
                    if (steppingBreakpoint != null) {
                        debugger.removeBreakpoint(steppingBreakpoint);
                        steppingBreakpoint = null;
                    }

                    XSourcePosition sourcePosition = XDebuggerUtil.getInstance().createPosition(virtualFile, (line - 1));

                    FreemarkerAttachSuspendContext freemarkerAttachSuspendContext = new FreemarkerAttachSuspendContext(debuggedEnvironment, sourcePosition);

                    getSession().positionReached(freemarkerAttachSuspendContext);
                } else {
                    if (steppingBreakpoint != null) {
                        debugger.removeBreakpoint(steppingBreakpoint);
                        steppingBreakpoint = null;
                    }

                    ApplicationManager.getApplication().invokeLater(
                        () -> {
                            try {
                                debuggedEnvironment.resume();
                            } catch (RemoteException e1) {
                                e1.printStackTrace();
                            }
                        }
                    );
                }
            }
        });
    }

    @NotNull
    @Override
    public XDebuggerEditorsProvider getEditorsProvider() {
        return editorsProvider;
    }

    @NotNull
    @Override
    public ExecutionConsole createConsole() {
        return executionConsole;
    }

    @NotNull
    @Override
    public XBreakpointHandler<?>[] getBreakpointHandlers() {
        return new XBreakpointHandler[]{freemarkerAttachBreakpointHandler};
    }

    @Override
    public void resume(@Nullable XSuspendContext context) {
        FreemarkerAttachSuspendContext freemarkerAttachSuspendContext = (FreemarkerAttachSuspendContext) context;

        try {
            DebuggedEnvironment debuggedEnvironment = freemarkerAttachSuspendContext.getDebuggedEnvironment();

            if (steppingBreakpoint != null) {
                debugger.removeBreakpoint(steppingBreakpoint);

                steppingBreakpoint = null;
            }

            debuggedEnvironment.resume();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        try {
            Collection<DebuggedEnvironment> suspendedEnvironments = (Collection<DebuggedEnvironment>) this.debugger.getSuspendedEnvironments();
            for (DebuggedEnvironment debuggedEnvironment : suspendedEnvironments) {
                debuggedEnvironment.resume();
            }

            this.debugger.removeDebuggerListener(debuggerListenerId);
            this.debugger.removeBreakpoints();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startStepOver(@Nullable XSuspendContext context) {
        FreemarkerAttachSuspendContext freemarkerAttachSuspendContext = (FreemarkerAttachSuspendContext) context;

        XSourcePosition sourcePosition = freemarkerAttachSuspendContext.getSourcePosition();
        VirtualFile virtualFile = sourcePosition.getFile();
        int line = (sourcePosition.getLine() + 1);

        Project project = getSession().getProject();

        String templateName = freemarkerAttachBreakpointHandler.getTemplateName(virtualFile);

        if (steppingBreakpoint != null) {
            try {
                debugger.removeBreakpoint(steppingBreakpoint);

                steppingBreakpoint = null;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }


        if (templateName != null) {

            try {
                line++;

                boolean isValidFreemarkerLocation = freemarkerAttachBreakpointHandler.isValidFreemarkerLocation(project, virtualFile, line);

                while (!isValidFreemarkerLocation) {
                    line++;

                    isValidFreemarkerLocation = freemarkerAttachBreakpointHandler.isValidFreemarkerLocation(project, virtualFile, line);
                }

                XLineBreakpoint<FreemarkerAttachBreakpointProperties> xlineBreakpoint = freemarkerAttachBreakpointHandler.findXlineBreakpoint(templateName, line);

                if (xlineBreakpoint == null) {
                    steppingBreakpoint = new Breakpoint(templateName, line);

                    debugger.addBreakpoint(steppingBreakpoint);

                    DebuggedEnvironment debuggedEnvironment = freemarkerAttachSuspendContext.getDebuggedEnvironment();

                    debuggedEnvironment.resume();
                }
            } catch (IndexOutOfBoundsException e) {
                //line after document end

                resume(context);
            } catch (RemoteException e) {
                e.printStackTrace();

                resume(context);
            }
        } else {
            resume(context);
        }
    }

    @Override
    public void startStepInto(@Nullable XSuspendContext context) {
        startStepOver(context);
    }

    @Override
    public void startStepOut(@Nullable XSuspendContext context) {
        resume(context);
    }

    @Override
    public void runToPosition(@NotNull XSourcePosition position, @Nullable XSuspendContext context) {
        FreemarkerAttachSuspendContext freemarkerAttachSuspendContext = (FreemarkerAttachSuspendContext)context;

        try {
            if (steppingBreakpoint != null) {
                debugger.removeBreakpoint(steppingBreakpoint);

                steppingBreakpoint = null;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        VirtualFile virtualFile = position.getFile();
        int line = (position.getLine() + 1);

        Project project = getSession().getProject();

        String templateName = freemarkerAttachBreakpointHandler.getTemplateName(virtualFile);

        boolean isValidFreemarkerLocation = freemarkerAttachBreakpointHandler.isValidFreemarkerLocation(project, virtualFile, line);

        if (templateName != null) {
            if (isValidFreemarkerLocation) {
                steppingBreakpoint = new Breakpoint(templateName, line);

                try {
                    debugger.addBreakpoint(steppingBreakpoint);

                    DebuggedEnvironment debuggedEnvironment = freemarkerAttachSuspendContext.getDebuggedEnvironment();

                    debuggedEnvironment.resume();

                } catch (RemoteException e) {
                    resume(context);
                }
            }
        }

        resume(context);
    }

    private VirtualFile getVirtualFileFromTemplateName(String templateName) {
        VirtualFile virtualFile = freemarkerAttachBreakpointHandler.getVirtualFileByTemplateName(templateName);

        return virtualFile;
    }

    public FreemarkerAttachDebugConfiguration getFreemarkerAttachDebugConfiguration() {
        return freemarkerAttachDebugConfiguration;
    }

    @Override
    public String getCurrentStateMessage() {
        try {
            Collection suspendedEnvironments = debugger.getSuspendedEnvironments();

            if (suspendedEnvironments.size() == 0) {
                return "Connected to Freemarker debugger at " + freemarkerAttachDebugConfiguration.getHost() + ":" + freemarkerAttachDebugConfiguration.getPort();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return super.getCurrentStateMessage();
    }

}
