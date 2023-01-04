package de.dm.intellij.liferay.language.poshi.parser;

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
import de.dm.intellij.liferay.language.poshi.PoshiLanguage;
import de.dm.intellij.liferay.language.poshi.lexer.PoshiLexerAdapter;
import de.dm.intellij.liferay.language.poshi.psi.PoshiFile;
import de.dm.intellij.liferay.language.poshi.psi.PoshiTokenSets;
import de.dm.intellij.liferay.language.poshi.psi.PoshiTypes;
import org.jetbrains.annotations.NotNull;

public class PoshiParserDefinition implements ParserDefinition {

    public static final IFileElementType FILE = new IFileElementType(PoshiLanguage.INSTANCE);
    @Override
    public @NotNull Lexer createLexer(Project project) {
        return new PoshiLexerAdapter();
    }

    @Override
    public @NotNull PsiParser createParser(Project project) {
        return new PoshiParser();
    }

    @Override
    public @NotNull IFileElementType getFileNodeType() {
        return FILE;
    }

    @Override
    public @NotNull TokenSet getCommentTokens() {
        return PoshiTokenSets.COMMENTS;
    }

    @Override
    public @NotNull TokenSet getStringLiteralElements() {
        return TokenSet.EMPTY;
    }

    @Override
    public @NotNull PsiElement createElement(ASTNode node) {
        return PoshiTypes.Factory.createElement(node);
    }

    @Override
    public @NotNull PsiFile createFile(@NotNull FileViewProvider viewProvider) {
        return new PoshiFile(viewProvider);
    }
}
