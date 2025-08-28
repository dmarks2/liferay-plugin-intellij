package de.dm.intellij.liferay.language.osgi.config;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;

public class LiferayConfigParserDefinition implements ParserDefinition {

    public static final IFileElementType FILE = new IFileElementType("LIFERAY_CONFIG_FILE", LiferayConfigLanguage.INSTANCE);

    @NotNull
    @Override
    public Lexer createLexer(Project project) {
        return new LiferayConfigLexer();
    }

    @NotNull
    @Override
    public PsiParser createParser(Project project) {
        return new LiferayConfigParser();
    }

    @NotNull
    @Override
    public IFileElementType getFileNodeType() {
        return FILE;
    }

    @NotNull
    @Override
    public TokenSet getWhitespaceTokens() {
        return TokenSet.create(LiferayConfigTokenTypes.WHITE_SPACE);
    }

    @NotNull
    @Override
    public TokenSet getCommentTokens() {
        return TokenSet.EMPTY;
    }

    @NotNull
    @Override
    public TokenSet getStringLiteralElements() {
        return TokenSet.create(LiferayConfigTokenTypes.STRING_LITERAL, LiferayConfigTokenTypes.TYPED_VALUE);
    }

    @NotNull
    @Override
    public PsiElement createElement(ASTNode node) {
        return new LiferayConfigPsiElement(node);
    }

    @NotNull
    @Override
    public PsiFile createFile(@NotNull FileViewProvider viewProvider) {
        return new LiferayConfigFile(viewProvider);
    }
}
