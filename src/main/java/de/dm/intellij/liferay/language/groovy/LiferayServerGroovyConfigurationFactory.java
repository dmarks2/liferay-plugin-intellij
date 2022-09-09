package de.dm.intellij.liferay.language.groovy;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.components.BaseState;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LiferayServerGroovyConfigurationFactory extends ConfigurationFactory {

    protected LiferayServerGroovyConfigurationFactory(@NotNull ConfigurationType type) {
        super(type);
    }

    @NotNull
    @Override
    public String getId() {
        return LiferayServerGroovyConfigurationType.ID;
    }

    @NotNull
    @Override
    public RunConfiguration createTemplateConfiguration(@NotNull Project project) {
        return new LiferayServerGroovyConfiguration(project, this, "");
    }

    @Override
    public @Nullable Class<? extends BaseState> getOptionsClass() {
        return LiferayServerGroovyConfigurationOptions.class;
    }
}
