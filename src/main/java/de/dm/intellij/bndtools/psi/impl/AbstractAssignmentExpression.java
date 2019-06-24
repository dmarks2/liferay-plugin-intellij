package de.dm.intellij.bndtools.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import de.dm.intellij.bndtools.psi.AssignmentExpression;
import de.dm.intellij.bndtools.psi.BndHeaderValuePart;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractAssignmentExpression extends ASTWrapperPsiElement implements AssignmentExpression {

    public AbstractAssignmentExpression(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public String getName() {
        BndHeaderValuePart namePsi = getNameElement();

        String result = (namePsi != null) ? namePsi.getUnwrappedText() : null;

        if (result != null) {
            return result;
        }

        return "<unnamed>";
    }

    @Override
    public BndHeaderValuePart getNameElement() {
        return PsiTreeUtil.getChildOfType(this, BndHeaderValuePart.class);
    }

    @Override
    public String getValue() {
        BndHeaderValuePart valuePsi = getValueElement();

        String result = (valuePsi != null) ? valuePsi.getUnwrappedText() : null;

        if (result != null) {
            return result;
        }

        return "";
    }

    @Override
    public BndHeaderValuePart getValueElement() {
        BndHeaderValuePart namePsi = getNameElement();

        if (namePsi != null) {
            return PsiTreeUtil.getNextSiblingOfType(namePsi, BndHeaderValuePart.class);
        }

        return null;
    }

    @Override
    public PsiElement setName(@NotNull String name) throws IncorrectOperationException {
        throw new IncorrectOperationException();
    }

}
