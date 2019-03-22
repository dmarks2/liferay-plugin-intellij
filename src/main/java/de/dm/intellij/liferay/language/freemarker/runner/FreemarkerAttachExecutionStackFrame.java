package de.dm.intellij.liferay.language.freemarker.runner;

import com.intellij.xdebugger.XSourcePosition;
import com.intellij.xdebugger.evaluation.XDebuggerEvaluator;
import com.intellij.xdebugger.frame.XCompositeNode;
import com.intellij.xdebugger.frame.XStackFrame;
import com.intellij.xdebugger.frame.XValueChildrenList;
import freemarker.debug.DebuggedEnvironment;
import freemarker.template.TemplateModelException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.rmi.RemoteException;

public class FreemarkerAttachExecutionStackFrame extends XStackFrame {

    private DebuggedEnvironment debuggedEnvironment;
    private XSourcePosition xSourcePosition;

    public FreemarkerAttachExecutionStackFrame(DebuggedEnvironment debuggedEnvironment, XSourcePosition xSourcePosition) {
        this.debuggedEnvironment = debuggedEnvironment;
        this.xSourcePosition = xSourcePosition;
    }

    @Nullable
    @Override
    public Object getEqualityObject() {
        return FreemarkerAttachExecutionStackFrame.class;
    }

    @Nullable
    @Override
    public XDebuggerEvaluator getEvaluator() {
        //TODO
        return super.getEvaluator();
    }

    @Nullable
    @Override
    public XSourcePosition getSourcePosition() {
        return xSourcePosition;
    }

    @Override
    public void computeChildren(@NotNull XCompositeNode node) {
        XValueChildrenList xValueChildrenList = new XValueChildrenList();

        addVariable(xValueChildrenList, "currentNamespace", FreemarkerAttachBaseValue.class);
        addVariable(xValueChildrenList, "dataModel", FreemarkerAttachBaseValue.class);
        addVariable(xValueChildrenList, "globalNamespace", FreemarkerAttachBaseValue.class);
        addVariable(xValueChildrenList, "knownVariables", FreemarkerAttachBaseValue.class);
        addVariable(xValueChildrenList, "mainNamespace", FreemarkerAttachBaseValue.class);
        addVariable(xValueChildrenList, "template", FreemarkerAttachTemplateValue.class);

        node.addChildren(xValueChildrenList, true);
    }

    private void addVariable(XValueChildrenList xValueChildrenList, String name, Class<? extends FreemarkerAttachValue> type) {
        try {
            FreemarkerAttachValue freemarkerAttachValue = FreemarkerAttachValueFactory.createFreemarkerAttachValue(debuggedEnvironment, name, type);

            if (freemarkerAttachValue != null) {
                xValueChildrenList.add(name, freemarkerAttachValue);
            }
        } catch (TemplateModelException | RemoteException e) {
            e.printStackTrace();
        }
    }
}
