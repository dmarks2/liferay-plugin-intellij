package de.dm.intellij.bndtools.highlighting;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.tree.IElementType;
import de.dm.intellij.bndtools.parser.BndLexer;
import de.dm.intellij.bndtools.psi.BndTokenType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.lang.manifest.highlighting.ManifestColorsAndFonts;

import java.util.HashMap;
import java.util.Map;

public class BndSyntaxHighlighterFactory extends SyntaxHighlighterFactory {

    public static final SyntaxHighlighter HIGHLIGHTER = new SyntaxHighlighterBase() {
        private final Map<IElementType, TextAttributesKey> myAttributes;
        {
            myAttributes = new HashMap<>();
            myAttributes.put(BndTokenType.HEADER_NAME, ManifestColorsAndFonts.HEADER_NAME_KEY);
            myAttributes.put(BndTokenType.COLON, ManifestColorsAndFonts.HEADER_ASSIGNMENT_KEY);
            myAttributes.put(BndTokenType.HEADER_VALUE_PART, ManifestColorsAndFonts.HEADER_VALUE_KEY);
        }

        @NotNull
        @Override
        public Lexer getHighlightingLexer() {
            return new BndLexer();
        }

        @NotNull
        @Override
        public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
            return pack(myAttributes.get(tokenType));
        }
    };

    @NotNull
    @Override
    public SyntaxHighlighter getSyntaxHighlighter(@Nullable Project project, @Nullable VirtualFile virtualFile) {
        return HIGHLIGHTER;
    }
}
