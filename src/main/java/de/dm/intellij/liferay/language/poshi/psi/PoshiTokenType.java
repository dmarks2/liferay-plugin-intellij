package de.dm.intellij.liferay.language.poshi.psi;

import com.intellij.psi.tree.IElementType;
import de.dm.intellij.liferay.language.poshi.PoshiLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class PoshiTokenType extends IElementType {

    public PoshiTokenType(@NonNls @NotNull String debugName) {
        super(debugName, PoshiLanguage.INSTANCE);
    }

    @Override
    public String toString() {
        return "PoshiTokenType." + super.toString();
    }
}
