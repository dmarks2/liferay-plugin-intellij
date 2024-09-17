package de.dm.intellij.liferay.workspace;

import com.intellij.openapi.util.text.StringUtil;

public class LiferayWorkspaceUtil {

	//see https://releases.liferay.com/releases.json
	public static String getLiferayVersion(String liferayWorkspaceProduct) {
		if (liferayWorkspaceProduct != null) {
			if (StringUtil.startsWith(liferayWorkspaceProduct, "portal")) {
				String versionPart = liferayWorkspaceProduct.substring(7);
				String majorVersion = versionPart.substring(0, versionPart.indexOf("-"));
				String gaVersion = versionPart.substring(versionPart.indexOf("-"));

				if (StringUtil.equals(majorVersion, "7.4")) {
					return majorVersion + ".3." + gaVersion.substring(3);
				} else {
					return majorVersion + "." + (Integer.parseInt(gaVersion.substring(3)) - 1);
				}
			} else if (StringUtil.startsWith(liferayWorkspaceProduct, "dxp")) {
				String versionPart = liferayWorkspaceProduct.substring(4);

				String majorVersion;
				String updateVersion;

				if (versionPart.contains("-")) {
					majorVersion = versionPart.substring(0, versionPart.indexOf("-"));
					updateVersion = versionPart.substring(versionPart.indexOf("-") + 1);
				} else {
					majorVersion = versionPart.substring(0, 3);
					updateVersion = versionPart.substring(4);
				}

				if (StringUtil.equals(majorVersion, "7.4")) {
					return majorVersion + ".13." + updateVersion;
				} else if (StringUtil.startsWith(majorVersion, "7.")) {
					return majorVersion + ".10." + updateVersion;
				} else {
					return versionPart;
				}
			}
		}

		return null;
	}
}
