package de.dm.intellij.liferay.language.poshi.psi;

import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementResolveResult;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiPolyVariantReference;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.psi.ResolveResult;
import com.intellij.psi.util.PsiTreeUtil;
import de.dm.intellij.liferay.util.Icons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PoshiMethodReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {

    private final String namespace;
    private final String className;
    private final String methodName;

    public PoshiMethodReference(@NotNull PsiElement element, String namespace, String className, String methodName, TextRange textRange) {
        super(element, textRange);

        this.namespace = namespace;
        this.className = className;
        this.methodName = methodName;
    }

    @Override
    public ResolveResult @NotNull [] multiResolve(boolean incompleteCode) {
        Collection<PsiElement> results = new ArrayList<>();

        List<PsiFile> psiFiles = PoshiClassReference.getClassFiles(namespace, getElement().getContainingFile().getOriginalFile());

        for (PsiFile psiFile : psiFiles) {
            if (FileUtil.getNameWithoutExtension(psiFile.getName()).equals(className)) {
                if (psiFile instanceof PoshiFile) {
                    PoshiFile poshiFile = (PoshiFile) psiFile;

                    Collection<? extends PoshiDefinitionBase> definitions = PsiTreeUtil.findChildrenOfAnyType(poshiFile, PoshiFunctionDefinition.class, PoshiMacroDefinition.class);

                    for (PoshiDefinitionBase definition : definitions) {
                        if (methodName.equals(definition.getName())) {
                            results.add(definition);
                        }
                    }
                }
            }
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

        List<PsiFile> psiFiles = PoshiClassReference.getClassFiles(namespace, getElement().getContainingFile().getOriginalFile());

        for (PsiFile psiFile : psiFiles) {
            if (FileUtil.getNameWithoutExtension(psiFile.getName()).equals(className)) {
                if (psiFile instanceof PoshiFile) {
                    PoshiFile poshiFile = (PoshiFile) psiFile;

                    Collection<? extends PoshiDefinitionBase> definitions = PsiTreeUtil.findChildrenOfAnyType(poshiFile, PoshiFunctionDefinition.class, PoshiMacroDefinition.class);

                    for (PoshiDefinitionBase definition : definitions) {
                        if (definition.getName() != null) {
                            result.add(LookupElementBuilder.create(definition.getName()).withPsiElement(definition).withIcon(Icons.LIFERAY_ICON));
                        }
                    }
                }
            }
        }

        return result.toArray(new Object[0]);
    }


}