package de.dm.intellij.liferay.language.osgi;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class OsgiCommandReferenceProvider extends PsiReferenceProvider {

	@Override
	public PsiReference @NotNull [] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
		PsiLiteralExpression literalExpression;

		if (element instanceof PsiLiteralExpression) {
			literalExpression = (PsiLiteralExpression) element;
		} else {
			literalExpression = PsiTreeUtil.getParentOfType(element, PsiLiteralExpression.class);
		}

		if (literalExpression == null) {
			return PsiReference.EMPTY_ARRAY;
		}

		String value = literalExpression.getValue() instanceof String ? (String) literalExpression.getValue() : null;

		if (value == null || !isOsgiCommandFunction(value)) {
			return PsiReference.EMPTY_ARRAY;
		}

		String function = extractFunctionPart(value);

		if (function == null) {
			return PsiReference.EMPTY_ARRAY;
		}

		List<PsiReference> references = new ArrayList<>();

		int currentOffset = value.indexOf('=') + 1;

		if (!function.isEmpty()) {
			int startPos = value.indexOf(function, currentOffset);

			if (startPos >= 0) {
				TextRange range = new TextRange(startPos + 1, startPos + 1 + function.length());

				references.add(new OsgiCommandReference(literalExpression, range, function));
			}
		} else {
			TextRange range = new TextRange(currentOffset + 1, currentOffset + 1);

			references.add(new OsgiCommandReference(literalExpression, range, ""));
		}

		return references.toArray(new PsiReference[0]);
	}

	private boolean isOsgiCommandFunction(String value) {
		return value.startsWith("osgi.command.function=");
	}

	private String extractFunctionPart(String value) {
		if (! value.startsWith("osgi.command.function=")) {
			return null;
		}

		return value.substring("osgi.command.function=".length()).trim();
	}
}
