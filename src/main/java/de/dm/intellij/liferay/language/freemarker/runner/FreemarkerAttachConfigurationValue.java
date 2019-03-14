package de.dm.intellij.liferay.language.freemarker.runner;

import com.intellij.xdebugger.frame.XCompositeNode;
import com.intellij.xdebugger.frame.XValueChildrenList;
import freemarker.debug.DebugModel;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.TreeMap;

public class FreemarkerAttachConfigurationValue extends FreemarkerAttachValue {

    public FreemarkerAttachConfigurationValue(DebugModel debugModel) {
        super(debugModel);
    }

    @Override
    protected boolean hasChildren() {
        return true;
    }

    @Override
    public void computeChildren(@NotNull XCompositeNode node) {
        XValueChildrenList xValueChildrenList = new XValueChildrenList();

        Map<String, FreemarkerAttachValue> map = new TreeMap<>();

        addVariable(map, "sharedVariables", FreemarkerAttachValue.class);

        for (Map.Entry<String, FreemarkerAttachValue> entry : map.entrySet()) {
            xValueChildrenList.add(entry.getKey(),entry.getValue());
        }

        node.addChildren(xValueChildrenList, true);
    }
}
