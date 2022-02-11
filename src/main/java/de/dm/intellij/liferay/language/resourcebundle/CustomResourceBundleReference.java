package de.dm.intellij.liferay.language.resourcebundle;

import com.intellij.lang.properties.PropertiesUtil;
import com.intellij.lang.properties.ResourceBundleReference;
import com.intellij.lang.properties.psi.PropertiesFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

public class CustomResourceBundleReference extends ResourceBundleReference {

    public CustomResourceBundleReference(PsiElement element) {
        super(element);
    }

    @Override
    public String evaluateBundleName(PsiFile psiFile) {
        String baseName = super.evaluateBundleName(psiFile);

        if (psiFile instanceof PropertiesFile) {
            PropertiesFile propertiesFile = (PropertiesFile) psiFile;

            String suffix = PropertiesUtil.getSuffix(propertiesFile);

            if (suffix.length() > 0) {
                baseName = baseName + "_" + suffix;
            }
        }

        return baseName;
    }
}
