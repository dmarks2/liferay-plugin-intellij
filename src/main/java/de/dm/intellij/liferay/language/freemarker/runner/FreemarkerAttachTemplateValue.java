package de.dm.intellij.liferay.language.freemarker.runner;

import com.intellij.xdebugger.frame.XCompositeNode;
import com.intellij.xdebugger.frame.XValueChildrenList;
import freemarker.debug.DebugModel;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.TreeMap;

public class FreemarkerAttachTemplateValue extends FreemarkerAttachBaseValue {

    public FreemarkerAttachTemplateValue(DebugModel debugModel) {
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

        addVariable(map, "name", FreemarkerAttachValue.class);
        addVariable(map, "configuration", FreemarkerAttachConfigurationValue.class);

        for (Map.Entry<String, FreemarkerAttachValue> entry : map.entrySet()) {
            xValueChildrenList.add(entry.getKey(),entry.getValue());
        }

        node.addChildren(xValueChildrenList, true);
    }

}
