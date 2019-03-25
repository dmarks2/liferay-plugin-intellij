package de.dm.intellij.liferay.language.freemarker.runner;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.project.Project;
import icons.FreemarkerIcons;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class FreemarkerAttachDebugConfigurationType implements ConfigurationType {

    private static final FreemarkerAttachDebugConfigurationType INSTANCE = new FreemarkerAttachDebugConfigurationType();
    private static final ConfigurationFactory FACTORY = new ConfigurationFactory(INSTANCE) {

        @NotNull
        @Override
        public RunConfiguration createTemplateConfiguration(@NotNull Project project) {
            return new FreemarkerAttachDebugConfiguration(project, this, "");
        }
    };

    @NotNull
    @Override
    public String getDisplayName() {
        return "Freemarker Debug";
    }

    @Nls
    @Override
    public String getConfigurationTypeDescription() {
        return "Attach a debugger to Freemarker";
    }

    @Override
    public Icon getIcon() {
        return FreemarkerIcons.Freemarker_icon;
    }

    @NotNull
    @Override
    public String getId() {
        return "FreemarkerAttachDebugConfiguration";
    }

    @Override
    public ConfigurationFactory[] getConfigurationFactories() {
        return new ConfigurationFactory[] {
            FACTORY
        };
    }
}