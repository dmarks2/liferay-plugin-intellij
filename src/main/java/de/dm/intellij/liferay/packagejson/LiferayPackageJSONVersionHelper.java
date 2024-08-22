package de.dm.intellij.liferay.packagejson;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import de.dm.intellij.liferay.client.LiferayGithubClient;
import de.dm.intellij.liferay.util.LiferayVersions;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class LiferayPackageJSONVersionHelper {

	private final static Logger log = Logger.getInstance(LiferayPackageJSONVersionHelper.class);

	private static final String LIFERAY_FRONTEND_CSS_COMMON = "liferay-frontend-css-common";
	private static final String LIFERAY_FRONTEND_THEME_STYLED = "liferay-frontend-theme-styled";
	private static final String LIFERAY_FRONTEND_THEME_UNSTYLED = "liferay-frontend-theme-unstyled";
	private static final String LIFERAY_THEME_TASKS = "liferay-theme-tasks";
	private static final String GULP = "gulp";

	public static final Map<String, Function<String, String>> LIFERAY_DEV_DEPENDENCIES = new HashMap<>();

	static {
		LIFERAY_DEV_DEPENDENCIES.put(LIFERAY_FRONTEND_CSS_COMMON, (liferayVersion) -> getPluginVersionFromBndFile(liferayVersion, "/modules/apps/frontend-css/frontend-css-common/bnd.bnd"));
		LIFERAY_DEV_DEPENDENCIES.put(LIFERAY_FRONTEND_THEME_STYLED, (liferayVersion) -> getPluginVersionFromBndFile(liferayVersion, "/modules/apps/frontend-theme/frontend-theme-styled/bnd.bnd"));
		LIFERAY_DEV_DEPENDENCIES.put(LIFERAY_FRONTEND_THEME_UNSTYLED, (liferayVersion) -> getPluginVersionFromBndFile(liferayVersion, "/modules/apps/frontend-theme/frontend-theme-unstyled/bnd.bnd"));
		LIFERAY_DEV_DEPENDENCIES.put(LIFERAY_THEME_TASKS, (liferayVersion) -> getYarnLockVersion(liferayVersion, "/modules/yarn.lock", LIFERAY_THEME_TASKS));
		LIFERAY_DEV_DEPENDENCIES.put(GULP, (liferayVersion) -> getYarnLockVersion(liferayVersion, "/modules/yarn.lock", GULP));
	}

	private static String getPluginVersionFromBndFile(String liferayVersion, String path) {
		if (ApplicationManager.getApplication().isUnitTestMode()) {
			return "9.9.9";
		}

		try {
			LiferayGithubClient githubClient = new LiferayGithubClient();

			String bundleVersion = githubClient.getBundleVersion(liferayVersion, path);

			if (bundleVersion != null) {
				bundleVersion = LiferayVersions.minusOne(bundleVersion);

				return bundleVersion;
			}
		} catch (URISyntaxException | IOException e) {
			if (log.isDebugEnabled()) {
				log.debug("Unable to fetch plugin version from github: " + e.getMessage());
			}
		}

		return null;
	}

	private static String getYarnLockVersion(String liferayVersion, String githubPath, String dependencyName) {
		if (ApplicationManager.getApplication().isUnitTestMode()) {
			return "9.9.9";
		}

		try {
			LiferayGithubClient githubClient = new LiferayGithubClient();

			return githubClient.getYarnLockVersion(liferayVersion, githubPath, dependencyName);
		} catch (URISyntaxException | IOException e) {
			if (log.isDebugEnabled()) {
				log.debug("Unable to fetch plugin version from github: " + e.getMessage());
			}
		}

		return null;
	}
}


