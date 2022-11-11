package de.dm.intellij.liferay.language.osgi;

import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.PsiNameValuePair;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.PsiSearchHelper;
import com.intellij.psi.search.UsageSearchContext;
import com.intellij.psi.search.searches.ReferencesSearch;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.Processor;
import com.intellij.util.QueryExecutor;
import org.jetbrains.annotations.NotNull;

public class MetaConfigurationReferencesSearch implements QueryExecutor<PsiReference, ReferencesSearch.SearchParameters> {

    @Override
    public boolean execute(ReferencesSearch.@NotNull SearchParameters queryParameters, @NotNull Processor<? super PsiReference> consumer) {
        return ReadAction.compute(() -> doExecute(queryParameters, consumer));
    }

    private static boolean doExecute(ReferencesSearch.SearchParameters queryParameters, Processor<? super PsiReference> consumer) {
        PsiElement element = queryParameters.getElementToSearch();

        final PsiElement literalExpression = element instanceof PsiLiteralExpression ? element : PsiTreeUtil.getParentOfType(element, PsiLiteralExpression.class);

        if (literalExpression != null) {
            PsiNameValuePair nameValuePair = PsiTreeUtil.getParentOfType(element, PsiNameValuePair.class);

            if (nameValuePair != null && "id".equals(nameValuePair.getName())) {
                PsiAnnotation annotation = PsiTreeUtil.getParentOfType(nameValuePair, PsiAnnotation.class);

                if (annotation != null && "aQute.bnd.annotation.metatype.Meta.OCD".equals(annotation.getQualifiedName())) {
                    PsiSearchHelper searchHelper = PsiSearchHelper.getInstance(element.getProject());

                    String id = StringUtil.unquoteString(element.getText());

                    return searchHelper.processElementsWithWord((psiElement, offsetInElement) -> {
                        final PsiReference[] references = psiElement.getReferences();

                        for (PsiReference reference : references) {
                            if (reference instanceof MetaConfigurationReference && reference.isReferenceTo(literalExpression)) {
                                if (!consumer.process(reference)) {
                                    return false;
                                }
                            }
                        }

                        return true;
                    }, queryParameters.getEffectiveSearchScope(), id, UsageSearchContext.IN_STRINGS, true);
                }
            }
        }

        return true;
    }
}
