package de.dm.intellij.liferay.language.xml;

import com.intellij.lang.Language;
import com.intellij.lang.injection.MultiHostInjector;
import com.intellij.lang.injection.MultiHostRegistrar;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.ElementManipulators;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLanguageInjectionHost;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.PsiMethodCallExpression;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ArrayUtil;
import de.dm.intellij.liferay.util.ProjectUtils;
import org.intellij.lang.xpath.XPathLanguage;
import org.intellij.plugins.intelliLang.inject.InjectedLanguage;
import org.intellij.plugins.intelliLang.inject.InjectorUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LiferayXPathInjector implements MultiHostInjector {

	protected static final String[] XPATH_EXPRESSION_METHOD_SIGNATURES = new String[] {
			"com.liferay.portal.kernel.xml.Node.matches()",
			"com.liferay.portal.kernel.xml.Node.numberValueOf()",
			"com.liferay.portal.kernel.xml.Node.selectNodes()",
			"com.liferay.portal.kernel.xml.Node.selectObject()",
			"com.liferay.portal.kernel.xml.Node.selectSingleNode()",
			"com.liferay.portal.kernel.xml.Node.valueOf()",
			"com.liferay.portal.kernel.xml.Document.matches()",
			"com.liferay.portal.kernel.xml.Document.numberValueOf()",
			"com.liferay.portal.kernel.xml.Document.selectNodes()",
			"com.liferay.portal.kernel.xml.Document.selectObject()",
			"com.liferay.portal.kernel.xml.Document.selectSingleNode()",
			"com.liferay.portal.kernel.xml.Document.valueOf()",
			"resources.com.liferay.portal.kernel.xml.Document.matches()",
			"resources.com.liferay.portal.kernel.xml.Document.numberValueOf()",
			"resources.com.liferay.portal.kernel.xml.Document.selectNodes()",
			"resources.com.liferay.portal.kernel.xml.Document.selectObject()",
			"resources.com.liferay.portal.kernel.xml.Document.selectSingleNode()",
			"resources.com.liferay.portal.kernel.xml.Document.valueOf()",
			"com.liferay.portal.kernel.xml.SAXReader.createXPath()",
			"com.liferay.portal.kernel.xml.SAXReader.selectNodes()",
			"com.liferay.portal.kernel.xml.SAXReader.sort()",
			"com.liferay.portal.kernel.xml.SAXReaderUtil.createXPath()",
			"com.liferay.portal.kernel.xml.SAXReaderUtil.selectNodes()",
			"com.liferay.portal.kernel.xml.SAXReaderUtil.sort()"
	};

	private final static Logger log = Logger.getInstance(LiferayXPathInjector.class);

	@Override
	public void getLanguagesToInject(@NotNull MultiHostRegistrar registrar, @NotNull PsiElement context) {
		if (!(context.isValid())) {
			return;
		}

		if (context instanceof PsiLiteralExpression psiLiteralExpression) {
			injectIntoPsiLiteralExpression(registrar, context, psiLiteralExpression);
		}
	}

	private static void injectIntoPsiLiteralExpression(@NotNull MultiHostRegistrar registrar, @NotNull PsiElement context, PsiLiteralExpression psiLiteralExpression) {
		PsiMethodCallExpression methodCallExpression = PsiTreeUtil.getParentOfType(psiLiteralExpression, PsiMethodCallExpression.class);

		if (methodCallExpression != null) {
			String methodSignature = ProjectUtils.getMethodCallSignature(methodCallExpression);

			if (log.isDebugEnabled()) {
				log.debug("Examining method signature \"" + methodSignature + "\"");
			}

			if (ArrayUtil.contains(methodSignature, XPATH_EXPRESSION_METHOD_SIGNATURES)) {
				injectXPathLanguage(registrar, context);
			}
		}
	}

	protected static void injectXPathLanguage(@NotNull MultiHostRegistrar registrar, @NotNull PsiElement context) {
		InjectedLanguage xPathLanguage = InjectedLanguage.create(XPathLanguage.ID, "", "", true);

		List<InjectorUtils.InjectionInfo> list = new ArrayList<>();

		if (context instanceof PsiLanguageInjectionHost languageInjectionHost) {
			if (languageInjectionHost.isValidHost()) {
				TextRange textRange = ElementManipulators.getManipulator(context).getRangeInElement(context);

				String text = context.getText();

				if (text != null) {
					list.add(
							new InjectorUtils.InjectionInfo(
									((PsiLanguageInjectionHost) context),
									xPathLanguage,
									textRange
							)
					);
				}
			}

			if (!list.isEmpty()) {
				InjectorUtils.registerInjection(
						Language.findLanguageByID(XPathLanguage.ID),
						context.getContainingFile(),
						list,
						registrar
				);
			}
		}
	}

	@Override
	public @NotNull List<? extends Class<? extends PsiElement>> elementsToInjectIn() {
		return List.of(PsiLiteralExpression.class);
	}
}
