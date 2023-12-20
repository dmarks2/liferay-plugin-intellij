package de.dm.intellij.liferay.language.poshi.runner;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.components.BaseState;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PoshiTestcaseConfigurationFactory extends ConfigurationFactory {

    protected PoshiTestcaseConfigurationFactory(@NotNull ConfigurationType type) {
        super(type);
    }

    @NotNull
    @Override
    public String getId() {
        return PoshiTestcaseConfigurationType.ID;
    }

    @NotNull
    @Override
    public RunConfiguration createTemplateConfiguration(@NotNull Project project) {
        return new PoshiTestcaseConfiguration(project, this, "");
    }

    @Override
    public @Nullable Class<? extends BaseState> getOptionsClass() {
        return PoshiTestcaseConfigurationOptions.class;
    }
}
