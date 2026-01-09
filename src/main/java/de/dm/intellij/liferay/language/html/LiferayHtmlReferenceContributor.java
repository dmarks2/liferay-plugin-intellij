package de.dm.intellij.liferay.language.html;

import com.intellij.patterns.XmlPatterns;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;
import org.jetbrains.annotations.NotNull;

public class LiferayHtmlReferenceContributor extends PsiReferenceContributor {

	@Override
	public void registerReferenceProviders(@NotNull PsiReferenceRegistrar psiReferenceRegistrar) {
		psiReferenceRegistrar.registerReferenceProvider(
				XmlPatterns.xmlAttributeValue()
						.withLocalName("src", "href", "data-src", "action")
						.inside(XmlPatterns.xmlTag()),
				new LiferayWebContextPathReferenceProvider()
		);
	}
}
