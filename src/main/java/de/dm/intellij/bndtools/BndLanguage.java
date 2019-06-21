package de.dm.intellij.bndtools;

import com.intellij.lang.Language;

public class BndLanguage extends Language {

    public static final BndLanguage INSTANCE = new BndLanguage();

    public BndLanguage() {
        super("bnd");
    }

}
