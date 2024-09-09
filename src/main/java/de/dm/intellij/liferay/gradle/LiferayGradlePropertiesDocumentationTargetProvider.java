package de.dm.intellij.liferay.gradle;

import com.intellij.lang.properties.psi.Property;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.platform.backend.documentation.DocumentationTarget;
import com.intellij.platform.backend.documentation.PsiDocumentationTargetProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LiferayGradlePropertiesDocumentationTargetProvider implements PsiDocumentationTargetProvider {

	@Override
	public @Nullable DocumentationTarget documentationTarget(@NotNull PsiElement element, @Nullable PsiElement originalElement) {
		if (element instanceof Property property) {
			PsiFile psiFile = element.getContainingFile();

			PsiFile originalFile = psiFile.getOriginalFile();

			if (StringUtil.equals(originalFile.getName(), "gradle.properties")) {
				return new LiferayGradlePropertiesDocumentationTarget(element, originalElement, property.getKey());
			}
		}

		return null;
	}
}
