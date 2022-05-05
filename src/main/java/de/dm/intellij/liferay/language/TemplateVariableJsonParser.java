package de.dm.intellij.liferay.language;

import com.intellij.json.psi.JsonFile;
import com.intellij.psi.PsiFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TemplateVariableJsonParser implements TemplateVariableParser<JsonFile> {

    private static final List<TemplateVariableJsonSchemaProcessor> templateVariableJsonSchemaProcessors = Arrays.asList(
            new TemplateVariableJsonSchemaDefaultProcessor(),
            new TemplateVariableJsonSchema_2_0_Processor(),
            new TemplateVariableJsonSchemaDataDefinitionProcessor()
    );

    @Override
    public List<TemplateVariable> getTemplateVariables(JsonFile jsonFile, PsiFile templateFile) {
        for (TemplateVariableJsonSchemaProcessor templateVariableJsonSchemaProcessor : templateVariableJsonSchemaProcessors) {
            if (templateVariableJsonSchemaProcessor.isSuitable(jsonFile)) {
                return templateVariableJsonSchemaProcessor.getTemplateVariables(jsonFile, templateFile);
            }
        }

        return new ArrayList<>();
    }
}
