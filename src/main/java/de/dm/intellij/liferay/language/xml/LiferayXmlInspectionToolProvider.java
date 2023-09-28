package de.dm.intellij.liferay.language.xml;

import com.intellij.codeInspection.InspectionToolProvider;
import com.intellij.codeInspection.LocalInspectionTool;
import org.jetbrains.annotations.NotNull;

public class LiferayXmlInspectionToolProvider implements InspectionToolProvider {
	@Override
	@SuppressWarnings("unchecked")
	public Class<? extends LocalInspectionTool> @NotNull [] getInspectionClasses() {
		return new Class[] {
				LiferayXmlDeprecationInspection.class
		};
	}
}
