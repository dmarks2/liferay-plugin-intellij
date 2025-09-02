package de.dm.intellij.liferay.language.groovy;

import com.intellij.lang.injection.MultiHostRegistrar;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ArrayUtil;
import de.dm.intellij.liferay.language.xml.LiferayXPathInjector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.expressions.literals.GrLiteral;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.expressions.path.GrMethodCallExpression;

import java.util.List;

public class GroovyXPathInjector extends LiferayXPathInjector {

	private final static Logger log = Logger.getInstance(GroovyXPathInjector.class);

	@Override
	public void getLanguagesToInject(@NotNull MultiHostRegistrar registrar, @NotNull PsiElement context) {
		if (!(context.isValid())) {
			return;
		}

		if (context instanceof GrLiteral grLiteral) {
			injectIntoGrString(registrar, context, grLiteral);
		}
	}

	private void injectIntoGrString(@NotNull MultiHostRegistrar registrar, @NotNull PsiElement context, GrLiteral grLiteral) {
		GrMethodCallExpression methodCallExpression = PsiTreeUtil.getParentOfType(context, GrMethodCallExpression.class);

		if (methodCallExpression != null) {
			String methodSignature = GroovyUtil.getMethodCallSignature(methodCallExpression);

			if (methodSignature != null) {
				if (log.isDebugEnabled()) {
					log.debug("Examining method signature \"" + methodSignature + "\"");
				}

				if (ArrayUtil.contains(methodSignature, XPATH_EXPRESSION_METHOD_SIGNATURES)) {
					injectXPathLanguage(registrar, context);
				}
			}
		}
	}

	@Override
	public @NotNull List<? extends Class<? extends PsiElement>> elementsToInjectIn() {
		return List.of(GrLiteral.class);
	}

}
