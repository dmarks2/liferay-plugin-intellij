package de.dm.intellij.liferay.schema;

import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.jetbrains.jsonSchema.extension.JsonSchemaFileProvider;
import com.jetbrains.jsonSchema.extension.SchemaType;
import com.jetbrains.jsonSchema.impl.JsonSchemaVersion;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;

public class LiferayFragmentFragmentSchemaFileProvider implements JsonSchemaFileProvider {

    @Nullable
    private final VirtualFile schemaFile;

    public LiferayFragmentFragmentSchemaFileProvider() {
        this.schemaFile = getResourceFile();
    }

    private static VirtualFile getResourceFile() {
        URL url = LiferayAssetsJsonSchemaFileProvider.class.getResource("/com/liferay/schema/fragment-fragment-schema.json");
        if (url != null) {
            return VfsUtil.findFileByURL(url);
        }
        return null;
    }

    @Override
    public boolean isAvailable(@NotNull VirtualFile file) {
        return file.isValid() && "fragment.json".equals(file.getName());
    }

    @NotNull
    @Override
    public String getName() {
        return "Liferay Fragment Definition";
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
