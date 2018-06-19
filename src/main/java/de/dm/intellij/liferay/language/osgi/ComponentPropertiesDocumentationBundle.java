package de.dm.intellij.liferay.language.osgi;

import com.intellij.AbstractBundle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

public class ComponentPropertiesDocumentationBundle extends AbstractBundle {

    private static final String PATH_TO_BUNDLE = "ComponentPropertiesDocumentationBundle";
    private static final AbstractBundle ourInstance = new ComponentPropertiesDocumentationBundle();

    private ComponentPropertiesDocumentationBundle() {
        super(PATH_TO_BUNDLE);
    }

    public static String message(@NotNull @PropertyKey(resourceBundle = PATH_TO_BUNDLE) String key, @NotNull Object... params) {
        return ourInstance.getMessage(key, params);
    }

}
