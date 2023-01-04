package de.dm.intellij.liferay.language.poshi;

import com.intellij.lang.Language;

public class PoshiLanguage extends Language {

    public static final String LANGUAGE_ID = "Poshi";

    public static final PoshiLanguage INSTANCE = new PoshiLanguage();

    protected PoshiLanguage() {
        super(LANGUAGE_ID);
    }
}
