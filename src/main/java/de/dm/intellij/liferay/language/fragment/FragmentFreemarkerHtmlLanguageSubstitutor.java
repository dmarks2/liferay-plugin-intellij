package de.dm.intellij.liferay.language.fragment;

import com.intellij.freemarker.lexer.FtlDirectiveStyle;
import com.intellij.lang.Language;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.LanguageSubstitutor;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FragmentFreemarkerHtmlLanguageSubstitutor extends LanguageSubstitutor {

    @Override
    public @Nullable Language getLanguage(@NotNull VirtualFile virtualFile, @NotNull Project project) {
        if (LiferayFileUtil.isFragmentHtmlFile(virtualFile)) {
            return FtlDirectiveStyle.SQUARE.getLanguage();
        }

        return null;
    }
}
