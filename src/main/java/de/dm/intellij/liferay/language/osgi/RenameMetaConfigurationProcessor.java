package de.dm.intellij.liferay.language.osgi;

import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.refactoring.listeners.RefactoringElementListener;
import com.intellij.refactoring.rename.RenamePsiElementProcessor;
import com.intellij.usageView.UsageInfo;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RenameMetaConfigurationProcessor extends RenamePsiElementProcessor {

    @Override
    public boolean canProcessElement(@NotNull PsiElement element) {
        return MetaConfigurationReference.isMetaConfigurationIdElement(element);
    }

    @Override
    public void renameElement(@NotNull PsiElement element, @NotNull String newName, UsageInfo @NotNull [] usages, @Nullable RefactoringElementListener listener) throws IncorrectOperationException {
        String quotedName = StringUtil.wrapWithDoubleQuote(newName);

        PsiLiteralExpression literalExpression = (PsiLiteralExpression) element;

        for (UsageInfo usage : usages) {
            PsiElement usageElement = usage.getElement();

            PsiAnnotation annotation = PsiTreeUtil.getParentOfType(usageElement, PsiAnnotation.class);

            if (annotation != null) {
                if ("org.osgi.service.component.annotations.Component".equals(annotation.getQualifiedName())) {
                    PsiExpression expression = PsiElementFactory.getInstance(element.getProject()).createExpressionFromText(quotedName, null);

                    usageElement.replace(expression);
                }
            }
        }

        PsiAnnotation annotation = PsiTreeUtil.getParentOfType(literalExpression, PsiAnnotation.class);

        if (annotation != null) {

            PsiExpression expression = PsiElementFactory.getInstance(element.getProject()).createExpressionFromText(quotedName, null);

            annotation.setDeclaredAttributeValue("id", (PsiLiteralExpression)expression);

            if (listener != null) {
                listener.elementRenamed(element);
            }
        }
    }

}
