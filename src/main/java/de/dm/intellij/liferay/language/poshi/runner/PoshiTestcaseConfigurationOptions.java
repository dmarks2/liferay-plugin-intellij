package de.dm.intellij.liferay.language.poshi.runner;

import com.intellij.execution.configurations.LocatableRunConfigurationOptions;
import com.intellij.openapi.components.StoredProperty;

public class PoshiTestcaseConfigurationOptions extends LocatableRunConfigurationOptions {

    private final StoredProperty<String> scriptName = string("").provideDelegate(this, "scriptName");
    private final StoredProperty<String> testName = string("").provideDelegate(this, "testName");

    public String getTestName() {
        return this.testName.getValue(this);
    }

    public void setTestName(String testName) {
        this.testName.setValue(this, testName);
    }

    public String getScriptName() {
        return this.scriptName.getValue(this);
    }

    public void setScriptName(String scriptName) {
        this.scriptName.setValue(this, scriptName);
    }
}
