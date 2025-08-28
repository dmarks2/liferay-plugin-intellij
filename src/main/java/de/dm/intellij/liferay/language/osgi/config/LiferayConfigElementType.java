package de.dm.intellij.liferay.language.osgi.config;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class LiferayConfigElementType extends IElementType {
    public LiferayConfigElementType(@NotNull @NonNls String debugName) {
        super(debugName, LiferayConfigLanguage.INSTANCE);
    }

    @Override
    public String toString() {
        return "LiferayConfigElementType." + super.toString();
    }
}
