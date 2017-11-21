package de.dm.intellij.liferay.module;

import com.intellij.util.xmlb.annotations.Attribute;

import java.util.HashMap;
import java.util.Map;

public class LiferayModuleComponentStateWrapper {

    @Attribute("liferayVersion")
    public String liferayVersion;

    public Map<String, String> themeSettings;

    public String liferayLookAndFeelXml;

    public String liferayHookXml;

    public String osgiFragmentHost;

    public String parentTheme;

    public LiferayModuleComponentStateWrapper() {
        this.liferayVersion = "";
        this.liferayLookAndFeelXml = "";
        this.liferayHookXml = "";
        this.osgiFragmentHost = "";
        this.parentTheme = "";
        themeSettings = new HashMap<String, String>();
    }
}
