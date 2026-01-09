package de.dm.intellij.liferay.action;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.externalSystem.model.DataNode;
import com.intellij.openapi.externalSystem.model.ExternalProjectInfo;
import com.intellij.openapi.externalSystem.model.ProjectKeys;
import com.intellij.openapi.externalSystem.model.execution.ExternalSystemTaskExecutionSettings;
import com.intellij.openapi.externalSystem.model.project.ProjectData;
import com.intellij.openapi.externalSystem.model.task.TaskData;
import com.intellij.openapi.externalSystem.service.execution.ProgressExecutionMode;
import com.intellij.openapi.externalSystem.service.project.ProjectDataManager;
import com.intellij.openapi.externalSystem.util.ExternalSystemApiUtil;
import com.intellij.openapi.externalSystem.util.ExternalSystemUtil;
import com.intellij.openapi.externalSystem.util.task.TaskExecutionSpec;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import de.dm.intellij.liferay.util.ProjectUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.idea.maven.execution.MavenRunner;
import org.jetbrains.idea.maven.execution.MavenRunnerParameters;
import org.jetbrains.idea.maven.project.MavenProject;
import org.jetbrains.idea.maven.project.MavenProjectsManager;
import org.jetbrains.plugins.gradle.util.GradleConstants;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class RunSourceFormatterAction extends AnAction {

	@Override
	public void actionPerformed(AnActionEvent actionEvent) {
		Project project = actionEvent.getProject();

		VirtualFile virtualFile = getSelectedFile(actionEvent);

		if (project == null || project.getBasePath() == null || virtualFile == null || !virtualFile.isValid()) {
			return;
		}

		FileDocumentManager.getInstance().saveAllDocuments();

		ApplicationManager.getApplication().executeOnPooledThread(() -> {
			try {
				runMavenSourceFormatter(project, virtualFile);

				ApplicationManager.getApplication().invokeLater(() ->
						virtualFile.refresh(false, false)
				);
			 } catch (Exception exception) {
				Notifications.Bus.notify(
						new Notification(
								"Incorrect Formatting",
								"Formatting with liferay source formatter failed",
								exception.getMessage(),
								NotificationType.ERROR
						),
						project
				);
			}
		});
	}

	@Override
	public void update(@NotNull AnActionEvent actionEvent) {
		Project project = actionEvent.getProject();

		VirtualFile virtualFile = getSelectedFile(actionEvent);

		if (project != null && virtualFile != null && virtualFile.isValid() && !virtualFile.isDirectory()) {
			actionEvent.getPresentation().setEnabled(isSourceFormatterAvailable(project, virtualFile));
		}
	}


	@Nullable
	private static VirtualFile getSelectedFile(@NotNull AnActionEvent actionEvent) {
		VirtualFile[] array = actionEvent.getData(CommonDataKeys.VIRTUAL_FILE_ARRAY);

		if (array == null || array.length != 1 || array[0].isDirectory()) {
			return null;
		}

		return array[0];
	}

	private boolean isSourceFormatterAvailable(Project project, VirtualFile virtualFile) {
		if (ProjectUtils.isMavenProject(project, virtualFile)) {
			MavenProject mavenProject = MavenProjectsManager.getInstance(project).findProject(virtualFile);
			if (mavenProject != null) {
				return mavenProject.findPlugin(
						"com.liferay",
						"com.liferay.source.formatter") != null;
			}
		} else if (ProjectUtils.isGradleProject(project)) {
			Collection<ExternalProjectInfo> projectInfos = ProjectDataManager.getInstance().getExternalProjectsData(project, GradleConstants.SYSTEM_ID);

			for (ExternalProjectInfo projectInfo : projectInfos) {
				DataNode<ProjectData> projectData = projectInfo.getExternalProjectStructure();

				if (projectData != null) {
					Collection<DataNode<TaskData>> taskNodes = ExternalSystemApiUtil.findAll(projectData, ProjectKeys.TASK);

					if (taskNodes.stream().anyMatch(task -> task.getData().getName().equals("sourceFormatter"))) {
						return true;
					}
				}
			}
		}

		return false;
	}

	private void runMavenSourceFormatter(Project project, VirtualFile virtualFile) {
		File file = new File(virtualFile.getPath());

		if (ProjectUtils.isMavenProject(project, virtualFile)) {
			MavenProject mavenProject = MavenProjectsManager.getInstance(project).findProject(virtualFile);

			if (mavenProject != null && mavenProject.findPlugin(
					"com.liferay",
					"com.liferay.source.formatter") != null) {

				MavenRunner mavenRunner = MavenRunner.getInstance(project);

				mavenRunner.run(
						new MavenRunnerParameters(
								true,
								mavenProject.getDirectory(),
								mavenProject.getFile().getName(),
								List.of(
										"source-formatter:format",
										"-DfileNames=" + file.getPath()
								),
								new ArrayList<>()
						),
						null,
						null
				);
			}
		} else if (ProjectUtils.isGradleProject(project)) {
			Map<String, String> env = Map.of(
					"source.formatter.files", file.getPath()
			);

			ExternalSystemTaskExecutionSettings externalSystemTaskExecutionSettings = new ExternalSystemTaskExecutionSettings();

			externalSystemTaskExecutionSettings.setEnv(env);

			TaskExecutionSpec taskExecutionSpec = TaskExecutionSpec.create()
					.withProject(project)
					.withSystemId(GradleConstants.SYSTEM_ID)
					.withExecutorId("sourceFormatter")
					.withSettings(externalSystemTaskExecutionSettings)
					.withProgressExecutionMode(ProgressExecutionMode.IN_BACKGROUND_ASYNC)
					.build();

			ExternalSystemUtil.runTask(taskExecutionSpec);
		}
	}

}
