package de.dm.intellij.liferay.schema;

import com.intellij.openapi.project.Project;
import com.jetbrains.jsonSchema.extension.JsonSchemaFileProvider;
import com.jetbrains.jsonSchema.extension.JsonSchemaProviderFactory;
import de.dm.intellij.liferay.language.clientextension.ClientExtensionYamlSchemaFileProviderFactory;
import de.dm.intellij.liferay.site.initializer.SiteInitializerSchemaProviderFactory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LiferayJsonSchemaProviderFactory implements JsonSchemaProviderFactory {

    @NotNull
    @Override
    public List<JsonSchemaFileProvider> getProviders(@NotNull Project project) {

		List<JsonSchemaFileProvider> providers = new ArrayList<>(Arrays.asList(
				new LiferayJournalStructureJsonSchemaFileProvider(project),
				new LiferayJournalStructureJsonSchema_2_0_FileProvider(project),
				new LiferayJournalStructureJsonSchemaDataDefinitionFileProvider(project),
				new LiferayAssetsJsonSchemaFileProvider(),
				new LiferaySitemapJsonSchemaFileProvider(),
				new LiferaySettingsJsonSchemaFileProvider(),
				new LiferayFrontendTokenDefinitionJsonSchemaFileProvider(project),
				new LiferayFragmentCollectionSchemaFileProvider(),
				new LiferayFragmentConfigurationSchemaFileProvider(),
				new LiferayFragmentFragmentSchemaFileProvider(),
				new LiferayRestConfigSchemaProvider(),
				new LiferayNpmBundlerRcJsonSchemaFileProvider()
		));

		providers.addAll(ClientExtensionYamlSchemaFileProviderFactory.getClientExtensionYamlSchemaFileProviders(project));

        providers.addAll(SiteInitializerSchemaProviderFactory.getSiteInitializerProviders());

        return providers;
    }
}
