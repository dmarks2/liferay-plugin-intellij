package de.dm.intellij.bndtools.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.ObjectUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.lang.manifest.ManifestBundle;
import org.jetbrains.lang.manifest.header.HeaderParser;
import org.jetbrains.lang.manifest.header.HeaderParserRepository;
import org.jetbrains.lang.manifest.header.impl.StandardHeaderParser;
import org.jetbrains.lang.manifest.parser.ManifestParser;
import org.jetbrains.lang.manifest.psi.ManifestElementType;
import org.jetbrains.lang.manifest.psi.ManifestTokenType;

public class BndParser extends ManifestParser {

    private final HeaderParserRepository myRepository;

    public BndParser() {
        myRepository = ServiceManager.getService(HeaderParserRepository.class);
    }

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

    private void parseSection(PsiBuilder builder) {
        PsiBuilder.Marker section = builder.mark();

        while (!builder.eof()) {
            IElementType tokenType = builder.getTokenType();
            if (tokenType == ManifestTokenType.HEADER_NAME) {
                parseHeader(builder);
            }
            else if (tokenType == ManifestTokenType.SECTION_END) {
                builder.advanceLexer();
                break;
            }
            else {
                PsiBuilder.Marker marker = builder.mark();
                consumeHeaderValue(builder);
                marker.error(ManifestBundle.message("manifest.header.expected"));
            }
        }

        section.done(ManifestElementType.SECTION);
    }

    private void parseHeader(PsiBuilder builder) {
        PsiBuilder.Marker header = builder.mark();
        String headerName = builder.getTokenText();
        assert headerName != null : "[" + builder.getOriginalText() + "]@" + builder.getCurrentOffset();
        builder.advanceLexer();

        if (builder.getTokenType() == ManifestTokenType.COLON) {
            builder.advanceLexer();

/*            if (!expect(builder, ManifestTokenType.SIGNIFICANT_SPACE)) {
                builder.error(ManifestBundle.message("manifest.whitespace.expected"));
            }
 */

            HeaderParser headerParser = ObjectUtils.notNull(myRepository.getHeaderParser(headerName), StandardHeaderParser.INSTANCE);
            headerParser.parse(builder);
        }
        else {
            PsiBuilder.Marker marker = builder.mark();
            consumeHeaderValue(builder);
            marker.error(ManifestBundle.message("manifest.colon.expected"));
        }

        header.done(ManifestElementType.HEADER);
    }

    private static void consumeHeaderValue(PsiBuilder builder) {
        while (!builder.eof() && !HEADER_END_TOKENS.contains(builder.getTokenType())) {
            builder.advanceLexer();
        }
    }

}
