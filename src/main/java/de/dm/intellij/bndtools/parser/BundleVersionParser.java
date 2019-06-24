package de.dm.intellij.bndtools.parser;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.openapi.util.TextRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.lang.manifest.header.HeaderParser;
import org.jetbrains.lang.manifest.header.impl.StandardHeaderParser;
import org.jetbrains.lang.manifest.psi.Header;
import org.jetbrains.lang.manifest.psi.HeaderValue;
import org.jetbrains.lang.manifest.psi.HeaderValuePart;
import org.osgi.framework.Version;

public class BundleVersionParser extends OsgiHeaderParser {

    public static final HeaderParser INSTANCE = new BundleVersionParser();

    @Override
    public boolean annotate(@NotNull Header header, @NotNull AnnotationHolder annotationHolder) {
        HeaderValue value = header.getHeaderValue();

        if (value instanceof HeaderValuePart) {
            try {
                new Version(value.getUnwrappedText());
            }
            catch (IllegalArgumentException iae) {
                HeaderValuePart headerValuePart = (HeaderValuePart)value;

                TextRange range = headerValuePart.getHighlightingRange();

                annotationHolder.createErrorAnnotation(range, iae.getMessage());

                return true;
            }
        }

        return false;
    }

    @Nullable
    @Override
    public Object getConvertedValue(@NotNull Header header) {
        HeaderValue value = header.getHeaderValue();

        if (value instanceof HeaderValuePart) {
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