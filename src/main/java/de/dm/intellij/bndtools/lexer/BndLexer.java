package de.dm.intellij.bndtools.lexer;

import com.intellij.lexer.LexerBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import de.dm.intellij.bndtools.psi.BndTokenType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class BndLexer extends LexerBase {

    private static final Map<Character, IElementType> SPECIAL_CHARACTERS_TOKEN_MAP = new HashMap<Character, IElementType>() {
        {
            put(':', BndTokenType.COLON);
            put(';', BndTokenType.SEMICOLON);
            put(',', BndTokenType.COMMA);
            put('=', BndTokenType.EQUALS);
            put('(', BndTokenType.OPENING_PARENTHESIS_TOKEN);
            put(')', BndTokenType.CLOSING_PARENTHESIS_TOKEN);
            put('[', BndTokenType.OPENING_BRACKET_TOKEN);
            put(']', BndTokenType.CLOSING_BRACKET_TOKEN);
            put('\"', BndTokenType.QUOTE);
            put('\\', BndTokenType.BACKSLASH_TOKEN);
        }
    };

    private CharSequence buffer;
    private int endOffset;
    private int tokenStart;
    private int tokenEnd;
    private boolean defaultState;
    private IElementType tokenType;

    @Override
    public void start(@NotNull CharSequence buffer, int startOffset, int endOffset, int initialState) {
        this.buffer = buffer;
        this.endOffset = endOffset;
        tokenStart = tokenEnd = startOffset;
        defaultState = (initialState == 0);

        parseNextToken();
    }

    @Override
    public void advance() {
        tokenStart = tokenEnd;
        parseNextToken();
    }

    @Override
    public int getState() {
        return defaultState ? 0 : 1;
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

    @Override
    public int getBufferEnd() {
        return endOffset;
    }

    @NotNull
    @Override
    public CharSequence getBufferSequence() {
        return buffer;
    }

    private void parseNextToken() {
        if (tokenStart >= endOffset) {
            tokenType = null;
            tokenEnd = tokenStart;
            return;
        }

        boolean atLineStart = tokenStart == 0 || buffer.charAt(tokenStart - 1) == '\n';
        char c = buffer.charAt(tokenStart);

        if (tokenStart > 1) {
            int c1 = buffer.charAt(tokenStart - 2);
            int c2 = buffer.charAt(tokenStart - 1);

            if (c1 == '\\' && c2 == '\n') {
                atLineStart = false;
            }
        }

        if (atLineStart) {
            defaultState = true;
            if (c == ' ') {
                tokenType = TokenType.WHITE_SPACE;
                tokenEnd = tokenStart + 1;
            } else if (c == '\n') {
                tokenType = BndTokenType.SECTION_END;
                tokenEnd = tokenStart + 1;
            } else {
                int headerEnd = tokenStart + 1;
                while (headerEnd < endOffset) {
                    c = buffer.charAt(headerEnd);
                    if (c == ':') {
                        defaultState = false;
                        break;
                    } else if (c == '\n') {
                        break;
                    }
                    ++headerEnd;
                }
                tokenType = BndTokenType.HEADER_NAME;
                tokenEnd = headerEnd;
            }
        }
        else if (!defaultState && c == ':') {
            tokenType = BndTokenType.COLON;
            tokenEnd = tokenStart + 1;
        }
        else if (!defaultState && c == ' ') {
            tokenType = TokenType.WHITE_SPACE;
            tokenEnd = tokenStart + 1;
            defaultState = true;
        } else {
            defaultState = true;
            IElementType special;
            if (c == '\n') {
                tokenType = BndTokenType.NEWLINE;
                tokenEnd = tokenStart + 1;
            }
            else if ((special = SPECIAL_CHARACTERS_TOKEN_MAP.get(c)) != null) {
                tokenType = special;
                tokenEnd = tokenStart + 1;
            } else if (c == ' ') {
                tokenType = TokenType.WHITE_SPACE;
                tokenEnd = tokenStart + 1;
            }
            else {
                int valueEnd = tokenStart + 1;
                while (valueEnd < endOffset) {
                    c = buffer.charAt(valueEnd);
                    if (c == '\n' || SPECIAL_CHARACTERS_TOKEN_MAP.containsKey(c)) {
                        break;
                    }
                    ++valueEnd;
                }
                tokenType = BndTokenType.HEADER_VALUE_PART;
                tokenEnd = valueEnd;
            }
        }
    }
}
