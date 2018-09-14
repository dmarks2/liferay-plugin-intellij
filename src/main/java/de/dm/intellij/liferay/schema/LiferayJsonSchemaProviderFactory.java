package de.dm.intellij.liferay.schema;

import com.intellij.openapi.project.Project;
import com.jetbrains.jsonSchema.extension.JsonSchemaFileProvider;
import com.jetbrains.jsonSchema.extension.JsonSchemaProviderFactory;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class LiferayJsonSchemaProviderFactory implements JsonSchemaProviderFactory {

    @NotNull
    @Override
    public List<JsonSchemaFileProvider> getProviders(@NotNull Project project) {
        return Collections.singletonList(new LiferayJournalStructureJsonSchemaFileProvider(project));
    }
}
