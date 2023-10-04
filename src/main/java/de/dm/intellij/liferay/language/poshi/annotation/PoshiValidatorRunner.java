package de.dm.intellij.liferay.language.poshi.annotation;

import com.intellij.execution.CantRunException;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.SimpleJavaParameters;
import com.intellij.execution.process.ProcessOutput;
import com.intellij.execution.util.ExecUtil;
import com.intellij.execution.util.JavaParametersUtil;
import com.intellij.openapi.project.Project;
import com.intellij.util.PathsList;
import de.dm.intellij.liferay.language.poshi.constants.PoshiConstants;
import de.dm.intellij.liferay.language.poshi.runner.PoshiTestcaseCommandLineState;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Properties;

public class PoshiValidatorRunner {

	public static void prepare() {
		for (String poshiRuntimeLibrary : PoshiConstants.POSHI_RUNTIME_LIBRARIES) {
			PoshiTestcaseCommandLineState.copyLib(poshiRuntimeLibrary);
		}
	}

	public static String runPoshiValidator(@NotNull Project project, @NotNull File workingDirectory) throws ExecutionException {
		GeneralCommandLine commandLine = getCommandLine(project, workingDirectory);

		if (PoshiExternalAnnotator.log.isDebugEnabled()) {
			PoshiExternalAnnotator.log.debug("Running command line: " + commandLine);
		}

		ProcessOutput processOutput = ExecUtil.execAndGetOutput(commandLine);

		if (PoshiExternalAnnotator.log.isDebugEnabled()) {
			PoshiExternalAnnotator.log.debug("Exit Code = " + processOutput.getExitCode());
		}

		if (processOutput.getExitCode() != 0) {
			return processOutput.getStdout();
		}

		return null;
	}

	private static GeneralCommandLine getCommandLine(@NotNull Project project, @NotNull File workingDirectory) throws CantRunException {
		SimpleJavaParameters simpleJavaParameters = new SimpleJavaParameters();

		simpleJavaParameters.setJdk(JavaParametersUtil.createProjectJdk(project, null));

		simpleJavaParameters.setWorkingDirectory(workingDirectory);

		simpleJavaParameters.setMainClass("com.liferay.poshi.core.PoshiValidation");

		PathsList classPath = simpleJavaParameters.getClassPath();

		for (String poshiRuntimeLibrary : PoshiConstants.POSHI_RUNTIME_LIBRARIES) {
			addClassPath(classPath, poshiRuntimeLibrary);
		}

		return simpleJavaParameters.toCommandLine();
	}


	private static void addClassPath(PathsList classPath, String source) {
		Properties properties = System.getProperties();

		File temp = new File(properties.getProperty("user.home"), ".liferay-plugin-intellij");

		File targetFile = new File(temp, source);

		classPath.add(targetFile);
	}

}
