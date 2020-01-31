package de.dm.intellij.bndtools.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.psi.PsiReference;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import de.dm.intellij.bndtools.psi.BndHeader;
import de.dm.intellij.bndtools.psi.BndHeaderValue;
import de.dm.intellij.bndtools.psi.BndHeaderValuePart;
import de.dm.intellij.bndtools.psi.BndTokenType;
import de.dm.intellij.bndtools.psi.BndElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.intellij.lang.PsiBuilderUtil.expect;

public class BndHeaderParser {

    public static final BndHeaderParser INSTANCE = new BndHeaderParser();

    private static final TokenSet CLAUSE_END_TOKENS = TokenSet.orSet(BndParser.HEADER_END_TOKENS, TokenSet.create(BndTokenType.COMMA));
    private static final TokenSet SUB_CLAUSE_END_TOKENS = TokenSet.orSet(CLAUSE_END_TOKENS, TokenSet.create(BndTokenType.SEMICOLON));

    public void parse(@NotNull PsiBuilder psiBuilder) {
        while (!psiBuilder.eof()) {
            if (!parseClause(psiBuilder)) {
                break;
            }

            IElementType tokenType = psiBuilder.getTokenType();

            if (BndParser.HEADER_END_TOKENS.contains(tokenType)) {
                break;
            } else if (tokenType == BndTokenType.COMMA) {
                psiBuilder.advanceLexer();
            }
        }
    }

    public boolean annotate(@NotNull BndHeader bndHeader, @NotNull AnnotationHolder annotationHolder) {
        return false;
    }

    @Nullable
    public Object getConvertedValue(@NotNull BndHeader bndHeader) {
        BndHeaderValue value = bndHeader.getBndHeaderValue();
        return value != null ? value.getUnwrappedText() : null;
    }

    @NotNull
    public PsiReference[] getReferences(@NotNull BndHeaderValuePart bndHeaderValuePart) {
        return PsiReference.EMPTY_ARRAY;
    }


    private static boolean parseAttribute(PsiBuilder psiBuilder, PsiBuilder.Marker marker) {
        psiBuilder.advanceLexer();

        boolean result = parseSubClause(psiBuilder, true);
        marker.done(BndElementType.ATTRIBUTE);

        return result;
    }

    private static boolean parseClause(PsiBuilder psiBuilder) {
        PsiBuilder.Marker clauseMarker = psiBuilder.mark();

        boolean result = true;

        while (!psiBuilder.eof()) {
            if (!parseSubClause(psiBuilder, false)) {
                result = false;

                break;
            }

            IElementType tokenType = psiBuilder.getTokenType();

            if (CLAUSE_END_TOKENS.contains(tokenType)) {
                break;
            } else if (tokenType == BndTokenType.SEMICOLON) {
                psiBuilder.advanceLexer();
            }
        }

        clauseMarker.done(BndElementType.CLAUSE);

        return result;
    }

    private static boolean parseDirective(PsiBuilder psiBuilder, PsiBuilder.Marker marker) {
        psiBuilder.advanceLexer();

        if (expect(psiBuilder, BndTokenType.NEWLINE)) {
            expect(psiBuilder, TokenType.WHITE_SPACE);
        }

        expect(psiBuilder, BndTokenType.EQUALS);

        boolean result = parseSubClause(psiBuilder, true);

        marker.done(BndElementType.DIRECTIVE);

        return result;
    }

    private static void parseQuotedString(PsiBuilder psiBuilder) {
        do {
            psiBuilder.advanceLexer();
        }
        while (!psiBuilder.eof() && !BndParser.HEADER_END_TOKENS.contains(psiBuilder.getTokenType()) &&
            !expect(psiBuilder, BndTokenType.QUOTE));
    }

    private static boolean parseSubClause(PsiBuilder psiBuilder, boolean assignment) {
        PsiBuilder.Marker marker = psiBuilder.mark();
        boolean result = true;

        while (!psiBuilder.eof()) {
            IElementType tokenType = psiBuilder.getTokenType();

            String tokenText = psiBuilder.getTokenText();
            if (tokenText != null) {
                tokenText = tokenText.trim();

                //do not parse single backslashes as clause
                if ("\\".equals(tokenText)) {
                    psiBuilder.advanceLexer();

                    marker.drop();

                    return result;
                }
            }

            if (SUB_CLAUSE_END_TOKENS.contains(tokenType)) {
                break;
            }
            else if (tokenType == BndTokenType.QUOTE) {
                parseQuotedString(psiBuilder);
            }
            else if (!assignment && (tokenType == BndTokenType.EQUALS)) {
                marker.done(BndElementType.HEADER_VALUE_PART);

                return parseAttribute(psiBuilder, marker.precede());
            }
            else if (!assignment && (tokenType == BndTokenType.COLON)) {
                marker.done(BndElementType.HEADER_VALUE_PART);

                return parseDirective(psiBuilder, marker.precede());
            }
            else {
                IElementType lastToken = psiBuilder.getTokenType();
                psiBuilder.advanceLexer();

                if ((psiBuilder.getTokenType() == BndTokenType.NEWLINE) &&
                    (! (lastToken == BndTokenType.BACKSLASH_TOKEN)) ) {

                    break;
                }
            }
        }

        marker.done(BndElementType.HEADER_VALUE_PART);

        return result;
    }

}