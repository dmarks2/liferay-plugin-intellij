package de.dm.intellij.liferay.schema;

import com.intellij.json.JsonFileType;
import de.dm.intellij.liferay.util.Icons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class LiferayNpmBundlerRcFileType extends JsonFileType {

	public static final LiferayNpmBundlerRcFileType INSTANCE = new LiferayNpmBundlerRcFileType();

	@Override
	public @NotNull String getName() {
		return ".npmbundlerrc";
	}

	@Override
	public @Nullable Icon getIcon() {
		return Icons.LIFERAY_ICON;
	}
}
