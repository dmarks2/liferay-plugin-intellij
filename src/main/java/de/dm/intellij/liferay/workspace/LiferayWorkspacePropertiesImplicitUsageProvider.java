package de.dm.intellij.liferay.workspace;

import com.intellij.codeInspection.unused.ImplicitPropertyUsageProvider;
import com.intellij.lang.properties.psi.Property;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public class LiferayWorkspacePropertiesImplicitUsageProvider extends ImplicitPropertyUsageProvider {

    private static final String BLADE_PROPERTIES = ".blade.properties";

    @Override
    protected boolean isUsed(@NotNull Property property) {
        PsiFile containingFile = property.getContainingFile();

        String propertyKey = property.getKey();

        if ( (containingFile != null) && (propertyKey != null) ) {
            String fileName = containingFile.getName();

            return BLADE_PROPERTIES.equals(fileName);
        }

        return false;
    }

}
