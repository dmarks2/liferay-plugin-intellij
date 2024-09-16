package de.dm.intellij.liferay.gradle;

import com.intellij.AbstractBundle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.PropertyKey;

import java.util.ResourceBundle;
import java.util.Set;

public class LiferayGradlePropertiesDocumentationBundle extends AbstractBundle {

    private static final String PATH_TO_BUNDLE = "LiferayGradlePropertiesDocumentationBundle";
    private static final AbstractBundle ourInstance = new LiferayGradlePropertiesDocumentationBundle();

    private LiferayGradlePropertiesDocumentationBundle() {
        super(PATH_TO_BUNDLE);
    }

    @Nullable
    public static String message(@NotNull @PropertyKey(resourceBundle = PATH_TO_BUNDLE) String key, @NotNull Object... params) {
        return ourInstance.messageOrNull(key, params);
    }

    public static Set<String> keys() {
        ResourceBundle resourceBundle = ourInstance.getResourceBundle();

        return resourceBundle.keySet();
    }

}
