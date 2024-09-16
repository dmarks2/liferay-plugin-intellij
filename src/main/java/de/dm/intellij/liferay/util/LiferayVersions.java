package de.dm.intellij.liferay.util;

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

    public static String getGAVersion(String liferayVersion) {
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
