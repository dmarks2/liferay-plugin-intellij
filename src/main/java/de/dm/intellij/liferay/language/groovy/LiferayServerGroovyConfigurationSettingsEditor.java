package de.dm.intellij.liferay.language.groovy;

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.GuiUtils;
import com.intellij.ui.PortField;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class LiferayServerGroovyConfigurationSettingsEditor extends SettingsEditor<LiferayServerGroovyConfiguration> {

    private final JTextField hostField;
    private final PortField portField;
    private final TextFieldWithBrowseButton scriptNameField;

    public LiferayServerGroovyConfigurationSettingsEditor(Project project) {
        this.scriptNameField = new TextFieldWithBrowseButton();

		this.scriptNameField.addBrowseFolderListener(
				project,
				FileChooserDescriptorFactory.createSingleFileDescriptor("groovy")
						.withTitle("Script File")
						.withDescription("Choose the script file location (*.groovy)")
		);

        this.hostField = GuiUtils.createUndoableTextField();
        this.portField = new PortField(11311, 1024);
    }

    @Override
    protected void resetEditorFrom(@NotNull LiferayServerGroovyConfiguration configuration) {
        scriptNameField.setText(StringUtil.notNullize(configuration.getScriptName(), ""));
        hostField.setText(StringUtil.notNullize(configuration.getHost(), "localhost"));
        portField.setNumber(configuration.getPort());
    }

    @Override
    protected void applyEditorTo(@NotNull LiferayServerGroovyConfiguration configuration) {
        configuration.setScriptName(scriptNameField.getText());
        configuration.setHost(hostField.getText());
        configuration.setPort(portField.getNumber());
    }

    @Override
    protected @NotNull JComponent createEditor() {
        return FormBuilder
                .createFormBuilder()
                .addLabeledComponent("&Script file:", scriptNameField)
                .addLabeledComponent("&Host:", hostField)
                .addLabeledComponent("Port:", portField)
                .getPanel();
    }
}
