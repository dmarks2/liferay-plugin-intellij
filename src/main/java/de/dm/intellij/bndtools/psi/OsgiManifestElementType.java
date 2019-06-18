package de.dm.intellij.bndtools.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import de.dm.intellij.bndtools.psi.impl.AttributeImpl;
import de.dm.intellij.bndtools.psi.impl.ClauseImpl;
import de.dm.intellij.bndtools.psi.impl.DirectiveImpl;
import org.jetbrains.lang.manifest.psi.ManifestElementType;

public abstract class OsgiManifestElementType extends ManifestElementType {

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
        super(name);
    }

}