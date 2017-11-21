package de.dm.intellij.liferay.language.freemarker;

import com.intellij.freemarker.psi.FtlType;
import com.intellij.freemarker.psi.files.FtlFile;
import com.intellij.freemarker.psi.variables.FtlLightVariable;
import com.intellij.freemarker.psi.variables.FtlPsiType;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiType;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CustomFtlVariable extends FtlLightVariable {

    private PsiElement navigationElement;

    public CustomFtlVariable(@NotNull String name, @NotNull PsiElement parent, @Nullable FtlType type) {
        super(name, parent, type);
    }

    public CustomFtlVariable(@NotNull String name, @NotNull PsiElement parent, @Nullable FtlType type, PsiElement navigationElement) {
        super(name, parent, type);
        this.navigationElement = navigationElement;
    }

    public CustomFtlVariable(@NotNull String name, @NotNull PsiElement parent, @NotNull String typeText, PsiElement navigationElement) {
        super(name, parent, typeText);
        this.navigationElement = navigationElement;
    }

    @NotNull
    @Override
    public PsiElement getNavigationElement() {
        if (navigationElement != null) {
            return navigationElement;
        }
        return super.getNavigationElement();
    }

}
