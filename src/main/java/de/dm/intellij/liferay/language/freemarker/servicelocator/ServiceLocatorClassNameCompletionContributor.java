package de.dm.intellij.liferay.language.freemarker.servicelocator;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.CompletionType;
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

public class ServiceLocatorClassNameCompletionContributor extends CompletionContributor {

    private static final PsiElementPattern.Capture<PsiElement> ELEMENT_FILTER =
            LiferayFreemarkerUtil.getFtlStringLiteralFilter(
                    ServiceLocatorClassNameCompletionContributor::isServiceLocatorCall
            );

    public ServiceLocatorClassNameCompletionContributor() {
        extend(
                CompletionType.BASIC,
                ELEMENT_FILTER,
                new CompletionProvider<>() {
                    @Override
                    protected void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context, @NotNull CompletionResultSet result) {
                        PsiElement originalPosition = parameters.getOriginalPosition();
                        if (originalPosition != null) {
                            PsiFile psiFile = originalPosition.getContainingFile();
                            psiFile = psiFile.getOriginalFile();

                            Module module = ModuleUtil.findModuleForFile(psiFile);

                            if (isServiceLocatorCall(originalPosition)) {
                                PsiClass baseLocalServiceClass = ProjectUtils.getClassByName(originalPosition.getProject(), "com.liferay.portal.kernel.service.BaseLocalService", originalPosition);
                                PsiClass baseServiceClass = ProjectUtils.getClassByName(originalPosition.getProject(), "com.liferay.portal.kernel.service.BaseService", originalPosition);

                                LiferayFreemarkerUtil.addClassInheritorsLookup(baseLocalServiceClass, result, module, qualifiedName -> !qualifiedName.endsWith("Wrapper"));
                                LiferayFreemarkerUtil.addClassInheritorsLookup(baseServiceClass, result, module, qualifiedName -> !qualifiedName.endsWith("Wrapper"));

                                result.stopHere();
                            }
                        }
                    }
                }
        );
    }

    public static boolean isServiceLocatorCall(PsiElement element) {
        FtlMethodCallExpression ftlMethodCallExpression = LiferayFreemarkerUtil.getMethodCallExpression(element);
        if (ftlMethodCallExpression != null) {
            String signature = LiferayFreemarkerUtil.getMethodSignature(ftlMethodCallExpression);

            if ( (ServiceLocatorFtlVariable.SERVICE_LOCATOR_CLASS_NAME + ".findService").equals(signature)) {
                return true;
            }
        }
        return false;
    }

}
