package de.dm.intellij.liferay.language.freemarker.staticfieldgetter;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.freemarker.psi.FtlArgumentList;
import com.intellij.freemarker.psi.FtlExpression;
import com.intellij.freemarker.psi.FtlMethodCallExpression;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import de.dm.intellij.liferay.language.freemarker.LiferayFreemarkerUtil;
import de.dm.intellij.liferay.util.ProjectUtils;
import org.jetbrains.annotations.NotNull;

public class StaticFieldGetterFieldNameCompletionContributor extends CompletionContributor {

    private static final PsiElementPattern.Capture<PsiElement> ELEMENT_FILTER =
            LiferayFreemarkerUtil.getFtlStringLiteralFilter(
                    StaticFieldGetterFieldNameCompletionContributor::isStaticFieldGetterFieldNameCall
            );

    public StaticFieldGetterFieldNameCompletionContributor() {
        extend(
                CompletionType.BASIC,
                ELEMENT_FILTER,
                new CompletionProvider<>() {
                    @Override
                    protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {
                        PsiElement originalPosition = parameters.getOriginalPosition();
                        if (originalPosition != null) {
                            if (isStaticFieldGetterFieldNameCall(originalPosition)) {
                                FtlArgumentList argumentList = PsiTreeUtil.getParentOfType(originalPosition, FtlArgumentList.class);
                                String className = LiferayFreemarkerUtil.getArgumentListEntryValue(argumentList, 0);
                                if (className != null) {
                                    PsiClass psiClass = ProjectUtils.getClassByName(originalPosition.getProject(), className, originalPosition);
                                    if (psiClass != null) {
                                        LiferayFreemarkerUtil.addClassPublicStaticFieldsLookup(psiClass, result);

                                        result.stopHere();
                                    }
                                }
                            }
                        }
                    }
                }
        );

    }

    public static boolean isStaticFieldGetterFieldNameCall(PsiElement element) {
        FtlExpression[] positionalArguments = LiferayFreemarkerUtil.getPositionalArguments(element);
        int positionalArgumentIndex = LiferayFreemarkerUtil.getPositionalArgumentsIndex(positionalArguments, element);

        if ( positionalArgumentIndex == 1 ) {
            FtlMethodCallExpression ftlMethodCallExpression = LiferayFreemarkerUtil.getMethodCallExpression(element);

            String signature = LiferayFreemarkerUtil.getMethodSignature(ftlMethodCallExpression);

			return "com.liferay.portal.kernel.util.StaticFieldGetter.getFieldValue".equals(signature);
        }
        return false;
    }
}
