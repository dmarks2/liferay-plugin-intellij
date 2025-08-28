package de.dm.intellij.liferay.language.osgi.config;

import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;

public interface LiferayConfigTokenTypes {
	IElementType PROPERTY_NAME = new LiferayConfigTokenType("PROPERTY_NAME");
	IElementType EQUALS = new LiferayConfigTokenType("EQUALS");
	IElementType STRING_LITERAL = new LiferayConfigTokenType("STRING_LITERAL");
	IElementType TYPED_VALUE = new LiferayConfigTokenType("TYPED_VALUE");
	IElementType LBRACKET = new LiferayConfigTokenType("LBRACKET");
	IElementType RBRACKET = new LiferayConfigTokenType("RBRACKET");
	IElementType COMMA = new LiferayConfigTokenType("COMMA");
	IElementType BACKSLASH = new LiferayConfigTokenType("BACKSLASH");
	IElementType WHITE_SPACE = TokenType.WHITE_SPACE;
	IElementType BAD_CHARACTER = TokenType.BAD_CHARACTER;
}
