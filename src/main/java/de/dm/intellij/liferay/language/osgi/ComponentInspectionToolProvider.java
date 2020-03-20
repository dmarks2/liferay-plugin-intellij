package de.dm.intellij.liferay.language.osgi;

import com.intellij.codeInspection.InspectionToolProvider;
import org.jetbrains.annotations.NotNull;

public class ComponentInspectionToolProvider implements InspectionToolProvider {
    @NotNull
    @Override
    public Class[] getInspectionClasses() {
        return new Class[] {
                ComponentServiceInheritanceInspection.class
        };
    }
}
