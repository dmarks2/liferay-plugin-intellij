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
    private final JTextField passwordField;

    private final JTextArea argsArea = new JTextArea();

    public FreemarkerAttachDebugConfigurationSettingsEditor() {
        this.hostField = GuiUtils.createUndoableTextField();
        this.portField = new PortField(7011, 1024);
        this.passwordField = GuiUtils.createUndoableTextField();

        this.portField.addChangeListener(l -> updateArgsText());
        this.passwordField.getDocument().addDocumentListener(new DocumentAdapter() {
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
        if ( (passwordField.getText() != null) && (passwordField.getText().length() > 0)) {
            text = text + "-Dfreemarker.debug.password=" + passwordField.getText() + " ";
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
        passwordField.setText(configuration.getPassword());
    }

    @Override
    protected void applyEditorTo(@NotNull FreemarkerAttachDebugConfiguration configuration) {
        configuration.setHost(hostField.getText());
        configuration.setPort(portField.getNumber());
        configuration.setPassword(passwordField.getText());
    }

    @NotNull
    @Override
    protected JComponent createEditor() {
        return FormBuilder
            .createFormBuilder()
            .addLabeledComponent("&Host:", hostField)
            .addLabeledComponent("Port:", portField)
            .addLabeledComponent("&Password:", passwordField)
            .addLabeledComponent("&Command line arguments for remote JVM:", argsArea)
            .getPanel();
    }
}
