package de.dm.intellij.bndtools.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import de.dm.intellij.bndtools.psi.AssignmentExpression;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.lang.manifest.psi.HeaderValuePart;

public abstract class AbstractAssignmentExpression extends ASTWrapperPsiElement implements AssignmentExpression {

    public AbstractAssignmentExpression(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public String getName() {
        HeaderValuePart namePsi = getNameElement();

        String result = (namePsi != null) ? namePsi.getUnwrappedText() : null;

        if (result != null) {
            return result;
        }

        return "<unnamed>";
    }

    @Override
    public HeaderValuePart getNameElement() {
        return PsiTreeUtil.getChildOfType(this, HeaderValuePart.class);
    }

    @Override
    public String getValue() {
        HeaderValuePart valuePsi = getValueElement();

        String result = (valuePsi != null) ? valuePsi.getUnwrappedText() : null;

        if (result != null) {
            return result;
        }

        return "";
    }

    @Override
    public HeaderValuePart getValueElement() {
        HeaderValuePart namePsi = getNameElement();

        if (namePsi != null) {
            return PsiTreeUtil.getNextSiblingOfType(namePsi, HeaderValuePart.class);
        }

        return null;
    }

    @Override
    public PsiElement setName(@NotNull String name) throws IncorrectOperationException {
        throw new IncorrectOperationException();
    }

}
