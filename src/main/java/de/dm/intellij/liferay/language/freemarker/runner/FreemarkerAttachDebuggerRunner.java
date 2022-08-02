package de.dm.intellij.liferay.language.freemarker.runner;

import com.intellij.debugger.impl.GenericDebuggerRunner;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.ExecutionResult;
import com.intellij.execution.configurations.RunProfile;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.executors.DefaultDebugExecutor;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.ui.RunContentDescriptor;
import com.intellij.xdebugger.XDebugProcess;
import com.intellij.xdebugger.XDebugProcessStarter;
import com.intellij.xdebugger.XDebugSession;
import com.intellij.xdebugger.XDebuggerManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;

import java.io.IOException;
import java.net.URISyntaxException;

public class FreemarkerAttachDebuggerRunner extends GenericDebuggerRunner {

    @NotNull
    @Override
    public String getRunnerId() {
        return "FreemarkerAttachDebuggerRunner";
    }

    @Override
    public boolean canRun(@NotNull String executorId, @NotNull RunProfile profile) {
        return executorId.equals(DefaultDebugExecutor.EXECUTOR_ID) && profile instanceof FreemarkerAttachDebugConfiguration;
    }

    @Nullable
    @Override
    protected RunContentDescriptor createContentDescriptor(@NotNull RunProfileState state, @NotNull ExecutionEnvironment environment) throws ExecutionException {
        final XDebugSession debugSession = XDebuggerManager.getInstance(environment.getProject()).startSession(
            environment, new XDebugProcessStarter()
            {
                @NotNull
                @Override
                public XDebugProcess start(@NotNull XDebugSession session) throws ExecutionException
                {
                    final ExecutionResult result = state.execute(environment.getExecutor(), FreemarkerAttachDebuggerRunner.this);

                    FreemarkerAttachExecutionResult freemarkerAttachExecutionResult = (FreemarkerAttachExecutionResult)result;

                    try {
                        return new FreemarkerAttachDebugProcess(session, freemarkerAttachExecutionResult);
                    } catch (IOException | JSONException | URISyntaxException e) {
                        throw new ExecutionException(e.getMessage(), e);
                    }
                }
            });
        return debugSession.getRunContentDescriptor();
    }
}
