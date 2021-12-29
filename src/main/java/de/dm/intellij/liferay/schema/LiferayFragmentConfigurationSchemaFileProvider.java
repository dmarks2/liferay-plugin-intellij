package de.dm.intellij.liferay.schema;

import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.jetbrains.jsonSchema.extension.JsonSchemaFileProvider;
import com.jetbrains.jsonSchema.extension.SchemaType;
import com.jetbrains.jsonSchema.impl.JsonSchemaVersion;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;

public class LiferayFragmentConfigurationSchemaFileProvider implements JsonSchemaFileProvider {

    @Nullable
    private final VirtualFile schemaFile;

    public LiferayFragmentConfigurationSchemaFileProvider() {
        this.schemaFile = getResourceFile();
    }

    private static VirtualFile getResourceFile() {
        URL url = LiferayAssetsJsonSchemaFileProvider.class.getResource("/com/liferay/schema/configuration-json-schema.json");
        if (url != null) {
            return VfsUtil.findFileByURL(url);
        }
        return null;
    }

    @Override
    public boolean isAvailable(@NotNull VirtualFile file) {
        return file.isValid() && LiferayFileUtil.isFragmentFile(file) && "configuration.json".equals(file.getName());
    }

    @NotNull
    @Override
    public String getName() {
        return "Liferay Fragment configuration";
    }

    @Nullable
    @Override
    public VirtualFile getSchemaFile() {
        return schemaFile;
    }

    @NotNull
    @Override
    public SchemaType getSchemaType() {
        return SchemaType.schema;
    }

    @Override
    public JsonSchemaVersion getSchemaVersion() {
        return JsonSchemaVersion.SCHEMA_7;
    }
}
