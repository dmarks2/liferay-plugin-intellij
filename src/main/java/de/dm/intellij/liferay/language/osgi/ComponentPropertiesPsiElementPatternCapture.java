package de.dm.intellij.liferay.language.osgi;

import com.intellij.patterns.PatternCondition;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.patterns.PsiJavaPatterns;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiArrayInitializerMemberValue;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameValuePair;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

public class ComponentPropertiesPsiElementPatternCapture {

    public static PsiElementPattern.Capture<PsiElement> instance;

    static {
        instance = PlatformPatterns.psiElement();
        instance = instance.inside(PsiJavaPatterns.literalExpression());
        instance = instance.with(
                new PatternCondition<PsiElement>("pattern") {

                    @Override
                    public boolean accepts(@NotNull PsiElement psiElement, ProcessingContext context) {
                        PsiNameValuePair psiNameValuePair;

                        PsiArrayInitializerMemberValue psiArrayInitializerMemberValue = PsiTreeUtil.getParentOfType(
                                psiElement, PsiArrayInitializerMemberValue.class);

                        if (psiArrayInitializerMemberValue != null) {
                            psiNameValuePair = PsiTreeUtil.getParentOfType(
                                    psiArrayInitializerMemberValue, PsiNameValuePair.class);
                        } else {
                            psiNameValuePair = PsiTreeUtil.getParentOfType(
                                    psiElement, PsiNameValuePair.class);
                        }

                        if (psiNameValuePair != null) {
                            String name = psiNameValuePair.getName();

                            if ("property".equals(name)) {
                                PsiAnnotation psiAnnotation = PsiTreeUtil.getParentOfType(
                                        psiNameValuePair, PsiAnnotation.class);

                                if (psiAnnotation != null) {
                                    String qualifiedName = psiAnnotation.getQualifiedName();

                                    if ("org.osgi.service.component.annotations.Component".equals(qualifiedName)) {
                                        return true;
                                    }
                                }
                            }
                        }

                        return false;
                    }

                });
    }

}
