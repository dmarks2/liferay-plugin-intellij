package de.dm.intellij.liferay.language.freemarker.runner;

import com.intellij.execution.DefaultExecutionResult;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.ui.ExecutionConsole;

public class FreemarkerAttachExecutionResult extends DefaultExecutionResult {

    private final FreemarkerAttachDebugConfiguration freemarkerAttachDebugConfiguration;

    public FreemarkerAttachExecutionResult(ExecutionConsole executionConsole, ProcessHandler processHandler, FreemarkerAttachDebugConfiguration freemarkerAttachDebugConfiguration) {
        super(executionConsole, processHandler);

        this.freemarkerAttachDebugConfiguration = freemarkerAttachDebugConfiguration;
    }

    public FreemarkerAttachDebugConfiguration getFreemarkerAttachDebugConfiguration() {
        return freemarkerAttachDebugConfiguration;
    }
}
