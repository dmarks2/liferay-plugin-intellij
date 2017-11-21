package de.dm.intellij.liferay.util;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElementResolveResult;
import com.intellij.psi.PsiFile;
import com.intellij.psi.ResolveResult;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReference;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceSet;
import org.jetbrains.annotations.NotNull;

public class ThemeSettingsPathFileReference extends FileReference {

    private String placeholder;
    private String themeSetting;

    public ThemeSettingsPathFileReference(@NotNull FileReferenceSet fileReferenceSet, TextRange range, int index, String text, @NotNull String placeholder, @NotNull String themeSetting) {
        super(fileReferenceSet, range, index, text);
        this.placeholder = placeholder;
        this.themeSetting = themeSetting;
    }

    @NotNull
    @Override
    protected ResolveResult[] innerResolve(boolean caseSensitive, @NotNull PsiFile containingFile) {
        if (placeholder.equals(getText())) {
            PsiFile psiFile = containingFile;
            if (psiFile.getOriginalFile() != null) {
                psiFile = psiFile.getOriginalFile();
            }
            final Module module = ModuleUtil.findModuleForPsiElement(psiFile);
            if (module != null) {
                VirtualFile virtualFile = LiferayFileUtil.getThemeSettingsDirectory(module, themeSetting);
                if (virtualFile != null) {
                    PsiDirectory psiDirectory = psiFile.getManager().findDirectory(virtualFile);
                    if (psiDirectory != null) {
                        return new ResolveResult[]{new PsiElementResolveResult(psiDirectory)};
                    }
                }
            }
        }
        return super.innerResolve(caseSensitive, containingFile);
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        return super.getVariants();
    }

}
