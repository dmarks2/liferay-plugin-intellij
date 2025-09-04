package de.dm.intellij.liferay.language.osgi.run;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.ui.JBColor;
import com.intellij.ui.SideBorder;
import com.intellij.ui.components.JBScrollPane;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class GogoShellSettingsEditor extends SettingsEditor<GogoShellRunConfiguration> {

	private JTextField commandField;
    private JTextField hostField;
    private JTextField portField;
	private JTextArea parametersArea;

	@Override
    protected void resetEditorFrom(@NotNull GogoShellRunConfiguration configuration) {
        commandField.setText(configuration.getGogoCommand());
        hostField.setText(configuration.getHost());
        portField.setText(String.valueOf(configuration.getPort()));
		parametersArea.setText(configuration.getParameters());
    }

    @Override
    protected void applyEditorTo(@NotNull GogoShellRunConfiguration configuration) throws ConfigurationException {
        configuration.setGogoCommand(commandField.getText());
        configuration.setHost(hostField.getText());

		try {
            configuration.setPort(Integer.parseInt(portField.getText()));
        } catch (NumberFormatException e) {
            throw new ConfigurationException("Invalid port number");
        }

		configuration.setParameters(parametersArea.getText());
    }

    @Override
    protected @NotNull JComponent createEditor() {
		JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        commandField = new JTextField();
        hostField = new JTextField();
        portField = new JTextField();
        parametersArea = new JTextArea(3, 30);
        parametersArea.setLineWrap(true);
        parametersArea.setWrapStyleWord(true);
		parametersArea.setEditable(true);
		parametersArea.setBorder(new SideBorder(JBColor.border(), SideBorder.ALL));
		parametersArea.setMinimumSize(parametersArea.getPreferredSize());

		JLabel parametersHintLabel = new JLabel("<html><i>Enter parameters separated by spaces. Use quotes for strings with spaces.</i></html>");
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        panel.add(LabeledComponent.create(commandField, "Gogo command:"), gbc);
        
        gbc.gridy = 1;
        panel.add(LabeledComponent.create(hostField, "Host:"), gbc);
        
        gbc.gridy = 2;
        panel.add(LabeledComponent.create(portField, "Port:"), gbc);
        
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.BOTH; gbc.weighty = 1.0;
        JScrollPane scrollPane = new JBScrollPane(parametersArea);
        panel.add(LabeledComponent.create(scrollPane, "Parameters:"), gbc);
        
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weighty = 0.0;
        panel.add(parametersHintLabel, gbc);
        
        return panel;
    }
}
