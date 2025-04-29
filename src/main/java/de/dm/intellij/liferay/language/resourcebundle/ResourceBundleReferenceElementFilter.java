package de.dm.intellij.liferay.language.resourcebundle;

import com.intellij.openapi.project.DumbService;
import com.intellij.psi.*;
import com.intellij.psi.filters.ElementFilter;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ArrayUtil;
import org.jetbrains.annotations.Nullable;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class ResourceBundleReferenceElementFilter implements ElementFilter {

    private static final Collection<AbstractMap.SimpleEntry<String, AbstractMap.SimpleEntry<String, Integer>>> RESOURCE_BUNDLE_METHODS = new ArrayList<>();

    static {
        RESOURCE_BUNDLE_METHODS.add(new AbstractMap.SimpleEntry<>("java.util.ResourceBundle", new AbstractMap.SimpleEntry<>("getBundle", 0)));
        RESOURCE_BUNDLE_METHODS.add(new AbstractMap.SimpleEntry<>("com.liferay.portal.kernel.util.ResourceBundleUtil", new AbstractMap.SimpleEntry<>("getBundle", 0)));
        RESOURCE_BUNDLE_METHODS.add(new AbstractMap.SimpleEntry<>("com.liferay.portal.kernel.util.ResourceBundleUtil", new AbstractMap.SimpleEntry<>("getLocalizationMap", 0)));
    }

    @Override
    public boolean isAcceptable(Object element, @Nullable PsiElement context) {
        if (element instanceof PsiLiteralExpression literalExpression) {
            if (DumbService.isDumb(literalExpression.getProject())) {
                return false;
            }

			PsiExpressionList expressionList = PsiTreeUtil.getParentOfType(literalExpression, PsiExpressionList.class);

            if (expressionList != null) {
                int index = ArrayUtil.indexOf(expressionList.getExpressions(), literalExpression);

                PsiMethodCallExpression methodCallExpression = PsiTreeUtil.getParentOfType(literalExpression, PsiMethodCallExpression.class);

                if (methodCallExpression != null) {
                    PsiReferenceExpression methodExpression = methodCallExpression.getMethodExpression();

                    JavaResolveResult resolveResult = methodExpression.advancedResolve(false);

                    PsiMethod method = (PsiMethod) resolveResult.getElement();

                    if (method != null) {
                        for (Map.Entry<String, AbstractMap.SimpleEntry<String, Integer>> entry : RESOURCE_BUNDLE_METHODS) {
                            AbstractMap.SimpleEntry<String, Integer> value = entry.getValue();

                            if (value.getKey().equals(method.getName())) {
                                if (value.getValue().equals(index)) {
                                    PsiClass psiClass = (PsiClass) method.getParent();

                                    if (psiClass != null) {
                                        if (entry.getKey().equals(psiClass.getQualifiedName())) {
                                            return true;
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

    @Override
    public boolean isClassAcceptable(Class hintClass) {
        return true;
    }
}
