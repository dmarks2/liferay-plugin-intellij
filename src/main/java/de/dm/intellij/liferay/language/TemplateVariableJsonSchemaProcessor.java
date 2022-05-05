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

import java.util.ArrayList;
import java.util.List;

public interface TemplateVariableJsonSchemaProcessor {

    boolean isSuitable(JsonFile jsonFile);

    String getFieldsDefinitionPropertyName();

    JsonProperty getFieldReferenceProperty(JsonObject jsonObject);

    JsonProperty getTypeReferenceProperty(JsonObject jsonObject);

    JsonProperty getNestedFieldsReferenceProperty(JsonObject jsonObject);

    default String getDefaultLanguageId(JsonFile jsonFile) {
        JsonValue root = jsonFile.getTopLevelValue();

        String defaultLanguageId = null;

        for (PsiElement value : root.getChildren()) {
            if (value instanceof JsonProperty) {
                JsonProperty property = (JsonProperty) value;
                if ("defaultLanguageId".equals(property.getName())) {
                    JsonValue defaultLangaugeIdValue = property.getValue();
                    if (defaultLangaugeIdValue != null) {
                        defaultLanguageId = defaultLangaugeIdValue.getText();

                        if (defaultLanguageId != null) {
                            defaultLanguageId = StringUtil.unquoteString(defaultLanguageId);
                        }
                    }
                }
            }
        }


        return defaultLanguageId;
    }

    default List<TemplateVariable> getTemplateVariables(JsonFile jsonFile, PsiFile templateFile) {
        List<TemplateVariable> result = new ArrayList<>();

        String defaultLanguageId = getDefaultLanguageId(jsonFile);

        JsonValue root = jsonFile.getTopLevelValue();

        for (PsiElement value : root.getChildren()) {
            if (value instanceof JsonProperty) {
                JsonProperty property = (JsonProperty) value;
                if (getFieldsDefinitionPropertyName().equals(property.getName())) {
                    JsonArray jsonArray = (JsonArray) property.getValue();
                    if (jsonArray != null) {
                        for (JsonValue jsonValue : jsonArray.getValueList()) {
                            if (jsonValue instanceof JsonObject) {
                                JsonObject jsonObject = (JsonObject) jsonValue;

                                TemplateVariable templateVariable = getTemplateVariable(templateFile, jsonFile, jsonObject, defaultLanguageId);
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

    default TemplateVariable getTemplateVariable(PsiFile templateFile, PsiFile originalFile, JsonObject jsonObject, String defaultLanguageId) {
        JsonProperty fieldReferenceProperty = getFieldReferenceProperty(jsonObject);

        if (fieldReferenceProperty != null) {
            String name = fieldReferenceProperty.getValue().getText();
            if ((name != null) && (name.trim().length() > 0)) {
                name = StringUtil.unquoteString(name);

                TemplateVariable templateVariable = new TemplateVariable();
                templateVariable.setName(name);

                boolean repeatable = false;
                if ( (jsonObject.findProperty("repeatable") != null) && (jsonObject.findProperty("repeatable").getValue() instanceof JsonBooleanLiteral) ) {
                    repeatable = ((JsonBooleanLiteral) jsonObject.findProperty("repeatable").getValue()).getValue();
                }

                JsonProperty typeJsonProperty = getTypeReferenceProperty(jsonObject);

                if (typeJsonProperty != null) {
                    JsonValue typeJsonPropertyValue = typeJsonProperty.getValue();
                    if (typeJsonPropertyValue != null) {
                        String type = typeJsonPropertyValue.getText();
                        if ( (type != null) && (type.length() > 0) ) {
                            type = StringUtil.unquoteString(type);

                            templateVariable.setType(type);
                        }
                    }
                }

                JsonProperty labelJsonProperty = jsonObject.findProperty("label");
                if (labelJsonProperty != null) {
                    JsonValue value = labelJsonProperty.getValue();
                    if (value instanceof JsonObject) {
                        JsonObject labelJsonObject = (JsonObject) value;

                        for (PsiElement psiElement : labelJsonObject.getChildren()) {
                            if (psiElement instanceof JsonProperty) {
                                JsonProperty jsonProperty = (JsonProperty) psiElement;
                                String locale = jsonProperty.getName();
                                locale = StringUtil.unquoteString(locale);

                                JsonValue jsonValue = jsonProperty.getValue();
                                if (jsonValue != null) {
                                    String text = jsonValue.getText();
                                    if ((text != null) && (text.trim().length() > 0)) {
                                        text = StringUtil.unquoteString(text);

                                        templateVariable.getLabels().put(locale, text);
                                    }
                                }
                            }
                        }
                    }
                }

                JsonProperty tipJsonProperty = jsonObject.findProperty("tip");
                if (tipJsonProperty != null) {
                    JsonValue value = tipJsonProperty.getValue();
                    if (value instanceof JsonObject) {
                        JsonObject tipJsonObject = (JsonObject) value;

                        for (PsiElement psiElement : tipJsonObject.getChildren()) {
                            if (psiElement instanceof JsonProperty) {
                                JsonProperty jsonProperty = (JsonProperty) psiElement;
                                String locale = jsonProperty.getName();
                                locale = StringUtil.unquoteString(locale);

                                JsonValue jsonValue = jsonProperty.getValue();
                                if (jsonValue != null) {
                                    String text = jsonValue.getText();
                                    if ((text != null) && (text.trim().length() > 0)) {
                                        text = StringUtil.unquoteString(text);

                                        templateVariable.getTips().put(locale, text);
                                    }
                                }
                            }
                        }
                    }
                }

                templateVariable.setRepeatable(repeatable);
                templateVariable.setNavigationalElement(fieldReferenceProperty.getValue());
                templateVariable.setParentFile(templateFile);
                templateVariable.setOriginalFile(originalFile);
                templateVariable.setDefaultLanguageId(defaultLanguageId);

                JsonProperty subproperty = getNestedFieldsReferenceProperty(jsonObject);

                if ( (subproperty != null) ) {
                    JsonArray subjsonArray = (JsonArray) subproperty.getValue();
                    if (subjsonArray != null) {
                        for (JsonValue subjsonValue : subjsonArray.getValueList()) {
                            if (subjsonValue instanceof JsonObject) {
                                JsonObject subjsonObject = (JsonObject) subjsonValue;

                                TemplateVariable nestedVariable = getTemplateVariable(templateFile, originalFile, subjsonObject, defaultLanguageId);
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
