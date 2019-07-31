package de.dm.intellij.liferay.language.freemarker.servicelocator;

import com.intellij.freemarker.psi.FtlArgumentList;
import com.intellij.freemarker.psi.variables.FtlCallableType;
import com.intellij.freemarker.psi.variables.FtlDynamicMember;
import com.intellij.freemarker.psi.variables.FtlLightVariable;
import com.intellij.freemarker.psi.variables.FtlPsiType;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
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

public class ServiceLocatorFtlVariable extends FtlLightVariable {

    public static final String SERVICE_LOCATOR_CLASS_NAME = "com.liferay.portal.template.ServiceLocator";
    public static final String VARIABLE_NAME = "serviceLocator";

    private PsiClass serviceLocatorClass;

    public ServiceLocatorFtlVariable(@NotNull PsiElement parent) {
        super(VARIABLE_NAME, parent, SERVICE_LOCATOR_CLASS_NAME);

        serviceLocatorClass = ProjectUtils.getClassByName(parent.getProject(), SERVICE_LOCATOR_CLASS_NAME, parent);
    }

    @NotNull
    @Override
    public PsiElement getNavigationElement() {
        return serviceLocatorClass;
    }

    @Override
    public Icon getIcon(boolean open) {
        return Icons.LIFERAY_ICON;
    }


    @Override
    public boolean processDeclarations(@NotNull PsiScopeProcessor processor, @NotNull ResolveState state, PsiElement lastParent, @NotNull PsiElement place) {
        PsiElement parent = getParent();

        if (serviceLocatorClass != null) {
            for (PsiMethod psiMethod : serviceLocatorClass.getMethods()) {
                if (("findService".equals(psiMethod.getName()) && (psiMethod.getParameterList().getParametersCount() == 1))) {
                    FtlArgumentList argumentList = LiferayFreemarkerUtil.getQualifiedReferenceArgumentList(place);

                    String valueText = LiferayFreemarkerUtil.getArgumentListEntryValue(argumentList, 0);

                    if (valueText != null) {
                        try {
                            PsiFile psiFile = parent.getContainingFile();
                            if (psiFile.getOriginalFile() != null) {
                                psiFile = psiFile.getOriginalFile();
                            }
                            final PsiType targetType = JavaPsiFacade.getInstance(parent.getProject()).getElementFactory().createTypeFromText(valueText, parent);
                            FtlPsiType stringType = FtlPsiType.wrap(PsiType.getJavaLangString(psiFile.getManager(), psiFile.getResolveScope()));

                            FtlCallableType findServiceType = FtlCallableType.createLightFunctionType(psiMethod, FtlPsiType.wrap(targetType), "serviceName", stringType);

                            //TODO findService is shown as "variable" instead of "method".
                            FtlDynamicMember findService = new FtlDynamicMember("findService", psiMethod, findServiceType);

                            processor.execute(findService, state);

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
