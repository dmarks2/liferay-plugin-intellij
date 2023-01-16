package de.dm.intellij.liferay.language.poshi.runner;

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.GuiUtils;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class PoshiTestcaseConfigurationSettingsEditor extends SettingsEditor<PoshiTestcaseConfiguration> {

    private final JTextField testNameField;
    private final TextFieldWithBrowseButton scriptNameField;

    public PoshiTestcaseConfigurationSettingsEditor(Project project) {
        this.scriptNameField = new TextFieldWithBrowseButton();

        this.scriptNameField.addBrowseFolderListener(
                "Script File", "Choose the script file location (*.testcase)", project,
                FileChooserDescriptorFactory.createSingleFileDescriptor("testcase")
        );

        this.testNameField = GuiUtils.createUndoableTextField();
    }

    @Override
    protected void resetEditorFrom(@NotNull PoshiTestcaseConfiguration configuration) {
        scriptNameField.setText(StringUtil.notNullize(configuration.getScriptName(), ""));
        testNameField.setText(StringUtil.notNullize(configuration.getTestName(), ""));
    }

    @Override
    protected void applyEditorTo(@NotNull PoshiTestcaseConfiguration configuration) {
        configuration.setScriptName(scriptNameField.getText());
        configuration.setTestName(testNameField.getText());
    }

    @Override
    protected @NotNull JComponent createEditor() {
        return FormBuilder
                .createFormBuilder()
                .addLabeledComponent("&Script file:", scriptNameField)
                .addLabeledComponent("&Test name:", testNameField)
                .getPanel();
    }
}
