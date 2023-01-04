package de.dm.intellij.liferay.language.poshi;

import com.intellij.openapi.fileTypes.LanguageFileType;
import de.dm.intellij.liferay.util.Icons;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class PoshiFileType extends LanguageFileType {

    public static final PoshiFileType INSTANCE = new PoshiFileType();

    private PoshiFileType() {
        super(PoshiLanguage.INSTANCE);
    }

    @Override
    public @NonNls @NotNull String getName() {
        return "Poshi File";
    }

    @Override
    public @NotNull String getDescription() {
        return "Poshi language file";
    }

    @Override
    public @NotNull String getDefaultExtension() {
        return "testcase";
    }

    @Override
    public Icon getIcon() {
        return Icons.LIFERAY_ICON;
    }
}
