package de.dm.intellij.bndtools.psi.impl;

import com.intellij.lang.ASTNode;
import de.dm.intellij.bndtools.psi.Directive;
import org.jetbrains.annotations.NotNull;

public class DirectiveImpl extends AbstractAssignmentExpression implements Directive {

    public DirectiveImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public String toString() {
        return "Directive:" + getName();
    }

}