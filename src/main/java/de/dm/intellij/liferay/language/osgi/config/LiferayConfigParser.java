package de.dm.intellij.liferay.language.osgi.config;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

public class LiferayConfigParser implements PsiParser {

    @NotNull
    @Override
    public ASTNode parse(@NotNull IElementType root, PsiBuilder builder) {
        PsiBuilder.Marker rootMarker = builder.mark();
        
        while (!builder.eof()) {
            parseProperty(builder);
        }
        
        rootMarker.done(root);

        return builder.getTreeBuilt();
    }

    private void parseProperty(PsiBuilder builder) {
        // Skip whitespace and comments
        while (builder.getTokenType() == LiferayConfigTokenTypes.WHITE_SPACE) {
            builder.advanceLexer();
        }

        if (builder.eof()) {
            return;
        }

        PsiBuilder.Marker propertyMarker = builder.mark();

        // Parse property name
        if (builder.getTokenType() == LiferayConfigTokenTypes.PROPERTY_NAME) {
            builder.advanceLexer();
        } else {
            builder.error("Expected property name");

            propertyMarker.drop();

            recoverToNextPropertyOrEOF(builder);
            return;
        }

        // Skip whitespace
        while (builder.getTokenType() == LiferayConfigTokenTypes.WHITE_SPACE) {
            builder.advanceLexer();
        }

        // Parse equals sign
        if (builder.getTokenType() == LiferayConfigTokenTypes.EQUALS) {
            builder.advanceLexer();
        } else {
            builder.error("Expected '='");
        }

        // Skip whitespace
        while (builder.getTokenType() == LiferayConfigTokenTypes.WHITE_SPACE) {
            builder.advanceLexer();
        }

        // Parse value
        parseValue(builder);

        propertyMarker.done(LiferayConfigElementTypes.PROPERTY);
    }

    private void parseValue(PsiBuilder builder) {
        PsiBuilder.Marker valueMarker = builder.mark();

        if (builder.getTokenType() == LiferayConfigTokenTypes.STRING_LITERAL ||
            builder.getTokenType() == LiferayConfigTokenTypes.TYPED_VALUE) {
            // Simple value
            builder.advanceLexer();

            valueMarker.done(LiferayConfigElementTypes.PROPERTY_VALUE);
        } else if (builder.getTokenType() == LiferayConfigTokenTypes.LBRACKET) {
            // Array value
            parseArray(builder, valueMarker);
        } else {
            builder.error("Expected value");

            valueMarker.drop();

            recoverToNextPropertyOrEOF(builder);
        }
    }

    private void parseArray(PsiBuilder builder, PsiBuilder.Marker valueMarker) {
        // Parse opening bracket
        builder.advanceLexer(); // consume '['

        // Skip whitespace and backslash (line continuation)
        skipWhitespaceAndContinuation(builder);

        // Parse array elements
        boolean first = true;

        while (!builder.eof() && builder.getTokenType() != LiferayConfigTokenTypes.RBRACKET) {
            if (!first) {
                // Expect comma between elements
                if (builder.getTokenType() == LiferayConfigTokenTypes.COMMA) {
                    builder.advanceLexer();

                    skipWhitespaceAndContinuation(builder);
                } else {
                    builder.error("Expected ',' between array elements");
                }
            }

            if (builder.getTokenType() == LiferayConfigTokenTypes.RBRACKET) {
                break; // trailing comma case
            }

            // Check if we're at EOF or unexpected token that suggests we should stop parsing the array
            if (builder.eof() || isPropertyStart(builder)) {
                builder.error("Unclosed array - expected ']'");
                valueMarker.done(LiferayConfigElementTypes.ARRAY_VALUE);
                return;
            }

            if (!parseArrayElement(builder)) {
                // If we couldn't parse an array element, try to recover
                builder.error("Expected array element value");
                // Skip the problematic token to avoid infinite loop
                if (!builder.eof()) {
                    builder.advanceLexer();
                }
            }

            first = false;
            
            skipWhitespaceAndContinuation(builder);
        }

        // Parse closing bracket
        if (builder.getTokenType() == LiferayConfigTokenTypes.RBRACKET) {
            builder.advanceLexer();
        } else {
            builder.error("Expected ']'");
        }

        valueMarker.done(LiferayConfigElementTypes.ARRAY_VALUE);
    }

    private boolean parseArrayElement(PsiBuilder builder) {
        PsiBuilder.Marker elementMarker = builder.mark();

        if (builder.getTokenType() == LiferayConfigTokenTypes.STRING_LITERAL) {
            builder.advanceLexer();

            elementMarker.done(LiferayConfigElementTypes.ARRAY_ELEMENT);

            return true;
        } else {
            elementMarker.drop();

            return false;
        }
    }

    private void skipWhitespaceAndContinuation(PsiBuilder builder) {
        while (builder.getTokenType() == LiferayConfigTokenTypes.WHITE_SPACE ||
               builder.getTokenType() == LiferayConfigTokenTypes.BACKSLASH) {
            builder.advanceLexer();
        }
    }

    private boolean isPropertyStart(PsiBuilder builder) {
        return builder.getTokenType() == LiferayConfigTokenTypes.PROPERTY_NAME;
    }

    private void recoverToNextPropertyOrEOF(PsiBuilder builder) {
        // Skip tokens until we find a property name or reach EOF
        while (!builder.eof() && !isPropertyStart(builder)) {
            builder.advanceLexer();
        }
    }
}
