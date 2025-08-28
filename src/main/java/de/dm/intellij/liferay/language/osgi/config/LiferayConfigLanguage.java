package de.dm.intellij.liferay.language.osgi.config;

import com.intellij.lang.Language;

public class LiferayConfigLanguage extends Language {

	public static final LiferayConfigLanguage INSTANCE = new LiferayConfigLanguage();

	private LiferayConfigLanguage() {
		super("LiferayConfig");
	}
}
