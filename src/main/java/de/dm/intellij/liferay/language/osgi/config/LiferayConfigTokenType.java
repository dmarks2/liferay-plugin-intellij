package de.dm.intellij.liferay.language.osgi.config;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class LiferayConfigTokenType extends IElementType {

	public LiferayConfigTokenType(@NonNls @NotNull String debugName) {
		super(debugName, LiferayConfigLanguage.INSTANCE);
	}

	@Override
	public String toString() {
		return "LiferayConfigTokenType." + super.toString();
	}
}
