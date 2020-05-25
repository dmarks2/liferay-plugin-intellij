package de.dm.intellij.bndtools;

import com.intellij.lang.Language;

public class BndLanguage extends Language {

    private static BndLanguage INSTANCE;

    public static BndLanguage getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BndLanguage();
        }

        return INSTANCE;
    }

    public BndLanguage() {
        super("bnd");
    }

}
