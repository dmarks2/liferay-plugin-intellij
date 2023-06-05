package de.dm.intellij.liferay.language.clientextension;

import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.jetbrains.jsonSchema.extension.JsonSchemaFileProvider;
import com.jetbrains.jsonSchema.extension.SchemaType;
import de.dm.intellij.liferay.schema.LiferayAssetsJsonSchemaFileProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.util.Objects;

public class ClientExtensionYamlSchemaFileProvider implements JsonSchemaFileProvider {

	@Nullable
	private final VirtualFile schemaFile;

	public ClientExtensionYamlSchemaFileProvider() {
		this.schemaFile = getResourceFile();
	}

	private static VirtualFile getResourceFile() {
		URL url = LiferayAssetsJsonSchemaFileProvider.class.getResource("/com/liferay/schema/client-extension-schema.json");
		if (url != null) {
			return VfsUtil.findFileByURL(url);
		}
		return null;
	}

	@Override
	public boolean isAvailable(@NotNull VirtualFile file) {
		return file.isValid() &&
				file.getNameWithoutExtension().startsWith("client-extension") &&
				(Objects.equals(file.getExtension(), "yaml") || Objects.equals(file.getExtension(), "yml"));
	}

	@NotNull
	@Override
	public String getName() {
		return "Liferay Client Extension";
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
