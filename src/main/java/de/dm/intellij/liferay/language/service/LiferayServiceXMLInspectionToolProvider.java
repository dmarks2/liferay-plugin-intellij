package de.dm.intellij.liferay.language.service;

import com.intellij.codeInspection.InspectionToolProvider;
import org.jetbrains.annotations.NotNull;

public class LiferayServiceXMLInspectionToolProvider implements InspectionToolProvider {
    @NotNull
    @Override
    public Class[] getInspectionClasses() {
        return new Class[] {
                LiferayServiceXMLExceptionNameInspection.class
        };
    }
}
