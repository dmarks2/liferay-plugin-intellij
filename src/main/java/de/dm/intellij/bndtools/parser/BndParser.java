package de.dm.intellij.bndtools.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.util.ObjectUtils;
import de.dm.intellij.bndtools.psi.BndTokenType;
import de.dm.intellij.bndtools.psi.BndElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.lang.manifest.ManifestBundle;

public class BndParser implements PsiParser {

    public static final TokenSet HEADER_END_TOKENS = TokenSet.create(BndTokenType.SECTION_END, BndTokenType.HEADER_NAME);

    @NotNull
    @Override
    public ASTNode parse(@NotNull IElementType root, @NotNull PsiBuilder builder) {
        builder.setDebugMode(ApplicationManager.getApplication().isUnitTestMode());

        PsiBuilder.Marker rootMarker = builder.mark();
        while (!builder.eof()) {
            parseSection(builder);
        }
        rootMarker.done(root);

        return builder.getTreeBuilt();
    }

    private void parseSection(PsiBuilder psiBuilder) {
        PsiBuilder.Marker sectionMarker = psiBuilder.mark();

        while (!psiBuilder.eof()) {
            IElementType tokenType = psiBuilder.getTokenType();
            if (tokenType == BndTokenType.HEADER_NAME) {
                parseHeader(psiBuilder);
            }
            else if (tokenType == BndTokenType.SECTION_END) {
                psiBuilder.advanceLexer();
                break;
            }
            else {
                PsiBuilder.Marker marker = psiBuilder.mark();
                consumeHeaderValue(psiBuilder);
                marker.error(ManifestBundle.message("manifest.header.expected"));
            }
        }

        sectionMarker.done(BndElementType.SECTION);
    }

    private void parseHeader(PsiBuilder psiBuilder) {
        PsiBuilder.Marker headerMarker = psiBuilder.mark();
        String headerName = psiBuilder.getTokenText();

        psiBuilder.advanceLexer();

        if (psiBuilder.getTokenType() == BndTokenType.COLON) {
            psiBuilder.advanceLexer();

            BndHeaderParser bndHeaderParser = ObjectUtils.notNull(BndHeaderParsers.PARSERS_MAP.get(headerName), BndHeaderParser.INSTANCE);
            bndHeaderParser.parse(psiBuilder);
        }
        else {
            PsiBuilder.Marker marker = psiBuilder.mark();
            consumeHeaderValue(psiBuilder);
            marker.error(ManifestBundle.message("manifest.colon.expected"));
        }

        headerMarker.done(BndElementType.HEADER);
    }

    private static void consumeHeaderValue(PsiBuilder psiBuilder) {
        while (!psiBuilder.eof() && !HEADER_END_TOKENS.contains(psiBuilder.getTokenType())) {
            psiBuilder.advanceLexer();
        }
    }

}
