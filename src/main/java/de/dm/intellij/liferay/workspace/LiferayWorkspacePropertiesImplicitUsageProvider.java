package de.dm.intellij.liferay.workspace;

import com.intellij.lang.properties.codeInspection.unused.ImplicitPropertyUsageProvider;
import com.intellij.lang.properties.psi.Property;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public class LiferayWorkspacePropertiesImplicitUsageProvider implements ImplicitPropertyUsageProvider {

    private static final String BLADE_PROPERTIES = ".blade.properties";

    @Override
    public boolean isUsed(@NotNull Property property) {
        PsiFile containingFile = property.getContainingFile();

        String propertyKey = property.getKey();

        if ( (containingFile != null) && (propertyKey != null) ) {
            String fileName = containingFile.getName();

            return BLADE_PROPERTIES.equals(fileName);
        }

        return false;
    }

}
