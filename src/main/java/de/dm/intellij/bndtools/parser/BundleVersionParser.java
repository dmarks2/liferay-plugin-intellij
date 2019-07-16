package de.dm.intellij.bndtools.parser;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.openapi.util.TextRange;
import de.dm.intellij.bndtools.psi.BndHeader;
import de.dm.intellij.bndtools.psi.BndHeaderValue;
import de.dm.intellij.bndtools.psi.BndHeaderValuePart;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.osgi.framework.Version;

public class BundleVersionParser extends OsgiHeaderParser {

    public static final BundleVersionParser INSTANCE = new BundleVersionParser();

    @Override
    public boolean annotate(@NotNull BndHeader bndHeader, @NotNull AnnotationHolder annotationHolder) {
        BndHeaderValue value = bndHeader.getBndHeaderValue();

        if (value instanceof BndHeaderValuePart) {
            try {
                new Version(value.getUnwrappedText());
            }
            catch (IllegalArgumentException iae) {
                BndHeaderValuePart bndHeaderValuePart = (BndHeaderValuePart)value;

                TextRange range = bndHeaderValuePart.getHighlightingRange();

                annotationHolder.createErrorAnnotation(range, iae.getMessage());

                return true;
            }
        }

        return false;
    }

    @Nullable
    @Override
    public Object getConvertedValue(@NotNull BndHeader bndHeader) {
        BndHeaderValue value = bndHeader.getBndHeaderValue();

        if (value instanceof BndHeaderValuePart) {
            try {
                return new Version(value.getUnwrappedText());
            }
            catch (IllegalArgumentException iae) {
            }
        }

        return null;
    }

    private BundleVersionParser() {
    }

}