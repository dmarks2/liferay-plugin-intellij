package de.dm.intellij.liferay.language.jsp;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiClassType;
import com.intellij.psi.PsiCodeBlock;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiReturnStatement;
import com.intellij.psi.PsiStatement;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.searches.ClassInheritorsSearch;
import com.intellij.psi.util.PsiUtil;
import com.intellij.util.Query;
import org.jetbrains.annotations.NotNull;

public class LiferayCustomJspBagUtil {

    private static final String CUSTOM_JSP_BAG_CLASS_NAME = "com.liferay.portal.deploy.hot.CustomJspBag";

    public static boolean hasCustomJspBags(@NotNull final Module module) {
        Project project = module.getProject();

        PsiClass psiClass = JavaPsiFacade.getInstance(project).findClass(CUSTOM_JSP_BAG_CLASS_NAME, GlobalSearchScope.allScope(project));
        if (psiClass != null) {
            GlobalSearchScope moduleScope = module.getModuleScope(false);
            Query<PsiClass> query = ClassInheritorsSearch.search(psiClass, moduleScope, true);

            return (query.findFirst() != null);
        }

        return false;
    }

    public static String getCustomJspDir(PsiJavaFile psiJavaFile) {
        PsiClass[] classes = psiJavaFile.getClasses();
        for (PsiClass psiClass : classes) {
            PsiClassType[] listTypes = psiClass.getImplementsListTypes();
            for (PsiClassType classType : listTypes) {
                PsiClass interfaceClass = classType.resolve();
                if (interfaceClass != null) {
                    if (CUSTOM_JSP_BAG_CLASS_NAME.equals(interfaceClass.getQualifiedName())) {
                        return getCustomJspDir(psiClass);
                    }
                }
            }
        }

        return null;
    }

    private static String getCustomJspDir(PsiClass psiClass) {
        PsiMethod[] methods = psiClass.getAllMethods();

        for (PsiMethod method : methods) {
            if ("getCustomJspDir".equals(method.getName())) {
                if (PsiUtil.getAccessLevel(method.getModifierList()) == PsiUtil.ACCESS_LEVEL_PUBLIC) {
                    PsiCodeBlock body = method.getBody();
                    PsiStatement[] statements = body.getStatements();
                    for (PsiStatement statement : statements) {
                        if (statement instanceof PsiReturnStatement) {
                            PsiReturnStatement returnStatement = (PsiReturnStatement)statement;
                            PsiExpression returnValue = returnStatement.getReturnValue();
                            if (returnValue instanceof PsiLiteralExpression) {
                                PsiLiteralExpression literalExpression = (PsiLiteralExpression) returnValue;
                                String text = literalExpression.getText();
                                text = StringUtil.unquoteString(text);
                                return text;
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

}