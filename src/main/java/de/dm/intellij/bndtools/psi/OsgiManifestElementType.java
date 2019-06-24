package de.dm.intellij.bndtools.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import de.dm.intellij.bndtools.BndLanguage;
import de.dm.intellij.bndtools.psi.impl.AttributeImpl;
import de.dm.intellij.bndtools.psi.impl.BndHeaderImpl;
import de.dm.intellij.bndtools.psi.impl.BndHeaderValuePartImpl;
import de.dm.intellij.bndtools.psi.impl.ClauseImpl;
import de.dm.intellij.bndtools.psi.impl.DirectiveImpl;
import org.jetbrains.lang.manifest.psi.impl.SectionImpl;

public abstract class OsgiManifestElementType extends IElementType {

    public static final IElementType SECTION = new OsgiManifestElementType("SECTION") {
        @Override
        public PsiElement createPsi(ASTNode node) {
            return new SectionImpl(node);
        }
    };

    public static final IElementType HEADER = new OsgiManifestElementType("HEADER") {
        @Override
        public PsiElement createPsi(ASTNode node) {
            return new BndHeaderImpl(node);
        }
    };

    public static final IElementType HEADER_VALUE_PART = new OsgiManifestElementType("HEADER_VALUE_PART") {
        @Override
        public PsiElement createPsi(ASTNode node) {
            return new BndHeaderValuePartImpl(node);
        }
    };

    public static final IElementType ATTRIBUTE = new OsgiManifestElementType("ATTRIBUTE") {

        @Override
        public PsiElement createPsi(ASTNode node) {
            return new AttributeImpl(node);
        }

    };

    public static final IElementType CLAUSE = new OsgiManifestElementType("CLAUSE") {

        @Override
        public PsiElement createPsi(ASTNode node) {
            return new ClauseImpl(node);
        }

    };

    public static final IElementType DIRECTIVE = new OsgiManifestElementType("DIRECTIVE") {

        @Override
        public PsiElement createPsi(ASTNode node) {
            return new DirectiveImpl(node);
        }

    };

    private OsgiManifestElementType(String name) {
        super(name, BndLanguage.INSTANCE);
    }

    public abstract PsiElement createPsi(ASTNode node);

    @Override
    public String toString() {
        return "bnd:" + super.toString();
    }

}