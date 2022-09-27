package de.dm.intellij.liferay.language.groovy;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.application.BaseJavaApplicationCommandLineState;
import com.intellij.execution.configurations.JavaParameters;
import com.intellij.execution.configurations.ParametersList;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.util.JavaParametersUtil;
import com.intellij.util.PathsList;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class LiferayServerGroovyCommandLineState extends BaseJavaApplicationCommandLineState<LiferayServerGroovyConfiguration> {

    public LiferayServerGroovyCommandLineState(ExecutionEnvironment environment, @NotNull LiferayServerGroovyConfiguration configuration) {
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

        ParametersList programParametersList = javaParameters.getProgramParametersList();

        programParametersList.add(getConfiguration().getHost());
        programParametersList.add(String.valueOf(getConfiguration().getPort()));
        programParametersList.add(getConfiguration().getScriptName());

        javaParameters.setJdk(JavaParametersUtil.createProjectJdk(getConfiguration().getProject(), null));

        javaParameters.setMainClass("de.dm.intellij.liferay.scripts.ScriptExecutor");

        return javaParameters;
    }
}
