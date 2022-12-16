package de.dm.intellij.liferay.language.service;

import com.intellij.codeInspection.InspectionToolProvider;
import com.intellij.codeInspection.LocalInspectionTool;
import org.jetbrains.annotations.NotNull;

public class LiferayServiceXMLInspectionToolProvider implements InspectionToolProvider {
    @NotNull
    @Override
    @SuppressWarnings("unchecked")
    public Class<? extends LocalInspectionTool> @NotNull [] getInspectionClasses() {
        return new Class[] {
            LiferayServiceXMLExceptionNameInspection.class,
            LiferayServiceXMLNamespaceInspection.class,
            LiferayServiceXMLDuplicateExceptionInspection.class,
            LiferayServiceXMLDuplicateColumnInspection.class,
            LiferayServiceXMLDuplicateFinderInspection.class,
            LiferayServiceXMLDuplicateEntityInspection.class,
            LiferayServiceXMLPrimaryKeyColumnInspection.class,
            LiferayServiceXMLEntityUuidInspection.class
        };
    }
}
