package de.dm.intellij.bndtools.parser;

import de.dm.intellij.bndtools.LiferayBndConstants;
import org.gradle.internal.impldep.aQute.bnd.osgi.Constants;

import java.util.HashMap;
import java.util.Map;

public class OsgiManifestHeaderParsers {

    public static final Map<String, OsgiHeaderParser> PARSERS = new HashMap<>();
    
    static {
        PARSERS.put(Constants.BUNDLE_VERSION, BundleVersionParser.INSTANCE);
        PARSERS.put(Constants.BUNDLE_ACTIVATOR, BundleActivatorParser.INSTANCE);
        PARSERS.put(Constants.PRIVATE_PACKAGE, BasePackageParser.INSTANCE);
        PARSERS.put(Constants.IGNORE_PACKAGE, BasePackageParser.INSTANCE);
        PARSERS.put(Constants.IMPORT_PACKAGE, BasePackageParser.INSTANCE);
        PARSERS.put(Constants.CONDITIONAL_PACKAGE, BasePackageParser.INSTANCE);
        PARSERS.put(Constants.EXPORT_PACKAGE, ExportPackageParser.INSTANCE);

        for (String header : LiferayBndConstants.CLASS_REFERENCE_PROPERTIES) {
            PARSERS.put(header, BndClassReferenceParser.INSTANCE);
        }

        for (String header : LiferayBndConstants.FILE_REFERENCE_PROPERTIES) {
            PARSERS.put(header, FileReferenceParser.INSTANCE);
        }

        for (String header : LiferayBndConstants.DEFAULT_HEADER_PROPERTIES) {
            PARSERS.put(header, OsgiHeaderParser.INSTANCE);
        }

        for (String header : Constants.headers) {
            if (!PARSERS.containsKey(header)) {
                PARSERS.put(header, OsgiHeaderParser.INSTANCE);
            }
        }

        for (String option : Constants.options) {
            if (!PARSERS.containsKey(option)) {
                PARSERS.put(option, OsgiHeaderParser.INSTANCE);
            }
        }
    }

}