package de.dm.intellij.bndtools.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import de.dm.intellij.bndtools.psi.BndSection;

public class BndSectionImpl extends ASTWrapperPsiElement implements BndSection {

    public BndSectionImpl(ASTNode node) {
        super(node);
    }

    @Override
    public String toString() {
        return "BndSection";
    }
}
