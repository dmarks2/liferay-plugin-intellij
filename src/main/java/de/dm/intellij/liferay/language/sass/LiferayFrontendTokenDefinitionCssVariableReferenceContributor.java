package de.dm.intellij.liferay.language.sass;

import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;
import com.intellij.psi.css.impl.CssElementTypes;
import org.jetbrains.annotations.NotNull;

public class LiferayFrontendTokenDefinitionCssVariableReferenceContributor extends PsiReferenceContributor {

    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(
                PlatformPatterns.psiElement(CssElementTypes.CSS_IDENT).withParent(PlatformPatterns.psiElement(CssElementTypes.CSS_TERM)),
                new LiferayFrontendTokenDefinitionCssVariableReferenceProvider()
        );

    }
}
