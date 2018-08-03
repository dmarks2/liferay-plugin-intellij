package de.dm.intellij.liferay.language.osgi;

import com.intellij.codeInsight.AnnotationUtil;
import com.intellij.codeInsight.daemon.ImplicitUsageProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiModifierList;
import com.intellij.psi.PsiModifierListOwner;

import java.util.Arrays;
import java.util.Collection;

public class LiferayOsgiImplicitUsageProvider implements ImplicitUsageProvider {

    private static final Collection<String> WRITE_ANNOTATIONS = Arrays.asList(
        "org.osgi.service.component.annotations.Reference", "com.liferay.portal.spring.extender.service.ServiceReference",
        "com.liferay.portal.kernel.bean.BeanReference", "com.liferay.arquillian.containter.remote.enricher.Inject"
    );

    @Override
    public boolean isImplicitUsage(PsiElement element) {
        return isImplicitWrite(element);
    }

    @Override
    public boolean isImplicitRead(PsiElement element) {
        return false;
    }

    @Override
    public boolean isImplicitWrite(PsiElement element) {
        if (element instanceof PsiModifierListOwner) {
            PsiModifierListOwner modifierListOwner = (PsiModifierListOwner)element;
            PsiModifierList modifierList = modifierListOwner.getModifierList();
            if ( (modifierList != null) && (modifierList.getAnnotations().length > 0)) {
                return AnnotationUtil.isAnnotated(modifierListOwner, WRITE_ANNOTATIONS);
            }
        }

        return false;
    }

}
