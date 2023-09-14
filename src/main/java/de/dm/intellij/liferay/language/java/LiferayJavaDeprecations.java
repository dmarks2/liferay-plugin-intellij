package de.dm.intellij.liferay.language.java;

public class LiferayJavaDeprecations {

	public record JavaImportDeprecation(float majorLiferayVersion, String message, String ticket, String[] importStatements, String[] newImportStatements) {}

	public static LiferayJavaDeprecations.JavaImportDeprecation LPS_50156_UTIL_BRIDGES = new LiferayJavaDeprecations.JavaImportDeprecation(
			7.0f, "The classes from package com.liferay.util.bridges.mvc in util-bridges.jar were moved to a new package com.liferay.portal.kernel.portlet.bridges.mvc in portal-service.jar.", "LPS-50156",
			new String[] {"com.liferay.util.bridges.mvc.ActionCommand", "com.liferay.util.bridges.mvc.BaseActionCommand", "com.liferay.util.bridges.mvc.MVCPortlet"},
			new String[] {"com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand", "com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand", "com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet"});


}
