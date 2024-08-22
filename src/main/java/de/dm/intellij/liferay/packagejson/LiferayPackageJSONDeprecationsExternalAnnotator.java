package de.dm.intellij.liferay.packagejson;

import com.intellij.codeInsight.daemon.Validator;
import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.codeInsight.intention.preview.IntentionPreviewInfo;
import com.intellij.codeInspection.util.InspectionMessage;
import com.intellij.codeInspection.util.IntentionFamilyName;
import com.intellij.codeInspection.util.IntentionName;
import com.intellij.json.psi.JsonElementGenerator;
import com.intellij.json.psi.JsonFile;
import com.intellij.json.psi.JsonProperty;
import com.intellij.json.psi.JsonValue;
import com.intellij.lang.annotation.AnnotationBuilder;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.ExternalAnnotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.util.IncorrectOperationException;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LiferayPackageJSONDeprecationsExternalAnnotator extends ExternalAnnotator<LiferayPackageJSONDeprecationsExternalAnnotator.MyHost, LiferayPackageJSONDeprecationsExternalAnnotator.MyHost> {

	private static final Logger log = Logger.getInstance(LiferayPackageJSONDeprecationsExternalAnnotator.class);

	@Override
	public @Nullable LiferayPackageJSONDeprecationsExternalAnnotator.MyHost collectInformation(@NotNull PsiFile file) {
		if (! (file instanceof JsonFile)) {
			return null;
		}

		if (! "package.json".equals(file.getName())) {
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

			LiferayPackageJSONDeprecationsExternalAnnotator.MyHost host = new LiferayPackageJSONDeprecationsExternalAnnotator.MyHost();

			JsonFile jsonFile = (JsonFile) file;

			JsonValue topLevelValue = jsonFile.getTopLevelValue();

			if (topLevelValue != null) {
				PsiElement[] children = topLevelValue.getChildren();

				for (PsiElement psiElement : children) {
					if (psiElement instanceof JsonProperty jsonProperty) {
						if ("devDependencies".equals(jsonProperty.getName())) {
							JsonValue jsonValue = jsonProperty.getValue();

							if (jsonValue != null) {
								for (PsiElement dependencyElement : jsonValue.getChildren()) {
									if (dependencyElement instanceof JsonProperty dependencyJsonProperty) {
										String name = dependencyJsonProperty.getName();

										if (dependencyJsonProperty.getValue() != null) {
											validateDependencyVersion(host, liferayVersion, name, dependencyJsonProperty.getValue());
										}
									}
								}
							}
						}
					}
				}
			}

			return host;
		}

		return null;
	}

	@Override
	public @Nullable LiferayPackageJSONDeprecationsExternalAnnotator.MyHost doAnnotate(LiferayPackageJSONDeprecationsExternalAnnotator.MyHost collectedInfo) {
		return collectedInfo;
	}

	@Override
	public void apply(@NotNull PsiFile file, LiferayPackageJSONDeprecationsExternalAnnotator.MyHost annotationResult, @NotNull AnnotationHolder holder) {
		annotationResult.apply(holder);
	}

	public static void addMessageWithFixes(PsiElement context, @InspectionMessage String message, @NotNull Validator.ValidationHost.@NotNull ErrorType type, AnnotationHolder myHolder, @NotNull IntentionAction... fixes) {
		if (message != null && !message.isEmpty()) {
			HighlightSeverity severity = type == Validator.ValidationHost.ErrorType.ERROR ? HighlightSeverity.ERROR : HighlightSeverity.WARNING;

			AnnotationBuilder builder = myHolder.newAnnotation(severity, message).range(context);

			for (IntentionAction intentionAction : fixes) {
				builder = builder.withFix(intentionAction);
			}

			builder.create();
		}
	}

	public static class MyHost implements Validator.ValidationHost {

		private record Message(PsiElement context, @InspectionMessage String inspectionMessage, ErrorType errorType, IntentionAction... fixes) {}

		private final List<LiferayPackageJSONDeprecationsExternalAnnotator.MyHost.Message> messages = new ArrayList<>();

		@Override
		public void addMessage(PsiElement context, @InspectionMessage String inspectionMessage, @NotNull Validator.ValidationHost.@NotNull ErrorType errorType) {
			addMessage(context, inspectionMessage, errorType, new IntentionAction[0]);
		}

		public void addMessage(PsiElement context, @InspectionMessage String inspectionMessage, @NotNull Validator.ValidationHost.@NotNull ErrorType errorType, IntentionAction... fixes) {
			this.messages.add(new LiferayPackageJSONDeprecationsExternalAnnotator.MyHost.Message(context, inspectionMessage, errorType, fixes));
		}

		void apply(AnnotationHolder holder) {
			for (LiferayPackageJSONDeprecationsExternalAnnotator.MyHost.Message message : messages) {
				LiferayPackageJSONDeprecationsExternalAnnotator.addMessageWithFixes(message.context, message.inspectionMessage, message.errorType, holder, message.fixes);
			}
		}
	}

	private static void validateDependencyVersion(LiferayPackageJSONDeprecationsExternalAnnotator.MyHost host, String liferayVersion, String dependencyName, JsonValue jsonValue) {
		for (String liferayDevDependency : LiferayPackageJSONVersionHelper.LIFERAY_DEV_DEPENDENCIES.keySet()) {
			if (Objects.equals(liferayDevDependency, dependencyName)) {
				String dependencyVersion = jsonValue.getText();

				dependencyVersion = StringUtil.unquoteString(dependencyVersion);

				String targetVersion = LiferayPackageJSONVersionHelper.LIFERAY_DEV_DEPENDENCIES.get(liferayDevDependency).apply(liferayVersion);

				if (log.isDebugEnabled()) {
					log.debug("Target " + dependencyName + " Dependency from github is " + targetVersion);
				}

				if (StringUtil.isEmpty(targetVersion)) {
					return;
				}

				if (!StringUtil.equals(dependencyVersion, targetVersion)) {
					host.addMessage(
							jsonValue,
							"Mismatched " + dependencyName + " Dependency version (" + dependencyVersion + " does not match Liferay " + liferayVersion + ", should be " + targetVersion + ")",
							Validator.ValidationHost.ErrorType.WARNING,
							updateVersion(jsonValue, targetVersion)
					);
				}
			}
		}
	}

	private static IntentionAction updateVersion(JsonValue jsonValue, String newVersion) {
		return new LiferayPackageJSONDeprecationsExternalAnnotator.UpdateVersion(jsonValue, newVersion);
	}

	private static class UpdateVersion implements IntentionAction {
		private final JsonValue jsonValue;
		private final String newVersion;

		private UpdateVersion(JsonValue jsonValue, String newVersion) {
			this.jsonValue = jsonValue;
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
			JsonElementGenerator jsonElementGenerator = new JsonElementGenerator(project);

			jsonValue.replace(jsonElementGenerator.createValue(StringUtil.wrapWithDoubleQuote(newVersion)));
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

}
