package de.dm.intellij.liferay.language.sass;

import com.intellij.json.psi.JsonProperty;
import com.intellij.json.psi.JsonValue;
import com.intellij.json.psi.impl.JsonRecursiveElementVisitor;
import com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class LiferayFrontendTokenDefinitionJsonElementVisitor extends JsonRecursiveElementVisitor {

    private final Map<String, JsonProperty> cssVariableMap = new HashMap<>();

    private boolean insideMapping;
    private String typeMapping;
    private String valueMapping;
    private JsonProperty valueReference;

    public Map<String, JsonProperty> getCssVariableMap() {
        return cssVariableMap;
    }

    public void visitProperty(@NotNull JsonProperty property) {
        if ("mappings".equals(property.getName())) {
            insideMapping = true;
        } else if ("type".equals(property.getName())) {
            if (insideMapping) {
                JsonValue value = property.getValue();

                if (value != null) {
                    typeMapping = value.getText();

                    checkTypeValueMapping();
                }
            }
        } else if ("value".equals(property.getName())) {
            if (insideMapping) {
                JsonValue value = property.getValue();

                if (value != null) {
                    valueMapping = property.getValue().getText();
                    valueReference = property;

                    checkTypeValueMapping();
                }
            }
        } else {
            insideMapping = false;
            typeMapping = null;
            valueMapping = null;
            valueReference = null;
        }

        super.visitProperty(property);
    }

    private void checkTypeValueMapping() {
        if (insideMapping) {
            if (typeMapping != null && valueMapping != null) {
                if ("cssVariable".equals(StringUtil.unquoteString(typeMapping))) {
                    if (valueReference != null) {
                        cssVariableMap.put(StringUtil.unquoteString(valueMapping), valueReference);
                    }
                }
            }
        }
    }
}
