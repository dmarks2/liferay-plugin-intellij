package de.dm.intellij.liferay.language.freemarker;

import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.AutoCompletionPolicy;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.freemarker.psi.FtlArgumentList;
import com.intellij.freemarker.psi.FtlBinaryExpression;
import com.intellij.freemarker.psi.FtlExpression;
import com.intellij.freemarker.psi.FtlIndexExpression;
import com.intellij.freemarker.psi.FtlMethodCallExpression;
import com.intellij.freemarker.psi.FtlNameValuePair;
import com.intellij.freemarker.psi.FtlQualifiedReference;
import com.intellij.freemarker.psi.FtlReferenceQualifier;
import com.intellij.freemarker.psi.FtlStringLiteral;
import com.intellij.freemarker.psi.FtlType;
import com.intellij.freemarker.psi.variables.FtlCallableType;
import com.intellij.freemarker.psi.variables.FtlMethodType;
import com.intellij.freemarker.psi.variables.FtlPsiType;
import com.intellij.openapi.module.Module;
import com.intellij.patterns.PatternCondition;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiClassType;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.SearchScope;
import com.intellij.psi.search.searches.ClassInheritorsSearch;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import com.intellij.util.Query;
import de.dm.intellij.liferay.util.Icons;
import de.dm.intellij.liferay.util.ProjectUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class LiferayFreemarkerUtil {

    public static String getFtlStringLiteralText(PsiElement psiElement) {
        return Stream.of(psiElement)
                .map(ftlStringLiteral -> PsiTreeUtil.getParentOfType(ftlStringLiteral, FtlStringLiteral.class, false))
                .filter(Objects::nonNull)
                .map(FtlStringLiteral::getValueText)
                .findFirst().orElse(null);
    }

    public static PsiClassType getClassType(FtlType ftlType) {
        return Stream.of(ftlType)
                .filter(ftlPsiType -> ftlPsiType instanceof FtlPsiType)
                .map(ftlPsiType -> (FtlPsiType)ftlPsiType)
                .map(FtlPsiType::getPsiType)
                .filter(psiType -> psiType instanceof PsiClassType)
                .map(psiClassType -> (PsiClassType)psiClassType)
                .findFirst().orElse(null);
    }

    public static PsiElementPattern.Capture<PsiElement> getFtlStringLiteralFilter(Predicate<PsiElement> condition) {
        return PlatformPatterns.psiElement()
                .withParent(PlatformPatterns.psiElement(FtlStringLiteral.class))
                .with(new PatternCondition<>("pattern") {

                    @Override
                    public boolean accepts(@NotNull PsiElement psiElement, ProcessingContext context) {
                        return condition.test(psiElement);
                    }
                });
    }

    public static FtlReferenceQualifier getIndexExpressionReferenceQualifier(PsiElement psiElement) {
        return Stream.of(psiElement)
                .map(ftlIndexExpression -> PsiTreeUtil.getParentOfType(ftlIndexExpression, FtlIndexExpression.class, false))
                .filter(Objects::nonNull)
                .map(FtlIndexExpression::getReferenceQualifier)
                .filter(Objects::nonNull)
                .findFirst().orElse(null);
    }

    public static String getIndexExpressionReferenceQualifierName(PsiElement psiElement) {
        FtlReferenceQualifier ftlReferenceQualifier = getIndexExpressionReferenceQualifier(psiElement);
        if (ftlReferenceQualifier != null) {
            return ftlReferenceQualifier.getText();
        }
        return null;
    }

    public static String getIndexExpressionQualifiedReferenceName(FtlIndexExpression ftlIndexExpression) {
        return Stream.of(ftlIndexExpression)
                .map(FtlIndexExpression::getQualifiedReference)
                .filter(Objects::nonNull)
                .map(FtlQualifiedReference::getReferenceName)
                .filter(Objects::nonNull)
                .findFirst().orElse(null);
    }

    public static FtlMethodCallExpression getMethodCallExpression(PsiElement psiElement) {
        return Stream.of(psiElement)
                .map(ftlArgumentList -> PsiTreeUtil.getParentOfType(ftlArgumentList, FtlArgumentList.class, false))
                .filter(Objects::nonNull)
                .map(ftlMethodCallExpression -> PsiTreeUtil.getParentOfType(ftlMethodCallExpression, FtlMethodCallExpression.class))
                .filter(Objects::nonNull)
                .findFirst().orElse(null);
    }

    @Nullable
    public static FtlExpression[] getPositionalArguments(PsiElement psiElement) {
        return Stream.of(psiElement)
                .map(ftlArgumentList -> PsiTreeUtil.getParentOfType(ftlArgumentList, FtlArgumentList.class, false))
                .filter(Objects::nonNull)
                .map(FtlArgumentList::getPositionalArguments)
                .findFirst().orElse(null);
    }

    @Nullable
    public static String getMethodSignature(@Nullable FtlMethodCallExpression ftlMethodCallExpression) {
        if (ftlMethodCallExpression != null) {
            FtlCallableType[] callableCandidates = ftlMethodCallExpression.getCallableCandidates();

            PsiMethod psiMethod = Arrays.stream(callableCandidates)
                    .filter(ftlCallableType -> ftlCallableType instanceof FtlMethodType)
                    .map(ftlMethodType -> (FtlMethodType) ftlMethodType)
                    .map(FtlMethodType::getMethod)
                    .filter(Objects::nonNull)
                    .findFirst().orElse(null);

            if (psiMethod != null) {
                PsiClass containingClass = psiMethod.getContainingClass();
                if (containingClass != null) {
                    String qualifiedName = containingClass.getQualifiedName();
                    if (qualifiedName != null) {
                        String methodName = psiMethod.getName();

                        return qualifiedName + "." + methodName;
                    }
                }

            }
        }
        return null;
    }

    public static int getPositionalArgumentsIndex(@Nullable FtlExpression[] positionalArguments, @NotNull PsiElement element) {
        if (positionalArguments != null) {
            FtlStringLiteral ftlStringLiteral = PsiTreeUtil.getParentOfType(element, FtlStringLiteral.class);
            if (ftlStringLiteral != null) {
                for (int i = 0; i < positionalArguments.length; i++) {
                    FtlExpression positionalArgument = positionalArguments[i];
                    if (positionalArgument instanceof FtlStringLiteral) {
                        if (positionalArgument.equals(ftlStringLiteral)) {
                            return i;
                        }
                    }
                }
            }
        }

        return -1;
    }

    @Nullable
    public static FtlArgumentList getQualifiedReferenceArgumentList(PsiElement psiElement) {
        PsiElement[] children = Stream.of(psiElement)
                .map(PsiElement::getReference)
                .filter(psiReference -> psiReference instanceof FtlQualifiedReference)
                .map(ftlQualifiedReference -> (FtlQualifiedReference) ftlQualifiedReference)
                .map(FtlQualifiedReference::getExpressionParent)
                .filter(Objects::nonNull)
                .map(PsiElement::getChildren)
                .findFirst().orElse(null);

        if (children != null) {
            return Arrays.stream(children)
                    .filter(child -> child instanceof FtlArgumentList)
                    .map(ftlArgumentList -> (FtlArgumentList)ftlArgumentList)
                    .findFirst().orElse(null);
        }

        return null;
    }

    @Nullable
    public static String getArgumentListEntryValue(@Nullable FtlArgumentList ftlArgumentList, int index) {
        if (ftlArgumentList != null) {
            PsiElement[] arguments = ftlArgumentList.getChildren();
            if (index < arguments.length) {
                PsiElement argument = arguments[index];
                if (argument instanceof FtlStringLiteral) {
                    FtlStringLiteral stringLiteral = (FtlStringLiteral)argument;

                    return stringLiteral.getValueText();
                }
            }
        }

        return null;
    }

    public static void addClassInheritorsLookup(PsiClass baseClass, CompletionResultSet result, Module module) {
        addClassInheritorsLookup(baseClass, result, module, filter -> true);
    }

    public static void addClassInheritorsLookup(PsiClass baseClass, CompletionResultSet result, Module module, Predicate<String> qualifiedNameFilter) {
        if (baseClass != null) {
            SearchScope scope = GlobalSearchScope.allScope(module.getProject());
            Query<PsiClass> query = ClassInheritorsSearch.search(baseClass, scope, false);

            query.findAll().forEach(psiClass -> {
                String qualifiedName = psiClass.getQualifiedName();
                if (qualifiedName != null) {
                    if (qualifiedNameFilter.test(qualifiedName)) {
                        result.addElement(LookupElementBuilder.create(qualifiedName).withPsiElement(psiClass).withIcon(Icons.LIFERAY_ICON).withAutoCompletionPolicy(AutoCompletionPolicy.NEVER_AUTOCOMPLETE));
                    }
                }
            });
        }
    }

    public static void addClassPublicStaticFieldsLookup(@Nullable PsiClass psiClass, CompletionResultSet result) {
        Collection<PsiField> publicStaticFields = ProjectUtils.getClassPublicStaticFields(psiClass);

        for (PsiField psiField : publicStaticFields) {
            String name = psiField.getName();

            result.addElement(LookupElementBuilder.create(name).withPsiElement(psiField).withIcon(Icons.LIFERAY_ICON));
        }
    }

    public static String getAttributeName(PsiElement psiElement) {
        FtlBinaryExpression ftlBinaryExpression = PsiTreeUtil.getParentOfType(psiElement, FtlBinaryExpression.class);
        if (ftlBinaryExpression != null) {
            FtlExpression leftOperand = ftlBinaryExpression.getLeftOperand();
            if (leftOperand != null) {
                return leftOperand.getText();
            }
        } else {
            FtlNameValuePair ftlNameValuePair = PsiTreeUtil.getParentOfType(psiElement, FtlNameValuePair.class);
            if (ftlNameValuePair != null) {
                return ftlNameValuePair.getName();
            }
        }

        return null;
    }
}
