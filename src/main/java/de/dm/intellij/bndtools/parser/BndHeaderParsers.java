package de.dm.intellij.bndtools.parser;

import java.util.HashMap;
import java.util.Map;
import de.dm.intellij.bndtools.LiferayBndConstants;

public class BndHeaderParsers {

    public static final Map<String, BndHeaderParser> PARSERS_MAP = new HashMap<>() {
		{
			put(OsgiConstants.BUNDLE_VERSION, BundleVersionParser.INSTANCE);
			put(OsgiConstants.BUNDLE_ACTIVATOR, BundleActivatorParser.INSTANCE);
			put(OsgiConstants.PRIVATE_PACKAGE, BasePackageParser.INSTANCE);
			put(OsgiConstants.IGNORE_PACKAGE, BasePackageParser.INSTANCE);
			put(OsgiConstants.IMPORT_PACKAGE, BasePackageParser.INSTANCE);
			put(OsgiConstants.CONDITIONAL_PACKAGE, BasePackageParser.INSTANCE);
			put(OsgiConstants.EXPORT_PACKAGE, ExportPackageParser.INSTANCE);

			for (String header : LiferayBndConstants.CLASS_REFERENCE_PROPERTIES) {
				put(header, ClassReferenceParser.INSTANCE);
			}

			for (String header : LiferayBndConstants.FILE_REFERENCE_PROPERTIES) {
				put(header, FileReferenceParser.INSTANCE);
			}

			for (String header : LiferayBndConstants.DEFAULT_HEADER_PROPERTIES) {
				put(header, BndHeaderParser.INSTANCE);
			}

			for (String header : OsgiConstants.headers) {
				if (!containsKey(header)) {
					put(header, BndHeaderParser.INSTANCE);
				}
			}

			for (String option : OsgiConstants.options) {
				if (!containsKey(option)) {
					put(option, BndHeaderParser.INSTANCE);
				}
			}
		}
	};


}
