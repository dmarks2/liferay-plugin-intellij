package de.dm.intellij.liferay.gradle;

import com.intellij.lang.documentation.DocumentationMarkup;
import com.intellij.model.Pointer;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.platform.backend.documentation.DocumentationResult;
import com.intellij.platform.backend.documentation.DocumentationTarget;
import com.intellij.platform.backend.presentation.TargetPresentation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.SmartPointerManager;
import com.intellij.psi.SmartPsiElementPointer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LiferayGradlePropertiesDocumentationTarget implements DocumentationTarget {

	private final PsiElement psiElement;
	private final PsiElement originalElement;
	private final String key;

	public LiferayGradlePropertiesDocumentationTarget(PsiElement psiElement, PsiElement originalElement, String key) {
		this.psiElement = psiElement;
		this.originalElement = originalElement;
		this.key = key;
	}

	@Override
	public @Nullable DocumentationResult computeDocumentation() {
		String message = getMessage();

		if (message != null) {
			return DocumentationResult.documentation(message);
		}

		return null;
	}

	@Override
	public @Nullable String computeDocumentationHint() {
		return getMessage();
	}

	@Override
	public @NotNull TargetPresentation computePresentation() {
		VirtualFile virtualFile = psiElement.getContainingFile().getOriginalFile().getVirtualFile();

		return TargetPresentation.builder(psiElement.getText())
				.locationText(virtualFile.getName(), virtualFile.getFileType().getIcon())
				.presentation();
	}

	@Override
	public @NotNull Pointer<? extends DocumentationTarget> createPointer() {
		SmartPsiElementPointer<PsiElement> elementPtr = SmartPointerManager.createPointer(psiElement);
		SmartPsiElementPointer<PsiElement> originalElementPtr = SmartPointerManager.createPointer(originalElement);

		return new Pointer<>() {
			@Override
			public @Nullable DocumentationTarget dereference() {
				PsiElement element = elementPtr.dereference() == null ? null : elementPtr.dereference();

				return new LiferayGradlePropertiesDocumentationTarget(element, originalElementPtr.dereference(), key);
			}
		};
	}

	private String getMessage() {
		String description = LiferayGradlePropertiesDocumentationBundle.message(key);

		if (description != null) {
			return DocumentationMarkup.DEFINITION_START +
					key +
					DocumentationMarkup.DEFINITION_END +
					DocumentationMarkup.CONTENT_START +
					description +
					DocumentationMarkup.CONTENT_END;
		}

		return null;
	}
}
