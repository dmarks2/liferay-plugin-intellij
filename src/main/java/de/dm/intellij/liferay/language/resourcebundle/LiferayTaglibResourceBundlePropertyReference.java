package de.dm.intellij.liferay.language.resourcebundle;

import com.intellij.lang.properties.IProperty;
import com.intellij.lang.properties.psi.PropertiesFile;
import com.intellij.lang.properties.references.PropertyReference;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementResolveResult;
import com.intellij.psi.ResolveResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LiferayTaglibResourceBundlePropertyReference extends PropertyReference {

    public LiferayTaglibResourceBundlePropertyReference(@NotNull String key, @NotNull PsiElement element, @Nullable String bundleName, boolean soft, TextRange range) {
        super(key, element, bundleName, soft, range);
    }

    public LiferayTaglibResourceBundlePropertyReference(@NotNull String key, @NotNull PsiElement element, @Nullable String bundleName, boolean soft) {
        super(key, element, bundleName, soft);
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean incompleteCode) {
        ResolveResult[] resolveResults = super.multiResolve(incompleteCode);
        List<ResolveResult> filteredResult = new ArrayList<ResolveResult>();
        for (ResolveResult resolveResult : resolveResults) {
            if (resolveResult instanceof PsiElementResolveResult) {
                PsiElementResolveResult psiElementResolveResult = (PsiElementResolveResult)resolveResult;
                PsiElement psiElement = psiElementResolveResult.getElement();
                if (psiElement instanceof IProperty) {
                    IProperty property = (IProperty)psiElement;
                    PropertiesFile propertiesFile = property.getPropertiesFile();
                    if (isLanguageFile(propertiesFile)) {
                        filteredResult.add(resolveResult);
                    }
                }
            }
        }

        return filteredResult.toArray(new ResolveResult[filteredResult.size()]);
    }

    @Override
    protected void addKey(Object propertyObject, Set<Object> variants) {
        if (propertyObject instanceof IProperty) {
            IProperty property = (IProperty)propertyObject;
            PropertiesFile propertiesFile = property.getPropertiesFile();
            if (isLanguageFile(propertiesFile)) {
                super.addKey(propertyObject, variants);
            }
        }
    }

    private static boolean isLanguageFile(PropertiesFile propertiesFile) {
        if (propertiesFile != null) {
            String name = propertiesFile.getName();
            if (name != null) {
                return (name.startsWith("Language"));
            }
        }
        return false;
    }
}
