package de.dm.intellij.liferay.language.jsp;

import com.intellij.codeInspection.InspectionToolProvider;
import org.jetbrains.annotations.NotNull;

public class LiferayTaglibInspectionToolProvider implements InspectionToolProvider {

    @NotNull
    @Override
    public Class[] getInspectionClasses() {
        return new Class[] {
                LiferayTaglibStrictQuoteEscapingInspection.class,
                LiferayTaglibStringConcatInspection.class
        };
    }
}
