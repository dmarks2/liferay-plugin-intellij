package de.dm.intellij.bndtools;

import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.vfs.VirtualFile;
import de.dm.intellij.liferay.util.Icons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

public class BndFileType extends LanguageFileType {

    public static BndFileType INSTANCE = new BndFileType();

    private BndFileType() {
        super(BndLanguage.getInstance());
    }

    @NotNull
    public String getName() {
        return "bnd";
    }

    @NotNull
    public String getDescription() {
        return "BND file";
    }

    @NotNull
    public String getDefaultExtension() {
        return "bnd";
    }

    @Nullable
    public Icon getIcon() {
        return Icons.BND_ICON;
    }

    public boolean isReadOnly() {
        return false;
    }

    @Nullable
    public String getCharset(@NotNull VirtualFile file, @NotNull byte[] content) {
        return null;
    }

}
