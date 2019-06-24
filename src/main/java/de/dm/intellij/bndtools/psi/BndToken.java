package de.dm.intellij.bndtools.psi;

import com.intellij.psi.PsiElement;

public interface BndToken extends PsiElement {

    BndTokenType getTokenType();

}
