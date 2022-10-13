package de.dm.intellij.liferay.language.resourcebundle;

import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;
import com.intellij.psi.filters.position.FilterPattern;
import org.jetbrains.annotations.NotNull;

public class ResourceBundleReferenceContributor extends PsiReferenceContributor {
    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(
                PlatformPatterns.psiElement(PsiElement.class).and(new FilterPattern(new ResourceBundleReferenceElementFilter())),
                new ResourceBundleReferenceProvider()
        );
    }
}
