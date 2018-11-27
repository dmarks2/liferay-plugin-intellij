package de.dm.intellij.liferay.theme;

import java.util.HashMap;
import java.util.Map;

public class LiferayThemeTemplateVariables {

    public static final Map<String, String> THEME_TEMPLATE_VARIABLE_DIRECTORY_REFERENCES = new HashMap<String, String>();

    static {
        THEME_TEMPLATE_VARIABLE_DIRECTORY_REFERENCES.put("images_folder", LiferayLookAndFeelXmlParser.IMAGES_PATH);
        THEME_TEMPLATE_VARIABLE_DIRECTORY_REFERENCES.put("css_folder", LiferayLookAndFeelXmlParser.CSS_PATH);
        THEME_TEMPLATE_VARIABLE_DIRECTORY_REFERENCES.put("full_css_path", LiferayLookAndFeelXmlParser.CSS_PATH);
        THEME_TEMPLATE_VARIABLE_DIRECTORY_REFERENCES.put("javascript_folder", LiferayLookAndFeelXmlParser.JAVASCRIPT_PATH);
        THEME_TEMPLATE_VARIABLE_DIRECTORY_REFERENCES.put("templates_folder", LiferayLookAndFeelXmlParser.TEMPLATES_PATH);
        THEME_TEMPLATE_VARIABLE_DIRECTORY_REFERENCES.put("full_templates_path", LiferayLookAndFeelXmlParser.TEMPLATES_PATH);
    }

}
