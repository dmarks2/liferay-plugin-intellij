package de.dm.intellij.liferay.language.html;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.util.ProcessingContext;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import org.jetbrains.annotations.NotNull;

public class LiferayWebContextPathReferenceProvider extends PsiReferenceProvider {

	@Override
	public PsiReference @NotNull [] getReferencesByElement(@NotNull PsiElement psiElement, @NotNull ProcessingContext processingContext) {
		String value = getStringValue(psiElement);

		if (value == null || !value.startsWith("/o/")) {
			return PsiReference.EMPTY_ARRAY;
		}

		Module module = ModuleUtilCore.findModuleForPsiElement(psiElement);

		if (module == null) {
			return PsiReference.EMPTY_ARRAY;
		}

		String contextPath = LiferayFileUtil.getWebContextPath(module, null);

		if (contextPath == null) {
			return PsiReference.EMPTY_ARRAY;
		}

		String urlPrefix = "/o/" + contextPath + "/";

		if (! value.startsWith(urlPrefix)) {
			return PsiReference.EMPTY_ARRAY;
		}

		VirtualFile resourcesRoot = LiferayFileUtil.getLiferayResourcesWebRoot(module);

		if (resourcesRoot == null) {
			return PsiReference.EMPTY_ARRAY;
		}

		String relativePath = value.substring(urlPrefix.length());

		return new LiferayWebContextFileReferenceSet(
				relativePath,
				psiElement,
				urlPrefix.length() + 1,
				resourcesRoot
		).getAllReferences();
	}

	private String getStringValue(PsiElement element) {
		if (element instanceof XmlAttributeValue) {
			return ((XmlAttributeValue) element).getValue();
		}

		if (element instanceof PsiLiteralExpression) {
			Object value = ((PsiLiteralExpression) element).getValue();

			return value instanceof String ? (String) value : null;
		}

		return null;
	}
}
