package de.dm.intellij.liferay.language.poshi.runner;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import de.dm.intellij.liferay.util.Icons;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class PoshiTestcaseConfigurationType implements ConfigurationType {

    static String ID = "PoshiTestcaseConfiguration";

    @Override
    public @NotNull @Nls(capitalization = Nls.Capitalization.Title) String getDisplayName() {
        return "Poshi Testcase";
    }

    @Override
    public @Nls(capitalization = Nls.Capitalization.Sentence) String getConfigurationTypeDescription() {
        return "Run poshi testcase";
    }

    @Override
    public Icon getIcon() {
        return Icons.LIFERAY_ICON;
    }

    @Override
    public @NotNull @NonNls String getId() {
        return ID;
    }

    @Override
    public ConfigurationFactory[] getConfigurationFactories() {
        return new ConfigurationFactory[] {new PoshiTestcaseConfigurationFactory(this)};
    }
}
