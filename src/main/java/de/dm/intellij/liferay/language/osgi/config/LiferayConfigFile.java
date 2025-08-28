package de.dm.intellij.liferay.language.osgi.config;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;

public class LiferayConfigFile extends PsiFileBase {
    public LiferayConfigFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, LiferayConfigLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return LiferayConfigFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "Liferay Config File";
    }
}
