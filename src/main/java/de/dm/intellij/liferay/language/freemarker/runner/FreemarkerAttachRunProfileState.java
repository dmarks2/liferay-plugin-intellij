package de.dm.intellij.liferay.language.freemarker.runner;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.ExecutionResult;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.filters.TextConsoleBuilder;
import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.ProgramRunner;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.project.Project;
import com.intellij.xdebugger.DefaultDebugProcessHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FreemarkerAttachRunProfileState implements RunProfileState {

    private ExecutionEnvironment executionEnvironment;
    private FreemarkerAttachDebugConfiguration freemarkerAttachDebugConfiguration;
    private ConsoleView consoleView;

    public FreemarkerAttachRunProfileState(ExecutionEnvironment executionEnvironment, FreemarkerAttachDebugConfiguration freemarkerAttachDebugConfiguration) {
        this.executionEnvironment = executionEnvironment;
        this.freemarkerAttachDebugConfiguration = freemarkerAttachDebugConfiguration;
    }


    @Nullable
    @Override
    public ExecutionResult execute(Executor executor, @NotNull ProgramRunner runner) throws ExecutionException {
        ProcessHandler processHandler = new DefaultDebugProcessHandler();

        Project project = freemarkerAttachDebugConfiguration.getProject();
        final TextConsoleBuilder builder = TextConsoleBuilderFactory.getInstance().createBuilder(project);
        consoleView = builder.getConsole();

        return new FreemarkerAttachExecutionResult(consoleView, processHandler, freemarkerAttachDebugConfiguration);
    }
}
