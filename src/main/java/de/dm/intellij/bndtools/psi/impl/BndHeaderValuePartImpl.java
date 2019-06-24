package de.dm.intellij.bndtools.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.tree.TokenSet;
import com.intellij.psi.util.PsiTreeUtil;
import de.dm.intellij.bndtools.parser.OsgiHeaderParser;
import de.dm.intellij.bndtools.psi.BndHeader;
import de.dm.intellij.bndtools.psi.BndHeaderValuePart;
import de.dm.intellij.bndtools.psi.BndToken;
import de.dm.intellij.bndtools.psi.BndTokenType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.lang.manifest.header.HeaderParser;
import org.jetbrains.lang.manifest.header.HeaderParserRepository;
import org.jetbrains.lang.manifest.psi.impl.HeaderValuePartImpl;

public class BndHeaderValuePartImpl extends HeaderValuePartImpl implements BndHeaderValuePart {
    private static final TokenSet SPACES = TokenSet.create(BndTokenType.SIGNIFICANT_SPACE, BndTokenType.NEWLINE);

    private final HeaderParserRepository myRepository;

    public BndHeaderValuePartImpl(ASTNode node) {
        super(node);
        myRepository = ServiceManager.getService(HeaderParserRepository.class);
    }

    @NotNull
    @Override
    public PsiReference[] getReferences() {
        if (getUnwrappedText().isEmpty()) {
            return PsiReference.EMPTY_ARRAY;
        }

        BndHeader bndHeader = PsiTreeUtil.getParentOfType(this, BndHeader.class);
        if (bndHeader != null) {
            HeaderParser headerParser = myRepository.getHeaderParser(bndHeader.getName());
            if (headerParser instanceof OsgiHeaderParser) {
                OsgiHeaderParser osgiHeaderParser = (OsgiHeaderParser)headerParser;
                return osgiHeaderParser.getReferences(this);
            }
        }

        return PsiReference.EMPTY_ARRAY;
    }

    @NotNull
    @Override
    public TextRange getHighlightingRange() {
        int endOffset = getTextRange().getEndOffset();
        PsiElement last = getLastChild();
        while (isSpace(last)) {
            endOffset -= last.getTextLength();
            last = last.getPrevSibling();
        }

        int startOffset = getTextOffset();
        PsiElement first = getFirstChild();
        while (startOffset < endOffset && isSpace(first)) {
            startOffset += first.getTextLength();
            first = first.getNextSibling();
        }

        return new TextRange(startOffset, endOffset);
    }

    private static boolean isSpace(PsiElement element) {
        return element instanceof BndToken && SPACES.contains(((BndToken)element).getTokenType());
    }
}
