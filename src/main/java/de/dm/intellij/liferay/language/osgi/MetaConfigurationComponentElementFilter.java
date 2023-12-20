package de.dm.intellij.liferay.language.osgi;

import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameValuePair;
import com.intellij.psi.filters.ElementFilter;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class MetaConfigurationComponentElementFilter implements ElementFilter {
    @Override
    public boolean isAcceptable(Object element, @Nullable PsiElement context) {
        if (element instanceof PsiElement psiElement) {

			PsiAnnotation annotation = PsiTreeUtil.getParentOfType(psiElement, PsiAnnotation.class);

            if (annotation != null) {
                if (annotation.hasQualifiedName("org.osgi.service.component.annotations.Component")) {
                    PsiNameValuePair nameValuePair = PsiTreeUtil.getParentOfType(psiElement, PsiNameValuePair.class);

                    return (nameValuePair != null && Objects.equals(nameValuePair.getName(), "configurationPid"));
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
