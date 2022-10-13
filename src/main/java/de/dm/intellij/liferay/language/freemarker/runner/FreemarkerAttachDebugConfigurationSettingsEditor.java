package de.dm.intellij.liferay.language.freemarker.runner;

import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.DocumentAdapter;
import com.intellij.ui.GuiUtils;
import com.intellij.ui.JBColor;
import com.intellij.ui.PortField;
import com.intellij.ui.SideBorder;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class FreemarkerAttachDebugConfigurationSettingsEditor extends SettingsEditor<FreemarkerAttachDebugConfiguration> {

    private final JTextField hostField;
    private final PortField portField;
    private final JTextField secretField;

    private final JTextField liferayURLField;
    private final JTextField liferayUsernameField;
    private final JTextField liferayPasswordField;

    private final JTextArea argsArea = new JTextArea();

    public FreemarkerAttachDebugConfigurationSettingsEditor() {
        this.hostField = GuiUtils.createUndoableTextField();
        this.portField = new PortField(7011, 1024);
        this.secretField = GuiUtils.createUndoableTextField();

        this.liferayURLField = GuiUtils.createUndoableTextField();
        this.liferayUsernameField = GuiUtils.createUndoableTextField();
        this.liferayPasswordField = GuiUtils.createUndoableTextField();

        this.portField.addChangeListener(l -> updateArgsText());
        this.secretField.getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            protected void textChanged(@NotNull DocumentEvent e) {
                updateArgsText();
            }
        });

        argsArea.setLineWrap(true);
        argsArea.setWrapStyleWord(true);
        argsArea.setRows(4);
        argsArea.setEditable(false);
        argsArea.setBorder(new SideBorder(JBColor.border(), SideBorder.ALL));
        argsArea.setMinimumSize(argsArea.getPreferredSize());
        argsArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                argsArea.selectAll();
            }
        });

        updateArgsText();
    }

    private void updateArgsText() {
        String text = "";
        if (portField.getNumber() != 7011) {
            text = text + "-Dfreemarker.debug.port=" + String.valueOf(portField.getNumber()) + " ";
        }
        if ( (secretField.getText() != null) && (secretField.getText().length() > 0)) {
            text = text + "-Dfreemarker.debug.password=" + secretField.getText() + " ";
        }

        try {
            String host = InetAddress.getLocalHost().getHostName();
            text = text + "-Djava.rmi.server.hostname=" + host;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        argsArea.setText(text);
    }

    @Override
    protected void resetEditorFrom(@NotNull FreemarkerAttachDebugConfiguration configuration) {
        hostField.setText(StringUtil.notNullize(configuration.getHost(), "localhost"));
        portField.setNumber(configuration.getPort());
        secretField.setText(configuration.getSecret());
        liferayURLField.setText(configuration.getLiferayURL());
        liferayUsernameField.setText(configuration.getLiferayUsername());
        liferayPasswordField.setText(configuration.getLiferayPassword());
    }

    @Override
    protected void applyEditorTo(@NotNull FreemarkerAttachDebugConfiguration configuration) {
        configuration.setHost(hostField.getText());
        configuration.setPort(portField.getNumber());
        configuration.setSecret(secretField.getText());
        configuration.setLiferayURL(liferayURLField.getText());
        configuration.setLiferayUsername(liferayUsernameField.getText());
        configuration.setLiferayPassword(liferayPasswordField.getText());
    }

    @NotNull
    @Override
    protected JComponent createEditor() {
        return FormBuilder
            .createFormBuilder()
            .addLabeledComponent("&Host:", hostField)
            .addLabeledComponent("Port:", portField)
            .addLabeledComponent("&Secret:", secretField)
            .addLabeledComponent("&Command line arguments for remote JVM:", argsArea, true)
            .addSeparator()
            .addLabeledComponent("Liferay URL:", liferayURLField)
            .addLabeledComponent("Liferay username: ", liferayUsernameField)
            .addLabeledComponent("Liferay password:", liferayPasswordField)
            .getPanel();
    }
}
