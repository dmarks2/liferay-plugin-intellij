package de.dm.intellij.bndtools.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.psi.util.PsiTreeUtil;
import de.dm.intellij.bndtools.parser.BndHeaderParser;
import de.dm.intellij.bndtools.parser.BndHeaderParsers;
import de.dm.intellij.bndtools.psi.BndHeader;
import de.dm.intellij.bndtools.psi.BndHeaderValuePart;
import de.dm.intellij.bndtools.psi.BndToken;
import de.dm.intellij.bndtools.psi.BndTokenType;
import org.jetbrains.annotations.NotNull;

public class BndHeaderValuePartImpl extends ASTWrapperPsiElement implements BndHeaderValuePart {
    private static final TokenSet SPACES = TokenSet.create(TokenType.WHITE_SPACE, BndTokenType.NEWLINE);

    public BndHeaderValuePartImpl(ASTNode node) {
        super(node);
    }

    @NotNull
    @Override
    public PsiReference[] getReferences() {
        if (getUnwrappedText().isEmpty()) {
            return PsiReference.EMPTY_ARRAY;
        }

        BndHeader bndHeader = PsiTreeUtil.getParentOfType(this, BndHeader.class);
        if (bndHeader != null) {
            BndHeaderParser bndHeaderParser = BndHeaderParsers.PARSERS_MAP.get(bndHeader.getName());
            if (bndHeaderParser != null) {
                return bndHeaderParser.getReferences(this);
            }
        }

        return PsiReference.EMPTY_ARRAY;
    }

    @NotNull
    @Override
    public String getUnwrappedText() {
        StringBuilder builder = new StringBuilder();

        for (PsiElement element = getFirstChild(); element != null; element = element.getNextSibling()) {
            if (!(isSpace(element))) {
                builder.append(element.getText());
            }
        }

        return builder.toString().trim();
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
