package de.dm.intellij.liferay.language.freemarker;

import com.intellij.codeInspection.InspectionToolProvider;
import com.intellij.codeInspection.LocalInspectionTool;
import org.jetbrains.annotations.NotNull;

public class LiferayFreemarkerInspectionToolProvider implements InspectionToolProvider {
	@Override
	@SuppressWarnings("unchecked")
	public Class<? extends LocalInspectionTool> @NotNull [] getInspectionClasses() {
		return new Class[] {
				LiferayFreemarkerTaglibDeprecationInspection.class
		};
	}
}
