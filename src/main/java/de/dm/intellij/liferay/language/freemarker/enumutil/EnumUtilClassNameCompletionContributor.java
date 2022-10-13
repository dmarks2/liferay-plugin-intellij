package de.dm.intellij.liferay.language.freemarker.enumutil;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.CompletionType;
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

public class EnumUtilClassNameCompletionContributor extends CompletionContributor {

    private static final PsiElementPattern.Capture<PsiElement> ELEMENT_FILTER =
        LiferayFreemarkerUtil.getFtlStringLiteralFilter(
            EnumUtilClassNameCompletionContributor::isEnumUtilCall
        );

    public EnumUtilClassNameCompletionContributor() {
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

                            if (isEnumUtilCall(originalPosition)) {
                                PsiClass enumClass = ProjectUtils.getClassByName(originalPosition.getProject(), "java.lang.Enum", originalPosition);

                                LiferayFreemarkerUtil.addClassInheritorsLookup(enumClass, result, module);

                                result.stopHere();
                            }
                        }
                    }
                }
        );
    }

    public static boolean isEnumUtilCall(PsiElement element) {
        String qualifierName = LiferayFreemarkerUtil.getIndexExpressionReferenceQualifierName(element);

        return "enumUtil".equals(qualifierName);
    }

}
