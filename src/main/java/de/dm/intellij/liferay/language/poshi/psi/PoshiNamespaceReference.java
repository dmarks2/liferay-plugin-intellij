package de.dm.intellij.liferay.language.poshi.psi;

import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementResolveResult;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiPolyVariantReference;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.psi.ResolveResult;
import de.dm.intellij.liferay.language.poshi.constants.PoshiConstants;
import de.dm.intellij.liferay.util.Icons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PoshiNamespaceReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {

    private final String namespace;

    public PoshiNamespaceReference(@NotNull PsiElement element, String namespace, TextRange textRange) {
        super(element, textRange);

        this.namespace = namespace;
    }

    @Override
    public ResolveResult @NotNull [] multiResolve(boolean incompleteCode) {
        Collection<PsiElement> results = new ArrayList<>();

        if (PoshiConstants.DEFAULT_NAMESPACE.equals(namespace)) {
            ClassLoader classLoader = PoshiNamespaceReference.class.getClassLoader();

            URL resource = classLoader.getResource(PoshiConstants.DEFAULT_TEST_FUNCTIONAL_PATH);

            VirtualFile virtualFile = VfsUtil.findFileByURL(resource);

            results.add(PsiManager.getInstance(getElement().getProject()).findDirectory(virtualFile));
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

        //TODO only "Default" supported by now...

        result.add(LookupElementBuilder.create("Default").withIcon(Icons.LIFERAY_ICON));

        return result.toArray(new Object[0]);
    }
}
