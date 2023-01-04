package de.dm.intellij.liferay.language.poshi.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import de.dm.intellij.liferay.language.poshi.PoshiLanguage;
import de.dm.intellij.liferay.language.poshi.PoshiFileType;
import org.jetbrains.annotations.NotNull;

public class PoshiFile extends PsiFileBase {

    public PoshiFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, PoshiLanguage.INSTANCE);
    }

    @Override
    public @NotNull FileType getFileType() {
        return PoshiFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "Poshi File";
    }
}
