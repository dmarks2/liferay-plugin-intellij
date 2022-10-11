package de.dm.intellij.bndtools;

public class LiferayBndConstants {

    //List of several "known" Liferay specific properties: https://github.com/liferay/liferay-portal/blob/master/modules/util/source-formatter/src/main/java/com/liferay/source/formatter/check/util/BNDSourceUtil.java
    public static final String AGENT_CLASS = "Agent-Class";
    public static final String ADD_RESOURCE = "-add-resource";
    public static final String CAN_REDEFINE_CLASSES = "Can-Redefine-Classes";
    public static final String CAN_RETRANSFORM_CLASSES = "Can-Retransform-Classes";
    public static final String DYNAMIC_IMPORT_PACKAGE = "DynamicImport-Package";
    public static final String ECLIPSE_PLATFORM_FILTER = "Eclipse-PlatformFilter";
    public static final String IMPLEMENTATION_VERSION = "Implementation-Version";
    public static final String JPM_COMMAND = "JPM-Command";
    public static final String JSP = "-jsp";
    public static final String LAUNCHER_AGENT_CLASS = "Launcher-Agent-Class";
    public static final String LIFERAY_AGGREGATE_RESOURCE_BUNDLES = "-liferay-aggregate-resource-bundles";
    public static final String LIFERAY_SPRING_DEPENDENCY = "-liferay-spring-dependency";
    public static final String LIFERAY_ENTERPRISE_APP = "Liferay-Enterprise-App";
    public static final String LIFERAY_SERVICE_XML = "-liferay-service-xml";
    public static final String LIFERAY_RELENG_BUNDLE = "Liferay-Releng-Bundle";
    public static final String LIFERAY_RELENG_CATEGORY = "Liferay-Releng-Category";
    public static final String LIFERAY_RELENG_FIX_DELIVERY_METHOD = "Liferay-Releng-Fix-Delivery-Method";
    public static final String LIFERAY_RELENG_LABS = "Liferay-Releng-Labs";
    public static final String LIFERAY_RELENG_MARKETPLACE = "Liferay-Releng-Marketplace";
    public static final String LIFERAY_RELENG_PORTAL_REQUIRED = "Liferay-Releng-Portal-Required";
    public static final String LIFERAY_RELENG_PUBLIC = "Liferay-Releng-Public";
    public static final String LIFERAY_RELENG_RESTART_REQUIRED = "Liferay-Releng-Restart-Required";
    public static final String LIFERAY_RELENG_SUITE = "Liferay-Releng-Suite";
    public static final String LIFERAY_RELENG_SUPPORT_URL = "Liferay-Releng-Support-Url";
    public static final String LIFERAY_RELENG_SUPPORTED = "Liferay-Releng-Supported";
    public static final String LIFERAY_RELENG_MODULE_GROUP_DESCRIPTION = "Liferay-Releng-Module-Group-Description";
    public static final String LIFERAY_RELENG_MODULE_GROUP_TITLE = "Liferay-Releng-Module-Group-Title";
    public static final String LIFERAY_REQUIRE_SCHEMA_VERSION = "Liferay-Require-SchemaVersion";
    public static final String LIFERAY_SERVICE = "Liferay-Service";
    public static final String LIFERAY_MODULES_COMPAT_ADAPTERS = "Liferay-Modules-Compat-Adapters";
    public static final String LIFERAY_JS_CONFIG = "Liferay-JS-Config";
    public static final String LIFERAY_CONFIGURATION_PATH = "Liferay-Configuration-Path";
    public static final String LIFERAY_JS_RESSOURCS_TOP_HEAD_AUTHENTICATED = "Liferay-JS-Resources-Top-Head-Authenticated";
    public static final String LIFERAY_JS_RESSOURCS_TOP_HEAD = "Liferay-JS-Resources-Top-Head";
    public static final String LIFERAY_JS_SUBMODULES_BRIDGE = "Liferay-JS-Submodules-Bridge";
    public static final String LIFERAY_JS_SUBMODULES_EXPORT = "Liferay-JS-Submodules-Export";
    public static final String LIFERAY_RELENG_APP_DESCRIPTION = "Liferay-Releng-App-Description";
    public static final String LIFERAY_RELENG_APP_TITLE = "Liferay-Releng-App-Title";
    public static final String LIFERAY_RTL_SUPPORT_REQUIRED = "Liferay-RTL-Support-Required";
    public static final String LIFERAY_THEME_CONTRIBUTOR_TYPE = "Liferay-Theme-Contributor-Type";
    public static final String LIFERAY_THEME_CONTRIBUTOR_WEIGHT = "Liferay-Theme-Contributor-Weight";
    public static final String LIFERAY_VERSIONS = "Liferay-Versions";
    public static final String LIFERAY_ICONS_PACK_NAME = "Liferay-Icons-Pack-Name";
    public static final String LIFERAY_ICONS_PATH = "Liferay-Icons-Path";
    public static final String LIFERAY_SITE_INITIALIZER_DESCRIPTION = "Liferay-Site-Initializer-Description";
    public static final String LIFERAY_SITE_INITIALIZER_FEATURE_FLAG = "Liferay-Site-Initializer-Feature-Flag";
    public static final String LIFERAY_SITE_INITIALIZER_NAME = "Liferay-Site-Initializer-Name";
    public static final String LIFERAY_RELENG_SUBSYSTEM_TITLE = "Liferay-Releng-Subsystem-Title";
    public static final String LIFERAY_RELENG_SUITE_DESCRIPTION = "Liferay-Releng-Suite-Description";
    public static final String LIFERAY_RELENG_SUITE_TITLE = "Liferay-Releng-Suite-Title";
    public static final String MAIN_CLASS = "Main-Class";
    public static final String METATYPE_INHERIT = "-metatype-inherit";
    public static final String PLUGIN_BUNDLE = "-plugin.bundle";
    public static final String PLUGIN_JSP = "-plugin.jsp";
    public static final String PLUGIN_METATYPE = "-plugin.metatype";
    public static final String PLUGIN_NPM = "-plugin.npm";
    public static final String PLUGIN_RESOURCEBUNDLE = "-plugin.resourcebundle";
    public static final String PLUGIN_SASS = "-plugin.sass";
    public static final String PLUGIN_SERVICE = "-plugin.service";
    public static final String PLUGIN_SPRING = "-plugin.spring";
    public static final String PLUGIN_SOCIAL = "-plugin.social";
    public static final String PREMAIN_CLASS = "Premain-Class";
    public static final String SASS = "-sass";
    public static final String TESTCASES = "Test-Cases";
    public static final String WEB_CONTEXT_PATH = "Web-ContextPath";

    public static final String[] DEFAULT_HEADER_PROPERTIES = {
        CAN_REDEFINE_CLASSES, CAN_RETRANSFORM_CLASSES, DYNAMIC_IMPORT_PACKAGE, ECLIPSE_PLATFORM_FILTER,
        IMPLEMENTATION_VERSION, JPM_COMMAND, JSP, LIFERAY_SERVICE_XML, LIFERAY_RELENG_MODULE_GROUP_DESCRIPTION, LIFERAY_RELENG_MODULE_GROUP_TITLE,
        LIFERAY_REQUIRE_SCHEMA_VERSION, LIFERAY_SERVICE, LIFERAY_MODULES_COMPAT_ADAPTERS,
        LIFERAY_JS_RESSOURCS_TOP_HEAD_AUTHENTICATED, LIFERAY_JS_RESSOURCS_TOP_HEAD, LIFERAY_JS_SUBMODULES_BRIDGE,
        LIFERAY_JS_SUBMODULES_EXPORT, LIFERAY_RELENG_APP_DESCRIPTION, LIFERAY_RTL_SUPPORT_REQUIRED,
        LIFERAY_THEME_CONTRIBUTOR_TYPE, LIFERAY_THEME_CONTRIBUTOR_WEIGHT, LIFERAY_VERSIONS,
        LIFERAY_AGGREGATE_RESOURCE_BUNDLES, LIFERAY_ENTERPRISE_APP, ADD_RESOURCE, LIFERAY_SPRING_DEPENDENCY,
        LIFERAY_RELENG_APP_TITLE, LIFERAY_RELENG_BUNDLE, LIFERAY_RELENG_CATEGORY, LIFERAY_RELENG_FIX_DELIVERY_METHOD,
        LIFERAY_RELENG_LABS, LIFERAY_RELENG_MARKETPLACE, LIFERAY_RELENG_PORTAL_REQUIRED, LIFERAY_RELENG_PUBLIC,
        LIFERAY_RELENG_RESTART_REQUIRED, LIFERAY_RELENG_SUITE, LIFERAY_RELENG_SUPPORT_URL, LIFERAY_RELENG_SUPPORTED,
        LIFERAY_ICONS_PACK_NAME, LIFERAY_SITE_INITIALIZER_DESCRIPTION, LIFERAY_SITE_INITIALIZER_FEATURE_FLAG, LIFERAY_SITE_INITIALIZER_NAME,
        LIFERAY_RELENG_SUBSYSTEM_TITLE, LIFERAY_RELENG_SUITE_DESCRIPTION, LIFERAY_RELENG_SUITE_TITLE, METATYPE_INHERIT, SASS, WEB_CONTEXT_PATH
    };

    public static final String[] CLASS_REFERENCE_PROPERTIES = {
        PLUGIN_BUNDLE, PLUGIN_JSP, PLUGIN_METATYPE, PLUGIN_NPM, PLUGIN_RESOURCEBUNDLE,
        PLUGIN_SASS, PLUGIN_SERVICE, PLUGIN_SPRING, MAIN_CLASS, PREMAIN_CLASS, AGENT_CLASS, LAUNCHER_AGENT_CLASS, TESTCASES,
        PLUGIN_SOCIAL
    };

    public static final String[] FILE_REFERENCE_PROPERTIES = {
        LIFERAY_JS_CONFIG, LIFERAY_CONFIGURATION_PATH, LIFERAY_ICONS_PATH
    };

}
