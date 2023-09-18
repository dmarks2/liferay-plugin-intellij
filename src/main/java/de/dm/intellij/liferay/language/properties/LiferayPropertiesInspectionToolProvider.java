package de.dm.intellij.liferay.language.properties;

import com.intellij.codeInspection.InspectionToolProvider;
import com.intellij.codeInspection.LocalInspectionTool;
import org.jetbrains.annotations.NotNull;

public class LiferayPropertiesInspectionToolProvider implements InspectionToolProvider {
	@Override
	@SuppressWarnings("unchecked")
	public Class<? extends LocalInspectionTool> @NotNull [] getInspectionClasses() {
		return new Class[] {
				LiferayPropertiesDeprecationInspection.class
		};
	}
}