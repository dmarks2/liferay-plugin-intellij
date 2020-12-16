package de.dm.intellij.liferay.language.freemarker.runner;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.project.Project;
import icons.FreemarkerIcons;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.lang.reflect.Field;

public class FreemarkerAttachDebugConfigurationType implements ConfigurationType {

    private static final FreemarkerAttachDebugConfigurationType INSTANCE = new FreemarkerAttachDebugConfigurationType();
    private static final ConfigurationFactory FACTORY = new ConfigurationFactory(INSTANCE) {

        @NotNull
        @Override
        public String getId() {
            return "";
        }

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
        try {
            Field freemarker_icon = FreemarkerIcons.class.getDeclaredField("Freemarker_icon");
            return (Icon) freemarker_icon.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            try {
                Field freemarkerIcon = FreemarkerIcons.class.getDeclaredField("FreemarkerIcon");
                return (Icon) freemarkerIcon.get(null);
            } catch (NoSuchFieldException | IllegalAccessException e2) {
                return AllIcons.RunConfigurations.RemoteDebug;
            }
        }
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
