package de.dm.intellij.liferay.language.poshi.psi;

import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementResolveResult;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiPolyVariantReference;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.psi.ResolveResult;
import com.intellij.psi.util.PsiTreeUtil;
import de.dm.intellij.liferay.language.poshi.constants.PoshiConstants;
import de.dm.intellij.liferay.util.Icons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PoshiClassReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {

    private static final String[] CLASS_DIRECTORIES = {PoshiConstants.FUNCTIONS_DIRECTORY, PoshiConstants.MACROS_DIRECTORY};

    private final String className;

    public PoshiClassReference(@NotNull PsiElement element, String className, TextRange textRange) {
        super(element, textRange);

        this.className = className;
    }

    @Override
    public ResolveResult @NotNull [] multiResolve(boolean incompleteCode) {
        Collection<PsiElement> results = new ArrayList<>();

        List<PsiFile> psiFiles = getClassFiles(getElement().getContainingFile().getOriginalFile());

        for (PsiFile psiFile : psiFiles) {
            if (FileUtil.getNameWithoutExtension(psiFile.getName()).equals(className)) {
                results.add(psiFile);
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
        List<Object> result = new ArrayList<Object>();

        List<PsiFile> psiFiles = getClassFiles(getElement().getContainingFile().getOriginalFile());

        for (PsiFile psiFile : psiFiles) {
            result.add(LookupElementBuilder.create(FileUtil.getNameWithoutExtension(psiFile.getName())).withPsiElement(psiFile).withIcon(Icons.LIFERAY_ICON));
        }

        return result.toArray(new Object[0]);
    }

    public static List<PsiFile> getClassFiles(@NotNull PsiFile testcaseFile) {
        List<PsiFile> result = new ArrayList<>();

        PsiDirectory parent = testcaseFile.getParent();

        if (parent != null) {
            parent = parent.getParent();

            if (parent != null) {
                for (String classDirectory : CLASS_DIRECTORIES) {
                    PsiDirectory subdirectory = parent.findSubdirectory(classDirectory);

                    if (subdirectory != null) {
                        for (PsiFile psiFile : subdirectory.getFiles()) {
                            if (psiFile.getName().endsWith(PoshiConstants.FUNCTION_EXTENSION) || psiFile.getName().endsWith(PoshiConstants.MACRO_EXTENSION)) {
                                result.add(psiFile);
                            }
                        }
                    }
                }
            }
        }

        return result;
    }


}