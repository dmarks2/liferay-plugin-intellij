package de.dm.intellij.bndtools.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import de.dm.intellij.bndtools.BndLanguage;
import de.dm.intellij.bndtools.psi.impl.AttributeImpl;
import de.dm.intellij.bndtools.psi.impl.BndHeaderImpl;
import de.dm.intellij.bndtools.psi.impl.BndHeaderValuePartImpl;
import de.dm.intellij.bndtools.psi.impl.BndSectionImpl;
import de.dm.intellij.bndtools.psi.impl.ClauseImpl;
import de.dm.intellij.bndtools.psi.impl.DirectiveImpl;

public abstract class BndElementType extends IElementType {

    public static final IElementType SECTION = new BndElementType("SECTION") {
        @Override
        public PsiElement createPsi(ASTNode node) {
            return new BndSectionImpl(node);
        }
    };

    public static final IElementType HEADER = new BndElementType("HEADER") {
        @Override
        public PsiElement createPsi(ASTNode node) {
            return new BndHeaderImpl(node);
        }
    };

    public static final IElementType HEADER_VALUE_PART = new BndElementType("HEADER_VALUE_PART") {
        @Override
        public PsiElement createPsi(ASTNode node) {
            return new BndHeaderValuePartImpl(node);
        }
    };

    public static final IElementType ATTRIBUTE = new BndElementType("ATTRIBUTE") {

        @Override
        public PsiElement createPsi(ASTNode node) {
            return new AttributeImpl(node);
        }

    };

    public static final IElementType CLAUSE = new BndElementType("CLAUSE") {

        @Override
        public PsiElement createPsi(ASTNode node) {
            return new ClauseImpl(node);
        }

    };

    public static final IElementType DIRECTIVE = new BndElementType("DIRECTIVE") {

        @Override
        public PsiElement createPsi(ASTNode node) {
            return new DirectiveImpl(node);
        }

    };

    private BndElementType(String name) {
        super(name, BndLanguage.getInstance());
    }

    public abstract PsiElement createPsi(ASTNode node);

    @Override
    public String toString() {
        return "bnd:" + super.toString();
    }

}