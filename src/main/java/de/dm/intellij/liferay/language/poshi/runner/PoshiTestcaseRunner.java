package de.dm.intellij.liferay.language.poshi.runner;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.RunProfile;
import com.intellij.execution.executors.DefaultRunExecutor;
import com.intellij.execution.impl.DefaultJavaProgramRunner;
import com.intellij.execution.runners.ExecutionEnvironment;
import org.jetbrains.annotations.NotNull;

public class PoshiTestcaseRunner extends DefaultJavaProgramRunner {

    @Override
    public @NotNull String getRunnerId() {
        return "PoshiTestcaseRunner";
    }

    @Override
    public boolean canRun(@NotNull String executorId, @NotNull RunProfile profile) {
        return DefaultRunExecutor.EXECUTOR_ID.equals(executorId) && profile instanceof PoshiTestcaseConfiguration;
    }

    @Override
    public void execute(@NotNull ExecutionEnvironment environment) throws ExecutionException {
        PoshiTestcaseCommandLineState.copyLib("accessors-smart-1.1.jar");
        PoshiTestcaseCommandLineState.copyLib("asm-5.0.3.jar");
        PoshiTestcaseCommandLineState.copyLib("bcpkix-jdk15on-1.64.jar");
        PoshiTestcaseCommandLineState.copyLib("bcprov-jdk15on-1.64.jar");
        PoshiTestcaseCommandLineState.copyLib("browsermob-core-2.1.5.jar");
        PoshiTestcaseCommandLineState.copyLib("byte-buddy-1.8.15.jar");
        PoshiTestcaseCommandLineState.copyLib("checker-qual-3.5.0.jar");
        PoshiTestcaseCommandLineState.copyLib("com.liferay.poshi.core-1.0.87.jar");
        PoshiTestcaseCommandLineState.copyLib("com.liferay.poshi.runner.resources-1.0.14.jar");
        PoshiTestcaseCommandLineState.copyLib("com.liferay.poshi.runner-1.0.396.jar");
        PoshiTestcaseCommandLineState.copyLib("commons-codec-1.15.jar");
        PoshiTestcaseCommandLineState.copyLib("commons-compress-1.21.jar");
        PoshiTestcaseCommandLineState.copyLib("commons-exec-1.3.jar");
        PoshiTestcaseCommandLineState.copyLib("commons-io-2.11.0.jar");
        PoshiTestcaseCommandLineState.copyLib("commons-lang3-3.12.0.jar");
        PoshiTestcaseCommandLineState.copyLib("commons-lang-2.6.jar");
        PoshiTestcaseCommandLineState.copyLib("dec-0.1.2.jar");
        PoshiTestcaseCommandLineState.copyLib("dnsjava-2.1.8.jar");
        PoshiTestcaseCommandLineState.copyLib("docker-java-3.2.13.jar");
        PoshiTestcaseCommandLineState.copyLib("docker-java-api-3.2.13.jar");
        PoshiTestcaseCommandLineState.copyLib("docker-java-core-3.2.13.jar");
        PoshiTestcaseCommandLineState.copyLib("docker-java-transport-3.2.13.jar");
        PoshiTestcaseCommandLineState.copyLib("docker-java-transport-httpclient5-3.2.13.jar");
        PoshiTestcaseCommandLineState.copyLib("dom4j-2.1.3.jar");
        PoshiTestcaseCommandLineState.copyLib("error_prone_annotations-2.3.4.jar");
        PoshiTestcaseCommandLineState.copyLib("failureaccess-1.0.1.jar");
        PoshiTestcaseCommandLineState.copyLib("gson-2.10.jar");
        PoshiTestcaseCommandLineState.copyLib("guava-30.1-jre.jar");
        PoshiTestcaseCommandLineState.copyLib("hamcrest-core-1.3.jar");
        PoshiTestcaseCommandLineState.copyLib("httpclient5-5.1.3.jar");
        PoshiTestcaseCommandLineState.copyLib("httpcore5-5.1.3.jar");
        PoshiTestcaseCommandLineState.copyLib("httpcore5-h2-5.1.3.jar");
        PoshiTestcaseCommandLineState.copyLib("j2objc-annotations-1.3.jar");
        PoshiTestcaseCommandLineState.copyLib("jackson-annotations-2.11.1.jar");
        PoshiTestcaseCommandLineState.copyLib("jackson-core-2.11.1.jar");
        PoshiTestcaseCommandLineState.copyLib("jackson-databind-2.11.1.jar");
        PoshiTestcaseCommandLineState.copyLib("jakarta.activation-1.2.1.jar");
        PoshiTestcaseCommandLineState.copyLib("jakarta.mail-1.6.6.jar");
        PoshiTestcaseCommandLineState.copyLib("javacpp-0.10.jar");
        PoshiTestcaseCommandLineState.copyLib("javassist-3.28.0-GA.jar");
        PoshiTestcaseCommandLineState.copyLib("jcl-over-slf4j-1.7.30.jar");
        PoshiTestcaseCommandLineState.copyLib("jna-5.8.0.jar");
        PoshiTestcaseCommandLineState.copyLib("jodd.util-6.0.1.LIFERAY-PATCHED-1.jar");
        PoshiTestcaseCommandLineState.copyLib("json-20220320.jar");
        PoshiTestcaseCommandLineState.copyLib("json-path-2.1.0.jar");
        PoshiTestcaseCommandLineState.copyLib("json-smart-2.2.jar");
        PoshiTestcaseCommandLineState.copyLib("jsoup-1.15.3.jar");
        PoshiTestcaseCommandLineState.copyLib("jsr305-3.0.2.jar");
        PoshiTestcaseCommandLineState.copyLib("junit-4.13.1.jar");
        PoshiTestcaseCommandLineState.copyLib("jzlib-1.1.3.jar");
        PoshiTestcaseCommandLineState.copyLib("listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar");
        PoshiTestcaseCommandLineState.copyLib("littleproxy-1.1.0-beta-bmp-17.jar");
        PoshiTestcaseCommandLineState.copyLib("mitm-2.1.5.jar");
        PoshiTestcaseCommandLineState.copyLib("net.jsourcerer.webdriver.JSErrorCollector-0.6.jar");
        PoshiTestcaseCommandLineState.copyLib("netty-all-4.0.51.Final.jar");
        PoshiTestcaseCommandLineState.copyLib("ocular-1.0.5.jar");
        PoshiTestcaseCommandLineState.copyLib("okhttp-3.12.12.jar");
        PoshiTestcaseCommandLineState.copyLib("okio-1.15.0.jar");
        PoshiTestcaseCommandLineState.copyLib("opencv-2.4.10-0.10.jar");
        PoshiTestcaseCommandLineState.copyLib("piccolo2d-core-1.3.1.jar");
        PoshiTestcaseCommandLineState.copyLib("piccolo2d-extras-1.3.1.jar");
        PoshiTestcaseCommandLineState.copyLib("reflections-0.10.2.jar");
        PoshiTestcaseCommandLineState.copyLib("scribejava-core-3.2.0.jar");
        PoshiTestcaseCommandLineState.copyLib("selenium-4.3.2.jar");
        PoshiTestcaseCommandLineState.copyLib("selenium-api-3.141.59.jar");
        PoshiTestcaseCommandLineState.copyLib("selenium-chrome-driver-3.141.59.jar");
        PoshiTestcaseCommandLineState.copyLib("selenium-edge-driver-3.141.59.jar");
        PoshiTestcaseCommandLineState.copyLib("selenium-firefox-driver-3.141.59.jar");
        PoshiTestcaseCommandLineState.copyLib("selenium-ie-driver-3.141.59.jar");
        PoshiTestcaseCommandLineState.copyLib("selenium-java-3.141.59.jar");
        PoshiTestcaseCommandLineState.copyLib("selenium-opera-driver-3.141.59.jar");
        PoshiTestcaseCommandLineState.copyLib("selenium-remote-driver-3.141.59.jar");
        PoshiTestcaseCommandLineState.copyLib("selenium-safari-driver-3.141.59.jar");
        PoshiTestcaseCommandLineState.copyLib("selenium-support-3.141.59.jar");
        PoshiTestcaseCommandLineState.copyLib("sikuli-api-1.2.0.jar");
        PoshiTestcaseCommandLineState.copyLib("sikuli-core-1.2.2.jar");
        PoshiTestcaseCommandLineState.copyLib("slf4j-api-2.0.3.jar");
        PoshiTestcaseCommandLineState.copyLib("webdrivermanager-5.3.1.jar");
        PoshiTestcaseCommandLineState.copyLib("xml-apis-1.4.01.jar");

        super.execute(environment);
    }
}
