package de.dm.intellij.bndtools.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.psi.util.PsiUtilCore;
import de.dm.intellij.bndtools.BndLanguage;
import de.dm.intellij.bndtools.lexer.BndLexer;
import de.dm.intellij.bndtools.psi.BndElementType;
import de.dm.intellij.bndtools.psi.impl.BndFileImpl;
import org.jetbrains.annotations.NotNull;

public class BndParserDefinition implements ParserDefinition {

    public static final IFileElementType BND_FILE_ELEMENT_TYPE = new IFileElementType("BndFile", BndLanguage.INSTANCE);

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
        return BND_FILE_ELEMENT_TYPE;
    }

    @Override
    public PsiFile createFile(FileViewProvider viewProvider) {
        return new BndFileImpl(viewProvider);
    }


    @NotNull
    @Override
    public PsiElement createElement(ASTNode node) {
        IElementType elementType = node.getElementType();
        if (elementType instanceof BndElementType) {
            BndElementType bndElementType = (BndElementType)elementType;

            return bndElementType.createPsi(node);
        }

        return PsiUtilCore.NULL_PSI_ELEMENT;
    }

    @NotNull
    @Override
    public TokenSet getCommentTokens() {
        return TokenSet.EMPTY;
    }

    @NotNull
    @Override
    public TokenSet getStringLiteralElements() {
        return TokenSet.EMPTY;
    }
}
