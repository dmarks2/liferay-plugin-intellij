package de.dm.intellij.liferay.language.poshi.highlighting;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import de.dm.intellij.liferay.language.poshi.lexer.PoshiLexerAdapter;
import de.dm.intellij.liferay.language.poshi.psi.PoshiTypes;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class PoshiSyntaxHighlighter extends SyntaxHighlighterBase {

    private static final Map<IElementType, TextAttributesKey> typeTextAttributesMap = new HashMap<>();

    static {
        typeTextAttributesMap.put(PoshiTypes.ARITHMETIC_OPERATOR, PoshiHighlightingColors.OPERATOR);
        typeTextAttributesMap.put(PoshiTypes.COMPARISION_OPERATOR, PoshiHighlightingColors.OPERATOR);
        typeTextAttributesMap.put(PoshiTypes.ELSE, PoshiHighlightingColors.KEYWORD);
        typeTextAttributesMap.put(PoshiTypes.ELSE_IF, PoshiHighlightingColors.KEYWORD);
        typeTextAttributesMap.put(PoshiTypes.IF, PoshiHighlightingColors.KEYWORD);
        typeTextAttributesMap.put(PoshiTypes.RETURN, PoshiHighlightingColors.KEYWORD);
        typeTextAttributesMap.put(PoshiTypes.CONTINUE, PoshiHighlightingColors.KEYWORD);
        typeTextAttributesMap.put(PoshiTypes.BREAK, PoshiHighlightingColors.KEYWORD);
        typeTextAttributesMap.put(PoshiTypes.STATIC, PoshiHighlightingColors.KEYWORD);
        typeTextAttributesMap.put(PoshiTypes.TASK, PoshiHighlightingColors.KEYWORD);
        typeTextAttributesMap.put(PoshiTypes.VAR, PoshiHighlightingColors.KEYWORD);
        typeTextAttributesMap.put(PoshiTypes.WHILE, PoshiHighlightingColors.KEYWORD);
        typeTextAttributesMap.put(PoshiTypes.NUMERIC_CONSTANT, PoshiHighlightingColors.NUMBER);
        typeTextAttributesMap.put(PoshiTypes.DOUBLE_QUOTED_STRING, PoshiHighlightingColors.STRING);
        typeTextAttributesMap.put(PoshiTypes.SINGLE_QUOTED_STRING, PoshiHighlightingColors.STRING);
        typeTextAttributesMap.put(PoshiTypes.SINGLE_QUOTED_MULTILINE, PoshiHighlightingColors.STRING);
        typeTextAttributesMap.put(PoshiTypes.LINE_COMMENT, PoshiHighlightingColors.COMMENT);
        typeTextAttributesMap.put(PoshiTypes.BLOCK_COMMENT, PoshiHighlightingColors.COMMENT);
        typeTextAttributesMap.put(PoshiTypes.IDENTIFIER, PoshiHighlightingColors.IDENTIFIER);
        typeTextAttributesMap.put(PoshiTypes.ROUND_LBRACE, PoshiHighlightingColors.PARENTHESES);
        typeTextAttributesMap.put(PoshiTypes.ROUND_RBRACE, PoshiHighlightingColors.PARENTHESES);
        typeTextAttributesMap.put(PoshiTypes.SQUARE_LBRACE, PoshiHighlightingColors.BRACKETS);
        typeTextAttributesMap.put(PoshiTypes.SQUARE_RBRACE, PoshiHighlightingColors.BRACKETS);
        typeTextAttributesMap.put(PoshiTypes.CURLY_LBRACE, PoshiHighlightingColors.BRACES);
        typeTextAttributesMap.put(PoshiTypes.CURLY_RBRACE, PoshiHighlightingColors.BRACES);
        typeTextAttributesMap.put(PoshiTypes.COMMA, PoshiHighlightingColors.COMMA);
        typeTextAttributesMap.put(PoshiTypes.PERIOD, PoshiHighlightingColors.PERIOD);
        typeTextAttributesMap.put(PoshiTypes.SEMICOLON, PoshiHighlightingColors.SEMICOLON);
        typeTextAttributesMap.put(PoshiTypes.ANNOTATION_NAME, PoshiHighlightingColors.ANNOTATION);
    }

    private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];

    @Override
    public @NotNull Lexer getHighlightingLexer() {
        return new PoshiLexerAdapter();
    }

    @Override
    public TextAttributesKey @NotNull [] getTokenHighlights(IElementType tokenType) {
        if (typeTextAttributesMap.containsKey(tokenType)) {
            return new TextAttributesKey[] {typeTextAttributesMap.get(tokenType)};
        }

        return EMPTY_KEYS;
    }
}
