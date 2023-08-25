package de.dm.intellij.liferay.language.poshi.runner;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.application.BaseJavaApplicationCommandLineState;
import com.intellij.execution.configurations.JavaParameters;
import com.intellij.execution.configurations.ParametersList;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.util.JavaParametersUtil;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.util.PathsList;
import de.dm.intellij.liferay.language.groovy.LiferayServerGroovyConsoleLinkFilter;
import de.dm.intellij.liferay.language.poshi.constants.PoshiConstants;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class PoshiTestcaseCommandLineState extends BaseJavaApplicationCommandLineState<PoshiTestcaseConfiguration> {

    public PoshiTestcaseCommandLineState(ExecutionEnvironment environment, @NotNull PoshiTestcaseConfiguration configuration) {
        super(environment, configuration);

        addConsoleFilters(new LiferayServerGroovyConsoleLinkFilter(environment.getProject(), getConfiguration().getScriptName()));
    }

    @Override
    protected JavaParameters createJavaParameters() throws ExecutionException {
        JavaParameters javaParameters = new JavaParameters();

        String currentClassPath = '/' + this.getClass().getName().replace('.','/') + ".class";

        URL resource = this.getClass().getResource(currentClassPath);

        String file = resource.getFile();

        String baseName = file.substring(0, file.indexOf(currentClassPath));

        if (baseName.endsWith("!")) {
            baseName = baseName.substring(0, baseName.length() -1);
        }

        if (baseName.startsWith("file:/")) {
            baseName = baseName.substring(6);
        }

        baseName = URLDecoder.decode(baseName, StandardCharsets.UTF_8);

        PathsList classPath = javaParameters.getClassPath();

        classPath.add(baseName);

        for (String poshiRuntimeLibrary : PoshiConstants.POSHI_RUNTIME_LIBRARIES) {
            addClassPath(classPath, poshiRuntimeLibrary);
        }

        ParametersList programParametersList = javaParameters.getProgramParametersList();

        programParametersList.add(getConfiguration().getScriptName());
        programParametersList.add(getConfiguration().getTestName());

        javaParameters.setJdk(JavaParametersUtil.createProjectJdk(getConfiguration().getProject(), null));

        javaParameters.setMainClass("de.dm.intellij.liferay.language.poshi.scripts.PoshiStandaloneRunner");

        File workingDirectory = new File(getConfiguration().getScriptName());
        workingDirectory = workingDirectory.getParentFile();
        workingDirectory = workingDirectory.getParentFile();

        javaParameters.setWorkingDirectory(workingDirectory);

        return javaParameters;
    }

    private void addClassPath(PathsList classPath, String source) {
        Properties properties = System.getProperties();

        File temp = new File(properties.getProperty("user.home"), ".liferay-plugin-intellij");

        File targetFile = new File(temp, source);

        classPath.add(targetFile);
    }

    public static void copyLib(String source) {
        ClassLoader classLoader = PoshiTestcaseCommandLineState.class.getClassLoader();

        Properties properties = System.getProperties();

        File temp = new File(properties.getProperty("user.home"), ".liferay-plugin-intellij");

        temp.mkdirs();

        try (InputStream inputStream = classLoader.getResourceAsStream("lib/" + source)) {
            if (inputStream != null) {
                File targetFile = new File(temp, source);

                try (FileOutputStream outputStream = new FileOutputStream(targetFile)) {
                    FileUtil.copy(inputStream, outputStream);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
