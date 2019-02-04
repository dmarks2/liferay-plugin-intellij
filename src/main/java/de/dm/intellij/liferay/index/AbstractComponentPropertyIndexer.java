package de.dm.intellij.liferay.index;

import com.intellij.lang.LighterAST;
import com.intellij.lang.LighterASTNode;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiAnnotationMemberValue;
import com.intellij.psi.PsiAnnotationParameterList;
import com.intellij.psi.PsiArrayInitializerMemberValue;
import com.intellij.psi.PsiBinaryExpression;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiConstantEvaluationHelper;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiNameValuePair;
import com.intellij.psi.PsiReferenceExpression;
import com.intellij.psi.impl.source.FileLocalResolver;
import com.intellij.psi.impl.source.tree.JavaElementType;
import com.intellij.psi.impl.source.tree.LightTreeUtil;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.indexing.DataIndexer;
import com.intellij.util.indexing.FileContent;
import com.intellij.util.indexing.FileContentImpl;
import com.intellij.util.indexing.PsiDependentIndex;
import de.dm.intellij.liferay.language.osgi.ComponentPropertiesCompletionContributor;
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

        VirtualFile virtualFile = fileContent.getFile();

        PsiManager psiManager = PsiManager.getInstance(fileContent.getProject());

        PsiFile psiFile = psiManager.findFile(virtualFile);

        FileType fileType = fileContent.getFileType();
        if (! fileType.isBinary()) {
            LighterASTJavaFileHelper lighterASTJavaFileHelper = new LighterASTJavaFileHelper(fileContent);

            System.out.println(lighterASTJavaFileHelper.getClassQualifiedName());
        }

        DumbService dumbService = DumbService.getInstance(fileContent.getProject());

        try {
            dumbService.setAlternativeResolveEnabled(true);

            //TODO how to handle changes in dependant file?

            if (psiFile instanceof PsiJavaFile) {
                PsiJavaFile psiJavaFile = (PsiJavaFile) psiFile;
                PsiElement[] children = psiJavaFile.getChildren();

                for (PsiElement child : children) {
                    if (child instanceof PsiClass) {
                        PsiClass psiClass = (PsiClass)child;

                        for (String serviceClassName : getServiceClassNames()) {
                            Map<String, Collection<String>> componentProperties = getComponentProperties(psiClass, serviceClassName);

                            //ok?
                            if (!componentProperties.isEmpty()) {
                                processProperties(map, componentProperties, psiClass, serviceClassName);
                            }
                        }

                    }
                }
            }

        } catch (Exception e) {
            //ignore?
        } finally {
            dumbService.setAlternativeResolveEnabled(false);
        }

        return map;
    }

    @NotNull
    protected abstract String[] getServiceClassNames();

    protected abstract void processProperties(@NotNull Map<Key, Void> map, @NotNull Map<String, Collection<String>> properties, @NotNull PsiClass psiClass, String serviceClassName);

    protected Map<String, Collection<String>> getComponentProperties(PsiClass psiClass, String requiredServiceClassName) {
        Map<String, Collection<String>> properties = new HashMap<>();

        for (PsiAnnotation psiAnnotation : psiClass.getAnnotations()) {
            if ("org.osgi.service.component.annotations.Component".equals(psiAnnotation.getQualifiedName())) {
                PsiAnnotationParameterList psiAnnotationParameterList = psiAnnotation.getParameterList();

                List<String> serviceClassNames = ComponentPropertiesCompletionContributor.getServiceClassNames(psiAnnotationParameterList);
                if (!(serviceClassNames.isEmpty())) {
                    for (String serviceClassName : serviceClassNames) {
                        if (requiredServiceClassName.equals(serviceClassName)) {
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
                                            } else if (initializer instanceof PsiBinaryExpression) {
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
                        }
                    }
                }
            }
        }

        return properties;
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
                    PsiConstantEvaluationHelper constantEvaluationHelper = JavaPsiFacade.getInstance(psiBinaryExpression.getProject()).getConstantEvaluationHelper();

                    String propertyValue = (String)constantEvaluationHelper.computeConstantExpression(psiReferenceExpression);

                    return new AbstractMap.SimpleImmutableEntry<>(parts[0], propertyValue);
                }
            }
        }

        return null;
    }


}
