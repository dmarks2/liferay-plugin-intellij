package de.dm.intellij.liferay.language.osgi;

import com.intellij.codeInsight.AnnotationUtil;
import com.intellij.codeInsight.daemon.ImplicitUsageProvider;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiModifierList;
import com.intellij.psi.PsiModifierListOwner;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;

public class LiferayOsgiImplicitUsageProvider implements ImplicitUsageProvider {

    private static final Collection<String> WRITE_ANNOTATIONS = Arrays.asList(
            "com.liferay.arquillian.containter.remote.enricher.Inject",
            "com.liferay.arquillian.portal.annotation.PortalURL",
            "com.liferay.portal.kernel.bean.BeanReference",
            "com.liferay.portal.spring.extender.service.ServiceReference",
            "com.liferay.portal.test.rule.Inject",
            "javax.inject.Inject",
            "org.jboss.arquillian.core.api.annotation.Inject",
            "org.osgi.service.component.annotations.Activate",
            "org.osgi.service.component.annotations.Component",
            "org.osgi.service.component.annotations.Deactivate",
            "org.osgi.service.component.annotations.Reference",
            "org.osgi.service.component.annotations.Modified");

    @Override
    public boolean isImplicitUsage(@NotNull PsiElement element) {
        return isImplicitWrite(element);
    }

    @Override
    public boolean isImplicitRead(@NotNull PsiElement element) {
        return false;
    }

    @Override
    public boolean isImplicitWrite(@NotNull PsiElement element) {
        if (element instanceof PsiModifierListOwner modifierListOwner) {

			if (element instanceof PsiMethod constructor && ((PsiMethod)element).isConstructor()) {

				PsiClass containingClass = constructor.getContainingClass();

                return containingClass != null && AnnotationUtil.isAnnotated(containingClass, "org.osgi.service.component.annotations.Component", AnnotationUtil.CHECK_TYPE);
            }

            PsiModifierList modifierList = modifierListOwner.getModifierList();
            if ( (modifierList != null) && (modifierList.getAnnotations().length > 0)) {
                return AnnotationUtil.isAnnotated(modifierListOwner, WRITE_ANNOTATIONS, AnnotationUtil.CHECK_TYPE);
            }
        }

        return false;
    }

}
