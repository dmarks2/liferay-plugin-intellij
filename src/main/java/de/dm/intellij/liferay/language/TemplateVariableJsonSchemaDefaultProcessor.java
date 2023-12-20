package de.dm.intellij.liferay.language;

import com.intellij.json.psi.JsonFile;
import com.intellij.json.psi.JsonObject;
import com.intellij.json.psi.JsonProperty;
import de.dm.intellij.liferay.util.LiferayFileUtil;

public class TemplateVariableJsonSchemaDefaultProcessor implements TemplateVariableJsonSchemaProcessor {

    @Override
    public boolean isSuitable(JsonFile jsonFile) {
        String definitionSchemaVersion = LiferayFileUtil.getJournalStructureJsonFileDefinitionSchemaVersion(jsonFile);

        if ("2.0".equals(definitionSchemaVersion)) {
            return false;
        }

        return !LiferayFileUtil.isJournalStructureDataDefinitionSchema(jsonFile);
    }

    public String getFieldsDefinitionPropertyName() {
        return "fields";
    }

    public JsonProperty getFieldReferenceProperty(JsonObject jsonObject) {
        return jsonObject.findProperty("name");
    }

    @Override
    public JsonProperty getTypeReferenceProperty(JsonObject jsonObject) {
        return jsonObject.findProperty("type");
    }

    @Override
    public JsonProperty getNestedFieldsReferenceProperty(JsonObject jsonObject) {
        return jsonObject.findProperty("nestedFields");
    }
}
