package de.dm.intellij.liferay.client;

import com.intellij.openapi.diagnostic.Logger;
import de.dm.intellij.liferay.util.LiferayVersions;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.SystemDefaultRoutePlanner;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LiferayGithubClient {

	private final static Logger log = Logger.getInstance(LiferayGithubClient.class);
	private static final String RAW_GITHUB_LIFERAY_PORTAL_URL = "https://raw.githubusercontent.com/liferay/liferay-portal";

	private final HttpClient httpClient;

	public LiferayGithubClient() {
		HttpClientBuilder httpClientBuilder = HttpClientBuilder
				.create()
				.setRoutePlanner(new SystemDefaultRoutePlanner(null));

		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(3000)
				.setSocketTimeout(3000)
				.setConnectionRequestTimeout(3000)
				.build();

		httpClientBuilder.setDefaultRequestConfig(requestConfig);

		httpClient = httpClientBuilder.build();
	}

	public String getGithubFileContent(String path) throws URISyntaxException, IOException {
		URI uri = new URI(RAW_GITHUB_LIFERAY_PORTAL_URL + path);

		if (log.isDebugEnabled()) {
			log.debug("Requesting " + uri);
		}

		HttpGet httpGet = new HttpGet(uri);

		HttpResponse response = httpClient.execute(httpGet);

		HttpEntity entity = response.getEntity();

		return EntityUtils.toString(entity);
	}

	public String getBundleVersion(String liferayVersion, String path) throws URISyntaxException, IOException {
		String gaVersion = LiferayVersions.getGAVersion(liferayVersion);

		String bndFile = getGithubFileContent("/" + gaVersion + path);

		String bundleVersion = bndFile.lines().filter(
				line -> line.startsWith("Bundle-Version")
		).findFirst().orElse(null);

		if (bundleVersion != null) {
			return bundleVersion.substring(16);
		}

		return null;
	}

	public String getBuildArtifactVersion(String liferayVersion, String path) throws URISyntaxException, IOException {
		String gaVersion = LiferayVersions.getGAVersion(liferayVersion);

		String buildFile = getGithubFileContent("/" + gaVersion + path);

		String artifactVersion = buildFile.lines().filter(
				line -> line.contains("property name=\"artifact.version\" value=\"")
		).findFirst().orElse(null);

		if (artifactVersion != null) {
			Pattern pattern = Pattern.compile("value=\"(.*)\"");

			Matcher matcher = pattern.matcher(artifactVersion);

			if (matcher.find()) {
				return matcher.group(1);
			}
		}

		return null;
	}

}
