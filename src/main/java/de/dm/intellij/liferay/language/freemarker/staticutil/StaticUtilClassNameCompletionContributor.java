package de.dm.intellij.liferay.language.freemarker.staticutil;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.freemarker.psi.FtlIndexExpression;
import com.intellij.freemarker.psi.FtlReferenceQualifier;
import com.intellij.freemarker.psi.FtlStringLiteral;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.patterns.PatternCondition;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.SearchScope;
import com.intellij.psi.search.searches.ClassInheritorsSearch;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import com.intellij.util.Query;
import de.dm.intellij.liferay.util.Icons;
import de.dm.intellij.liferay.util.ProjectUtils;
import org.jetbrains.annotations.NotNull;

public class StaticUtilClassNameCompletionContributor extends CompletionContributor {

    private static final PsiElementPattern.Capture<PsiElement> ELEMENT_FILTER =
        PlatformPatterns.psiElement()
            .withParent(PlatformPatterns.psiElement(FtlStringLiteral.class))
            .with(new PatternCondition<PsiElement>("pattern") {

                @Override
                public boolean accepts(@NotNull PsiElement psiElement, ProcessingContext context) {
                    return isStaticUtilCall(psiElement);
                }
            });

    public StaticUtilClassNameCompletionContributor() {
        extend(
            CompletionType.BASIC,
            ELEMENT_FILTER,
            new CompletionProvider<CompletionParameters>() {
                @Override
                protected void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context, @NotNull CompletionResultSet result) {
                    PsiElement originalPosition = parameters.getOriginalPosition();
                    if (originalPosition != null) {
                        PsiFile psiFile = originalPosition.getContainingFile();
                        psiFile = psiFile.getOriginalFile();

                        Module module = ModuleUtil.findModuleForFile(psiFile);

                        if (isStaticUtilCall(originalPosition)) {
                            //TODO filter by classes having static methods
                            PsiClass objectClass = ProjectUtils.getClassByName(originalPosition.getProject(), "java.lang.Object", originalPosition);

                            addClassInheritorsLookup(objectClass, result, module);

                            result.stopHere();
                        }

                    }
                }
            }
        );
    }

    private static void addClassInheritorsLookup(PsiClass baseClass, CompletionResultSet result, Module module) {
        if (baseClass != null) {
            SearchScope scope = GlobalSearchScope.allScope(module.getProject());
            Query<PsiClass> query = ClassInheritorsSearch.search(baseClass, scope, false);

            query.forEach(psiClass -> {
                String qualifiedName = psiClass.getQualifiedName();
                if (qualifiedName != null) {
                    result.addElement(LookupElementBuilder.create(qualifiedName).withIcon(Icons.LIFERAY_ICON));
                }
            });
        }
    }

    public static boolean isStaticUtilCall(PsiElement element) {
        FtlIndexExpression indexExpression = PsiTreeUtil.getParentOfType(element, FtlIndexExpression.class);
        if (indexExpression != null) {
            FtlReferenceQualifier referenceQualifier = indexExpression.getReferenceQualifier();
            if (referenceQualifier != null) {
                String text = referenceQualifier.getText();
                if ("staticUtil".equals(text)) {
                    return true;
                }
            }
        }
        return false;
    }


}
