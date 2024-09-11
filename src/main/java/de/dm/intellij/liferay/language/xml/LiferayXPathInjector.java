package de.dm.intellij.liferay.language.xml;

import com.intellij.freemarker.psi.FtlMethodCallExpression;
import com.intellij.freemarker.psi.FtlStringLiteral;
import com.intellij.lang.Language;
import com.intellij.lang.injection.MultiHostInjector;
import com.intellij.lang.injection.MultiHostRegistrar;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.ElementManipulators;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLanguageInjectionHost;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.PsiMethodCallExpression;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ArrayUtil;
import com.intellij.util.SmartList;
import de.dm.intellij.liferay.language.freemarker.LiferayFreemarkerUtil;
import de.dm.intellij.liferay.language.groovy.GroovyUtil;
import de.dm.intellij.liferay.util.ProjectUtils;
import org.intellij.lang.xpath.XPathLanguage;
import org.intellij.plugins.intelliLang.inject.InjectedLanguage;
import org.intellij.plugins.intelliLang.inject.InjectorUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.expressions.literals.GrLiteral;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.expressions.path.GrMethodCallExpression;

import java.util.ArrayList;
import java.util.List;

public class LiferayXPathInjector implements MultiHostInjector {

	private static final String[] XPATH_EXPRESSION_METHOD_SIGNATURES = new String[] {
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
		} else if (context instanceof FtlStringLiteral ftlStringLiteral) {
			injectIntoFtlStringLiteral(registrar, (PsiLanguageInjectionHost) context, ftlStringLiteral);
		} else if (context instanceof GrLiteral grLiteral) {
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

	private static void injectIntoFtlStringLiteral(@NotNull MultiHostRegistrar registrar, PsiLanguageInjectionHost context, FtlStringLiteral ftlStringLiteral) {
		FtlMethodCallExpression methodCallExpression = LiferayFreemarkerUtil.getMethodCallExpression(ftlStringLiteral);

		if (methodCallExpression != null) {
			String methodSignature = LiferayFreemarkerUtil.getMethodSignature(methodCallExpression);

			if (methodSignature != null) {
				if (log.isDebugEnabled()) {
					log.debug("Examining method signature \"" + methodSignature + "\"");
				}

				for (String xPathExpressionMethodSignature : XPATH_EXPRESSION_METHOD_SIGNATURES) {
					if (StringUtil.equals(StringUtil.substringBeforeLast(xPathExpressionMethodSignature, "()"), methodSignature)) {
						if (ftlStringLiteral.isValidHost()) {
							List<TextRange> ranges = new SmartList<TextRange>();
							PsiElement[] children = ftlStringLiteral.getChildren();
							ranges.add(ftlStringLiteral.getValueRange());

							int startOffset = ftlStringLiteral.getTextRange().getStartOffset();

							for (PsiElement child : children) {
								TextRange textRange = child.getTextRange();
								TextRange rangeInElement = textRange.shiftLeft(startOffset);

								TextRange lastRange = ranges.remove(ranges.size() - 1);
								TextRange leftRange = new TextRange(lastRange.getStartOffset(), rangeInElement.getStartOffset());
								if (leftRange.getLength() > 0) {
									ranges.add(leftRange);
								}

								TextRange rightRange = new TextRange(rangeInElement.getEndOffset(), lastRange.getEndOffset());
								if (rightRange.getLength() > 0) {
									ranges.add(rightRange);
								}
							}

							if (!(ranges.isEmpty())) {
								Language xpathLanguage = Language.findLanguageByID(XPathLanguage.ID);

								if (xpathLanguage != null) {
									registrar.startInjecting(xpathLanguage);
									for (TextRange textRange : ranges) {
										registrar.addPlace(null, null, context, textRange);
									}
									registrar.doneInjecting();
								}
							}
						}

						break;
					}
				}
			}
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

	private static void injectXPathLanguage(@NotNull MultiHostRegistrar registrar, @NotNull PsiElement context) {
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
		return List.of(PsiLiteralExpression.class, FtlStringLiteral.class, GrLiteral.class);
	}
}
