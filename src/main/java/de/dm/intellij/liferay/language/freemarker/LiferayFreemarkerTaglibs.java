package de.dm.intellij.liferay.language.freemarker;

import com.intellij.freemarker.psi.directives.FtlMacro;
import de.dm.intellij.liferay.util.LiferayTaglibs;

import java.util.HashMap;
import java.util.Map;

public class LiferayFreemarkerTaglibs {

    public static final Map<String, String> FTL_MACRO_PREFIXES = new HashMap<String, String>();

    static {
        FTL_MACRO_PREFIXES.put("liferay_aui", LiferayTaglibs.TAGLIB_URI_AUI);
        FTL_MACRO_PREFIXES.put("liferay_ui", LiferayTaglibs.TAGLIB_URI_LIFERAY_UI);
        FTL_MACRO_PREFIXES.put("liferay_frontend", LiferayTaglibs.TAGLIB_URI_LIFERAY_FRONTEND);
    }

    public static String getNamespace(FtlMacro ftlMacro) {
        String directiveName = ftlMacro.getDirectiveName();
        if (directiveName.contains(".")) {
            String[] split = directiveName.split("\\.");
            if (split.length == 2) {
                String prefix = split[0];
                if (LiferayFreemarkerTaglibs.FTL_MACRO_PREFIXES.containsKey(prefix)) {
                    return LiferayFreemarkerTaglibs.FTL_MACRO_PREFIXES.get(prefix);
                }
            }
        }
        return "";
    }

    public static String getLocalName(FtlMacro ftlMacro) {
        String directiveName = ftlMacro.getDirectiveName();
        if (directiveName.contains(".")) {
            String[] split = directiveName.split("\\.");
            if (split.length == 2) {
                return split[1];
            }
        }
        return "";
    }
}
