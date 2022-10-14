package de.dm.intellij.liferay.language.osgi;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.lang.documentation.AbstractDocumentationProvider;
import com.intellij.lang.documentation.DocumentationMarkup;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiAnnotationParameterList;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiModifierList;
import com.intellij.psi.PsiNameValuePair;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ComponentPropertiesDocumentationProvider extends AbstractDocumentationProvider {

    @Nullable
    @Override
    public String generateDoc(PsiElement element, @Nullable PsiElement originalElement) {
        if (ComponentPropertiesPsiElementPatternCapture.instance.accepts(element)) {
            List<String> serviceClassNames = ComponentPropertiesCompletionContributor.getServiceClassNames(originalElement == null ? element : originalElement);
            if (! (serviceClassNames.isEmpty()) ) {
                String text = element.getText();
                text = StringUtil.unquoteString(text);

                for (String serviceClassName : serviceClassNames) {
                    String[][] properties = ComponentPropertiesCompletionContributor.COMPONENT_PROPERTIES.get(serviceClassName);
                    if (properties != null) {
                        for (String[] property : properties) {
                            if (text.startsWith(property[0])) {
                                String componentPropertiesDocumentationBundleKey = serviceClassName + "." + property[0];

                                String description = ComponentPropertiesDocumentationBundle.message(componentPropertiesDocumentationBundleKey);

                                if (description != null) {
                                    description = StringUtil.join(StringUtil.split(description, "\n"), "<br>");

                                    return DocumentationMarkup.DEFINITION_START +
                                            property[0] +
                                            DocumentationMarkup.DEFINITION_END +
                                            DocumentationMarkup.CONTENT_START +
                                            description +
                                            DocumentationMarkup.CONTENT_END;
                                }
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

    @Override
    public @Nullable @Nls String getQuickNavigateInfo(PsiElement element, PsiElement originalElement) {
        return generateDoc(element, originalElement);
    }

    @Override
    public @Nullable PsiElement getDocumentationElementForLookupItem(PsiManager psiManager, Object object, PsiElement element) {
        if (object instanceof String) {
            String lookupString = (String) object;

            PsiAnnotationParameterList annotationParameterList = PsiTreeUtil.getParentOfType(element, PsiAnnotationParameterList.class);

            List<String> serviceClassNames = ComponentPropertiesCompletionContributor.getServiceClassNames(annotationParameterList);

            String dummyClass = "@org.osgi.service.component.annotations.Component(\n" +
                    "   property=\"" + lookupString + "\",\n" +
                    "   " + getServiceClassDeclaration(serviceClassNames) + "\n" +
                    ")\n" +
                    "public class MyComponent { }";

            PsiFile psiFile = PsiFileFactory.getInstance(psiManager.getProject()).createFileFromText("MyComponent.java", JavaFileType.INSTANCE, dummyClass);

            PsiClass clazz = PsiTreeUtil.getChildOfType(psiFile, PsiClass.class);

            PsiModifierList modifierList = PsiTreeUtil.getChildOfType(clazz, PsiModifierList.class);

            PsiAnnotation[] annotations = PsiTreeUtil.getChildrenOfType(modifierList, PsiAnnotation.class);

            if (annotations != null) {

                for (PsiAnnotation annotation : annotations) {
                    String qualifiedName = annotation.getQualifiedName();

                    if ("org.osgi.service.component.annotations.Component".equals(qualifiedName)) {
                        PsiAnnotationParameterList parameterList = annotation.getParameterList();
                        for (PsiNameValuePair psiNameValuePair : parameterList.getAttributes()) {
                            if ("property".equals(psiNameValuePair.getName())) {
                                return psiNameValuePair.getValue();
                            }
                        }
                    }
                }
            }


            return null;
        }

        return super.getDocumentationElementForLookupItem(psiManager, object, element);
    }

    private String getServiceClassDeclaration(List<String> serviceClassNames) {
        if (serviceClassNames.size() > 1) {
            StringBuilder builder = new StringBuilder();

            builder.append("{");

            for (int i = 0; i < serviceClassNames.size(); i++) {
                builder.append(serviceClassNames.get(i)).append(".class");

                if (i < serviceClassNames.size() - 1) {
                    builder.append(", ");
                }
            }

            builder.append("}");

            return "service = " + builder.toString();
        } else {
            return "service = " + serviceClassNames.get(0) + ".class";
        }
    }

}
