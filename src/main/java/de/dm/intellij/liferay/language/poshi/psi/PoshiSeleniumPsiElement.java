package de.dm.intellij.liferay.language.poshi.psi;

import com.intellij.lang.Language;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import com.intellij.psi.ResolveState;
import com.intellij.psi.impl.FakePsiElement;
import com.intellij.psi.scope.PsiScopeProcessor;
import de.dm.intellij.liferay.language.poshi.PoshiLanguage;
import de.dm.intellij.liferay.language.poshi.constants.PoshiConstants;
import org.jetbrains.annotations.NotNull;

public class PoshiSeleniumPsiElement extends FakePsiElement {

    private final Project project;

    public PoshiSeleniumPsiElement(Project project) {
        this.project = project;
    }

    @Override
    public PsiElement getParent() {
        return null;
    }

    @Override
    public @NotNull Project getProject() {
        return project;
    }

    @Override
    public PsiManager getManager() {
        return PsiManager.getInstance(getProject());
    }

    @Override
    public @NotNull Language getLanguage() {
        return PoshiLanguage.INSTANCE;
    }

    @Override
    public String getName() {
        return PoshiConstants.SELENIUM_KEYWORD;
    }

    @Override
    public boolean processDeclarations(@NotNull PsiScopeProcessor processor, @NotNull ResolveState state, PsiElement lastParent, @NotNull PsiElement place) {
        return super.processDeclarations(processor, state, lastParent, place);
    }
}
