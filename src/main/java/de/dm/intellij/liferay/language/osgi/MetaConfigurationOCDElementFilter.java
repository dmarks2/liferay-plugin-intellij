package de.dm.intellij.liferay.language.osgi;

import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameValuePair;
import com.intellij.psi.filters.ElementFilter;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class MetaConfigurationOCDElementFilter implements ElementFilter {
    @Override
    public boolean isAcceptable(Object element, @Nullable PsiElement context) {
        if (element instanceof PsiElement psiElement) {
			PsiAnnotation annotation = PsiTreeUtil.getParentOfType(psiElement, PsiAnnotation.class);

            if (annotation != null) {
                if (annotation.hasQualifiedName("aQute.bnd.annotation.metatype.Meta.OCD")) {
                    PsiNameValuePair nameValuePair = PsiTreeUtil.getParentOfType(psiElement, PsiNameValuePair.class);

                    return (nameValuePair != null && Objects.equals(nameValuePair.getName(), "id"));
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
