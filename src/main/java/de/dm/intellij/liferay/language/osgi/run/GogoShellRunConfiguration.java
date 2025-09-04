package de.dm.intellij.liferay.language.osgi.run;

import com.intellij.execution.Executor;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.configurations.RunConfigurationBase;
import com.intellij.execution.configurations.RunConfigurationOptions;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.configurations.RuntimeConfigurationError;
import com.intellij.execution.configurations.RuntimeConfigurationException;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import com.intellij.openapi.util.text.StringUtil;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GogoShellRunConfiguration extends RunConfigurationBase<RunConfigurationOptions> {
    
    private String gogoCommand;
    private String host = "localhost";
    private int port = 11311;
    private String parameters;

    protected GogoShellRunConfiguration(Project project, ConfigurationFactory factory, String name) {
        super(project, factory, name);
    }

    @NotNull
    @Override
    public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        return new GogoShellSettingsEditor();
    }

    @Override
    public void checkConfiguration() throws RuntimeConfigurationException {
        if (gogoCommand == null || gogoCommand.trim().isEmpty()) {
            throw new RuntimeConfigurationError("Gogo command is required");
        }
    }

    @Nullable
    @Override
    public RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment environment) {
        return new GogoShellRunProfileState(environment, this);
    }

    @Override
    public void readExternal(@NotNull Element element) throws InvalidDataException {
        super.readExternal(element);

        gogoCommand = element.getAttributeValue("gogoCommand");
        host = element.getAttributeValue("host", "localhost");
        port = Integer.parseInt(element.getAttributeValue("port", "11311"));
        parameters = element.getAttributeValue("parameters", "");
    }

    @Override
    public void writeExternal(@NotNull Element element) throws WriteExternalException {
        super.writeExternal(element);

		if (StringUtil.isNotEmpty(gogoCommand)) {
			element.setAttribute("gogoCommand", gogoCommand);
		}

        element.setAttribute("host", host);
        element.setAttribute("port", String.valueOf(port));

		if (StringUtil.isNotEmpty(parameters)) {
			element.setAttribute("parameters", parameters);
		}
    }

	public String getGogoCommand() {
		return gogoCommand;
	}

	public void setGogoCommand(String gogoCommand) {
		this.gogoCommand = gogoCommand;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
    
    public String getFullCommand() {
        if (parameters == null || parameters.trim().isEmpty()) {
            return gogoCommand;
        }
        return gogoCommand + " " + parameters.trim();
    }
}
