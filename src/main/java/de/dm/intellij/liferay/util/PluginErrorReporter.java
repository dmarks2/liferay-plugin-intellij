package de.dm.intellij.liferay.util;

import com.google.gson.JsonObject;
import com.intellij.ide.DataManager;
import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManagerCore;
import com.intellij.ide.plugins.PluginUtil;
import com.intellij.idea.IdeaLogger;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ApplicationNamesInfo;
import com.intellij.openapi.application.ex.ApplicationInfoEx;
import com.intellij.openapi.diagnostic.ErrorReportSubmitter;
import com.intellij.openapi.diagnostic.IdeaLoggingEvent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.diagnostic.SubmittedReportInfo;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.openapi.progress.EmptyProgressIndicator;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.util.Consumer;
import com.intellij.util.ExceptionUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Error Reporting in case of crashes.
 */
public class PluginErrorReporter extends ErrorReportSubmitter {

    private final static Logger log = Logger.getInstance(PluginErrorReporter.class);

    @NotNull
    @Override
    public String getReportActionText() {
        return "Report an Issue to the Developer";
    }

    @Override
    public boolean submit(IdeaLoggingEvent @NotNull [] events, @Nullable String additionalInfo, @NotNull Component parentComponent, @NotNull final Consumer<? super SubmittedReportInfo> consumer) {
        IdeaLoggingEvent event = events[0];

        DataContext dataContext = DataManager.getInstance().getDataContext(parentComponent);
        Project project = CommonDataKeys.PROJECT.getData(dataContext);

        final Consumer<String> success = s -> consumer.consume(
            new SubmittedReportInfo(null, s, SubmittedReportInfo.SubmissionStatus.NEW_ISSUE)
        );

        Task task = new Task.Backgroundable(project, "Sending error report", true) {

            @Override
            public void run(@NotNull ProgressIndicator indicator) {
                indicator.setIndeterminate(true);

                try {
                    String url = "https://api.github.com/repos/dmarks2/liferay-plugin-intellij/issues";

                    //see https://developer.github.com/v3/issues/
                    HttpURLConnection httpURLConnection = (HttpURLConnection)new URL(url).openConnection();
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setRequestProperty("Content-Type", "application/vnd.github.v3+json");
                    String token = getToken();
                    if (token != null) {
                        httpURLConnection.setRequestProperty("Authorization", "token " + token);
                    }

                    StringBuilder body = new StringBuilder();
                    body.append(additionalInfo).append("\n\n");
                    body.append(event.getThrowable() != null ? ExceptionUtil.getThrowableText(event.getThrowable()) : null).append("\n\n");
                    body.append(event.getMessage()).append("\n\n");

                    body.append("os.name: " + SystemInfo.OS_NAME).append("\n");
                    body.append("java.version: " + SystemInfo.JAVA_VERSION).append("\n");
                    body.append("java.vm.vendor: " + SystemInfo.JAVA_VENDOR).append("\n\n");

                    ApplicationInfoEx appInfo = ApplicationInfoEx.getInstanceEx();
                    ApplicationNamesInfo namesInfo = ApplicationNamesInfo.getInstance();
                    Application application = ApplicationManager.getApplication();

                    body.append("application.name: " + namesInfo.getProductName()).append("\n");
                    body.append("application.name.full: " + namesInfo.getFullProductName()).append("\n");
                    body.append("application.name.version: " + appInfo.getVersionName()).append("\n");
                    body.append("application.eap: " + appInfo.isEAP()).append("\n");
                    body.append("application.internal " + application.isInternal()).append("\n");
                    body.append("application.build.number: " + appInfo.getBuild().asString()).append("\n");
                    body.append("application.version.number: " + appInfo.getMajorVersion() + "." + appInfo.getMinorVersion()).append("\n");

                    if (event.getThrowable() != null) {
                        PluginId pluginId = PluginUtil.getInstance().findPluginId(event.getThrowable());
                        if (pluginId != null) {
                            IdeaPluginDescriptor plugin = PluginManagerCore.getPlugin(pluginId);
                            if ( (plugin != null) && (!plugin.isBundled()) ) {
                                body.append("plugin.name: "  + plugin.getName()).append("\n");
                                body.append("application.version: " + plugin.getVersion()).append("\n");
                            }
                        }
                    }

                    body.append("last.action: " + IdeaLogger.ourLastActionId).append("\n");

                    JsonObject jsonObject = new JsonObject();

                    jsonObject.addProperty("title", "Bug Report in Plugin [automatically created]");
                    jsonObject.addProperty("body", body.toString());

                    byte[] bytes = jsonObject.toString().getBytes(StandardCharsets.UTF_8);

					try (OutputStream outputStream = httpURLConnection.getOutputStream()) {
						outputStream.write(bytes);
					}

                    int responseCode = httpURLConnection.getResponseCode();
                    if (responseCode == 201) {
                        success.consume(httpURLConnection.getHeaderField("Location"));
                    } else {
                        //TODO error handling
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        };

        if (project == null) {
            task.run(new EmptyProgressIndicator());
        } else {
            ProgressManager.getInstance().run(task);
        }

        return true;
    }

    private static String getToken() {
        InputStream inputStream = PluginErrorReporter.class.getClassLoader().getResourceAsStream("/token");
        if (inputStream == null) {
            return null;
        }
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
			StringBuilder result = new StringBuilder();

			String line;
			while ((line = bufferedReader.readLine()) != null) {
				result.append(line);
			}

			return result.toString();
		} catch (IOException e) {
			//ignore
		}
		//ignore

		return null;
    }

}
