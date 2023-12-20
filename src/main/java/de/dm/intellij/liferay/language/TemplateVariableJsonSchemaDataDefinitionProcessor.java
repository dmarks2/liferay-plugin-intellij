package de.dm.intellij.liferay.language;

import com.intellij.json.psi.JsonFile;
import com.intellij.json.psi.JsonObject;
import com.intellij.json.psi.JsonProperty;
import com.intellij.json.psi.JsonValue;
import de.dm.intellij.liferay.util.LiferayFileUtil;

public class TemplateVariableJsonSchemaDataDefinitionProcessor implements TemplateVariableJsonSchemaProcessor {

    @Override
    public boolean isSuitable(JsonFile jsonFile) {
        String definitionSchemaVersion = LiferayFileUtil.getJournalStructureJsonFileDefinitionSchemaVersion(jsonFile);

        if ("2.0".equals(definitionSchemaVersion)) {
            return false;
        }

        return LiferayFileUtil.isJournalStructureDataDefinitionSchema(jsonFile);
    }

    @Override
    public String getFieldsDefinitionPropertyName() {
        return "dataDefinitionFields";
    }

    @Override
    public JsonProperty getFieldReferenceProperty(JsonObject jsonObject) {
        JsonProperty jsonProperty =  jsonObject.findProperty("customProperties");

        if (jsonProperty != null) {
            JsonValue customProperties = jsonProperty.getValue();

            if (customProperties instanceof JsonObject customPropertiesObj) {
				return customPropertiesObj.findProperty("fieldReference");
            }
        }

        return null;
    }

    @Override
    public JsonProperty getTypeReferenceProperty(JsonObject jsonObject) {
        return jsonObject.findProperty("fieldType");
    }

    @Override
    public JsonProperty getNestedFieldsReferenceProperty(JsonObject jsonObject) {
        return jsonObject.findProperty("nestedDataDefinitionFields");
    }
}
