package de.dm.intellij.bndtools.parser;

import com.intellij.lang.PsiBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.lang.manifest.header.impl.ClassReferenceParser;

public class BndClassReferenceParser extends ClassReferenceParser {

    public static final BndClassReferenceParser INSTANCE = new BndClassReferenceParser();

    @Override
    public void parse(@NotNull PsiBuilder builder) {
        OsgiHeaderParser.INSTANCE.parse(builder);
    }
}
