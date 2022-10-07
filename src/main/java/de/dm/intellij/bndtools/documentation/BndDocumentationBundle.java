package de.dm.intellij.bndtools.documentation;

import com.intellij.AbstractBundle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.PropertyKey;

public class BndDocumentationBundle extends AbstractBundle {

    private static final String PATH_TO_BUNDLE = "BndDocumentationBundle";

    private static final AbstractBundle ourInstance = new BndDocumentationBundle();

    private BndDocumentationBundle() {
        super(PATH_TO_BUNDLE);
    }

    @Nullable
    public static String message(@NotNull @PropertyKey(resourceBundle = PATH_TO_BUNDLE) String key, @NotNull Object... params) {
        return ourInstance.messageOrNull(key, params);
    }
}
