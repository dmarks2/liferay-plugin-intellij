package de.dm.intellij.liferay.language.tpl;

import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.fileTypes.TemplateLanguageFileType;
import com.intellij.velocity.Icons;
import com.intellij.velocity.psi.VtlLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

public class TplFileType extends LanguageFileType implements TemplateLanguageFileType {
    public static final TplFileType INSTANCE = new TplFileType();

    private TplFileType() {
        super(VtlLanguage.INSTANCE);
    }

    @NotNull
    @NonNls
    public String getName() {
        return "TPL";
    }

    @NotNull
    public String getDescription() {
        return "Liferay layout template";
    }

    @NotNull
    @NonNls
    public String getDefaultExtension() {
        return "tpl";
    }

    @Nullable
    public Icon getIcon() {
        return Icons.VTL_ICON;
    }

}
