package de.dm.intellij.liferay.language.resourcebundle;

import com.intellij.codeInsight.completion.PrioritizedLookupElement;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.icons.AllIcons;
import com.intellij.lang.properties.PropertiesFileProcessor;
import com.intellij.lang.properties.PropertiesReferenceManager;
import com.intellij.lang.properties.PropertiesUtil;
import com.intellij.lang.properties.ResourceBundleReference;
import com.intellij.lang.properties.psi.PropertiesFile;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementResolveResult;
import com.intellij.psi.PsiFile;
import com.intellij.psi.ResolveResult;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.SmartList;
import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomResourceBundleReference extends ResourceBundleReference {

    public CustomResourceBundleReference(PsiElement element) {
        super(element);
    }

    @Override
    public String evaluateBundleName(PsiFile psiFile) {
        String baseName = super.evaluateBundleName(psiFile);

        if (psiFile instanceof PropertiesFile propertiesFile) {
			String suffix = PropertiesUtil.getSuffix(propertiesFile);

            if (!suffix.isEmpty()) {
                baseName = baseName + "_" + suffix;
            }
        }

        return baseName;
    }

    @Override
    public ResolveResult @NotNull [] multiResolve(final boolean incompleteCode) {
        PropertiesReferenceManager referenceManager = PropertiesReferenceManager.getInstance(myElement.getProject());
        List<PropertiesFile> propertiesFiles = referenceManager.findPropertiesFiles(getResolveScope(), getCanonicalText(), this);
        return PsiElementResolveResult.createResults(ContainerUtil.map(propertiesFiles, PropertiesFile::getContainingFile));
    }

    @Override
    public Object @NotNull [] getVariants() {
        final ProjectFileIndex projectFileIndex = ProjectFileIndex.getInstance(getElement().getProject());
        final PropertiesReferenceManager referenceManager = PropertiesReferenceManager.getInstance(getElement().getProject());

        final Set<String> bundleNames = new HashSet<>();
        final List<LookupElement> variants = new SmartList<>();
        PropertiesFileProcessor processor = (baseName, propertiesFile) -> {
            if (!bundleNames.add(baseName)) return true;

            final LookupElementBuilder builder =
                    LookupElementBuilder.create(baseName)
                            .withIcon(AllIcons.Nodes.ResourceBundle);
            boolean isInContent = projectFileIndex.isInContent(propertiesFile.getVirtualFile());
            variants.add(isInContent ? PrioritizedLookupElement.withPriority(builder, Double.MAX_VALUE) : builder);
            return true;
        };

        referenceManager.processPropertiesFiles(getResolveScope(), processor, this);
        return variants.toArray(LookupElement.EMPTY_ARRAY);
    }


    private GlobalSearchScope getResolveScope() {
        Module module = ModuleUtil.findModuleForPsiElement(myElement);

        if (module != null) {
            return GlobalSearchScope.moduleScope(module);
        }

        return GlobalSearchScope.allScope(myElement.getProject());
    }
}
