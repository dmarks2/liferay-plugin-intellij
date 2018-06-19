package de.dm.intellij.liferay.language.osgi;

import com.intellij.patterns.PatternCondition;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.patterns.PsiJavaPatterns;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiArrayInitializerMemberValue;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameValuePair;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

public class ComponentPropertiesPsiElementPatternCapture extends PsiElementPattern.Capture<PsiElement> {

    public static final ComponentPropertiesPsiElementPatternCapture instance = new ComponentPropertiesPsiElementPatternCapture();

    protected ComponentPropertiesPsiElementPatternCapture() {
        super(PsiElement.class);
        inside(PsiJavaPatterns.literalExpression())
                .with(new PatternCondition<PsiElement>("pattern") {
                          @Override
                          public boolean accepts(@NotNull PsiElement psiJavaToken, ProcessingContext context) {
                              PsiArrayInitializerMemberValue psiArrayInitializerMemberValue = PsiTreeUtil.getParentOfType(psiJavaToken, PsiArrayInitializerMemberValue.class);

                              if (psiArrayInitializerMemberValue != null) {
                                  PsiNameValuePair psiNameValuePair = PsiTreeUtil.getParentOfType(psiArrayInitializerMemberValue, PsiNameValuePair.class);

                                  if (psiNameValuePair != null) {
                                      String name = psiNameValuePair.getName();

                                      if ("property".equals(name)) {
                                          PsiAnnotation psiAnnotation = PsiTreeUtil.getParentOfType(psiNameValuePair, PsiAnnotation.class);

                                          if (psiAnnotation != null) {
                                              String qualifiedName = psiAnnotation.getQualifiedName();
                                              if ("org.osgi.service.component.annotations.Component".equals(qualifiedName)) {
                                                  return true;
                                              }
                                          }
                                      }
                                  }
                              }

                              return false;
                          }
                      }
                );
    }
}
