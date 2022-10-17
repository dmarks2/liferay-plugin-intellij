package de.dm.intellij.liferay.language.sass;

import com.intellij.psi.PsiElement;
import com.intellij.psi.css.CssTerm;
import com.intellij.psi.css.CssUri;
import com.intellij.psi.filters.ElementFilter;
import com.intellij.psi.filters.position.FilterPattern;
import org.jetbrains.annotations.Nullable;

/**
 * Implementation of a FilterPattern to find CSS-Elements referencing a URL
 */
public class CssUrlFilterPattern extends FilterPattern {

    public CssUrlFilterPattern() {
        super(new ElementFilter() {
            @Override
            public boolean isAcceptable(Object element, @Nullable PsiElement context) {
                if (element instanceof PsiElement) {
                    PsiElement psiElement = (PsiElement)element;

                    if (
                            (
                                    //Parsed CSS file
                                    (psiElement.getParent() instanceof CssUri) ||
                                    //Parsed SCSS file
                                    ( (psiElement.getParent() instanceof CssTerm) && ("URI".equals( ((CssTerm)psiElement.getParent()).getTermType().toString() ) ) )
                            )
                        ) {
                        return true;
                    }
                }

                return false;
            }

            @Override
            public boolean isClassAcceptable(Class hintClass) {
                return true;
            }
        });
    }
}
