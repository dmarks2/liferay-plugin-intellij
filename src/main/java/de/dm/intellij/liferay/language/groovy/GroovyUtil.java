package de.dm.intellij.liferay.language.groovy;

import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiType;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.groovy.lang.psi.api.GroovyReference;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.expressions.GrExpression;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.expressions.GrReferenceExpression;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.expressions.path.GrMethodCallExpression;
import org.jetbrains.plugins.groovy.lang.psi.api.toplevel.imports.GrImportStatement;
import org.jetbrains.plugins.groovy.lang.resolve.api.GroovyMethodCallReference;

public class GroovyUtil {

	@NotNull
	public static String getMatchFromImports(@NotNull PsiFile psiFile, @NotNull String className) {
		GrImportStatement[] importStatements = PsiTreeUtil.getChildrenOfType(psiFile, GrImportStatement.class);

		if (importStatements != null) {
			//search for import statements
			for (GrImportStatement importStatement : importStatements) {
				String qualifiedName = importStatement.getImportFqn();

				if (qualifiedName != null) {
					if (
							(className.equals(qualifiedName)) ||
							(className.equals(StringUtil.getShortName(qualifiedName)))
					) {
						return qualifiedName;
					}
				}
			}
		}

		return className;
	}

	public static String getMethodCallSignature(GrMethodCallExpression methodCallExpression) {
		GrExpression invokedExpression = methodCallExpression.getInvokedExpression();

		PsiReference reference = invokedExpression.getReference();

		if (reference instanceof GrReferenceExpression referenceExpression) {
			GrExpression qualifierExpression = referenceExpression.getQualifierExpression();

			if (qualifierExpression instanceof GrReferenceExpression qualifierReferenceExpression) {
				GroovyReference staticReference = qualifierReferenceExpression.getStaticReference();

				PsiElement resolve = staticReference.resolve();

				GroovyMethodCallReference callReference = methodCallExpression.getCallReference();

				if (callReference != null) {
					if (resolve instanceof PsiClass psiClass) {
						return psiClass.getQualifiedName() + "." + callReference.getMethodName() + "()";
					} else {
						PsiType type = qualifierReferenceExpression.getType();

						if (type != null) {
							return type.getCanonicalText() + "." + callReference.getMethodName() + "()";
						}

						return getMatchFromImports(methodCallExpression.getContainingFile(), staticReference.getCanonicalText()) + "." + callReference.getMethodName() + "()";
					}
				}
			}
		}

		return null;
	}
}
