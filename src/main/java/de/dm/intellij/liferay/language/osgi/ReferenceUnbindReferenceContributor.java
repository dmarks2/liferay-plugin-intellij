package de.dm.intellij.liferay.language.osgi;

import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiJavaPatterns;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;
import org.jetbrains.annotations.NotNull;

public class ReferenceUnbindReferenceContributor extends PsiReferenceContributor {

	@Override
	public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
		registrar.registerReferenceProvider(
				PlatformPatterns.psiElement(PsiLiteralExpression.class)
						.withParent(
								PsiJavaPatterns.psiNameValuePair()
										.withName("unbind")
										.withSuperParent(2,
												PsiJavaPatterns.psiAnnotation()
														.qName("org.osgi.service.component.annotations.Reference")
										)
						),
				new ReferenceUnbindMethodReferenceProvider()
		);
	}
}
