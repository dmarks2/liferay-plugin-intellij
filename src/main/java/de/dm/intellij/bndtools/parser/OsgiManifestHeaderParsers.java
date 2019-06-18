package de.dm.intellij.bndtools.parser;

import com.intellij.util.containers.ContainerUtil;
import org.gradle.internal.impldep.aQute.bnd.osgi.Constants;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.lang.manifest.header.HeaderParser;
import org.jetbrains.lang.manifest.header.HeaderParserProvider;

import java.util.Map;

public class OsgiManifestHeaderParsers implements HeaderParserProvider {

    public OsgiManifestHeaderParsers() {
        _parsers = ContainerUtil.newHashMap();

        _parsers.put(Constants.BUNDLE_VERSION, BundleVersionParser.INSTANCE);

        for (String header : Constants.headers) {
            if (!_parsers.containsKey(header)) {
                _parsers.put(header, OsgiHeaderParser.INSTANCE);
            }
        }

        for (String option : Constants.options) {
            if (!_parsers.containsKey(option)) {
                _parsers.put(option, OsgiHeaderParser.INSTANCE);
            }
        }
    }

    @NotNull
    @Override
    public Map<String, HeaderParser> getHeaderParsers() {
        return _parsers;
    }

    private final Map<String, HeaderParser> _parsers;

}