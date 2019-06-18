package de.dm.intellij.bndtools;

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import org.jetbrains.lang.manifest.highlighting.ManifestColorsAndFonts;

public class OsgiManifestColorsAndFonts {

    public static final TextAttributesKey ATTRIBUTE_ASSIGNMENT_KEY = TextAttributesKey.createTextAttributesKey(
        "bnd.attributeAssignment", ManifestColorsAndFonts.HEADER_ASSIGNMENT_KEY);

    public static final TextAttributesKey ATTRIBUTE_NAME_KEY = TextAttributesKey.createTextAttributesKey(
        "bnd.attributeName", DefaultLanguageHighlighterColors.INSTANCE_FIELD);

    public static final TextAttributesKey ATTRIBUTE_VALUE_KEY = TextAttributesKey.createTextAttributesKey(
        "bnd.attributeValue", DefaultLanguageHighlighterColors.STRING);

    public static final TextAttributesKey CLAUSE_SEPARATOR_KEY = TextAttributesKey.createTextAttributesKey(
        "bnd.clauseSeparator", DefaultLanguageHighlighterColors.COMMA);

    public static final TextAttributesKey DIRECTIVE_ASSIGNMENT_KEY = TextAttributesKey.createTextAttributesKey(
        "bnd.directiveAssignment", ManifestColorsAndFonts.HEADER_ASSIGNMENT_KEY);

    public static final TextAttributesKey DIRECTIVE_NAME_KEY = TextAttributesKey.createTextAttributesKey(
        "bnd.directiveName", DefaultLanguageHighlighterColors.INSTANCE_FIELD);

    public static final TextAttributesKey DIRECTIVE_VALUE_KEY = TextAttributesKey.createTextAttributesKey(
        "bnd.directiveValue", DefaultLanguageHighlighterColors.STRING);

    public static final TextAttributesKey PARAMETER_SEPARATOR_KEY = TextAttributesKey.createTextAttributesKey(
        "bnd.parameterSeparator", DefaultLanguageHighlighterColors.SEMICOLON);

    private OsgiManifestColorsAndFonts() {
    }

}
