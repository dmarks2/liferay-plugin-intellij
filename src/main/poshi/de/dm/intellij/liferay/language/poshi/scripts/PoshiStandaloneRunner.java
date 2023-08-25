package de.dm.intellij.liferay.language.poshi.scripts;

import com.liferay.poshi.core.PoshiContext;
import com.liferay.poshi.core.PoshiGetterUtil;
import com.liferay.poshi.core.util.PropsUtil;
import com.liferay.poshi.runner.PoshiRunner;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.util.Properties;

public class PoshiStandaloneRunner {

    public static void main(String[] args) throws Throwable {
        if (args.length < 2) {
            System.err.println("Invalid syntax");

            System.exit(1);
        }

        String scriptName = args[0];
        String testName = args[1];

        File scriptFile = new File(scriptName);

        if (! scriptFile.exists()) {
            System.err.println("Script file " + scriptName + " does not exist!");

            System.exit(1);
        }

        File parent = scriptFile.getParentFile();

        if ("testcases".equals(parent.getName())) {
            Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
				System.err.println("Error while executing " + thread.getName() + ": " + throwable.getClass().getName() + ": " + throwable.getMessage());

				throwable.printStackTrace();
			});

            String baseDirName = parent.getParent();

            String namespacedClassCommandName = "LocalFile." + PoshiGetterUtil.getClassNameFromFilePath(scriptName) + "#" + testName;

            _initialize(baseDirName);

            PoshiRunner poshiRunner = new PoshiRunner(namespacedClassCommandName);
            try {
                try {
                    poshiRunner.setUp();

                    poshiRunner.test();
                } catch (Throwable throwable) {
                    System.err.println("Error executing " + scriptName + "." + testName + ": " + throwable.getMessage());

                    throwable.printStackTrace();

                    throw throwable;
                }
            } finally {
                poshiRunner.tearDown();
            }
        } else {
            System.err.println("Script file " + scriptName + " must be inside a \"testcases\" directory!");

            System.exit(1);
        }
    }

    private static void _initialize(String testBaseDirName) throws Exception {
        WebDriverManager chromedriver = WebDriverManager.chromedriver();

        chromedriver.setup();

        Properties properties = new Properties();

        File file = new File(chromedriver.getDownloadedDriverPath());

        properties.setProperty("selenium.executable.dir.name", file.getParent() + File.separator);
        properties.setProperty("selenium.chrome.driver.executable", file.getName());

        if (chromedriver.getBrowserPath().isPresent()) {
            properties.setProperty("browser.chrome.bin.file", chromedriver.getBrowserPath().get().toString());
        }

        properties.setProperty("test.base.dir.name", testBaseDirName);

        PropsUtil.setProperties(properties);

        PoshiContext.clear();

        PoshiContext.readFiles();
    }
}
