package de.dm.intellij.liferay.language.osgi;

import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.refactoring.rename.PsiElementRenameHandler;
import com.intellij.refactoring.rename.RenameDialog;
import com.intellij.refactoring.rename.RenameHandler;
import org.jetbrains.annotations.NotNull;

public class MetaConfigurationRenameHandler implements RenameHandler {

    @Override
    public boolean isAvailableOnDataContext(@NotNull DataContext dataContext) {
        PsiFile psiFile = CommonDataKeys.PSI_FILE.getData(dataContext);

        if (psiFile == null) {
            return false;
        }

        Caret caret = CommonDataKeys.CARET.getData(dataContext);

        if (caret == null) {
            return false;
        }

        PsiElement psiElement = psiFile.findElementAt(caret.getOffset());

        if (psiElement == null) {
            return false;
        }

        PsiLiteralExpression literalExpression = (psiElement instanceof PsiLiteralExpression) ? (PsiLiteralExpression)psiElement : PsiTreeUtil.getParentOfType(psiElement, PsiLiteralExpression.class);

        return MetaConfigurationReference.isMetaConfigurationIdElement(literalExpression);
    }

    @Override
    public void invoke(@NotNull Project project, Editor editor, PsiFile file, @NotNull DataContext dataContext) {
        invoke(project, PsiElement.EMPTY_ARRAY, dataContext);
    }

    @Override
    public void invoke(@NotNull Project project, PsiElement @NotNull [] elements, DataContext dataContext) {
        PsiElement element = elements.length == 1 ? elements[0] : null;

        if (element == null) {
            element = findTarget(dataContext);
        }

        if (element != null) {
            RenameDialog.showRenameDialog(dataContext, new RenameDialog(project, element, null, CommonDataKeys.EDITOR.getData(dataContext)));
        }
    }

    private static PsiElement findTarget(DataContext dataContext) {
        PsiElement element = PsiElementRenameHandler.getElement(dataContext);

        if (element == null) {
            PsiFile psiFile = CommonDataKeys.PSI_FILE.getData(dataContext);

            Caret caret = CommonDataKeys.CARET.getData(dataContext);

            if (psiFile != null && caret != null) {
                PsiElement psiElement = psiFile.findElementAt(caret.getOffset());

                if (psiElement != null) {
                    return (psiElement instanceof PsiLiteralExpression) ? (PsiLiteralExpression) psiElement : PsiTreeUtil.getParentOfType(psiElement, PsiLiteralExpression.class);
                }
            }
        }

        return null;
    }

}
