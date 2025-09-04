package de.dm.intellij.liferay.language.osgi.run;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.ConfigurationTypeUtil;
import com.intellij.icons.AllIcons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class GogoShellConfigurationType implements ConfigurationType {
    
    @Override
    public @NotNull String getDisplayName() {
        return "Gogo Shell Command";
    }

    @Override
    public String getConfigurationTypeDescription() {
        return "Execute gogo shell commands via telnet on liferay server";
    }

    @Override
    public Icon getIcon() {
        return AllIcons.Actions.Execute;
    }

    @Override
    public @NotNull String getId() {
        return "GogoShellConfigurationType";
    }

    @Override
    public ConfigurationFactory[] getConfigurationFactories() {
        return new ConfigurationFactory[]{new GogoShellConfigurationFactory(this)};
    }

    public static GogoShellConfigurationType getInstance() {
        return ConfigurationTypeUtil.findConfigurationType(GogoShellConfigurationType.class);
    }
}
