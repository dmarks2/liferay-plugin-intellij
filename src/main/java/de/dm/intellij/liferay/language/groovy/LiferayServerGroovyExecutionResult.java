package de.dm.intellij.liferay.language.groovy;

import com.intellij.execution.DefaultExecutionResult;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.ui.ExecutionConsole;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LiferayServerGroovyExecutionResult extends DefaultExecutionResult {

    public LiferayServerGroovyExecutionResult(@Nullable ExecutionConsole console, @NotNull ProcessHandler processHandler) {
        super(console, processHandler);
    }
}
