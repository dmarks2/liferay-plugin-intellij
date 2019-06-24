package de.dm.intellij.bndtools.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.util.PsiUtilCore;
import de.dm.intellij.bndtools.BndLanguage;
import de.dm.intellij.bndtools.psi.OsgiManifestElementType;
import de.dm.intellij.bndtools.psi.impl.BndFileImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.lang.manifest.parser.ManifestParserDefinition;

public class BndParserDefinition extends ManifestParserDefinition {

    public static final IFileElementType FILE = new IFileElementType("BndFile", BndLanguage.INSTANCE);

    @NotNull
    @Override
    public Lexer createLexer(Project project) {
        return new BndLexer();
    }

    @Override
    public PsiParser createParser(Project project) {
        return new BndParser();
    }

    @Override
    public IFileElementType getFileNodeType() {
        return FILE;
    }

    @Override
    public PsiFile createFile(FileViewProvider viewProvider) {
        return new BndFileImpl(viewProvider);
    }


    @NotNull
    @Override
    public PsiElement createElement(ASTNode node) {
        IElementType type = node.getElementType();
        if (type instanceof OsgiManifestElementType) {
            return ((OsgiManifestElementType)type).createPsi(node);
        }

        return PsiUtilCore.NULL_PSI_ELEMENT;
    }
}
