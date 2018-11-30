package de.dm.intellij.liferay.language.freemarker;

import com.intellij.codeInsight.navigation.actions.GotoDeclarationHandlerBase;
import com.intellij.freemarker.psi.FtlStringLiteral;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiClassType;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiType;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import de.dm.intellij.liferay.language.freemarker.enumutil.EnumUtilClassNameCompletionContributor;
import de.dm.intellij.liferay.language.freemarker.servicelocator.ServiceLocatorClassNameCompletionContributor;
import de.dm.intellij.liferay.language.freemarker.staticutil.StaticUtilClassNameCompletionContributor;
import org.jetbrains.annotations.Nullable;

public class LiferayFreemarkerClassNameGotoDeclarationHandler extends GotoDeclarationHandlerBase {

    @Nullable
    @Override
    public PsiElement getGotoDeclarationTarget(@Nullable PsiElement sourceElement, Editor editor) {
        FtlStringLiteral ftlStringLiteral = PsiTreeUtil.getParentOfType(sourceElement, FtlStringLiteral.class);
        if (ftlStringLiteral != null) {
            String text = ftlStringLiteral.getValueText();
            if (
                (EnumUtilClassNameCompletionContributor.isEnumUtilCall(sourceElement)) ||
                (ServiceLocatorClassNameCompletionContributor.isServiceLocatorCall(sourceElement)) ||
                (StaticUtilClassNameCompletionContributor.isStaticUtilCall(sourceElement))
            ) {
                try {
                    PsiType targetType = JavaPsiFacade.getInstance(sourceElement.getProject()).getElementFactory().createTypeFromText(text, sourceElement);
                    if (targetType instanceof PsiClassType) {
                        PsiClassType psiClassType = (PsiClassType)targetType;

                        PsiClass psiClass = psiClassType.resolve();

                        if (psiClass != null) {
                            return psiClass;
                        }
                    }
                } catch (IncorrectOperationException e) {
                    //unable to resolve target class
                }

            }
        }
        return null;
    }
}
