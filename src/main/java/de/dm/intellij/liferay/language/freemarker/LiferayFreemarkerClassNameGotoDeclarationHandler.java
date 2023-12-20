package de.dm.intellij.liferay.language.freemarker;

import com.intellij.codeInsight.navigation.actions.GotoDeclarationHandlerBase;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiClassType;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiType;
import com.intellij.util.IncorrectOperationException;
import de.dm.intellij.liferay.language.freemarker.enumutil.EnumUtilClassNameCompletionContributor;
import de.dm.intellij.liferay.language.freemarker.servicelocator.ServiceLocatorClassNameCompletionContributor;
import de.dm.intellij.liferay.language.freemarker.staticfieldgetter.StaticFieldGetterClassNameCompletionContributor;
import de.dm.intellij.liferay.language.freemarker.staticutil.StaticUtilClassNameCompletionContributor;
import org.jetbrains.annotations.Nullable;

public class LiferayFreemarkerClassNameGotoDeclarationHandler extends GotoDeclarationHandlerBase {

    @Nullable
    @Override
    public PsiElement getGotoDeclarationTarget(@Nullable PsiElement sourceElement, Editor editor) {
        if (sourceElement != null) {
            String text = LiferayFreemarkerUtil.getFtlStringLiteralText(sourceElement);
            if (text != null) {
                if (
                        (EnumUtilClassNameCompletionContributor.isEnumUtilCall(sourceElement)) ||
                        (ServiceLocatorClassNameCompletionContributor.isServiceLocatorCall(sourceElement)) ||
                        (StaticUtilClassNameCompletionContributor.isStaticUtilCall(sourceElement)) ||
                        (StaticFieldGetterClassNameCompletionContributor.isStaticFieldGetterCall(sourceElement)) ||
                        (LiferayFreemarkerTaglibClassNameCompletionContributor.isClassNameAttribute(sourceElement))
                ) {
                    try {
                        Project project = sourceElement.getProject();
                        PsiType targetType = JavaPsiFacade.getInstance(project).getElementFactory().createTypeFromText(text, sourceElement);
                        if (targetType instanceof PsiClassType psiClassType) {
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
        }
        return null;
    }
}
