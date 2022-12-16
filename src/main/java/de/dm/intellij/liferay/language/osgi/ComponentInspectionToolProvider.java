package de.dm.intellij.liferay.language.osgi;

import com.intellij.codeInspection.InspectionToolProvider;
import com.intellij.codeInspection.LocalInspectionTool;
import org.jetbrains.annotations.NotNull;

public class ComponentInspectionToolProvider implements InspectionToolProvider {

    @Override
    @SuppressWarnings("unchecked")
    public Class<? extends LocalInspectionTool> @NotNull [] getInspectionClasses() {
        return new Class[] {
                ComponentServiceInheritanceInspection.class,
                ReferenceWithoutComponentInspection.class,
                MetaConfigurationInvalidIdInspection.class
        };
    }
}
