package de.dm.intellij.liferay.language.sass;

import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;
import org.jetbrains.annotations.NotNull;

/**
 * Reference Contributor to resolve Liferay specific placeholders in CSS / SCSS files.
 *
 * Currently replaces @theme_image_path@ by the real path to the images
 */
public class LiferaySassPlaceholderReferenceContributor extends PsiReferenceContributor {

    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(
                PlatformPatterns.psiElement(PsiElement.class).and(new CssUrlFilterPattern()),
                new LiferaySassPlaceholderReferenceProvider()
        );
    }
}
