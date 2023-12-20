package de.dm.intellij.liferay.language.resourcebundle;

import com.intellij.lang.properties.IProperty;
import com.intellij.lang.properties.psi.PropertiesFile;
import com.intellij.lang.properties.references.PropertyReference;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementResolveResult;
import com.intellij.psi.ResolveResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LiferayTaglibResourceBundlePropertyReference extends PropertyReference {

    public LiferayTaglibResourceBundlePropertyReference(@NotNull String key, @NotNull PsiElement element, @Nullable String bundleName, boolean soft) {
        super(key, element, bundleName, soft);
    }

    @Override
    public ResolveResult @NotNull [] multiResolve(boolean incompleteCode) {
        ResolveResult[] resolveResults = super.multiResolve(incompleteCode);
        List<ResolveResult> filteredResult = new ArrayList<>();
        for (ResolveResult resolveResult : resolveResults) {
            if (resolveResult instanceof PsiElementResolveResult psiElementResolveResult) {
				PsiElement psiElement = psiElementResolveResult.getElement();
                if (psiElement instanceof IProperty property) {
					PropertiesFile propertiesFile = property.getPropertiesFile();
                    if (isLanguageFile(propertiesFile)) {
                        filteredResult.add(resolveResult);
                    }
                }
            }
        }

        return filteredResult.toArray(new ResolveResult[0]);
    }

    @Override
    protected void addKey(Object propertyObject, Set<Object> variants) {
        if (propertyObject instanceof IProperty property) {
			PropertiesFile propertiesFile = property.getPropertiesFile();
            if (isLanguageFile(propertiesFile)) {
                super.addKey(propertyObject, variants);
            }
        }
    }

    private static boolean isLanguageFile(PropertiesFile propertiesFile) {
        if (propertiesFile != null) {
            String name = propertiesFile.getName();
			return (name.startsWith("Language"));
		}
        return false;
    }
}
