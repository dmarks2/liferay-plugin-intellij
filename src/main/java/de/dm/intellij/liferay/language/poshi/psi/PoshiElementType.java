package de.dm.intellij.liferay.language.poshi.psi;

import com.intellij.psi.tree.IElementType;
import de.dm.intellij.liferay.language.poshi.PoshiLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class PoshiElementType extends IElementType {
    public PoshiElementType(@NonNls @NotNull String debugName) {
        super(debugName, PoshiLanguage.INSTANCE);
    }
}
