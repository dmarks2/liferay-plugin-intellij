package de.dm.intellij.liferay.language.sass;

import com.intellij.AbstractBundle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.PropertyKey;

public class ClayVariablesDocumentationBundle extends AbstractBundle {

    private static final String PATH_TO_BUNDLE = "ClayVariablesDocumentationBundle";
    private static final AbstractBundle ourInstance = new ClayVariablesDocumentationBundle();

    private ClayVariablesDocumentationBundle() {
        super(PATH_TO_BUNDLE);
    }

    public static @Nullable String message(@NotNull @PropertyKey(resourceBundle = PATH_TO_BUNDLE) String key, @NotNull Object... params) {
        return ourInstance.messageOrNull(key, params);
    }


}
