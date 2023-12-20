package de.dm.intellij.liferay.language.freemarker.objectutil;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.freemarker.psi.FtlExpression;
import com.intellij.freemarker.psi.FtlMethodCallExpression;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.util.ProcessingContext;
import de.dm.intellij.liferay.language.freemarker.LiferayFreemarkerUtil;
import de.dm.intellij.liferay.util.ProjectUtils;
import org.jetbrains.annotations.NotNull;

public class ObjectUtilClassNameCompletionContributor extends CompletionContributor {

    private static final PsiElementPattern.Capture<PsiElement> ELEMENT_FILTER =
        LiferayFreemarkerUtil.getFtlStringLiteralFilter(
            ObjectUtilClassNameCompletionContributor::isObjectUtilCall
        );

    public ObjectUtilClassNameCompletionContributor() {
        extend(
            CompletionType.BASIC,
            ELEMENT_FILTER,
                new CompletionProvider<>() {
                    @Override
                    protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {
                        PsiElement originalPosition = parameters.getOriginalPosition();
                        if (originalPosition != null) {
                            PsiFile psiFile = originalPosition.getContainingFile();
                            psiFile = psiFile.getOriginalFile();

                            Module module = ModuleUtil.findModuleForFile(psiFile);

                            if (isObjectUtilCall(originalPosition)) {
                                PsiClass objectClass = ProjectUtils.getClassByName(originalPosition.getProject(), "java.lang.Object", originalPosition);

                                LiferayFreemarkerUtil.addClassInheritorsLookup(objectClass, result, module);

                                result.stopHere();
                            }
                        }
                    }
                }
        );
    }

    public static boolean isObjectUtilCall(PsiElement element) {
        FtlMethodCallExpression ftlMethodCallExpression = LiferayFreemarkerUtil.getMethodCallExpression(element);

        if (ftlMethodCallExpression != null) {
            FtlExpression methodExpression = ftlMethodCallExpression.getMethodExpression();

            String text = methodExpression.getText();

            return "objectUtil".equals(text);
        }

        return false;
    }

}
