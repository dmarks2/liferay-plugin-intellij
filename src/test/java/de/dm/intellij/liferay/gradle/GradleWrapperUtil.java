package de.dm.intellij.liferay.gradle;

import com.intellij.ide.starters.local.StandardAssetsProvider;
import com.intellij.ide.starters.local.generator.AssetsProcessor;
import org.gradle.util.GradleVersion;
import org.gradle.wrapper.WrapperConfiguration;
import org.jetbrains.plugins.gradle.util.GradleUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class GradleWrapperUtil {

	public static void generateGradleWrapper(Path root, GradleVersion gradleVersion) {
		generateGradleWrapper(root, generateGradleWrapperConfiguration(gradleVersion));
	}

	private static void generateGradleWrapper(Path root, WrapperConfiguration configuration) {
		StandardAssetsProvider standardAssetsProvider = new StandardAssetsProvider();

		String propertiesLocation = standardAssetsProvider.getGradleWrapperPropertiesLocation();

		Path propertiesFile = root.resolve(propertiesLocation);

		if (! Files.exists(propertiesFile)) {
			try {
				Files.createDirectories(propertiesFile.getParent());

				Files.createFile(propertiesFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		GradleUtil.writeWrapperConfiguration(configuration, propertiesFile);

		AssetsProcessor.getInstance().generateSources(root, standardAssetsProvider.getGradlewAssets(), new HashMap<>());
	}

	private static WrapperConfiguration generateGradleWrapperConfiguration(GradleVersion gradleVersion) {
		WrapperConfiguration wrapperConfiguration = new WrapperConfiguration();

		wrapperConfiguration.setDistribution(GradleUtil.getWrapperDistributionUri(gradleVersion));

		return wrapperConfiguration;
	}
}
