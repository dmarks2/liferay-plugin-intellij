package de.dm.intellij.liferay.util;

import java.util.HashMap;
import java.util.Map;

public class LiferayVersions {

    public static final float LIFERAY_VERSION_UNKNOWN = 0.0f;
    public static final float LIFERAY_VERSION_6_1 = 6.1f;
    public static final float LIFERAY_VERSION_6_2 = 6.2f;
    public static final float LIFERAY_VERSION_7_0 = 7.0f;
    public static final float LIFERAY_VERSION_7_1 = 7.1f;
    public static final float LIFERAY_VERSION_7_2 = 7.2f;
    public static final float LIFERAY_VERSION_7_3 = 7.3f;
    public static final float LIFERAY_VERSION_7_4 = 7.4f;

    public static final String LIFERAY_2024_Q1_CE = "7.4.3.112";
    public static final String LIFERAY_2024_Q2_CE = "7.4.3.120";
    public static final String LIFERAY_2024_Q3_CE = "7.4.3.125";
    public static final String LIFERAY_2024_Q4_CE = "7.4.3.129";
    public static final String LIFERAY_2025_Q1_CE = "7.4.3.132";
    public static final String LIFERAY_2025_Q2 = "7.4.3.137";
    public static final String LIFERAY_2025_Q3 = "7.4.3.141";
    public static final String LIFERAY_2025_Q4 = "7.4.3.145";
    public static final String LIFERAY_2026_Q1 = "7.4.3.147";

    private static final Map<String, String> LIFERAY_DXP_INTERNAL_MAPPING = new HashMap<>() {
        {
            put("2025.q2", LIFERAY_2025_Q2);
            put("2025.q3", LIFERAY_2025_Q3);
            put("2025.q4", LIFERAY_2025_Q4);
            put("2026.q1", LIFERAY_2026_Q1);
        }
    };

    public static String getInternalVersion(String liferayVersion) {
        if (liferayVersion.startsWith("202")) {
            int lastIndex = liferayVersion.lastIndexOf('.');

            if (lastIndex >= 0) {
                String internalVersion = liferayVersion.substring(0, lastIndex);

                return LIFERAY_DXP_INTERNAL_MAPPING.getOrDefault(internalVersion, internalVersion);
            }
        }

        return liferayVersion;
    }

    public static String getGAVersion(String liferayVersion) {
        if (liferayVersion.startsWith("202")) {
            return liferayVersion;
        }

        int gaVersionIndex = liferayVersion.lastIndexOf('.') + 1;
        String gaVersion = liferayVersion.substring(gaVersionIndex);

        if (liferayVersion.startsWith("7.4.3")) {
            return "7.4.3." + gaVersion + "-ga" + gaVersion;
        } else {
            int gaVersionInt = Integer.parseInt(gaVersion);

            gaVersionInt++;

            return liferayVersion + "-ga" + gaVersionInt;
        }
    }

    public static String minusOne(String version) {
        int minorVersionIndex = version.lastIndexOf(".") + 1;

        String minorVersion = version.substring(minorVersionIndex);

        int minorVersionInt = Integer.parseInt(minorVersion);

        minorVersionInt--;

        return version.substring(0, minorVersionIndex) + minorVersionInt;
    }

}
