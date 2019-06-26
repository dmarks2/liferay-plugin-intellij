package de.dm.intellij.bndtools.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

public interface BndHeaderValue extends PsiElement {

    /**
     * Returns the unwrapped text without the newlines and extra continuation spaces.
     */
    @NotNull
    String getUnwrappedText();
}
