package de.dm.intellij.bndtools.parser;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.util.TextRange;
import de.dm.intellij.bndtools.psi.BndHeader;
import de.dm.intellij.bndtools.psi.BndHeaderValue;
import de.dm.intellij.bndtools.psi.BndHeaderValuePart;
import de.dm.intellij.bndtools.psi.Clause;
import org.jetbrains.annotations.NotNull;

public class BundleVersionParser extends BndHeaderParser {

    public static final BundleVersionParser INSTANCE = new BundleVersionParser();

    @Override
    public boolean annotate(@NotNull BndHeader bndHeader, @NotNull AnnotationHolder annotationHolder) {
        BndHeaderValue value = bndHeader.getBndHeaderValue();

        if (value instanceof Clause clause) {
			value = clause.getValue();
        }

        if (value instanceof BndHeaderValuePart) {
            try {
                new OsgiVersion(value.getUnwrappedText());
            }
            catch (IllegalArgumentException iae) {
                BndHeaderValuePart bndHeaderValuePart = (BndHeaderValuePart)value;

                TextRange range = bndHeaderValuePart.getHighlightingRange();

                annotationHolder.newAnnotation(
                        HighlightSeverity.ERROR, iae.getMessage()
                ).range(
                        range
                ).create();

                return true;
            }
        }

        return false;
    }

    private BundleVersionParser() {
    }

}
