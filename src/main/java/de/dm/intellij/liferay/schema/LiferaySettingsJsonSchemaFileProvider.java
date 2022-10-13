package de.dm.intellij.liferay.schema;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.jetbrains.jsonSchema.extension.JsonSchemaFileProvider;
import com.jetbrains.jsonSchema.extension.SchemaType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;

public class LiferaySettingsJsonSchemaFileProvider implements JsonSchemaFileProvider {

    @Nullable private final VirtualFile schemaFile;

    public LiferaySettingsJsonSchemaFileProvider() {
        this.schemaFile = getResourceFile();
    }

    private static VirtualFile getResourceFile() {
        URL url = LiferaySettingsJsonSchemaFileProvider.class.getResource("/com/liferay/schema/settings-schema.json");
        if (url != null) {
            return VfsUtil.findFileByURL(url);
        }
        return null;
    }

    @Override
    public boolean isAvailable(@NotNull VirtualFile file) {
        return file.isValid() && "settings.json".equals(file.getName());
    }

    @NotNull
    @Override
    public String getName() {
        return "Liferay Settings";
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
