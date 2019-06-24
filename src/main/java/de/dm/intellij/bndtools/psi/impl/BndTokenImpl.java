package de.dm.intellij.bndtools.psi.impl;

import com.intellij.psi.impl.source.tree.LeafPsiElement;
import de.dm.intellij.bndtools.psi.BndToken;
import de.dm.intellij.bndtools.psi.BndTokenType;
import org.jetbrains.annotations.NotNull;

public class BndTokenImpl extends LeafPsiElement implements BndToken {

    public BndTokenImpl(@NotNull BndTokenType type, CharSequence text) {
        super(type, text);
    }

    @Override
    public BndTokenType getTokenType() {
        return (BndTokenType)getElementType();
    }

    @Override
    public String toString() {
        return "BndToken:" + getTokenType();
    }

}
