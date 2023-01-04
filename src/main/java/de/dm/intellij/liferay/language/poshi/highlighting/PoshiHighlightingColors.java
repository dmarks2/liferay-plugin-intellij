package de.dm.intellij.liferay.language.poshi.highlighting;

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class PoshiHighlightingColors {

    public static final TextAttributesKey OPERATOR = createTextAttributesKey("POSHI_OPERATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN);
    public static final TextAttributesKey KEYWORD = createTextAttributesKey("POSHI_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey NUMBER = createTextAttributesKey("POSHI_NUMBER", DefaultLanguageHighlighterColors.NUMBER);
    public static final TextAttributesKey STRING = createTextAttributesKey("POSHI_STRING", DefaultLanguageHighlighterColors.STRING);
    public static final TextAttributesKey COMMENT = createTextAttributesKey("POSHI_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
    public static final TextAttributesKey IDENTIFIER = createTextAttributesKey("POSHI_IDENTIFIER", DefaultLanguageHighlighterColors.IDENTIFIER);
    public static final TextAttributesKey PARENTHESES = createTextAttributesKey("POSHI_PARENTHESES", DefaultLanguageHighlighterColors.PARENTHESES);
    public static final TextAttributesKey BRACKETS = createTextAttributesKey("POSHI_BRACKETS", DefaultLanguageHighlighterColors.BRACKETS);
    public static final TextAttributesKey BRACES = createTextAttributesKey("POSHI_BRACES", DefaultLanguageHighlighterColors.BRACES);
    public static final TextAttributesKey COMMA = createTextAttributesKey("POSHI_COMMA", DefaultLanguageHighlighterColors.COMMA);
    public static final TextAttributesKey PERIOD = createTextAttributesKey("POSHI_PERIOD", DefaultLanguageHighlighterColors.DOT);
    public static final TextAttributesKey SEMICOLON = createTextAttributesKey("POSHI_SEMICOLON", DefaultLanguageHighlighterColors.SEMICOLON);
    public static final TextAttributesKey ANNOTATION = createTextAttributesKey("POSHI_ANNOTATION", DefaultLanguageHighlighterColors.METADATA);

}
