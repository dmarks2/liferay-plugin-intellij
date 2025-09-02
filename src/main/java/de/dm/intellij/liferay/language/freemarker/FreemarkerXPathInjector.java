package de.dm.intellij.liferay.language.freemarker;

import com.intellij.freemarker.psi.FtlMethodCallExpression;
import com.intellij.freemarker.psi.FtlStringLiteral;
import com.intellij.lang.Language;
import com.intellij.lang.injection.MultiHostRegistrar;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLanguageInjectionHost;
import com.intellij.util.SmartList;
import de.dm.intellij.liferay.language.xml.LiferayXPathInjector;
import org.intellij.lang.xpath.XPathLanguage;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FreemarkerXPathInjector extends LiferayXPathInjector {

	private final static Logger log = Logger.getInstance(FreemarkerXPathInjector.class);

	@Override
	public void getLanguagesToInject(@NotNull MultiHostRegistrar registrar, @NotNull PsiElement context) {
		if (!(context.isValid())) {
			return;
		}

		if (context instanceof FtlStringLiteral ftlStringLiteral) {
			injectIntoFtlStringLiteral(registrar, (PsiLanguageInjectionHost) context, ftlStringLiteral);
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

	@Override
	public @NotNull List<? extends Class<? extends PsiElement>> elementsToInjectIn() {
		return List.of(FtlStringLiteral.class);
	}

}
