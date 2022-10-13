package de.dm.intellij.liferay.schema;

import com.intellij.openapi.project.Project;
import com.jetbrains.jsonSchema.extension.JsonSchemaFileProvider;
import com.jetbrains.jsonSchema.extension.JsonSchemaProviderFactory;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class LiferayJsonSchemaProviderFactory implements JsonSchemaProviderFactory {

    @NotNull
    @Override
    public List<JsonSchemaFileProvider> getProviders(@NotNull Project project) {
        return Arrays.asList(
            new LiferayJournalStructureJsonSchemaFileProvider(project),
            new LiferayJournalStructureJsonSchema_2_0_FileProvider(project),
            new LiferayJournalStructureJsonSchemaDataDefinitionFileProvider(project),
            new LiferayAssetsJsonSchemaFileProvider(),
            new LiferaySitemapJsonSchemaFileProvider(),
            new LiferaySettingsJsonSchemaFileProvider(),
            new LiferayFrontendTokenDefinitionJsonSchemaFileProvider(project),
            new LiferayFragmentCollectionSchemaFileProvider(),
            new LiferayFragmentConfigurationSchemaFileProvider(),
            new LiferayFragmentFragmentSchemaFileProvider()
        );
    }
}
