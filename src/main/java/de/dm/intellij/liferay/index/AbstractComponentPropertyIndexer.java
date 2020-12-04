package de.dm.intellij.liferay.index;

import com.intellij.lang.java.JavaParserDefinition;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiAnnotationMemberValue;
import com.intellij.psi.PsiAnnotationParameterList;
import com.intellij.psi.PsiArrayInitializerMemberValue;
import com.intellij.psi.PsiBinaryExpression;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaCodeReferenceElement;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.PsiNameValuePair;
import com.intellij.psi.PsiReferenceExpression;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.indexing.DataIndexer;
import com.intellij.util.indexing.FileContent;
import com.intellij.util.indexing.PsiDependentIndex;
import de.dm.intellij.liferay.language.osgi.ComponentPropertiesCompletionContributor;
import de.dm.intellij.liferay.util.ProjectUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractComponentPropertyIndexer<Key> implements DataIndexer<Key, Void, FileContent>, PsiDependentIndex {

    @NotNull
    @Override
    public Map<Key, Void> map(@NotNull FileContent fileContent) {
        Map<Key, Void> map = new HashMap<>();

        PsiJavaFile psiJavaFile = getPsiJavaFileForPsiDependentIndex(fileContent);

        if (psiJavaFile == null) {
            return map;
        }

        PsiClass[] psiClasses = PsiTreeUtil.getChildrenOfType(psiJavaFile, PsiClass.class);

        if (psiClasses != null) {
            for (PsiClass psiClass : psiClasses) {
                for (String serviceClassName : getServiceClassNames()) {
                    Map<String, Collection<String>> componentProperties = getComponentProperties(psiClass, serviceClassName);

                    if (componentProperties != null) {
                        processProperties(map, componentProperties, psiClass, serviceClassName);
                    }
                }
            }
        }

        return map;
    }

    @Nullable
    protected PsiJavaFile getPsiJavaFileForPsiDependentIndex(@NotNull FileContent fileContent) {
        VirtualFile virtualFile = fileContent.getFile();

        boolean shouldBuildStubFor = JavaParserDefinition.JAVA_FILE.shouldBuildStubFor(virtualFile);
        if (! shouldBuildStubFor) {
            return null;
        }

        PsiFile psiFile = fileContent.getPsiFile();

        if (! (psiFile instanceof PsiJavaFile)) {
            return null;
        }

        PsiJavaFile psiJavaFile = (PsiJavaFile) psiFile;

        return psiJavaFile;
    }

    @NotNull
    protected abstract String[] getServiceClassNames();

    protected abstract void processProperties(@NotNull Map<Key, Void> map, @NotNull Map<String, Collection<String>> properties, @NotNull PsiClass psiClass, String serviceClassName);

    @Nullable
    protected Map<String, Collection<String>> getComponentProperties(PsiClass psiClass, String requiredServiceClassName) {
        for (PsiAnnotation psiAnnotation : psiClass.getAnnotations()) {
            PsiJavaCodeReferenceElement nameReferenceElement = psiAnnotation.getNameReferenceElement();

            if (nameReferenceElement != null) {
                String qualifiedName = ProjectUtils.getQualifiedNameWithoutResolve(nameReferenceElement, false);

                if ("org.osgi.service.component.annotations.Component".equals(qualifiedName)) {
                    PsiAnnotationParameterList psiAnnotationParameterList = psiAnnotation.getParameterList();

                    List<String> serviceClassNames = ComponentPropertiesCompletionContributor.getServiceClassNames(psiAnnotationParameterList);
                    if (!(serviceClassNames.isEmpty())) {
                        for (String serviceClassName : serviceClassNames) {
                            if (requiredServiceClassName.equals(serviceClassName)) {
                                Map<String, Collection<String>> properties = new HashMap<>();

                                for (PsiNameValuePair psiNameValuePair : psiAnnotationParameterList.getAttributes()) {
                                    if ("property".equals(psiNameValuePair.getName())) {
                                        PsiAnnotationMemberValue psiNameValuePairValue = psiNameValuePair.getValue();

                                        if (psiNameValuePairValue instanceof PsiArrayInitializerMemberValue) {
                                            PsiArrayInitializerMemberValue psiArrayInitializerMemberValue = (PsiArrayInitializerMemberValue) psiNameValuePairValue;

                                            PsiAnnotationMemberValue[] initializers = psiArrayInitializerMemberValue.getInitializers();

                                            for (PsiAnnotationMemberValue initializer : initializers) {
                                                if (initializer instanceof PsiLiteralExpression) {
                                                    AbstractMap.SimpleImmutableEntry<String, String> property = getProperty((PsiLiteralExpression) initializer);
                                                    if (property != null) {
                                                        Collection<String> values = properties.computeIfAbsent(property.getKey(), k -> new ArrayList<>());
                                                        values.add(property.getValue());
                                                    }
                                                }
                                                else if (initializer instanceof PsiBinaryExpression) {
                                                    AbstractMap.SimpleImmutableEntry<String, String> property = getProperty((PsiBinaryExpression) initializer);
                                                    if (property != null) {
                                                        Collection<String> values = properties.computeIfAbsent(property.getKey(), k -> new ArrayList<>());
                                                        values.add(property.getValue());
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                return properties;
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

    @Nullable
    protected AbstractMap.SimpleImmutableEntry<String, String> getProperty(@NotNull PsiLiteralExpression psiLiteralExpression) {
        String text = psiLiteralExpression.getText();
        text = StringUtil.unquoteString(text);

        if (text.contains("=")) {
            String[] parts = text.split("=");

            return new AbstractMap.SimpleImmutableEntry<>(parts[0], parts[1]);
        }

        return null;
    }

    @Nullable
    protected AbstractMap.SimpleImmutableEntry<String, String> getProperty(@NotNull PsiBinaryExpression psiBinaryExpression) {
        PsiLiteralExpression psiLiteralExpression = PsiTreeUtil.getChildOfType(psiBinaryExpression, PsiLiteralExpression.class);
        if (psiLiteralExpression != null) {
            String text = psiLiteralExpression.getText();
            text = StringUtil.unquoteString(text);

            if (text.contains("=")) {
                String[] parts = text.split("=");

                PsiReferenceExpression psiReferenceExpression = PsiTreeUtil.getChildOfType(psiBinaryExpression, PsiReferenceExpression.class);
                if (psiReferenceExpression != null) {
                    String qualifiedName = ProjectUtils.getQualifiedNameWithoutResolve(psiReferenceExpression, true);

                    return new AbstractMap.SimpleImmutableEntry<>(parts[0], ProjectUtils.REFERENCE_PLACEHOLDER + qualifiedName);
                }
            }
        }

        return null;
    }


}
