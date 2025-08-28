package de.dm.intellij.liferay.language.osgi.config;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class LiferayConfigSyntaxHighlighter extends SyntaxHighlighterBase {

	public static final TextAttributesKey PROPERTY_NAME =
			createTextAttributesKey("LIFERAY_CONFIG_PROPERTY_NAME", DefaultLanguageHighlighterColors.INSTANCE_FIELD);

	public static final TextAttributesKey STRING_LITERAL =
			createTextAttributesKey("LIFERAY_CONFIG_STRING", DefaultLanguageHighlighterColors.STRING);

	public static final TextAttributesKey TYPED_VALUE =
			createTextAttributesKey("LIFERAY_CONFIG_TYPED_VALUE", DefaultLanguageHighlighterColors.NUMBER);

	public static final TextAttributesKey EQUALS =
			createTextAttributesKey("LIFERAY_CONFIG_EQUALS", DefaultLanguageHighlighterColors.OPERATION_SIGN);

    public static final TextAttributesKey BRACKET =
            createTextAttributesKey("LIFERAY_CONFIG_BRACKET", DefaultLanguageHighlighterColors.BRACKETS);

    public static final TextAttributesKey COMMA =
            createTextAttributesKey("LIFERAY_CONFIG_COMMA", DefaultLanguageHighlighterColors.COMMA);

    public static final TextAttributesKey BACKSLASH =
            createTextAttributesKey("LIFERAY_CONFIG_BACKSLASH", DefaultLanguageHighlighterColors.VALID_STRING_ESCAPE);

	private static final TextAttributesKey[] PROPERTY_NAME_KEYS = new TextAttributesKey[]{PROPERTY_NAME};
	private static final TextAttributesKey[] STRING_KEYS = new TextAttributesKey[]{STRING_LITERAL};
	private static final TextAttributesKey[] TYPED_VALUE_KEYS = new TextAttributesKey[]{TYPED_VALUE};
	private static final TextAttributesKey[] EQUALS_KEYS = new TextAttributesKey[]{EQUALS};
    private static final TextAttributesKey[] BRACKET_KEYS = new TextAttributesKey[]{BRACKET};
    private static final TextAttributesKey[] COMMA_KEYS = new TextAttributesKey[]{COMMA};
    private static final TextAttributesKey[] BACKSLASH_KEYS = new TextAttributesKey[]{BACKSLASH};
	private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];

	@Override
	public @NotNull Lexer getHighlightingLexer() {
		return new LiferayConfigLexer();
	}

	@NotNull
	@Override
	public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
		if (tokenType.equals(LiferayConfigTokenTypes.PROPERTY_NAME)) {
			return PROPERTY_NAME_KEYS;
		} else if (tokenType.equals(LiferayConfigTokenTypes.STRING_LITERAL)) {
			return STRING_KEYS;
		} else if (tokenType.equals(LiferayConfigTokenTypes.TYPED_VALUE)) {
			return TYPED_VALUE_KEYS;
		} else if (tokenType.equals(LiferayConfigTokenTypes.EQUALS)) {
			return EQUALS_KEYS;
        } else if (tokenType.equals(LiferayConfigTokenTypes.LBRACKET) ||
                   tokenType.equals(LiferayConfigTokenTypes.RBRACKET)) {
            return BRACKET_KEYS;
        } else if (tokenType.equals(LiferayConfigTokenTypes.COMMA)) {
            return COMMA_KEYS;
        } else if (tokenType.equals(LiferayConfigTokenTypes.BACKSLASH)) {
            return BACKSLASH_KEYS;
		} else {
			return EMPTY_KEYS;
		}
	}
}
