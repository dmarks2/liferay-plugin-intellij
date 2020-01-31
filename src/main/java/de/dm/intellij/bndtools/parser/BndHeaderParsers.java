package de.dm.intellij.bndtools.parser;

import de.dm.intellij.bndtools.LiferayBndConstants;
import org.gradle.internal.impldep.aQute.bnd.osgi.Constants;

import java.util.HashMap;
import java.util.Map;

public class BndHeaderParsers {

    public static final Map<String, BndHeaderParser> PARSERS_MAP = new HashMap<String, BndHeaderParser>() {
        {
            put(Constants.BUNDLE_VERSION, BundleVersionParser.INSTANCE);
            put(Constants.BUNDLE_ACTIVATOR, BundleActivatorParser.INSTANCE);
            put(Constants.PRIVATE_PACKAGE, BasePackageParser.INSTANCE);
            put(Constants.IGNORE_PACKAGE, BasePackageParser.INSTANCE);
            put(Constants.IMPORT_PACKAGE, BasePackageParser.INSTANCE);
            put(Constants.CONDITIONAL_PACKAGE, BasePackageParser.INSTANCE);
            put(Constants.EXPORT_PACKAGE, ExportPackageParser.INSTANCE);

            for (String header : LiferayBndConstants.CLASS_REFERENCE_PROPERTIES) {
                put(header, BndClassReferenceParser.INSTANCE);
            }

            for (String header : LiferayBndConstants.FILE_REFERENCE_PROPERTIES) {
                put(header, FileReferenceParser.INSTANCE);
            }

            for (String header : LiferayBndConstants.DEFAULT_HEADER_PROPERTIES) {
                put(header, BndHeaderParser.INSTANCE);
            }

            for (String header : Constants.headers) {
                if (!containsKey(header)) {
                    put(header, BndHeaderParser.INSTANCE);
                }
            }

            for (String option : Constants.options) {
                if (!containsKey(option)) {
                    put(option, BndHeaderParser.INSTANCE);
                }
            }
        }
    };


}