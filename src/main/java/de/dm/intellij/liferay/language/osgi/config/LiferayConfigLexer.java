package de.dm.intellij.liferay.language.osgi.config;

import com.intellij.lexer.LexerBase;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LiferayConfigLexer extends LexerBase {
	private CharSequence buffer;
	private int startOffset;
	private int endOffset;
	private int currentOffset;
	private IElementType tokenType;
	private int tokenStart;
	private int tokenEnd;

	@Override
	public void start(@NotNull CharSequence buffer, int startOffset, int endOffset, int initialState) {
		this.buffer = buffer;
		this.startOffset = startOffset;
		this.endOffset = endOffset;
		this.currentOffset = startOffset;

		advance();
	}

	@Override
	public int getState() {
		return 0;
	}

	@Nullable
	@Override
	public IElementType getTokenType() {
		return tokenType;
	}

	@Override
	public int getTokenStart() {
		return tokenStart;
	}

	@Override
	public int getTokenEnd() {
		return tokenEnd;
	}

	@NotNull
	@Override
	public CharSequence getBufferSequence() {
		return buffer;
	}

	@Override
	public int getBufferEnd() {
		return endOffset;
	}

	@Override
	public void advance() {
		tokenStart = currentOffset;

		if (currentOffset >= endOffset) {
			tokenType = null;
			return;
		}

		char ch = buffer.charAt(currentOffset);

		// Skip whitespace
		if (Character.isWhitespace(ch)) {
			while (currentOffset < endOffset && Character.isWhitespace(buffer.charAt(currentOffset))) {
				currentOffset++;
			}
			tokenEnd = currentOffset;
			tokenType = LiferayConfigTokenTypes.WHITE_SPACE;

			return;
		}


		// Array brackets
		if (ch == '[') {
			currentOffset++;
			tokenEnd = currentOffset;
			tokenType = LiferayConfigTokenTypes.LBRACKET;

			return;
		}

		if (ch == ']') {
			currentOffset++;
			tokenEnd = currentOffset;
			tokenType = LiferayConfigTokenTypes.RBRACKET;

			return;
		}

		// Comma separator
		if (ch == ',') {
			currentOffset++;
			tokenEnd = currentOffset;
			tokenType = LiferayConfigTokenTypes.COMMA;

			return;
		}

		// Backslash (line continuation)
		if (ch == '\\') {
			currentOffset++;
			tokenEnd = currentOffset;
			tokenType = LiferayConfigTokenTypes.BACKSLASH;

			return;
		}

		// Strings (quoted values)
		if (ch == '"') {
			currentOffset++; // skip opening quote

			while (currentOffset < endOffset) {
				char current = buffer.charAt(currentOffset);

				if (current == '"' && (currentOffset == 0 || buffer.charAt(currentOffset - 1) != '\\')) {
					currentOffset++; // include closing quote
					break;
				}

				currentOffset++;
			}

			tokenEnd = currentOffset;
			tokenType = LiferayConfigTokenTypes.STRING_LITERAL;

			return;
		}

		// Type prefixes (B", I", etc.)
		if (Character.isLetter(ch) && currentOffset + 1 < endOffset && buffer.charAt(currentOffset + 1) == '"') {
			currentOffset += 2; // skip type and opening quote

			while (currentOffset < endOffset) {
				char current = buffer.charAt(currentOffset);

				if (current == '"' && buffer.charAt(currentOffset - 1) != '\\') {
					currentOffset++; // include closing quote
					break;
				}

				currentOffset++;
			}

			tokenEnd = currentOffset;
			tokenType = LiferayConfigTokenTypes.TYPED_VALUE;

			return;
		}

		// Equals sign
		if (ch == '=') {
			currentOffset++;
			tokenEnd = currentOffset;
			tokenType = LiferayConfigTokenTypes.EQUALS;

			return;
		}

		// Property names (identifiers)
		if (Character.isLetter(ch) || ch == '_' || ch == '.') {
			while (currentOffset < endOffset) {
				char current = buffer.charAt(currentOffset);
				if (Character.isLetterOrDigit(current) || current == '_' || current == '.' || current == '-') {
					currentOffset++;
				} else {
					break;
				}
			}

			tokenEnd = currentOffset;
			tokenType = LiferayConfigTokenTypes.PROPERTY_NAME;

			return;
		}

		// Default: single character
		currentOffset++;
		tokenEnd = currentOffset;

		tokenType = LiferayConfigTokenTypes.BAD_CHARACTER;
	}
}
