package de.dm.intellij.liferay.language.osgi;

import com.intellij.lang.injection.MultiHostInjector;
import com.intellij.lang.injection.MultiHostRegistrar;
import com.intellij.lang.properties.PropertiesLanguage;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

//experimental: injecting properties language into @Component(property="")
public class ComponentPropertiesLanguageInjector implements MultiHostInjector {

    @Override
    public void getLanguagesToInject(@NotNull MultiHostRegistrar registrar, @NotNull PsiElement context) {
        if (context == null) {
            return;
        }
        if (! (context.isValid())) {
            return;
        }
        if (context instanceof PsiLiteralExpression) {
            PsiLiteralExpression psiLiteralExpression = (PsiLiteralExpression)context;
            //check for type??
            if (psiLiteralExpression.getParent() instanceof PsiArrayInitializerMemberValue) {
                if (psiLiteralExpression.getParent().getParent() instanceof PsiNameValuePair) {
                    PsiNameValuePair nameValuePair = (PsiNameValuePair)psiLiteralExpression.getParent().getParent();
                    if (nameValuePair.getNameIdentifier() != null) {
                        if ("property".equals(nameValuePair.getNameIdentifier().getText())) {
                            if (psiLiteralExpression.getParent().getParent().getParent() instanceof PsiAnnotationParameterList) {
                                if (psiLiteralExpression.getParent().getParent().getParent().getParent() instanceof PsiAnnotation) {
                                    PsiAnnotation annotation = (PsiAnnotation)psiLiteralExpression.getParent().getParent().getParent().getParent();
                                    if (annotation.getNameReferenceElement() != null) {
                                        if ("org.osgi.service.component.annotations.Component".equals(annotation.getNameReferenceElement().getQualifiedName())) {

                                            TextRange originalRange = psiLiteralExpression.getTextRange();
                                            TextRange valueRange = TextRange.create(1, originalRange.getLength() - 1);

                                            registrar.startInjecting(PropertiesLanguage.INSTANCE);
                                            registrar.addPlace(null, null, (PsiLanguageInjectionHost)psiLiteralExpression, valueRange);
                                            registrar.doneInjecting();

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

    @NotNull
    @Override
    public List<? extends Class<? extends PsiElement>> elementsToInjectIn() {
        return Arrays.asList(PsiLiteralExpression.class);
    }
}
