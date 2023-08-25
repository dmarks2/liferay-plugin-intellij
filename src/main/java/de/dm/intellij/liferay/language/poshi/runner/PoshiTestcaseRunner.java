package de.dm.intellij.liferay.language.poshi.runner;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.RunProfile;
import com.intellij.execution.executors.DefaultRunExecutor;
import com.intellij.execution.impl.DefaultJavaProgramRunner;
import com.intellij.execution.runners.ExecutionEnvironment;
import de.dm.intellij.liferay.language.poshi.constants.PoshiConstants;
import org.jetbrains.annotations.NotNull;

public class PoshiTestcaseRunner extends DefaultJavaProgramRunner {

    @Override
    public @NotNull String getRunnerId() {
        return "PoshiTestcaseRunner";
    }

    @Override
    public boolean canRun(@NotNull String executorId, @NotNull RunProfile profile) {
        return DefaultRunExecutor.EXECUTOR_ID.equals(executorId) && profile instanceof PoshiTestcaseConfiguration;
    }

    @Override
    public void execute(@NotNull ExecutionEnvironment environment) throws ExecutionException {
        for (String poshiRuntimeLibrary : PoshiConstants.POSHI_RUNTIME_LIBRARIES) {
            PoshiTestcaseCommandLineState.copyLib(poshiRuntimeLibrary);
        }

        super.execute(environment);
    }
}
