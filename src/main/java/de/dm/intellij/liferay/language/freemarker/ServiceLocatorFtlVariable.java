package de.dm.intellij.liferay.language.freemarker;

import com.intellij.freemarker.psi.FtlArgumentList;
import com.intellij.freemarker.psi.FtlQualifiedReference;
import com.intellij.freemarker.psi.FtlStringLiteral;
import com.intellij.freemarker.psi.FtlType;
import com.intellij.freemarker.psi.variables.FtlCallableType;
import com.intellij.freemarker.psi.variables.FtlDynamicMember;
import com.intellij.freemarker.psi.variables.FtlLightVariable;
import com.intellij.freemarker.psi.variables.FtlMethodType;
import com.intellij.freemarker.psi.variables.FtlPsiType;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiClassType;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiSubstitutor;
import com.intellij.psi.PsiType;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.util.IncorrectOperationException;
import de.dm.intellij.liferay.util.Icons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class ServiceLocatorFtlVariable extends FtlLightVariable {

    public static final String SERVICE_LOCATOR_CLASS_NAME_6_1 = "com.liferay.portal.velocity.ServiceLocator";
    public static final String SERVICE_LOCATOR_CLASS_NAME_6_2_7_0 = "com.liferay.portal.template.ServiceLocator";

    private static final String SERVICE_LOCATOR_VARIABLE_NAME = "serviceLocator";

    private String serviceLocatorClassName;

    public ServiceLocatorFtlVariable(@NotNull String serviceLocatorClassName, @NotNull PsiElement parent) {
        super(SERVICE_LOCATOR_VARIABLE_NAME, parent, serviceLocatorClassName);

        this.serviceLocatorClassName = serviceLocatorClassName;
    }

    @NotNull
    @Override
    public PsiElement getNavigationElement() {
        //TODO navigate to ServiceLocator class?
        return super.getNavigationElement();
    }

    @Override
    public Icon getIcon(boolean open) {
        return Icons.LIFERAY_ICON;
    }


    @Override
    public boolean processDeclarations(@NotNull PsiScopeProcessor processor, @NotNull ResolveState state, PsiElement lastParent, @NotNull PsiElement place) {
        PsiElement parent = getParent();

        PsiType psiType = JavaPsiFacade.getInstance(parent.getProject()).getElementFactory().createTypeFromText(serviceLocatorClassName, parent);

        if (psiType instanceof PsiClassType) {
            PsiClassType psiClassType = (PsiClassType) psiType;
            PsiClass psiClass = psiClassType.resolve();
            if (psiClass != null) {
                for (PsiMethod psiMethod : psiClass.getMethods()) {
                    if (("findService".equals(psiMethod.getName()) && (psiMethod.getParameterList().getParametersCount() == 1))) {
                        PsiReference reference = place.getReference();
                        if (reference instanceof FtlQualifiedReference) {
                            FtlQualifiedReference ftlQualifiedReference =(FtlQualifiedReference)reference;
                            PsiElement expressionParent = ftlQualifiedReference.getExpressionParent();
                            if (expressionParent != null) {
                                PsiElement[] children = expressionParent.getChildren();
                                for (PsiElement child : children) {
                                    if (child instanceof FtlArgumentList) {
                                        FtlArgumentList argumentList = (FtlArgumentList)child;
                                        PsiElement[] arguments = argumentList.getChildren();
                                        if ( (arguments.length == 1) && (arguments[0] instanceof FtlStringLiteral) ) {
                                            FtlStringLiteral stringLiteral = (FtlStringLiteral)arguments[0];
                                            String valueText = stringLiteral.getValueText();

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
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return false;
   }
}
