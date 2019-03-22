package de.dm.intellij.liferay.language.freemarker.runner;

import com.intellij.util.PlatformIcons;
import com.intellij.xdebugger.frame.XValueNode;
import com.intellij.xdebugger.frame.XValuePlace;
import freemarker.debug.DebugModel;
import org.jetbrains.annotations.NotNull;

public class FreemarkerAttachBaseValue extends FreemarkerAttachValue {

    public FreemarkerAttachBaseValue(DebugModel debugModel) {
        super(debugModel);
    }

    @Override
    public void computePresentation(@NotNull XValueNode node, @NotNull XValuePlace place) {
        String value = getValueString();
        String type = getTypeString();

        node.setPresentation(
            PlatformIcons.VARIABLE_ICON,
            type,
            value,
            hasChildren()
        );
    }
}
