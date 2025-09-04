package de.dm.intellij.liferay.language.osgi;

import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiJavaPatterns;
import com.intellij.patterns.StandardPatterns;
import com.intellij.psi.PsiArrayInitializerMemberValue;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;
import org.jetbrains.annotations.NotNull;

public class OsgiCommandReferenceContributor extends PsiReferenceContributor {

	@Override
	public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
		registrar.registerReferenceProvider(
				PlatformPatterns.psiElement()
						.inside(PsiJavaPatterns.literalExpression())
						.withParent(PsiArrayInitializerMemberValue.class)
						.inFile(PlatformPatterns.psiFile().withName(StandardPatterns.string().endsWith(".java"))),
				new OsgiCommandReferenceProvider()
		);
	}
}
