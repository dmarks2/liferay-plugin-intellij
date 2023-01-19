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
import de.dm.intellij.liferay.language.poshi.constants.PoshiConstants;
import de.dm.intellij.liferay.util.Icons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PoshiPathReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {

    private final String pathName;

    public PoshiPathReference(@NotNull PsiElement element, String pathName, TextRange textRange) {
        super(element, textRange);

        this.pathName = pathName;
    }

    @Override
    public ResolveResult @NotNull [] multiResolve(boolean incompleteCode) {
        Collection<PsiElement> results = new ArrayList<>();

        List<PsiFile> psiFiles = getPathFiles(getElement().getContainingFile().getOriginalFile());

        for (PsiFile psiFile : psiFiles) {
            if (FileUtil.getNameWithoutExtension(psiFile.getName()).equals(pathName)) {
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

        List<PsiFile> psiFiles = getPathFiles(getElement().getContainingFile().getOriginalFile());

        for (PsiFile psiFile : psiFiles) {
            result.add(LookupElementBuilder.create(FileUtil.getNameWithoutExtension(psiFile.getName())).withPsiElement(psiFile).withIcon(Icons.LIFERAY_ICON));
        }

        return result.toArray(new Object[0]);
    }

    public static List<PsiFile> getPathFiles(@NotNull PsiFile testcaseFile) {
        List<PsiFile> result = new ArrayList<>();

        PsiDirectory parent = testcaseFile.getParent();

        if (parent != null) {
            parent = parent.getParent();

            if (parent != null) {
                PsiDirectory subdirectory = parent.findSubdirectory(PoshiConstants.PATHS_DIRECTORY);

                if (subdirectory != null) {
                    for (PsiFile psiFile : subdirectory.getFiles()) {
                        if (psiFile.getName().endsWith(PoshiConstants.PATH_EXTENSION)) {
                            result.add(psiFile);
                        }
                    }
                }
            }
        }

        return result;
    }


}