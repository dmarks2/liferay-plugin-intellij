package de.dm.intellij.liferay.language.poshi.runner;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.application.BaseJavaApplicationCommandLineState;
import com.intellij.execution.configurations.JavaParameters;
import com.intellij.execution.configurations.ParametersList;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.util.JavaParametersUtil;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.util.PathsList;
import de.dm.intellij.liferay.language.groovy.LiferayServerGroovyConsoleLinkFilter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class PoshiTestcaseCommandLineState extends BaseJavaApplicationCommandLineState<PoshiTestcaseConfiguration> {

    public PoshiTestcaseCommandLineState(ExecutionEnvironment environment, @NotNull PoshiTestcaseConfiguration configuration) {
        super(environment, configuration);

        addConsoleFilters(new LiferayServerGroovyConsoleLinkFilter(environment.getProject(), getConfiguration().getScriptName()));
    }

    @Override
    protected JavaParameters createJavaParameters() throws ExecutionException {
        JavaParameters javaParameters = new JavaParameters();

        String currentClassPath = '/' + this.getClass().getName().replace('.','/') + ".class";

        URL resource = this.getClass().getResource(currentClassPath);

        String file = resource.getFile();

        String baseName = file.substring(0, file.indexOf(currentClassPath));

        if (baseName.endsWith("!")) {
            baseName = baseName.substring(0, baseName.length() -1);
        }

        if (baseName.startsWith("file:/")) {
            baseName = baseName.substring(6);
        }

        baseName = URLDecoder.decode(baseName, StandardCharsets.UTF_8);

        PathsList classPath = javaParameters.getClassPath();

        classPath.add(baseName);

        addClassPath(classPath, "accessors-smart-1.1.jar");
        addClassPath(classPath, "asm-5.0.3.jar");
        addClassPath(classPath, "bcpkix-jdk15on-1.64.jar");
        addClassPath(classPath, "bcprov-jdk15on-1.64.jar");
        addClassPath(classPath, "browsermob-core-2.1.5.jar");
        addClassPath(classPath, "byte-buddy-1.8.15.jar");
        addClassPath(classPath, "checker-qual-3.5.0.jar");
        addClassPath(classPath, "com.liferay.poshi.core-1.0.87.jar");
        addClassPath(classPath, "com.liferay.poshi.runner.resources-1.0.14.jar");
        addClassPath(classPath, "com.liferay.poshi.runner-1.0.396.jar");
        addClassPath(classPath, "commons-codec-1.15.jar");
        addClassPath(classPath, "commons-compress-1.21.jar");
        addClassPath(classPath, "commons-exec-1.3.jar");
        addClassPath(classPath, "commons-io-2.11.0.jar");
        addClassPath(classPath, "commons-lang3-3.12.0.jar");
        addClassPath(classPath, "commons-lang-2.6.jar");
        addClassPath(classPath, "dec-0.1.2.jar");
        addClassPath(classPath, "dnsjava-2.1.8.jar");
        addClassPath(classPath, "docker-java-3.2.13.jar");
        addClassPath(classPath, "docker-java-api-3.2.13.jar");
        addClassPath(classPath, "docker-java-core-3.2.13.jar");
        addClassPath(classPath, "docker-java-transport-3.2.13.jar");
        addClassPath(classPath, "docker-java-transport-httpclient5-3.2.13.jar");
        addClassPath(classPath, "dom4j-2.1.3.jar");
        addClassPath(classPath, "error_prone_annotations-2.3.4.jar");
        addClassPath(classPath, "failureaccess-1.0.1.jar");
        addClassPath(classPath, "gson-2.10.jar");
        addClassPath(classPath, "guava-30.1-jre.jar");
        addClassPath(classPath, "hamcrest-core-1.3.jar");
        addClassPath(classPath, "httpclient5-5.1.3.jar");
        addClassPath(classPath, "httpcore5-5.1.3.jar");
        addClassPath(classPath, "httpcore5-h2-5.1.3.jar");
        addClassPath(classPath, "j2objc-annotations-1.3.jar");
        addClassPath(classPath, "jackson-annotations-2.11.1.jar");
        addClassPath(classPath, "jackson-core-2.11.1.jar");
        addClassPath(classPath, "jackson-databind-2.11.1.jar");
        addClassPath(classPath, "jakarta.activation-1.2.1.jar");
        addClassPath(classPath, "jakarta.mail-1.6.6.jar");
        addClassPath(classPath, "javacpp-0.10.jar");
        addClassPath(classPath, "javassist-3.28.0-GA.jar");
        addClassPath(classPath, "jcl-over-slf4j-1.7.30.jar");
        addClassPath(classPath, "jna-5.8.0.jar");
        addClassPath(classPath, "jodd.util-6.0.1.LIFERAY-PATCHED-1.jar");
        addClassPath(classPath, "json-20220320.jar");
        addClassPath(classPath, "json-path-2.1.0.jar");
        addClassPath(classPath, "json-smart-2.2.jar");
        addClassPath(classPath, "jsoup-1.15.3.jar");
        addClassPath(classPath, "jsr305-3.0.2.jar");
        addClassPath(classPath, "junit-4.13.1.jar");
        addClassPath(classPath, "jzlib-1.1.3.jar");
        addClassPath(classPath, "listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar");
        addClassPath(classPath, "littleproxy-1.1.0-beta-bmp-17.jar");
        addClassPath(classPath, "mitm-2.1.5.jar");
        addClassPath(classPath, "net.jsourcerer.webdriver.JSErrorCollector-0.6.jar");
        addClassPath(classPath, "netty-all-4.0.51.Final.jar");
        addClassPath(classPath, "ocular-1.0.5.jar");
        addClassPath(classPath, "okhttp-3.12.12.jar");
        addClassPath(classPath, "okio-1.15.0.jar");
        addClassPath(classPath, "opencv-2.4.10-0.10.jar");
        addClassPath(classPath, "piccolo2d-core-1.3.1.jar");
        addClassPath(classPath, "piccolo2d-extras-1.3.1.jar");
        addClassPath(classPath, "reflections-0.10.2.jar");
        addClassPath(classPath, "scribejava-core-3.2.0.jar");
        addClassPath(classPath, "selenium-4.3.2.jar");
        addClassPath(classPath, "selenium-api-3.141.59.jar");
        addClassPath(classPath, "selenium-chrome-driver-3.141.59.jar");
        addClassPath(classPath, "selenium-edge-driver-3.141.59.jar");
        addClassPath(classPath, "selenium-firefox-driver-3.141.59.jar");
        addClassPath(classPath, "selenium-ie-driver-3.141.59.jar");
        addClassPath(classPath, "selenium-java-3.141.59.jar");
        addClassPath(classPath, "selenium-opera-driver-3.141.59.jar");
        addClassPath(classPath, "selenium-remote-driver-3.141.59.jar");
        addClassPath(classPath, "selenium-safari-driver-3.141.59.jar");
        addClassPath(classPath, "selenium-support-3.141.59.jar");
        addClassPath(classPath, "sikuli-api-1.2.0.jar");
        addClassPath(classPath, "sikuli-core-1.2.2.jar");
        addClassPath(classPath, "slf4j-api-2.0.3.jar");
        addClassPath(classPath, "webdrivermanager-5.3.1.jar");
        addClassPath(classPath, "xml-apis-1.4.01.jar");

        ParametersList programParametersList = javaParameters.getProgramParametersList();

        programParametersList.add(getConfiguration().getScriptName());
        programParametersList.add(getConfiguration().getTestName());

        javaParameters.setJdk(JavaParametersUtil.createProjectJdk(getConfiguration().getProject(), null));

        javaParameters.setMainClass("de.dm.intellij.liferay.language.poshi.scripts.PoshiStandaloneRunner");

        File workingDirectory = new File(getConfiguration().getScriptName());
        workingDirectory = workingDirectory.getParentFile();
        workingDirectory = workingDirectory.getParentFile();

        javaParameters.setWorkingDirectory(workingDirectory);

        return javaParameters;
    }

    private void addClassPath(PathsList classPath, String source) {
        Properties properties = System.getProperties();

        File temp = new File(properties.getProperty("user.home"), ".liferay-plugin-intellij");

        File targetFile = new File(temp, source);

        classPath.add(targetFile);
    }

    public static void copyLib(String source) {
        ClassLoader classLoader = PoshiTestcaseCommandLineState.class.getClassLoader();

        Properties properties = System.getProperties();

        File temp = new File(properties.getProperty("user.home"), ".liferay-plugin-intellij");

        temp.mkdirs();

        try (InputStream inputStream = classLoader.getResourceAsStream("lib/" + source)) {
            if (inputStream != null) {
                File targetFile = new File(temp, source);

                try (FileOutputStream outputStream = new FileOutputStream(targetFile)) {
                    FileUtil.copy(inputStream, outputStream);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
