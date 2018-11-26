package de.dm.intellij.liferay.language;

import com.intellij.json.psi.JsonArray;
import com.intellij.json.psi.JsonBooleanLiteral;
import com.intellij.json.psi.JsonFile;
import com.intellij.json.psi.JsonObject;
import com.intellij.json.psi.JsonProperty;
import com.intellij.json.psi.JsonValue;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TemplateVariableJsonParser implements TemplateVariableParser<JsonFile> {

    @Override
    public List<TemplateVariable> getTemplateVariables(JsonFile jsonFile, PsiFile templateFile) {
        List<TemplateVariable> result = new ArrayList<>();

        JsonValue root = jsonFile.getTopLevelValue();

        for (PsiElement value : root.getChildren()) {
            if (value instanceof JsonProperty) {
                JsonProperty property = (JsonProperty) value;
                if ("fields".equals(property.getName())) {
                    JsonArray jsonArray = (JsonArray) property.getValue();
                    if (jsonArray != null) {
                        for (JsonValue jsonValue : jsonArray.getValueList()) {
                            if (jsonValue instanceof JsonObject) {
                                JsonObject jsonObject = (JsonObject) jsonValue;

                                TemplateVariable templateVariable = getTemplateVariable(templateFile, jsonObject);
                                if (templateVariable != null) {
                                    result.add(templateVariable);
                                }
                            }
                        }
                    }
                }
            }
        }

        return result;
    }

    @Nullable
    private TemplateVariable getTemplateVariable(PsiFile templateFile, JsonObject jsonObject) {
        JsonProperty nameProperty = jsonObject.findProperty("name");
        if (nameProperty != null) {
            String name = nameProperty.getValue().getText();
            if ((name != null) && (name.trim().length() > 0)) {
                name = StringUtil.unquoteString(name);

                TemplateVariable templateVariable = new TemplateVariable();
                templateVariable.setName(name);

                boolean repeatable = false;
                if ( (jsonObject.findProperty("repeatable") != null) && (jsonObject.findProperty("repeatable").getValue() instanceof JsonBooleanLiteral) ) {
                    repeatable = ((JsonBooleanLiteral) jsonObject.findProperty("repeatable").getValue()).getValue();
                }

                templateVariable.setRepeatable(repeatable);
                templateVariable.setNavigationalElement(nameProperty.getValue());
                templateVariable.setParentFile(templateFile);

                JsonProperty subproperty = jsonObject.findProperty("nestedFields");
                if ( (subproperty != null) ) {
                    JsonArray subjsonArray = (JsonArray) subproperty.getValue();
                    if (subjsonArray != null) {
                        for (JsonValue subjsonValue : subjsonArray.getValueList()) {
                            if (subjsonValue instanceof JsonObject) {
                                JsonObject subjsonObject = (JsonObject) subjsonValue;

                                TemplateVariable nestedVariable = getTemplateVariable(templateFile, subjsonObject);
                                if (nestedVariable != null) {
                                    templateVariable.addNestedVariable(nestedVariable);
                                }
                            }
                        }
                    }
                }

                return templateVariable;
            }
        }

        return null;
    }
}
