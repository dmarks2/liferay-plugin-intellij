package de.dm.intellij.liferay.language.osgi;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

public class ReferenceUnbindMethodReferenceProvider extends PsiReferenceProvider {

	@Override
	public PsiReference @NotNull [] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
		if (!(element instanceof PsiLiteralExpression literal)) {
			return PsiReference.EMPTY_ARRAY;
		}

		Object value = literal.getValue();

		if (!(value instanceof String methodName)) {
			return PsiReference.EMPTY_ARRAY;
		}

		PsiClass containingClass = PsiTreeUtil.getParentOfType(element, PsiClass.class);
		if (containingClass == null) {
			return PsiReference.EMPTY_ARRAY;
		}

		return new PsiReference[] {
				new ReferenceUnbindMethodReference(literal, containingClass, methodName)
		};
	}
}
