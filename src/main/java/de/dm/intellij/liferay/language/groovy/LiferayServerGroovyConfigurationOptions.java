package de.dm.intellij.liferay.language.groovy;

import com.intellij.execution.configurations.LocatableRunConfigurationOptions;
import com.intellij.openapi.components.StoredProperty;

public class LiferayServerGroovyConfigurationOptions extends LocatableRunConfigurationOptions {

    private final StoredProperty<String> scriptName = string("").provideDelegate(this, "scriptName");
    private final StoredProperty<String> host = string("localhost").provideDelegate(this, "host");
    private final StoredProperty<Integer> port = property(11311).provideDelegate(this, "port");

    public String getHost() {
        return this.host.getValue(this);
    }

    public void setHost(String host) {
        this.host.setValue(this, host);
    }

    public Integer getPort() {
        return this.port.getValue(this);
    }

    public void setPort(Integer port) {
        this.port.setValue(this, port);
    }

    public String getScriptName() {
        return this.scriptName.getValue(this);
    }

    public void setScriptName(String scriptName) {
        this.scriptName.setValue(this, scriptName);
    }
}
