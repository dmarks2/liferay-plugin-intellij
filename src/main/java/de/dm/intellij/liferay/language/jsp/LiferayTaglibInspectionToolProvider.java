package de.dm.intellij.liferay.language.jsp;

import com.intellij.codeInspection.InspectionToolProvider;
import com.intellij.codeInspection.LocalInspectionTool;
import org.jetbrains.annotations.NotNull;

public class LiferayTaglibInspectionToolProvider implements InspectionToolProvider {

    @Override
    @SuppressWarnings("unchecked")
    public Class<? extends LocalInspectionTool> @NotNull [] getInspectionClasses() {
        return new Class[] {
                LiferayTaglibStrictQuoteEscapingInspection.class,
                LiferayTaglibStringConcatInspection.class,
                LiferayTaglibNestingInspection.class
        };
    }
}
