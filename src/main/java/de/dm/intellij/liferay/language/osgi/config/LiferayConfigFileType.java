package de.dm.intellij.liferay.language.osgi.config;

import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.openapi.util.NlsSafe;
import de.dm.intellij.liferay.util.Icons;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class LiferayConfigFileType extends LanguageFileType {

	public static LiferayConfigFileType INSTANCE = new LiferayConfigFileType();
	public static final String DEFAULT_EXTENSION = "config";

	protected LiferayConfigFileType() {
		super(LiferayConfigLanguage.INSTANCE);
	}

	@Override
	public @NonNls @NotNull String getName() {
		return "Liferay Config";
	}

	@Override
	public @NlsContexts.Label @NotNull String getDescription() {
		return "Liferay OSGi Configuration file";
	}

	@Override
	public @NlsSafe @NotNull String getDefaultExtension() {
		return DEFAULT_EXTENSION;
	}

	@Override
	public @Nullable Icon getIcon() {
		return Icons.LIFERAY_ICON;
	}
}
