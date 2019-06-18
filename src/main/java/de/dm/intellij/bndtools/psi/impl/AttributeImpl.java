package de.dm.intellij.bndtools.psi.impl;

import com.intellij.lang.ASTNode;
import de.dm.intellij.bndtools.psi.Attribute;
import org.jetbrains.annotations.NotNull;

public class AttributeImpl extends AbstractAssignmentExpression implements Attribute {

    public AttributeImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public String toString() {
        return "Attribute:" + getName();
    }

}