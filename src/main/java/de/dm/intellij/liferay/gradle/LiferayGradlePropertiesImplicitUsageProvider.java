package de.dm.intellij.liferay.gradle;

import com.intellij.lang.properties.codeInspection.unused.ImplicitPropertyUsageProvider;
import com.intellij.lang.properties.psi.Property;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public class LiferayGradlePropertiesImplicitUsageProvider implements ImplicitPropertyUsageProvider {

    private static final String GRADLE_PROPERTIES = "gradle.properties";
    private static final String GRADLE_LOCAL_PROPERTIES = "gradle-local.properties";
    private static final String LIFERAY_WORKSPACE_PREFIX = "liferay.workspace.";

    @Override
    public boolean isUsed(@NotNull Property property) {
        PsiFile containingFile = property.getContainingFile();

        String propertyKey = property.getKey();

        if ( (containingFile != null) && (propertyKey != null) ) {
            String fileName = containingFile.getName();

            if (
                    (GRADLE_PROPERTIES.equals(fileName)) || (GRADLE_LOCAL_PROPERTIES.equals(fileName))
            ) {
                return propertyKey.startsWith(LIFERAY_WORKSPACE_PREFIX);
            }
        }

        return false;
    }
}
