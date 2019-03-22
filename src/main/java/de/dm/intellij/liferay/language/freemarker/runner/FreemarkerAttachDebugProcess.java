package de.dm.intellij.liferay.language.freemarker.runner;

import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ExecutionConsole;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.xdebugger.XDebugProcess;
import com.intellij.xdebugger.XDebugSession;
import com.intellij.xdebugger.XDebuggerUtil;
import com.intellij.xdebugger.XSourcePosition;
import com.intellij.xdebugger.breakpoints.XBreakpointHandler;
import com.intellij.xdebugger.breakpoints.XLineBreakpoint;
import com.intellij.xdebugger.evaluation.XDebuggerEditorsProvider;
import com.intellij.xdebugger.frame.XSuspendContext;
import freemarker.debug.DebuggedEnvironment;
import freemarker.debug.Debugger;
import freemarker.debug.DebuggerClient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.util.Collection;

public class FreemarkerAttachDebugProcess extends XDebugProcess {

    private FreemarkerAttachDebugConfiguration freemarkerAttachDebugConfiguration;
    private Debugger debugger;
    private Object debuggerListenerId;

    private FreemarkerAttachBreakpointHandler freemarkerAttachBreakpointHandler;

    private ConsoleView executionConsole;

    private XDebuggerEditorsProvider editorsProvider = new FreemarkerAttachDebugEditorsProvider();

    public FreemarkerAttachDebugProcess(XDebugSession session, FreemarkerAttachExecutionResult freemarkerAttachExecutionResult) throws IOException {
        super(session);

        this.freemarkerAttachDebugConfiguration = freemarkerAttachExecutionResult.getFreemarkerAttachDebugConfiguration();
        this.executionConsole = (ConsoleView) freemarkerAttachExecutionResult.getExecutionConsole();

        InetAddress inetAddress = InetAddress.getByName(freemarkerAttachDebugConfiguration.getHost());

        this.debugger = DebuggerClient.getDebugger(inetAddress, freemarkerAttachDebugConfiguration.getPort(), freemarkerAttachDebugConfiguration.getPassword());

        this.freemarkerAttachBreakpointHandler = new FreemarkerAttachBreakpointHandler(debugger, this);

        this.debugger.removeBreakpoints();

        this.debuggerListenerId = this.debugger.addDebuggerListener(e -> {
            DebuggedEnvironment debuggedEnvironment = e.getEnvironment();


            String templateName = e.getName();
            int line = e.getLine();

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
                    XSourcePosition sourcePosition = XDebuggerUtil.getInstance().createPosition(virtualFile, line);

                    FreemarkerAttachSuspendContext freemarkerAttachSuspendContext = new FreemarkerAttachSuspendContext(debuggedEnvironment, sourcePosition);

                    getSession().positionReached(freemarkerAttachSuspendContext);
                } else {
                    //stopped at an unregistered breakpoint?? or stepping forward??
                    debuggedEnvironment.resume();
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
            freemarkerAttachSuspendContext.getDebuggedEnvironment().resume();
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
        //not supported

        resume(context);
    }

    @Override
    public void startStepInto(@Nullable XSuspendContext context) {
        //not supported

        resume(context);
    }

    @Override
    public void startStepOut(@Nullable XSuspendContext context) {
        //not supported

        resume(context);
    }

    @Override
    public void runToPosition(@NotNull XSourcePosition position, @Nullable XSuspendContext context) {
        //not supported

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
