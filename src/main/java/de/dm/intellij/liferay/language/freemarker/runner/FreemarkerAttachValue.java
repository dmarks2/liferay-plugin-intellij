package de.dm.intellij.liferay.language.freemarker.runner;

import com.intellij.util.PlatformIcons;
import com.intellij.xdebugger.frame.XCompositeNode;
import com.intellij.xdebugger.frame.XValue;
import com.intellij.xdebugger.frame.XValueChildrenList;
import com.intellij.xdebugger.frame.XValueNode;
import com.intellij.xdebugger.frame.XValuePlace;
import freemarker.debug.DebugModel;
import freemarker.template.TemplateModelException;
import org.jetbrains.annotations.NotNull;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.TreeMap;

public class FreemarkerAttachValue extends XValue {

    private DebugModel debugModel;

    public FreemarkerAttachValue(DebugModel debugModel) {
        this.debugModel = debugModel;
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

    protected boolean hasChildren() {
        try {
            int types = this.debugModel.getModelTypes();

            if ( (isSequenceType(types)) || (isHashTypeEx(types)) ) {
                return this.debugModel.size() > 0;
            }
        } catch (RemoteException | TemplateModelException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void computeChildren(@NotNull XCompositeNode node) {
        XValueChildrenList xValueChildrenList = new XValueChildrenList();

        try {
            int types = this.debugModel.getModelTypes();

            if (isHashTypeEx(types)) {
                String[] keys = this.debugModel.keys();

                Map<String, FreemarkerAttachValue> map = new TreeMap<>();

                for (String key : keys) {
                    addVariable(map, key, FreemarkerAttachValue.class);
                }

                for (Map.Entry<String, FreemarkerAttachValue> entry : map.entrySet()) {
                    xValueChildrenList.add(entry.getKey(), entry.getValue());
                }

            } else if (isSequenceType(types)) {
                if (FreemarkerAttachValueFactory.isValidSequence(debugModel)) {
                    int length = debugModel.size();

                    DebugModel[] debugModels = debugModel.get(0, length);

                    Map<String, FreemarkerAttachValue> map = new TreeMap<>();

                    for (int i = 0; i < length; i++) {
                        addVariable(map, debugModels[i], String.valueOf(i), FreemarkerAttachValue.class);
                    }

                    for (Map.Entry<String, FreemarkerAttachValue> entry : map.entrySet()) {
                        xValueChildrenList.add(entry.getKey(), entry.getValue());
                    }
                }
            }

            node.addChildren(xValueChildrenList, true);
        } catch (RemoteException | TemplateModelException | ClassCastException e) {
            e.printStackTrace();
        }
    }

    public String getValueString() {
        String result = null;

        try {
            int types = debugModel.getModelTypes();
            if (isBooleanType(types)) {
                result = Boolean.toString(debugModel.getAsBoolean());
            }
            if (isCollectionType(types)) {
                result = "Collection";
            }
            if (isConfigurationType(types)) {
                result = "Configuration";
            }
            if (isDateType(types)) {
                result = debugModel.getAsDate().toString();
            }
            if (isEnvironmentType(types)) {
                result = "Environment";
            }
            if (isHashType(types)) {
                result = "Hash";
            }
            if (isHashTypeEx(types)) {
                result = "HashEx";
            }
            if (isNumberType(types)) {
                result = debugModel.getAsNumber().toString();
            }
            if (isStringType(types)) {
                result = debugModel.getAsString();
            }
            if (isSequenceType(types)) {
                result = "Sequence";
            }
            if (isTemplateType(types)) {
                result = "Template";
            }
            if (isTransformType(types)) {
                result = "Transform";
            }
            if (isMethodType(types)) {
                result = "Method";
            }
        } catch (TemplateModelException | RemoteException e) {
            e.printStackTrace();
        }

        if (result == null) {
            result = "";
        }

        return result;
    }

    public String getTypeString() {
        String result = null;

        try {
            int types = debugModel.getModelTypes();
            if (isBooleanType(types)) {
                result = "Boolean";
            }
            if (isCollectionType(types)) {
                result = "Collection";
            }
            if (isConfigurationType(types)) {
                result = "Configuration";
            }
            if (isDateType(types)) {
                result = "Date";
            }
            if (isEnvironmentType(types)) {
                result = "Environment";
            }
            if (isHashType(types)) {
                result = "Hash";
            }
            if (isHashTypeEx(types)) {
                result = "HashEx";
            }
            if (isNumberType(types)) {
                result = "Number";
            }
            if (isStringType(types)) {
                result = "String";
            }
            if (isSequenceType(types)) {
                result = "Sequence";
            }
            if (isTemplateType(types)) {
                result = "Template";
            }
            if (isTransformType(types)) {
                result = "Transform";
            }
            if (isMethodType(types)) {
                result = "Method";
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        if (result == null) {
            result = "";
        }

        return result;
    }

    protected DebugModel getDebugModel() {
        return debugModel;
    }

    protected void addVariable(Map<String, FreemarkerAttachValue> map, String name, Class<? extends FreemarkerAttachValue> type) {
        try {
            FreemarkerAttachValue freemarkerAttachValue = FreemarkerAttachValueFactory.createFreemarkerAttachValue(getDebugModel(), name, type);

            if (freemarkerAttachValue != null) {
                map.put(name, freemarkerAttachValue);
            }
        } catch (TemplateModelException | RemoteException e) {
            e.printStackTrace();
        }
    }

    protected void addVariable(Map<String, FreemarkerAttachValue> map, DebugModel value, String name, Class<? extends FreemarkerAttachValue> type) {
        FreemarkerAttachValue freemarkerAttachValue = FreemarkerAttachValueFactory.createFreemarkerAttachValue(name, value, type);

        if (freemarkerAttachValue != null) {
            map.put(name, freemarkerAttachValue);
        }
    }


    private boolean isConfigurationType( int types ) {
        return ( DebugModel.TYPE_CONFIGURATION & types ) > 0;
    }

    private boolean isTemplateType( int types ) {
        return ( DebugModel.TYPE_TEMPLATE & types ) > 0;
    }

    private boolean isEnvironmentType( int types ) {
        return ( DebugModel.TYPE_ENVIRONMENT & types ) > 0;
    }

    private boolean isHashType( int types ) {
        return ( DebugModel.TYPE_HASH & types ) > 0 ;
    }

    private boolean isHashTypeEx( int types ) {
        return ( DebugModel.TYPE_HASH_EX & types ) > 0;
    }

    private boolean isMethodType( int types ) {
        return ( DebugModel.TYPE_METHOD & types ) > 0 || ( DebugModel.TYPE_METHOD_EX & types ) > 0;
    }

    private boolean isTransformType( int types ) {
        return ( DebugModel.TYPE_TRANSFORM & types ) > 0;
    }

    private boolean isStringType( int types ) {
        return ( DebugModel.TYPE_SCALAR & types ) > 0;
    }

    private boolean isNumberType( int types ) {
        return ( DebugModel.TYPE_NUMBER & types ) > 0;
    }

    private boolean isDateType( int types ) {
        return ( DebugModel.TYPE_DATE & types ) > 0;
    }

    private boolean isBooleanType( int types ) {
        return ( DebugModel.TYPE_BOOLEAN & types ) > 0;
    }

    private boolean isCollectionType( int types ) {
        return ( DebugModel.TYPE_COLLECTION & types ) > 0;
    }

    private boolean isSequenceType( int types ) {
        return ( DebugModel.TYPE_SEQUENCE & types ) > 0;
    }

}
