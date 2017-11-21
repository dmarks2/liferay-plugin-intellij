package de.dm.intellij.liferay.language.sass;

import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.ElementManipulators;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.css.CssTerm;
import com.intellij.psi.css.CssUri;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReference;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceSet;
import com.intellij.util.ProcessingContext;
import de.dm.intellij.liferay.theme.LiferayLookAndFeelXmlParser;
import de.dm.intellij.liferay.util.ThemeSettingsPathFileReference;
import org.jetbrains.annotations.NotNull;

/**
 * Provides References for Liferay Sass placeholder variables (like @theme_image_path@)
 */
public class LiferaySassPlaceholderReferenceProvider extends PsiReferenceProvider {

    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
        if (
                (
                        //Parsed CSS file
                        (element.getParent() instanceof CssUri) ||
                        //Parsed SCSS file
                        ( (element.getParent() instanceof CssTerm) && ("URI".equals( ((CssTerm)element.getParent()).getTermType().toString() ) ) )
                ) &&
                (element.getText() != null) ) {
            String text = StringUtil.unquoteString(element.getText());
            if (text.contains("@theme_image_path@")) {
                final TextRange textRange = ElementManipulators.getValueTextRange(element);

                FileReferenceSet set = new FileReferenceSet(text, element, textRange.getStartOffset(), null, true) {
                    @Override
                    public FileReference createFileReference(TextRange range, int index, String text) {
                        return new ThemeSettingsPathFileReference(this, range, index, text, "@theme_image_path@", LiferayLookAndFeelXmlParser.IMAGES_PATH);
                    }
                };

                return set.getAllReferences();
            }
        }
        return new PsiReference[0];
    }

}
