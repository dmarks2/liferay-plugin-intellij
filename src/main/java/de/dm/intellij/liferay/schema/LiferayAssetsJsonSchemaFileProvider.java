package de.dm.intellij.liferay.schema;

import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.jetbrains.jsonSchema.extension.JsonSchemaFileProvider;
import com.jetbrains.jsonSchema.extension.SchemaType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;

public class LiferayAssetsJsonSchemaFileProvider implements JsonSchemaFileProvider {

    @Nullable private final VirtualFile schemaFile;

    public LiferayAssetsJsonSchemaFileProvider() {
        this.schemaFile = getResourceFile();
    }

    private static VirtualFile getResourceFile() {
        URL url = LiferayAssetsJsonSchemaFileProvider.class.getResource("/com/liferay/schema/assets-schema.json");
        if (url != null) {
            return VfsUtil.findFileByURL(url);
        }
        return null;
    }

    @Override
    public boolean isAvailable(@NotNull VirtualFile file) {
        return file.isValid() && "assets.json".equals(file.getName());
    }

    @NotNull
    @Override
    public String getName() {
        return "Liferay Assets";
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
}
