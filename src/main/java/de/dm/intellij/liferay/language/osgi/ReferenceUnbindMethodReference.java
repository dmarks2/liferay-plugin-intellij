package de.dm.intellij.liferay.language.osgi;

import com.intellij.codeInsight.completion.PrioritizedLookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiModifier;
import com.intellij.psi.PsiParameter;
import com.intellij.psi.PsiParameterList;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.psi.PsiType;
import com.intellij.psi.PsiTypes;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReferenceUnbindMethodReference extends PsiReferenceBase<PsiLiteralExpression> {

	public ReferenceUnbindMethodReference(@NotNull PsiLiteralExpression element,
								 @NotNull PsiClass containingClass,
								 @NotNull String methodName) {
		super(element);

		_containingClass = containingClass;
		_methodName = methodName;
	}

	@Override
	public PsiElement resolve() {
		PsiMethod[] methods = _containingClass.findMethodsByName(_methodName, true);

		return methods.length > 0 ? methods[0] : null;
	}

	@Override
	public Object @NotNull [] getVariants() {
		List<Object> result = new ArrayList<>();

		PsiMethod[] allMethods = _containingClass.getAllMethods();

		for (PsiMethod method : allMethods) {
			if (_isValidUnbindMethod(method)) {
				String name = method.getName();

				if (log.isDebugEnabled()) {
					log.debug("Found unbind method " + name + " in class " + _containingClass.getQualifiedName());
				}

				LookupElementBuilder builder = LookupElementBuilder.create(method, name)
						.withIcon(method.getIcon(0))
						.withTypeText(getReturnTypeText(method), true)
						.withTailText(getParameterText(method), true)
						.withPresentableText(name);

				result.add(
						PrioritizedLookupElement.withPriority(builder, Objects.equals(method.getContainingClass(), _containingClass) ? 100.0 : 50.0 )
				);
			}
		}

		return result.toArray(new Object[0]);
	}

	private String getReturnTypeText(PsiMethod method) {
		PsiType returnType = method.getReturnType();
		if (returnType == null) {
			return "";
		}
		return returnType.getPresentableText();
	}

	/**
	 * Erstellt den Parameter-Text für die Anzeige
	 */
	private String getParameterText(PsiMethod method) {
		PsiParameterList parameterList = method.getParameterList();
		if (parameterList.getParametersCount() == 0) {
			return "()";
		}

		StringBuilder sb = new StringBuilder("(");
		PsiParameter[] parameters = parameterList.getParameters();

		for (int i = 0; i < parameters.length; i++) {
			if (i > 0) {
				sb.append(", ");
			}

			PsiParameter param = parameters[i];

			PsiType type = param.getType();
			sb.append(type.getPresentableText());

			String paramName = param.getName();

			sb.append(" ").append(paramName);
		}
		sb.append(")");

		return sb.toString();
	}

	private boolean _isValidUnbindMethod(PsiMethod method) {
		if (method.isConstructor() ||
				method.hasModifierProperty(PsiModifier.STATIC) ||
				method.hasModifierProperty(PsiModifier.ABSTRACT)) {
			return false;
		}

		PsiType returnType = method.getReturnType();

		if (returnType == null || !returnType.equals(PsiTypes.voidType())) {
			return false;
		}

		PsiParameterList parameterList = method.getParameterList();

		if (parameterList.getParametersCount() == 0) {
			return false;
		}

		PsiMethod currentMethod = PsiTreeUtil.getParentOfType(getElement(), PsiMethod.class);

		return !method.equals(currentMethod);
	}

	@Override
	public boolean isSoft() {
		return true;
	}

	private final static Logger log = Logger.getInstance(ReferenceUnbindMethodReference.class);

	private final PsiClass _containingClass;
	private final String _methodName;

}
