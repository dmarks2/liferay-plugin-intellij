package de.dm.intellij.liferay.language.freemarker.runner;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.ExecutionResult;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.filters.TextConsoleBuilder;
import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.runners.ProgramRunner;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.project.Project;
import com.intellij.xdebugger.DefaultDebugProcessHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FreemarkerAttachRunProfileState implements RunProfileState {

    private final FreemarkerAttachDebugConfiguration freemarkerAttachDebugConfiguration;

    public FreemarkerAttachRunProfileState(FreemarkerAttachDebugConfiguration freemarkerAttachDebugConfiguration) {
        this.freemarkerAttachDebugConfiguration = freemarkerAttachDebugConfiguration;
    }


    @Nullable
    @Override
    public ExecutionResult execute(Executor executor, @NotNull ProgramRunner runner) throws ExecutionException {
        ProcessHandler processHandler = new DefaultDebugProcessHandler();

        Project project = freemarkerAttachDebugConfiguration.getProject();
        final TextConsoleBuilder builder = TextConsoleBuilderFactory.getInstance().createBuilder(project);
        ConsoleView consoleView = builder.getConsole();

        return new FreemarkerAttachExecutionResult(consoleView, processHandler, freemarkerAttachDebugConfiguration);
    }
}
