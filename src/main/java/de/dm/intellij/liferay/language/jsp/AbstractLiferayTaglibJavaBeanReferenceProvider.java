package de.dm.intellij.liferay.language.jsp;

import com.intellij.psi.ElementManipulators;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.util.ClassUtil;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractLiferayTaglibJavaBeanReferenceProvider extends PsiReferenceProvider {

    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
        List<PsiReference> result = new ArrayList<PsiReference>();

        String className = getClassName(element);

        if (className != null) {
            PsiManager psiManager = PsiManager.getInstance(element.getProject());

            PsiClass psiClass = ClassUtil.findPsiClass(psiManager, className);
            if (psiClass != null) {
                result.add(new LiferayTaglibJavaBeanReference((XmlAttributeValue) element, ElementManipulators.getValueTextRange(element), psiClass));
            }

        }

        return result.toArray(new PsiReference[result.size()]);
    }

    @Nullable
    protected abstract String getClassName(PsiElement element);

}

