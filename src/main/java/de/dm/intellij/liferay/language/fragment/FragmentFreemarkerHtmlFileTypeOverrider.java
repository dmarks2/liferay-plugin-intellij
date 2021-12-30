package de.dm.intellij.liferay.language.fragment;

import com.intellij.freemarker.psi.files.FtlFileType;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.impl.FileTypeOverrider;
import com.intellij.openapi.vfs.VirtualFile;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FragmentFreemarkerHtmlFileTypeOverrider implements FileTypeOverrider {

    @Override
    public @Nullable FileType getOverriddenFileType(@NotNull VirtualFile virtualFile) {
        if (LiferayFileUtil.isFragmentHtmlFile(virtualFile)) {
            return FtlFileType.INSTANCE;
        }

        return null;
    }
}
