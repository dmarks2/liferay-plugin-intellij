package de.dm.intellij.bndtools.psi;

import com.intellij.openapi.util.TextRange;
import org.jetbrains.annotations.NotNull;

public interface BndHeaderValuePart extends BndHeaderValue {

    /**
     * Returns the range to highlight in the element.
     */
    @NotNull
    TextRange getHighlightingRange();
}
