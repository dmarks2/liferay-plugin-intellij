package de.dm.intellij.bndtools.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.tree.LeafElement;
import com.intellij.util.IncorrectOperationException;
import de.dm.intellij.bndtools.psi.BndHeader;
import de.dm.intellij.bndtools.psi.BndToken;
import de.dm.intellij.bndtools.psi.BndTokenType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.lang.manifest.psi.impl.HeaderImpl;

public class BndHeaderImpl extends HeaderImpl implements BndHeader {

    public BndHeaderImpl(@NotNull ASTNode node) {
        super(node);
    }

    @NotNull
    @Override
    public String getName() {
        return getBndNameElement().getText();
    }

    @Override
    public PsiElement setName(@NotNull String name) throws IncorrectOperationException {
        ((LeafElement)getBndNameElement().getNode()).replaceWithText(name);
        return this;
    }

    @NotNull
    @Override
    public BndToken getBndNameElement() {
        BndToken token = (BndToken)getNode().findChildByType(BndTokenType.HEADER_NAME);

        return token;
    }

}
