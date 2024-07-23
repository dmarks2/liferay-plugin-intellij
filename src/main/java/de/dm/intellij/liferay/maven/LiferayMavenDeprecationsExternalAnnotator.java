package de.dm.intellij.liferay.maven;

import com.intellij.codeInsight.daemon.Validator;
import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.codeInsight.intention.preview.IntentionPreviewInfo;
import com.intellij.codeInspection.util.InspectionMessage;
import com.intellij.codeInspection.util.IntentionFamilyName;
import com.intellij.codeInspection.util.IntentionName;
import com.intellij.ide.highlighter.XmlFileType;
import com.intellij.lang.annotation.AnnotationBuilder;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.ExternalAnnotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.psi.xml.XmlTagValue;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.xml.GenericDomValue;
import com.intellij.xml.util.XmlTagUtil;
import de.dm.intellij.liferay.client.LiferayGithubClient;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
import de.dm.intellij.liferay.util.LiferayVersions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.idea.maven.dom.MavenDomUtil;
import org.jetbrains.idea.maven.dom.model.MavenDomArtifactCoordinates;
import org.jetbrains.idea.maven.dom.model.MavenDomDependency;
import org.jetbrains.idea.maven.dom.model.MavenDomPlugin;
import org.jetbrains.idea.maven.dom.model.MavenDomProjectModel;
import org.jetbrains.idea.maven.dom.model.MavenDomProperties;
import org.jetbrains.idea.maven.project.MavenProjectsManager;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class LiferayMavenDeprecationsExternalAnnotator extends ExternalAnnotator<LiferayMavenDeprecationsExternalAnnotator.MyHost, LiferayMavenDeprecationsExternalAnnotator.MyHost> {

	private final static Logger log = Logger.getInstance(LiferayMavenDeprecationsExternalAnnotator.class);

	private static final String LIFERAY_GROUP_ID = "com.liferay";
	private static final String LIFERAY_PORTAL_GROUP_ID = "com.liferay.portal";
	private static final String BIZ_AQUTE_BND_GROUP_ID = "biz.aQute.bnd";
	private static final String SERVICE_BUILDER_ARTIFACT_ID = "com.liferay.portal.tools.service.builder";
	private static final String REST_BUILDER_ARTIFACT_ID = "com.liferay.portal.tools.rest.builder";
	private static final String PORTAL_WEB_ARTIFACT_ID = "com.liferay.portal.web";
	private static final String BIZ_AQUTE_BNDLIB_ARTIFACT_ID = "biz.aQute.bndlib";
	private static final String LIFERAY_ANT_BND_ARTIFACT_ID = "com.liferay.ant.bnd";


	@Override
	public @Nullable LiferayMavenDeprecationsExternalAnnotator.MyHost collectInformation(@NotNull PsiFile file) {
		if (! (file instanceof XmlFile)) {
			return null;
		}

		if (! (MavenDomUtil.isMavenFile(file))) {
			return null;
		}

		if (log.isDebugEnabled()) {
			if (file.getVirtualFile() != null) {
				log.debug("Examining " + file.getVirtualFile().getCanonicalPath());
			}
		}

		Module module = ModuleUtilCore.findModuleForPsiElement(file);

		if ((module != null) && (!module.isDisposed())) {
			String liferayVersion = LiferayModuleComponent.getLiferayVersion(module);

			if (StringUtil.isEmpty(liferayVersion)) {
				return null;
			}

			MavenDomProjectModel mavenDomModel = MavenDomUtil.getMavenDomModel(file, MavenDomProjectModel.class);

			if ( (mavenDomModel != null) && (mavenDomModel.isValid()) ) {
				LiferayMavenDeprecationsExternalAnnotator.MyHost host = new MyHost();

				validatePluginVersion(host, mavenDomModel, liferayVersion, LIFERAY_GROUP_ID, SERVICE_BUILDER_ARTIFACT_ID, "Service Builder", LiferayMavenDeprecationsExternalAnnotator::getServiceBuilderVersion);
				validatePluginVersion(host, mavenDomModel, liferayVersion, LIFERAY_GROUP_ID, REST_BUILDER_ARTIFACT_ID, "REST Builder", LiferayMavenDeprecationsExternalAnnotator::getRestBuilderVersion);

				validateDependencyVersion(host, mavenDomModel, liferayVersion, LIFERAY_PORTAL_GROUP_ID, PORTAL_WEB_ARTIFACT_ID, "Portal Web", LiferayMavenDeprecationsExternalAnnotator::getPortalWebVersion);
				validateDependencyVersion(host, mavenDomModel, liferayVersion, BIZ_AQUTE_BND_GROUP_ID, BIZ_AQUTE_BNDLIB_ARTIFACT_ID, "Biz aQute bndlib", LiferayMavenDeprecationsExternalAnnotator::getBizaQuteBnd);
				validateDependencyVersion(host, mavenDomModel, liferayVersion, LIFERAY_GROUP_ID, LIFERAY_ANT_BND_ARTIFACT_ID, "Liferay Ant bnd", LiferayMavenDeprecationsExternalAnnotator::getAntBndVersion);

				return host;
			} else {
				if (log.isDebugEnabled()) {
					log.debug("Maven Model is empty or invalid. Unable to examine...");
				}
			}
		}

		return null;
	}

	@Override
	public @Nullable LiferayMavenDeprecationsExternalAnnotator.MyHost doAnnotate(LiferayMavenDeprecationsExternalAnnotator.MyHost collectedInfo) {
		return collectedInfo;
	}

	@Override
	public void apply(@NotNull PsiFile file, MyHost annotationResult, @NotNull AnnotationHolder holder) {
		annotationResult.apply(holder);
	}

	public static void addMessageWithFixes(PsiElement context, @InspectionMessage String message, @NotNull Validator.ValidationHost.@NotNull ErrorType type, AnnotationHolder myHolder, @NotNull IntentionAction... fixes) {
		if (message != null && !message.isEmpty()) {
			HighlightSeverity severity = type == Validator.ValidationHost.ErrorType.ERROR ? HighlightSeverity.ERROR : HighlightSeverity.WARNING;

			if (context instanceof XmlTag) {
				addMessagesForTreeChild(XmlTagUtil.getStartTagNameElement((XmlTag)context), severity, message, myHolder, fixes);
				addMessagesForTreeChild(XmlTagUtil.getEndTagNameElement((XmlTag)context), severity, message, myHolder, fixes);
			} else {
				addMessagesForTreeChild(context, severity, message, myHolder, fixes);
			}
		}
	}

	private static void addMessagesForTreeChild(PsiElement token, HighlightSeverity type, @InspectionMessage String message, AnnotationHolder myHolder, @NotNull IntentionAction... actions) {
		if (token != null) {
			AnnotationBuilder builder = myHolder.newAnnotation(type, message).range(token);

			for (IntentionAction intentionAction : actions) {
				builder = builder.withFix(intentionAction);
			}

			builder.create();
		}
	}

	private static void validatePluginVersion(MyHost host, MavenDomProjectModel model, String liferayVersion, String groupId, String artifactId, String descriptiveName, Function<String, String> getTargetVersion) {
		List<MavenDomPlugin> plugins = findPlugins(model, groupId, artifactId);

		for (MavenDomPlugin plugin : plugins) {
			String currentVersion = plugin.getVersion().getStringValue();

			if (log.isDebugEnabled()) {
				log.debug("Found " + descriptiveName + " Plugin with version " + currentVersion);
			}

			if (currentVersion != null) {
				String targetVersion = getTargetVersion.apply(liferayVersion);

				if (log.isDebugEnabled()) {
					log.debug("Target " + descriptiveName + " Plugin from github is " + targetVersion);
				}

				if (StringUtil.isEmpty(targetVersion)) {
					return;
				}

				if (!StringUtil.equals(currentVersion, targetVersion)) {
					host.addMessage(
							plugin.getXmlTag(),
							"Mismatched " + descriptiveName + " Plugin version (" + currentVersion + " does not match Liferay " + liferayVersion + ", should be " + targetVersion + ")",
							Validator.ValidationHost.ErrorType.WARNING,
							updateVersion(model, plugin, targetVersion)
						);
				}
			}
		}
	}

	private static void validateDependencyVersion(MyHost host, MavenDomProjectModel model, String liferayVersion, String groupId, String artifactId, String descriptiveName, Function<String, String> getTargetVersion) {
		List<MavenDomDependency> dependencies = findDependencies(model, groupId, artifactId);

		for (MavenDomDependency dependency : dependencies) {
			String currentVersion = dependency.getVersion().getStringValue();

			if (log.isDebugEnabled()) {
				log.debug("Found " + descriptiveName + " Dependency with version " + currentVersion);
			}

			if (currentVersion != null) {
				String targetVersion = getTargetVersion.apply(liferayVersion);

				if (log.isDebugEnabled()) {
					log.debug("Target " + descriptiveName + " Dependency from github is " + targetVersion);
				}

				if (StringUtil.isEmpty(targetVersion)) {
					return;
				}

				if (!StringUtil.equals(currentVersion, targetVersion)) {
					host.addMessage(
							dependency.getXmlTag(),
							"Mismatched " + descriptiveName + " Dependency version (" + currentVersion + " does not match Liferay " + liferayVersion + ", should be " + targetVersion + ")",
							Validator.ValidationHost.ErrorType.WARNING,
							updateVersion(model, dependency, targetVersion)
					);
				}
			}
		}
	}

	private static String getVersionFromBuildFile(String liferayVersion, String path) {
		if (ApplicationManager.getApplication().isUnitTestMode()) {
			return "9.9.9";
		}

		try {
			LiferayGithubClient githubClient = new LiferayGithubClient();

			String buildArtifactVersion = githubClient.getBuildArtifactVersion(liferayVersion, path);

			if (buildArtifactVersion != null) {
				buildArtifactVersion = LiferayVersions.minusOne(buildArtifactVersion);

				return buildArtifactVersion;
			}
		} catch (URISyntaxException | IOException e) {
			if (log.isDebugEnabled()) {
				log.debug("Unable to fetch version from github: " + e.getMessage());
			}
		}

		return null;
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

	private static String getProjectTemplateVersion(String liferayVersion, String key) {
		if (ApplicationManager.getApplication().isUnitTestMode()) {
			return "9.9.9";
		}

		try {
			LiferayGithubClient githubClient = new LiferayGithubClient();

			return githubClient.getProjectTemplateVersion(liferayVersion, key);
		} catch (URISyntaxException | IOException e) {
			if (log.isDebugEnabled()) {
				log.debug("Unable to fetch plugin version from github: " + e.getMessage());
			}
		}

		return null;
	}

	private static String getServiceBuilderVersion(String liferayVersion) {
		return getProjectTemplateVersion(liferayVersion, "com.liferay.portal.tools.service.builder.version");
	}

	private static String getRestBuilderVersion(String liferayVersion) {
		return getProjectTemplateVersion(liferayVersion, "com.liferay.portal.tools.rest.builder.version");
	}

	private static String getBizaQuteBnd(String liferayVersion) {
		return getProjectTemplateVersion(liferayVersion, "biz.aQute.bnd.version");
	}

	private static String getAntBndVersion(String liferayVersion) {
		return getProjectTemplateVersion(liferayVersion, "com.liferay.ant.bnd.version");
	}

	private static String getPortalWebVersion(String liferayVersion) {
		return getVersionFromBuildFile(liferayVersion, "/portal-web/build.xml");
	}

	private static List<MavenDomPlugin> findPlugins(MavenDomProjectModel model, String groupId, String artifactId) {
		List<MavenDomPlugin> result = new ArrayList<>();

		result.addAll(findPlugins(model.getBuild().getPlugins().getPlugins(), groupId, artifactId));
		result.addAll(findPlugins(model.getBuild().getPluginManagement().getPlugins().getPlugins(), groupId, artifactId));

		return result;
	}

	private static List<MavenDomPlugin> findPlugins(List<MavenDomPlugin> plugins, String groupId, String artifactId) {
		List<MavenDomPlugin> result = new ArrayList<>();

		for (MavenDomPlugin plugin : plugins) {
			if (
					(Objects.equals(plugin.getGroupId().getStringValue(), groupId)) &&
							(Objects.equals(plugin.getArtifactId().getStringValue(), artifactId))
			) {
				result.add(plugin);
			}
		}

		return result;
	}

	private static List<MavenDomDependency> findDependencies(MavenDomProjectModel model, String groupId, String artifactId) {
		List<MavenDomDependency> result = new ArrayList<>();

		result.addAll(findDependencies(model.getDependencies().getDependencies(), groupId, artifactId));
		result.addAll(findDependencies(model.getDependencyManagement().getDependencies().getDependencies(), groupId, artifactId));

		List<MavenDomDependency> dependenciesFromPlugins = new ArrayList<>();

		for (MavenDomPlugin plugin : model.getBuild().getPlugins().getPlugins()) {
			dependenciesFromPlugins.addAll(plugin.getDependencies().getDependencies());
		}

		for (MavenDomPlugin plugin : model.getBuild().getPluginManagement().getPlugins().getPlugins()) {
			dependenciesFromPlugins.addAll(plugin.getDependencies().getDependencies());
		}

		result.addAll(findDependencies(dependenciesFromPlugins, groupId, artifactId));

		return result;
	}

	private static List<MavenDomDependency> findDependencies(List<MavenDomDependency> dependencies, String groupId, String artifactId) {
		List<MavenDomDependency> result = new ArrayList<>();

		for (MavenDomDependency dependency : dependencies) {
			if (
					(Objects.equals(dependency.getGroupId().getStringValue(), groupId)) &&
							(Objects.equals(dependency.getArtifactId().getStringValue(), artifactId))
			) {
				result.add(dependency);
			}
		}

		return result;
	}

	private static IntentionAction updateVersion(MavenDomProjectModel mavenDomProjectModel, MavenDomArtifactCoordinates mavenDomArtifactCoordinates, String newVersion) {
		return new UpdateVersion(mavenDomProjectModel, mavenDomArtifactCoordinates, newVersion);
	}

	private static class UpdateVersion implements IntentionAction {
		private final MavenDomProjectModel mavenDomProjectModel;
		private final MavenDomArtifactCoordinates mavenDomArtifactCoordinates;
		private final String newVersion;

		public UpdateVersion(MavenDomProjectModel mavenDomProjectModel, MavenDomArtifactCoordinates mavenDomArtifactCoordinates, String newVersion) {
			this.mavenDomProjectModel = mavenDomProjectModel;
			this.mavenDomArtifactCoordinates = mavenDomArtifactCoordinates;
			this.newVersion = newVersion;
		}


		@Override
		public @IntentionName @NotNull String getText() {
			return "Update version to " + newVersion;
		}

		@Override
		public @NotNull IntentionPreviewInfo generatePreview(@NotNull Project project, @NotNull Editor editor, @NotNull PsiFile file) {
			return IntentionPreviewInfo.EMPTY;
		}

		@Override
		public boolean isAvailable(@NotNull Project project, Editor editor, PsiFile file) {
			return true;
		}

		@Override
		public void invoke(@NotNull Project project, Editor editor, PsiFile file) throws IncorrectOperationException {
			GenericDomValue<String> version = mavenDomArtifactCoordinates.getVersion();

			String rawText = version.getRawText();

			if (rawText != null) {
				if (
						(StringUtil.startsWith(rawText, "${")) &&
						(StringUtil.endsWith(rawText, "}"))
				) {
					String propertyName = rawText.substring(2, rawText.length() - 1);

					MavenDomProperties properties = mavenDomProjectModel.getProperties();

					XmlTag propertiesTag = properties.ensureTagExists();

					XmlTag propertyNameTag = propertiesTag.findFirstSubTag(propertyName);

					if (propertyNameTag != null) {
						XmlTagValue value = propertyNameTag.getValue();

						value.setText(newVersion);
					}
				} else {
					mavenDomArtifactCoordinates.getVersion().setStringValue(newVersion);
				}
			}

			FileDocumentManager.getInstance().saveAllDocuments();

			MavenProjectsManager.getInstance(project).forceUpdateAllProjectsOrFindAllAvailablePomFiles();
		}

		@Override
		public boolean startInWriteAction() {
			return true;
		}

		@Override
		public @NotNull @IntentionFamilyName String getFamilyName() {
			return "Update version";
		}
	}

	private static XmlFile createDummyXmlFile(Project project, String text) {
		return (XmlFile) PsiFileFactory.getInstance(project).createFileFromText("_Dummy_." + XmlFileType.INSTANCE.getDefaultExtension(), XmlFileType.INSTANCE, text);
	}

	public static class MyHost implements Validator.ValidationHost {

		private record Message(PsiElement context, @InspectionMessage String inspectionMessage, ErrorType errorType, IntentionAction... fixes) {}

		private final List<Message> messages = new ArrayList<>();

		@Override
		public void addMessage(PsiElement context, @InspectionMessage String inspectionMessage, @NotNull Validator.ValidationHost.@NotNull ErrorType errorType) {
			addMessage(context, inspectionMessage, errorType, new IntentionAction[0]);
		}

		public void addMessage(PsiElement context, @InspectionMessage String inspectionMessage, @NotNull Validator.ValidationHost.@NotNull ErrorType errorType, IntentionAction... fixes) {
			this.messages.add(new Message(context, inspectionMessage, errorType, fixes));
		}

		void apply(AnnotationHolder holder) {
			for (Message message : messages) {
				LiferayMavenDeprecationsExternalAnnotator.addMessageWithFixes(message.context, message.inspectionMessage, message.errorType, holder, message.fixes);
			}
		}

	}
}
