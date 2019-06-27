package de.dm.intellij.bndtools.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.psi.PsiReference;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import de.dm.intellij.bndtools.psi.BndHeader;
import de.dm.intellij.bndtools.psi.BndHeaderValue;
import de.dm.intellij.bndtools.psi.BndHeaderValuePart;
import de.dm.intellij.bndtools.psi.BndTokenType;
import de.dm.intellij.bndtools.psi.OsgiManifestElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.intellij.lang.PsiBuilderUtil.expect;

public class OsgiHeaderParser {

    public static final OsgiHeaderParser INSTANCE = new OsgiHeaderParser();

    public void parse(@NotNull PsiBuilder psiBuilder) {
        while (!psiBuilder.eof()) {
            if (!_parseClause(psiBuilder)) {
                break;
            }

            IElementType tokenType = psiBuilder.getTokenType();

            if (BndParser.HEADER_END_TOKENS.contains(tokenType)) {
                break;
            }
            else if (tokenType == BndTokenType.COMMA) {
                psiBuilder.advanceLexer();
            }
        }
    }

    public boolean annotate(@NotNull BndHeader bndHeader, @NotNull AnnotationHolder holder) {
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


    private static boolean _parseAttribute(PsiBuilder psiBuilder, PsiBuilder.Marker marker) {
        psiBuilder.advanceLexer();

        boolean result = _parsesubClause(psiBuilder, true);
        marker.done(OsgiManifestElementType.ATTRIBUTE);

        return result;
    }

    private static boolean _parseClause(PsiBuilder psiBuilder) {
        PsiBuilder.Marker clause = psiBuilder.mark();

        boolean result = true;

        while (!psiBuilder.eof()) {
            if (!_parsesubClause(psiBuilder, false)) {
                result = false;

                break;
            }

            IElementType tokenType = psiBuilder.getTokenType();

            if (_clauseEndTokens.contains(tokenType)) {
                break;
            }
            else if (tokenType == BndTokenType.SEMICOLON) {
                psiBuilder.advanceLexer();
            }
        }

        clause.done(OsgiManifestElementType.CLAUSE);

        return result;
    }

    private static boolean _parseDirective(PsiBuilder psiBuilder, PsiBuilder.Marker marker) {
        psiBuilder.advanceLexer();

        if (expect(psiBuilder, BndTokenType.NEWLINE)) {
            expect(psiBuilder, BndTokenType.SIGNIFICANT_SPACE);
        }

        expect(psiBuilder, BndTokenType.EQUALS);

        boolean result = _parsesubClause(psiBuilder, true);

        marker.done(OsgiManifestElementType.DIRECTIVE);

        return result;
    }

    private static void _parseQuotedString(PsiBuilder psiBuilder) {
        do {
            psiBuilder.advanceLexer();
        }
        while (!psiBuilder.eof() && !BndParser.HEADER_END_TOKENS.contains(psiBuilder.getTokenType()) &&
            !expect(psiBuilder, BndTokenType.QUOTE));
    }

    private static boolean _parsesubClause(PsiBuilder psiBuilder, boolean assignment) {
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

            if (_subclauseEndTokens.contains(tokenType)) {
                break;
            }
            else if (tokenType == BndTokenType.QUOTE) {
                _parseQuotedString(psiBuilder);
            }
            else if (!assignment && (tokenType == BndTokenType.EQUALS)) {
                marker.done(OsgiManifestElementType.HEADER_VALUE_PART);

                return _parseAttribute(psiBuilder, marker.precede());
            }
            else if (!assignment && (tokenType == BndTokenType.COLON)) {
                marker.done(OsgiManifestElementType.HEADER_VALUE_PART);

                return _parseDirective(psiBuilder, marker.precede());
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

        marker.done(OsgiManifestElementType.HEADER_VALUE_PART);

        return result;
    }

    private static final TokenSet _clauseEndTokens = TokenSet.orSet(
        BndParser.HEADER_END_TOKENS, TokenSet.create(BndTokenType.COMMA));
    private static final TokenSet _subclauseEndTokens = TokenSet.orSet(
        _clauseEndTokens, TokenSet.create(BndTokenType.SEMICOLON));

}