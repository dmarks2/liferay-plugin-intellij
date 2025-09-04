package de.dm.intellij.liferay.language.osgi;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementResolveResult;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiModifier;
import com.intellij.psi.PsiParameter;
import com.intellij.psi.PsiPolyVariantReference;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.psi.ResolveResult;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OsgiCommandReference extends PsiReferenceBase<PsiLiteralExpression> implements PsiPolyVariantReference {
	private final static Logger log = Logger.getInstance(OsgiCommandReference.class);

	private final String functionName;

	public OsgiCommandReference(@NotNull PsiLiteralExpression element, TextRange range, String functionName) {
		super(element, range);

		this.functionName = functionName;
	}

	@Override
	public boolean isSoft() {
		return true;
	}

	@Override
	public @Nullable PsiElement resolve() {
		ResolveResult[] resolveResults = multiResolve(false);

		if (resolveResults.length > 0) {
			return resolveResults[0].getElement();
		}

		return null;
	}

	@Override
	public Object @NotNull [] getVariants() {
		if (log.isDebugEnabled()) {
			log.debug("getVariants() called for " + getElement().getText());
		}

		PsiClass containingClass = PsiTreeUtil.getParentOfType(getElement(), PsiClass.class);

		if (log.isDebugEnabled()) {
			log.debug("containingClass is " + containingClass);
		}

		if (containingClass == null) {
			if (log.isDebugEnabled()) {
				log.debug("Unable to find containing class for " + getElement().getText());
			}

			return EMPTY_ARRAY;
		}

		List<LookupElement> variants = new ArrayList<>();

		for (PsiMethod method : containingClass.getMethods()) {
			if (log.isDebugEnabled()) {
				log.debug("Examining " + method.getName() + " in class " + containingClass.getQualifiedName());
			}

			if (method.hasModifierProperty(PsiModifier.PUBLIC) &&
					!method.isConstructor() &&
					!isObjectMethod(method)) {

				if (log.isDebugEnabled()) {
					log.debug("Candidate " + method.getName() + " found");
				}

				variants.add(LookupElementBuilder.create(method.getName())
						.withIcon(method.getIcon(0))
						.withTypeText(getMethodSignature(method))
				);
			}
		}

		return variants.toArray();
	}

	private boolean isObjectMethod(PsiMethod method) {
		String name = method.getName();

		return "equals".equals(name) || "hashCode".equals(name) ||
				"toString".equals(name) || "getClass".equals(name);
	}

	private String getMethodSignature(PsiMethod method) {
		StringBuilder sb = new StringBuilder();

		PsiParameter[] parameters = method.getParameterList().getParameters();

		for (int i = 0; i < parameters.length; i++) {
			if (i > 0) sb.append(", ");

			sb.append(parameters[i].getType().getPresentableText());
		}

		return "(" + sb + ")";
	}

	@Override
	public PsiElement handleElementRename(@NotNull String newElementName) throws IncorrectOperationException {
		String currentValue = (String) getElement().getValue();

		if (currentValue == null) {
			return getElement();
		}

		String newValue = currentValue.replace(functionName, newElementName);

		return getElement().replace(
				JavaPsiFacade.getElementFactory(getElement().getProject())
						.createExpressionFromText("\"" + newValue + "\"", getElement())
		);
	}

	@Override
	public ResolveResult @NotNull [] multiResolve(boolean incompleteCode) {
		PsiClass containingClass = PsiTreeUtil.getParentOfType(getElement(), PsiClass.class);

		if (containingClass == null) {
			return ResolveResult.EMPTY_ARRAY;
		}

		Collection<ResolveResult> results = new ArrayList<>();

		PsiMethod[] methods = containingClass.findMethodsByName(functionName, false);

		for (PsiMethod method : methods) {
			if (method.hasModifierProperty(PsiModifier.PUBLIC)) {
				results.add(new PsiElementResolveResult(method));
			}
		}

		return results.toArray(new ResolveResult[0]);
	}
}
