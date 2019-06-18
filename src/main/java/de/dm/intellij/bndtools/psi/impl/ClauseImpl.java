package de.dm.intellij.bndtools.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.util.PsiTreeUtil;
import de.dm.intellij.bndtools.psi.Attribute;
import de.dm.intellij.bndtools.psi.Clause;
import de.dm.intellij.bndtools.psi.Directive;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.lang.manifest.psi.HeaderValuePart;

import java.util.List;

public class ClauseImpl extends ASTWrapperPsiElement implements Clause {

    public ClauseImpl(ASTNode node) {
        super(node);
    }

    @Nullable
    @Override
    public Attribute getAttribute(@NotNull String name) {
        for (Attribute child = PsiTreeUtil.findChildOfType(this, Attribute.class); child != null;
             child = PsiTreeUtil.getNextSiblingOfType(child, Attribute.class)) {

            if (name.equals(child.getName())) {
                return child;
            }
        }

        return null;
    }

    @NotNull
    @Override
    public List<Attribute> getAttributes() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, Attribute.class);
    }

    @Override
    public Directive getDirective(@NotNull String name) {
        for (Directive child = PsiTreeUtil.findChildOfType(this, Directive.class); child != null;
             child = PsiTreeUtil.getNextSiblingOfType(child, Directive.class)) {

            if (name.equals(child.getName())) {
                return child;
            }
        }

        return null;
    }

    @NotNull
    @Override
    public List<Directive> getDirectives() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, Directive.class);
    }

    @NotNull
    @Override
    public String getUnwrappedText() {
        String str = getText().replaceAll("(?s)\\s*\n\\s*", "");

        return str.trim();
    }

    @Override
    public HeaderValuePart getValue() {
        return findChildByClass(HeaderValuePart.class);
    }

    @Override
    public String toString() {
        return "Clause";
    }

}