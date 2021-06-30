package de.dm.intellij.liferay.language.sass;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.css.impl.CssElementTypes;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

public class LiferayFrontendTokenDefinitionCssVariableReferenceProvider extends PsiReferenceProvider {

    @Override
    public PsiReference @NotNull [] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
        IElementType elementType = element.getNode().getElementType();

        if (element.getParent().getNode().getElementType() == CssElementTypes.CSS_TERM) {
            if (elementType == CssElementTypes.CSS_IDENT) {
                return new PsiReference[]{new LiferayFrontendTokenDefinitionVariableReference(element)};
            }
        }

        return PsiReference.EMPTY_ARRAY;
    }
}
