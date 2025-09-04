package de.dm.intellij.liferay.language.osgi.run;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class GogoShellConfigurationFactory extends ConfigurationFactory {
    
    protected GogoShellConfigurationFactory(@NotNull ConfigurationType type) {
        super(type);
    }

    @Override
    public @NotNull RunConfiguration createTemplateConfiguration(@NotNull Project project) {
        return new GogoShellRunConfiguration(project, this, "Gogo Shell Command");
    }

    @Override
    public @NotNull String getId() {
        return "GogoShellConfiguration";
    }
}
