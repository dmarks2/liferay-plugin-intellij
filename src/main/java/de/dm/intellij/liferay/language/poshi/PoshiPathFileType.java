package de.dm.intellij.liferay.language.poshi;

import com.intellij.ide.highlighter.HtmlFileType;
import com.intellij.lang.html.HTMLLanguage;
import de.dm.intellij.liferay.util.Icons;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class PoshiPathFileType extends HtmlFileType {

    public static final PoshiPathFileType INSTANCE = new PoshiPathFileType();

    protected PoshiPathFileType() {
        super(HTMLLanguage.INSTANCE);
    }

    @Override
    public @NonNls @NotNull String getName() {
        return "Poshi Path File";
    }

    @Override
    public @NotNull String getDescription() {
        return "Poshi path file";
    }

    @Override
    public @NotNull String getDefaultExtension() {
        return "path";
    }

    @Override
    public Icon getIcon() {
        return Icons.LIFERAY_ICON;
    }
}
