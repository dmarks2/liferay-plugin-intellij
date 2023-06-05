package de.dm.intellij.liferay.schema;

import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.jetbrains.jsonSchema.extension.JsonSchemaFileProvider;
import com.jetbrains.jsonSchema.extension.SchemaType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;

public class LiferayRestConfigSchemaProvider implements JsonSchemaFileProvider {

	//see https://github.com/liferay/liferay-portal/blob/master/modules/util/portal-tools-rest-builder/src/main/java/com/liferay/portal/tools/rest/builder/internal/yaml/config/ConfigYAML.java
	//see https://github.com/javierdearcos/rest-builder-docs

	@Nullable
	private final VirtualFile schemaFile;

	public LiferayRestConfigSchemaProvider() {
		this.schemaFile = getResourceFile();
	}

	private static VirtualFile getResourceFile() {
		URL url = LiferaySettingsJsonSchemaFileProvider.class.getResource("/com/liferay/schema/rest-config-schema.json");
		if (url != null) {
			return VfsUtil.findFileByURL(url);
		}
		return null;
	}

	@Override
	public boolean isAvailable(@NotNull VirtualFile file) {
		return file.isValid() && "rest-config.yaml".equals(file.getName());
	}

	@NotNull
	@Override
	public String getName() {
		return "Liferay REST Config";
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
