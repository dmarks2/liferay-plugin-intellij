package de.dm.intellij.bndtools;

public class LiferayBndConstants {

    public static final String PLUGIN_BUNDLE = "-plugin.bundle";
    public static final String PLUGIN_JSP = "-plugin.jsp";
    public static final String PLUGIN_METATYPE = "-plugin.metatype";
    public static final String PLUGIN_NPM = "-plugin.npm";
    public static final String PLUGIN_RESOURCEBUNDLE = "-plugin.resourcebundle";
    public static final String PLUGIN_SASS = "-plugin.sass";
    public static final String PLUGIN_SERVICE = "-plugin.service";
    public static final String PLUGIN_SPRING = "-plugin.spring";

    public static final String JSP = "-jsp";
    public static final String SASS = "-sass";
    public static final String LIFERAY_SERVICE_XML = "-liferay-service-xml";
    public static final String LIFERAY_RELENG_MODULE_GROUP_DESCRIPTION = "Liferay-Releng-Module-Group-Description";
    public static final String LIFERAY_RELENG_MODULE_GROUP_TITLE = "Liferay-Releng-Module-Group-Title";
    public static final String LIFERAY_REQUIRE_SCHEMA_VERSION = "Liferay-Require-SchemaVersion";
    public static final String LIFERAY_SERVICE = "Liferay-Service";
    public static final String DYNAMIC_IMPORT_PACKAGE = "DynamicImport-Package";
    public static final String LIFERAY_MODULES_COMPAT_ADAPTERS = "Liferay-Modules-Compat-Adapters";

    public static final String LIFERAY_JS_CONFIG = "Liferay-JS-Config";
    public static final String LIFERAY_CONFIGURATION_PATH = "Liferay-Configuration-Path";

    public static final String LIFERAY_JS_RESSOURCS_TOP_HEAD_AUTHENTICATED = "Liferay-JS-Resources-Top-Head-Authenticated";
    public static final String LIFERAY_JS_RESSOURCS_TOP_HEAD = "Liferay-JS-Resources-Top-Head";
    public static final String LIFERAY_JS_SUBMODULES_BRIDGE = "Liferay-JS-Submodules-Bridge";
    public static final String LIFERAY_JS_SUBMODULES_EXPORT = "Liferay-JS-Submodules-Export";
    public static final String LIFERAY_RELENG_APP_DESCRIPTION = "Liferay-Releng-App-Description";
    public static final String LIFERAY_RTL_SUPPORT_REQUIRED = "Liferay-RTL-Support-Required";
    public static final String LIFERAY_THEME_CONTRIBUTOR_TYPE = "Liferay-Theme-Contributor-Type";
    public static final String LIFERAY_THEME_CONTRIBUTOR_WEIGHT = "Liferay-Theme-Contributor-Weight";
    public static final String LIFERAY_VERSIONS = "Liferay-Versions";
    public static final String WEB_CONTEXT_PATH = "Web-ContextPath";

    public static final String MAIN_CLASS = "Main-Class";
    public static final String PREMAIN_CLASS = "Premain-Class";
    public static final String AGENT_CLASS = "Agent-Class";
    public static final String LAUNCHER_AGENT_CLASS = "Launcher-Agent-Class";

    public static final String TESTCASES = "Test-Cases";

    public static final String[] DEFAULT_HEADER_PROPERTIES = {
        JSP, SASS, LIFERAY_SERVICE_XML, LIFERAY_RELENG_MODULE_GROUP_DESCRIPTION, LIFERAY_RELENG_MODULE_GROUP_TITLE,
        LIFERAY_REQUIRE_SCHEMA_VERSION, LIFERAY_SERVICE, DYNAMIC_IMPORT_PACKAGE, LIFERAY_MODULES_COMPAT_ADAPTERS,
        LIFERAY_JS_RESSOURCS_TOP_HEAD_AUTHENTICATED, LIFERAY_JS_RESSOURCS_TOP_HEAD, LIFERAY_JS_SUBMODULES_BRIDGE,
        LIFERAY_JS_SUBMODULES_EXPORT, LIFERAY_RELENG_APP_DESCRIPTION, LIFERAY_RTL_SUPPORT_REQUIRED,
        LIFERAY_THEME_CONTRIBUTOR_TYPE, LIFERAY_THEME_CONTRIBUTOR_WEIGHT, LIFERAY_VERSIONS, WEB_CONTEXT_PATH
    };

    public static final String[] CLASS_REFERENCE_PROPERTIES = {
        PLUGIN_BUNDLE, PLUGIN_JSP, PLUGIN_METATYPE, PLUGIN_NPM, PLUGIN_RESOURCEBUNDLE,
        PLUGIN_SASS, PLUGIN_SERVICE, PLUGIN_SPRING, MAIN_CLASS, PREMAIN_CLASS, AGENT_CLASS, LAUNCHER_AGENT_CLASS, TESTCASES
    };

    public static final String[] FILE_REFERENCE_PROPERTIES = {
        LIFERAY_JS_CONFIG, LIFERAY_CONFIGURATION_PATH
    };

}
