package de.dm.intellij.liferay.language.resourcebundle;

import com.intellij.codeInspection.unused.ImplicitPropertyUsageProvider;
import com.intellij.lang.properties.psi.Property;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public class LiferayResourceBundlePropertiesImplicitUsageProvider extends ImplicitPropertyUsageProvider {

    private static final String LANGUAGE_PROPERTY_FILE_PREFIX = "Language";

    private static final String[] IMPLICIT_PROPERTY_PREFIXES = new String[] {
            "javax.portlet.title.",
            "model.resource.",
            "action."
    };

    @Override
    protected boolean isUsed(@NotNull Property property) {
        PsiFile containingFile = property.getContainingFile();

        if (containingFile != null) {
            String fileName = containingFile.getName();

            if (fileName.startsWith(LANGUAGE_PROPERTY_FILE_PREFIX)) {
                String propertyKey = property.getKey();

                if (propertyKey != null) {

                    for (String implicitPropertyPrefix : IMPLICIT_PROPERTY_PREFIXES) {
                        if (propertyKey.startsWith(implicitPropertyPrefix)) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }
}
