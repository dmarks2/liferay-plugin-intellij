package de.dm.intellij.bndtools.parser;

import com.intellij.util.containers.ContainerUtil;
import de.dm.intellij.bndtools.LiferayBndConstants;
import org.gradle.internal.impldep.aQute.bnd.osgi.Constants;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.lang.manifest.header.HeaderParser;
import org.jetbrains.lang.manifest.header.HeaderParserProvider;
import org.jetbrains.lang.manifest.header.impl.ClassReferenceParser;

import java.util.Map;

public class OsgiManifestHeaderParsers implements HeaderParserProvider {

    public OsgiManifestHeaderParsers() {
        _parsers = ContainerUtil.newHashMap();

        _parsers.put(Constants.BUNDLE_VERSION, BundleVersionParser.INSTANCE);
        _parsers.put(Constants.BUNDLE_ACTIVATOR, BundleActivatorParser.INSTANCE);
        _parsers.put(Constants.PRIVATE_PACKAGE, BasePackageParser.INSTANCE);
        _parsers.put(Constants.IGNORE_PACKAGE, BasePackageParser.INSTANCE);
        _parsers.put(Constants.IMPORT_PACKAGE, BasePackageParser.INSTANCE);
        _parsers.put(Constants.EXPORT_PACKAGE, ExportPackageParser.INSTANCE);

        for (String header : LiferayBndConstants.CLASS_REFERENCE_PROPERTIES) {
            _parsers.put(header, BndClassReferenceParser.INSTANCE);
        }

        for (String header : LiferayBndConstants.FILE_REFERENCE_PROPERTIES) {
            _parsers.put(header, FileReferenceParser.INSTANCE);
        }

        for (String header : LiferayBndConstants.DEFAULT_HEADER_PROPERTIES) {
            _parsers.put(header, OsgiHeaderParser.INSTANCE);
        }

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