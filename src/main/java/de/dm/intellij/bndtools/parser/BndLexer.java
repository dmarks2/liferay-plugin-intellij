package de.dm.intellij.bndtools.parser;

import com.intellij.lexer.LexerBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import de.dm.intellij.bndtools.psi.BndTokenType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class BndLexer extends LexerBase {

    private static final Map<Character, IElementType> SPECIAL_CHARACTERS_TOKEN_MAPPING;
    static {
        SPECIAL_CHARACTERS_TOKEN_MAPPING = new HashMap<>();
        SPECIAL_CHARACTERS_TOKEN_MAPPING.put(':', BndTokenType.COLON);
        SPECIAL_CHARACTERS_TOKEN_MAPPING.put(';', BndTokenType.SEMICOLON);
        SPECIAL_CHARACTERS_TOKEN_MAPPING.put(',', BndTokenType.COMMA);
        SPECIAL_CHARACTERS_TOKEN_MAPPING.put('=', BndTokenType.EQUALS);
        SPECIAL_CHARACTERS_TOKEN_MAPPING.put('(', BndTokenType.OPENING_PARENTHESIS_TOKEN);
        SPECIAL_CHARACTERS_TOKEN_MAPPING.put(')', BndTokenType.CLOSING_PARENTHESIS_TOKEN);
        SPECIAL_CHARACTERS_TOKEN_MAPPING.put('[', BndTokenType.OPENING_BRACKET_TOKEN);
        SPECIAL_CHARACTERS_TOKEN_MAPPING.put(']', BndTokenType.CLOSING_BRACKET_TOKEN);
        SPECIAL_CHARACTERS_TOKEN_MAPPING.put('\"', BndTokenType.QUOTE);
        SPECIAL_CHARACTERS_TOKEN_MAPPING.put('\\', BndTokenType.BACKSLASH_TOKEN);
    }

    private CharSequence myBuffer;
    private int myEndOffset;
    private int myTokenStart;
    private int myTokenEnd;
    private boolean myDefaultState;
    private IElementType myTokenType;

    @Override
    public void start(@NotNull CharSequence buffer, int startOffset, int endOffset, int initialState) {
        myBuffer = buffer;
        myEndOffset = endOffset;
        myTokenStart = myTokenEnd = startOffset;
        myDefaultState = initialState == 0;

        parseNextToken();
    }

    @Override
    public void advance() {
        myTokenStart = myTokenEnd;
        parseNextToken();
    }

    @Override
    public int getState() {
        return myDefaultState ? 0 : 1;
    }

    @Nullable
    @Override
    public IElementType getTokenType() {
        return myTokenType;
    }

    @Override
    public int getTokenStart() {
        return myTokenStart;
    }

    @Override
    public int getTokenEnd() {
        return myTokenEnd;
    }

    @Override
    public int getBufferEnd() {
        return myEndOffset;
    }

    @NotNull
    @Override
    public CharSequence getBufferSequence() {
        return myBuffer;
    }

    private void parseNextToken() {
        if (myTokenStart >= myEndOffset) {
            myTokenType = null;
            myTokenEnd = myTokenStart;
            return;
        }

        boolean atLineStart = myTokenStart == 0 || myBuffer.charAt(myTokenStart - 1) == '\n';
        char c = myBuffer.charAt(myTokenStart);

        if (myTokenStart > 1) {
            int c1 = myBuffer.charAt(myTokenStart - 2);
            int c2 = myBuffer.charAt(myTokenStart - 1);

            if (c1 == '\\' && c2 == '\n') {
                atLineStart = false;
            }
        }

        if (atLineStart) {
            myDefaultState = true;
            if (c == ' ') {
                myTokenType = TokenType.WHITE_SPACE;
                myTokenEnd = myTokenStart + 1;
            }
            else if (c == '\n') {
                myTokenType = BndTokenType.SECTION_END;
                myTokenEnd = myTokenStart + 1;
            }
            else {
                int headerEnd = myTokenStart + 1;
                while (headerEnd < myEndOffset) {
                    c = myBuffer.charAt(headerEnd);
                    if (c == ':') {
                        myDefaultState = false;
                        break;
                    }
                    else if (c == '\n') {
                        break;
                    }
                    ++headerEnd;
                }
                myTokenType = BndTokenType.HEADER_NAME;
                myTokenEnd = headerEnd;
            }
        }
        else if (!myDefaultState && c == ':') {
            myTokenType = BndTokenType.COLON;
            myTokenEnd = myTokenStart + 1;
        }
        else if (!myDefaultState && c == ' ') {
            myTokenType = TokenType.WHITE_SPACE;
            myTokenEnd = myTokenStart + 1;
            myDefaultState = true;
        }
        else {
            myDefaultState = true;
            IElementType special;
            if (c == '\n') {
                myTokenType = BndTokenType.NEWLINE;
                myTokenEnd = myTokenStart + 1;
            }
            else if ((special = SPECIAL_CHARACTERS_TOKEN_MAPPING.get(c)) != null) {
                myTokenType = special;
                myTokenEnd = myTokenStart + 1;
            } else if (c == ' ') {
                myTokenType = TokenType.WHITE_SPACE;
                myTokenEnd = myTokenStart + 1;
            }
            else {
                int valueEnd = myTokenStart + 1;
                while (valueEnd < myEndOffset) {
                    c = myBuffer.charAt(valueEnd);
                    if (c == '\n' || SPECIAL_CHARACTERS_TOKEN_MAPPING.containsKey(c)) {
                        break;
                    }
                    ++valueEnd;
                }
                myTokenType = BndTokenType.HEADER_VALUE_PART;
                myTokenEnd = valueEnd;
            }
        }
    }
}
