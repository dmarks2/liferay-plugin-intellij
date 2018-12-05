package de.dm.intellij.liferay.language.freemarker.staticfieldgetter;

import com.intellij.codeInsight.navigation.actions.GotoDeclarationHandlerBase;
import com.intellij.freemarker.psi.FtlArgumentList;
import com.intellij.freemarker.psi.FtlExpression;
import com.intellij.freemarker.psi.FtlMethodCallExpression;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.intellij.psi.util.PsiTreeUtil;
import de.dm.intellij.liferay.language.freemarker.LiferayFreemarkerUtil;
import de.dm.intellij.liferay.util.ProjectUtils;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class StaticFieldGetterFieldNameGotoDeclarationHandler extends GotoDeclarationHandlerBase {

    @Nullable
    @Override
    public PsiElement getGotoDeclarationTarget(@Nullable PsiElement sourceElement, Editor editor) {
        if (sourceElement != null) {
            FtlMethodCallExpression ftlMethodCallExpression = LiferayFreemarkerUtil.getMethodCallExpression(sourceElement);

            String signature = LiferayFreemarkerUtil.getMethodSignature(ftlMethodCallExpression);

            if ("com.liferay.portal.kernel.util.StaticFieldGetter.getFieldValue".equals(signature)) {
                FtlExpression[] positionalArguments = LiferayFreemarkerUtil.getPositionalArguments(sourceElement);
                int positionalArgumentIndex = LiferayFreemarkerUtil.getPositionalArgumentsIndex(positionalArguments, sourceElement);

                if (positionalArgumentIndex == 1) {
                    FtlArgumentList argumentList = PsiTreeUtil.getParentOfType(sourceElement, FtlArgumentList.class, false);

                    String className = LiferayFreemarkerUtil.getArgumentListEntryValue(argumentList, 0);
                    String text = LiferayFreemarkerUtil.getFtlStringLiteralText(sourceElement);

                    if ( (text != null) && (className != null) ) {
                        PsiClass clazz = ProjectUtils.getClassByName(sourceElement.getProject(), className, sourceElement);
                        if (clazz != null) {
                            Collection<PsiField> publicStaticFields = ProjectUtils.getClassPublicStaticFields(clazz);

                            for (PsiField psiField : publicStaticFields) {
                                String name = psiField.getName();

                                if (name != null) {
                                    if (name.equals(text)) {
                                        return psiField;
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
        return null;
    }
}
