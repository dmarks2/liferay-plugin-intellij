package de.dm.intellij.liferay.language.freemarker.runner;

import com.intellij.diagnostic.logging.LogConfigurationPanel;
import com.intellij.execution.ExecutionBundle;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.LocatableConfigurationBase;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.configurations.RunConfigurationOptions;
import com.intellij.execution.configurations.RunProfileState;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.RunConfigurationWithSuppressedDefaultRunAction;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.options.SettingsEditorGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import com.intellij.util.xmlb.XmlSerializer;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FreemarkerAttachDebugConfiguration extends LocatableConfigurationBase<RunConfigurationOptions> implements RunConfigurationWithSuppressedDefaultRunAction {

    private String host;
    private int port = 7011;
    private String secret = "secret";

    private String liferayURL = "http://localhost:8080";
    private String liferayUsername = "test@liferay.com";
    private String liferayPassword = "test";

    protected FreemarkerAttachDebugConfiguration(@NotNull Project project, @NotNull ConfigurationFactory factory, @Nullable String name) {
        super(project, factory, name);
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

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @NotNull
    @Override
    public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        SettingsEditorGroup<FreemarkerAttachDebugConfiguration> group = new SettingsEditorGroup<>();

        String title = ExecutionBundle.message("run.configuration.configuration.tab.title");

        group.addEditor(title, new FreemarkerAttachDebugConfigurationSettingsEditor());

        group.addEditor(ExecutionBundle.message("logs.tab.title"), new LogConfigurationPanel<>());

        return group;
    }

    @Nullable
    @Override
    public RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment environment) throws ExecutionException {
        return new FreemarkerAttachRunProfileState(environment, this);
    }

    @Override
    public RunConfiguration clone() {
        FreemarkerAttachDebugConfiguration configuration = (FreemarkerAttachDebugConfiguration)super.clone();
        configuration.host = this.host;
        configuration.port = this.port;
        configuration.secret = this.secret;

        configuration.liferayURL = this.liferayURL;
        configuration.liferayUsername = this.liferayUsername;
        configuration.liferayPassword = this.liferayPassword;
        
        return configuration;
    }

    @Override
    public void readExternal(@NotNull Element element) throws InvalidDataException {
        super.readExternal(element);

        XmlSerializer.deserializeInto(this, element);
    }

    @Override
    public void writeExternal(@NotNull Element element) throws WriteExternalException {
        super.writeExternal(element);

        XmlSerializer.serializeInto(this, element);
    }

    public String getLiferayURL() {
        return liferayURL;
    }

    public void setLiferayURL(String liferayURL) {
        this.liferayURL = liferayURL;
    }

    public String getLiferayUsername() {
        return liferayUsername;
    }

    public void setLiferayUsername(String liferayUsername) {
        this.liferayUsername = liferayUsername;
    }

    public String getLiferayPassword() {
        return liferayPassword;
    }

    public void setLiferayPassword(String liferayPassword) {
        this.liferayPassword = liferayPassword;
    }
}
