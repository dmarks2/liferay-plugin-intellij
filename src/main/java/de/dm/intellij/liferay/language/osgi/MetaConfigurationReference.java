package de.dm.intellij.liferay.language.osgi;

import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiAnnotationMemberValue;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementResolveResult;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.PsiNameValuePair;
import com.intellij.psi.PsiPolyVariantReference;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.psi.ResolveResult;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.ProjectScope;
import com.intellij.psi.search.searches.AnnotatedElementsSearch;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.Query;
import de.dm.intellij.liferay.util.Icons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MetaConfigurationReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {

    public MetaConfigurationReference(@NotNull PsiElement element) {
        super(element);
    }

    @Override
    public ResolveResult @NotNull [] multiResolve(boolean incompleteCode) {
        String configurationPid = getValue();

        Collection<PsiElement> results = new ArrayList<>();

        Query<PsiClass> annotatedClasses = findAnnotatedClasses(getElement().getProject(), getElement(), "aQute.bnd.annotation.metatype.Meta.OCD");

        if (annotatedClasses != null) {
            annotatedClasses.findAll().forEach(
                    annotatedClass -> {
                        PsiAnnotation annotation = annotatedClass.getAnnotation("aQute.bnd.annotation.metatype.Meta.OCD");

                        if (annotation != null) {
                            PsiAnnotationMemberValue attributeValue = annotation.findAttributeValue("id");

                            if (attributeValue != null) {
                                String value = StringUtil.unquoteString(attributeValue.getText());

                                if (StringUtil.equals(value, configurationPid)) {
                                    results.add(attributeValue);
                                }
                            }
                        }
                    }
            );
        }

        return PsiElementResolveResult.createResults(results);
    }

    @Override
    public @Nullable PsiElement resolve() {
        ResolveResult[] resolveResults = multiResolve(false);

        if (resolveResults.length == 1) {
            return resolveResults[0].getElement();
        }

        return null;
    }

    @Override
    public Object @NotNull [] getVariants() {
        List<Object> result = new ArrayList<>();

        Query<PsiClass> annotatedClasses = findAnnotatedClasses(getElement().getProject(), getElement(), "aQute.bnd.annotation.metatype.Meta.OCD");

        if (annotatedClasses != null) {
            annotatedClasses.findAll().forEach(
                    annotatedClass -> {
                        PsiAnnotation annotation = annotatedClass.getAnnotation("aQute.bnd.annotation.metatype.Meta.OCD");

                        if (annotation != null) {
                            PsiAnnotationMemberValue attributeValue = annotation.findAttributeValue("id");

                            if (attributeValue != null) {
                                result.add(
                                        LookupElementBuilder.create(StringUtil.unquoteString(attributeValue.getText())).withPsiElement(attributeValue).withIcon(Icons.LIFERAY_ICON)
                                );
                            }
                        }
                    }
            );
        }

        return result.toArray(new Object[0]);
    }

    @Override
    public boolean isReferenceTo(@NotNull PsiElement element) {
        if (element instanceof PsiLiteralExpression literalExpression) {

			if (isMetaConfigurationIdElement(literalExpression)) {
                return StringUtil.equals(literalExpression.getText(), getElement().getText());
            }
        }
        return super.isReferenceTo(element);
    }

    public static boolean isMetaConfigurationIdElement(PsiElement element) {
        if (element instanceof PsiLiteralExpression literalExpression) {

			PsiNameValuePair nameValuePair = PsiTreeUtil.getParentOfType(literalExpression, PsiNameValuePair.class);

            if (nameValuePair != null) {
                if ("id".equals(nameValuePair.getName())) {
                    PsiAnnotation annotation = PsiTreeUtil.getParentOfType(nameValuePair, PsiAnnotation.class);

                    if (annotation != null) {
                        return ("aQute.bnd.annotation.metatype.Meta.OCD".equals(annotation.getQualifiedName()));
                    }
                }
            }
        }

        return false;
    }

    public static Query<PsiClass> findAnnotatedClasses(Project project, PsiElement element, String qualifiedName) {
        PsiFile containingFile = element.getContainingFile();

        containingFile = containingFile.getOriginalFile();

        Module module = ModuleUtilCore.findModuleForFile(containingFile);

        if (module != null) {
            JavaPsiFacade javaPsiFacade = JavaPsiFacade.getInstance(project);

            PsiClass metaClass = javaPsiFacade.findClass(qualifiedName, GlobalSearchScope.moduleWithDependenciesAndLibrariesScope(module));

            if (metaClass != null) {
                return AnnotatedElementsSearch.searchPsiClasses(metaClass, ProjectScope.getAllScope(project));
            }
        }

        return null;
    }
}
