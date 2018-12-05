package de.dm.intellij.liferay.language.freemarker.staticfieldgetter;

import com.intellij.freemarker.psi.FtlArgumentList;
import com.intellij.freemarker.psi.variables.FtlCallableType;
import com.intellij.freemarker.psi.variables.FtlDynamicMember;
import com.intellij.freemarker.psi.variables.FtlLightVariable;
import com.intellij.freemarker.psi.variables.FtlPsiType;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiType;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.util.IncorrectOperationException;
import de.dm.intellij.liferay.language.freemarker.LiferayFreemarkerUtil;
import de.dm.intellij.liferay.util.Icons;
import de.dm.intellij.liferay.util.ProjectUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Collection;

public class StaticFieldGetterFtlVariable extends FtlLightVariable {

    public static final String VARIABLE_NAME = "staticFieldGetter";

    private static final String STATIC_FIELD_GETTER_LOCATOR_CLASS_NAME = "com.liferay.portal.kernel.util.StaticFieldGetter";

    private PsiClass staticFieldGetterClass;

    public StaticFieldGetterFtlVariable(@NotNull PsiElement parent) {
        super(VARIABLE_NAME, parent, STATIC_FIELD_GETTER_LOCATOR_CLASS_NAME);

        staticFieldGetterClass = ProjectUtils.getClassByName(parent.getProject(), STATIC_FIELD_GETTER_LOCATOR_CLASS_NAME, parent);
    }

    @NotNull
    @Override
    public PsiElement getNavigationElement() {
        return staticFieldGetterClass;
    }

    @Override
    public Icon getIcon(boolean open) {
        return Icons.LIFERAY_ICON;
    }

    @Override
    public boolean processDeclarations(@NotNull PsiScopeProcessor processor, @NotNull ResolveState state, PsiElement lastParent, @NotNull PsiElement place) {
        PsiElement parent = getParent();

        if (staticFieldGetterClass != null) {
            for (PsiMethod psiMethod : staticFieldGetterClass.getMethods()) {
                if (("getFieldValue".equals(psiMethod.getName()) && (psiMethod.getParameterList().getParametersCount() == 2))) {
                    FtlArgumentList argumentList = LiferayFreemarkerUtil.getQualifiedReferenceArgumentList(place);

                    String className = LiferayFreemarkerUtil.getArgumentListEntryValue(argumentList, 0);
                    String valueText = LiferayFreemarkerUtil.getArgumentListEntryValue(argumentList, 1);

                    if ( (className != null) && (valueText != null) ) {
                        try {
                            PsiFile psiFile = parent.getContainingFile();
                            psiFile = psiFile.getOriginalFile();

                            PsiClass clazz = ProjectUtils.getClassByName(parent.getProject(), className, parent);
                            if (clazz != null) {
                                Collection<PsiField> publicStaticFields = ProjectUtils.getClassPublicStaticFields(clazz);

                                for (PsiField psiField : publicStaticFields) {
                                    String name = psiField.getName();

                                    if (name != null) {
                                        if (name.equals(valueText)) {
                                            PsiType targetType = psiField.getType();
                                            FtlPsiType stringType = FtlPsiType.wrap(PsiType.getJavaLangString(psiFile.getManager(), psiFile.getResolveScope()));

                                            FtlCallableType findServiceType = FtlCallableType.createLightFunctionType(psiMethod, FtlPsiType.wrap(targetType), "className", stringType, "fieldName", stringType);

                                            //TODO getFieldValue is shown as "variable" instead of "method".
                                            FtlDynamicMember findService = new FtlDynamicMember("getFieldValue", psiMethod, findServiceType);

                                            processor.execute(findService, state);

                                            break;
                                        }
                                    }
                                }
                            }
                        } catch (IncorrectOperationException e) {
                            //unable to resolve type
                        }
                    }

                }
            }
        }

        return true;
    }
}
