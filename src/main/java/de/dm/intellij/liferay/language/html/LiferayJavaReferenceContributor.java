package de.dm.intellij.liferay.language.html;

import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;
import org.jetbrains.annotations.NotNull;

public class LiferayJavaReferenceContributor extends PsiReferenceContributor {

	@Override
	public void registerReferenceProviders(@NotNull PsiReferenceRegistrar psiReferenceRegistrar) {
		psiReferenceRegistrar.registerReferenceProvider(
				PlatformPatterns.psiElement(PsiLiteralExpression.class),
				new LiferayWebContextPathReferenceProvider()
		);
	}
}
