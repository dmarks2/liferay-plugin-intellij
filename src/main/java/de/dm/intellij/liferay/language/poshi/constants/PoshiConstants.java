package de.dm.intellij.liferay.language.poshi.constants;

public class PoshiConstants {

    public static final String FUNCTIONS_DIRECTORY = "functions";
    public static final String MACROS_DIRECTORY = "macros";
    public static final String PATHS_DIRECTORY = "paths";
    public static final String TESTCASES_DIRECTORY = "testcases";

    public static final String FUNCTION_EXTENSION = ".function";
    public static final String MACRO_EXTENSION = ".macro";
    public static final String PATH_EXTENSION = ".path";
    public static final String TESTCASE_EXTENSION = ".testcase";

    public static final String DEFAULT_ANNOTATION = "@default";
    public static final String DEFAULT_NAMESPACE = "Default";

    public static final String DEFAULT_TEST_FUNCTIONAL_PATH = "default/testFunctional";

    public static final String DEFAULT_RESOURCES_LIBRARY = "com.liferay.poshi.runner.resources-1.0.17.jar";

    public static final String SELENIUM_KEYWORD = "selenium";

    public static final String LIFERAY_SELENIUM_FILE_PATH = "/com/liferay/poshi/core/selenium/LiferaySelenium.java";

    public static final String POSHI_UTILS_PATH = "/com/liferay/poshi/core/util";

    public static final String[] POSHI_UTILS_CLASSES = new String[] {
            "CharPool.java",
            "Dom4JUtil.java",
            "ExternalMethod.java",
            "FileUtil.java",
            "GetterUtil.java",
            "ListUtil.java",
            "MapUtil.java",
            "MathUtil.java",
            "NaturalOrderStringComparator.java",
            "OSDetector.java",
            "PropsUtil.java",
            "PropsValues.java",
            "RegexUtil.java",
            "StringPool.java",
            "StringUtil.java",
            "Validator.java"
    };

    public static final String[] POSHI_RUNTIME_LIBRARIES = new String[] {
            "accessors-smart-1.1.jar",
            "asm-5.0.3.jar",
            "async-http-client-2.12.3.jar",
            "async-http-client-netty-utils-2.12.3.jar",
            "auto-common-1.2.jar",
            "auto-service-1.0.1.jar",
            "auto-service-annotations-1.0.1.jar",
            "bcpkix-jdk15on-1.64.jar",
            "bcprov-jdk15on-1.64.jar",
            "browsermob-core-2.1.5.jar",
            "byte-buddy-1.14.4.jar",
            "checker-qual-3.12.0.jar",
            "com.liferay.poshi.core-1.0.132.jar",
            "com.liferay.poshi.runner.resources-1.0.17.jar",
            "com.liferay.poshi.runner-1.0.453.jar",
            "commons-compress-1.21.jar",
            "commons-exec-1.3.jar",
            "commons-io-2.11.0.jar",
            "commons-lang3-3.12.0.jar",
            "commons-lang-2.6.jar",
            "dec-0.1.2.jar",
            "dequeutilites-4.6.0.jar",
            "dnsjava-2.1.8.jar",
            "docker-java-3.3.2.jar",
            "docker-java-api-3.3.2.jar",
            "docker-java-core-3.3.2.jar",
            "docker-java-transport-3.3.2.jar",
            "docker-java-transport-httpclient5-3.3.2.jar",
            "dom4j-2.1.3.jar",
            "error_prone_annotations-2.11.0.jar",
            "failsafe-3.3.1.jar",
            "failureaccess-1.0.1.jar",
            "gson-2.10.1.jar",
            "guava-31.1-jre.jar",
            "hamcrest-core-1.3.jar",
            "httpclient5-5.2.1.jar",
            "httpcore5-5.2.jar",
            "httpcore5-h2-5.2.jar",
            "j2objc-annotations-1.3.jar",
            "jackson-annotations-2.14.1.jar",
            "jackson-core-2.14.1.jar",
            "jackson-databind-2.14.1.jar",
            "jakarta.activation-1.2.2.jar",
            "jakarta.mail-1.6.6.jar",
            "javacpp-0.10.jar",
            "javassist-3.28.0-GA.jar",
            "jcl-over-slf4j-1.7.30.jar",
            "jna-5.12.1.jar",
            "jodd.util-6.0.1.LIFERAY-PATCHED-1.jar",
            "json-20230227.jar",
            "json-path-2.1.0.jar",
            "json-smart-2.2.jar",
            "jsoup-1.15.3.jar",
            "jsr305-3.0.2.jar",
            "junit-4.13.1.jar",
            "jzlib-1.1.3.jar",
            "listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar",
            "littleproxy-1.1.0-beta-bmp-17.jar",
            "mitm-2.1.5.jar",
            "net.jsourcerer.webdriver.JSErrorCollector-0.6.jar",
            "netty-buffer-4.1.92.Final.jar",
            "netty-codec-4.1.92.Final.jar",
            "netty-codec-http-4.1.92.Final.jar",
            "netty-codec-socks-4.1.60.Final.jar",
            "netty-common-4.1.92.Final.jar",
            "netty-handler-4.1.92.Final.jar",
            "netty-handler-proxy-4.1.60.Final.jar",
            "netty-reactive-streams-2.0.4.jar",
            "netty-resolver-4.1.92.Final.jar",
            "netty-transport-4.1.92.Final.jar",
            "netty-transport-classes-epoll-4.1.92.Final.jar",
            "netty-transport-classes-kqueue-4.1.92.Final.jar",
            "netty-transport-native-epoll-4.1.92.Final.jar",
            "netty-transport-native-epoll-4.1.92.Final-linux-x86_64.jar",
            "netty-transport-native-kqueue-4.1.92.Final.jar",
            "netty-transport-native-kqueue-4.1.92.Final-osx-x86_64.jar",
            "netty-transport-native-unix-common-4.1.92.Final.jar",
            "ocular-1.0.5.jar",
            "opencv-2.4.10-0.10.jar",
            "opentelemetry-api-1.26.0.jar",
            "opentelemetry-api-events-1.26.0-alpha.jar",
            "opentelemetry-api-logs-1.26.0-alpha.jar",
            "opentelemetry-context-1.26.0.jar",
            "opentelemetry-exporter-logging-1.26.0.jar",
            "opentelemetry-extension-incubator-1.26.0-alpha.jar",
            "opentelemetry-sdk-1.26.0.jar",
            "opentelemetry-sdk-common-1.26.0.jar",
            "opentelemetry-sdk-extension-autoconfigure-1.26.0-alpha.jar",
            "opentelemetry-sdk-extension-autoconfigure-spi-1.26.0.jar",
            "opentelemetry-sdk-logs-1.26.0-alpha.jar",
            "opentelemetry-sdk-metrics-1.26.0.jar",
            "opentelemetry-sdk-trace-1.26.0.jar",
            "opentelemetry-semconv-1.26.0-alpha.jar",
            "piccolo2d-core-1.3.1.jar",
            "piccolo2d-extras-1.3.1.jar",
            "reactive-streams-1.0.3.jar",
            "reflections-0.10.2.jar",
            "scribejava-core-3.2.0.jar",
            "selenium-4.6.0.jar",
            "selenium-api-4.11.0.jar",
            "selenium-chrome-driver-4.11.0.jar",
            "selenium-chromium-driver-4.11.0.jar",
            "selenium-devtools-v85-4.11.0.jar",
            "selenium-devtools-v100-4.2.2.jar",
            "selenium-devtools-v113-4.11.0.jar",
            "selenium-devtools-v114-4.11.0.jar",
            "selenium-devtools-v115-4.11.0.jar",
            "selenium-edge-driver-4.11.0.jar",
            "selenium-firefox-driver-4.11.0.jar",
            "selenium-http-4.11.0.jar",
            "selenium-ie-driver-4.11.0.jar",
            "selenium-java-4.11.0.jar",
            "selenium-json-4.11.0.jar",
            "selenium-manager-4.11.0.jar",
            "selenium-remote-driver-4.11.0.jar",
            "selenium-safari-driver-4.11.0.jar",
            "selenium-support-4.11.0.jar",
            "sikuli-api-1.2.0.jar",
            "sikuli-core-1.2.2.jar",
            "slf4j-api-2.0.7.jar",
            "slf4j-simple-2.0.7.jar",
            "webdrivermanager-5.4.1.jar",
            "xml-apis-1.4.01.jar"
    };
}
