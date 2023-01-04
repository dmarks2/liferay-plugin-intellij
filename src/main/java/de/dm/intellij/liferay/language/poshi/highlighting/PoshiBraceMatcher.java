package de.dm.intellij.liferay.language.poshi.highlighting;

import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import de.dm.intellij.liferay.language.poshi.psi.PoshiTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PoshiBraceMatcher implements PairedBraceMatcher {

    private static final BracePair[] PAIRS = new BracePair[]{
            new BracePair(PoshiTypes.ROUND_LBRACE, PoshiTypes.ROUND_RBRACE, false),
            new BracePair(PoshiTypes.SQUARE_LBRACE, PoshiTypes.SQUARE_RBRACE, false),
            new BracePair(PoshiTypes.CURLY_LBRACE, PoshiTypes.CURLY_RBRACE, true)
    };

    @Override
    public BracePair @NotNull [] getPairs() {
        return PAIRS;
    }

    @Override
    public boolean isPairedBracesAllowedBeforeType(@NotNull IElementType lbraceType, @Nullable IElementType contextType) {
        return true;
    }

    @Override
    public int getCodeConstructStart(PsiFile file, int openingBraceOffset) {
        return openingBraceOffset;
    }
}
