package de.dm.intellij.liferay.schema;

import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.jetbrains.jsonSchema.extension.JsonSchemaFileProvider;
import com.jetbrains.jsonSchema.extension.SchemaType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;

public class LiferayBatchEngineDataSchemaFileProvider implements JsonSchemaFileProvider {

	@Nullable
	private final VirtualFile schemaFile;

	public LiferayBatchEngineDataSchemaFileProvider() {
		this.schemaFile = getResourceFile();
	}

	private static VirtualFile getResourceFile() {
		URL url = LiferayAssetsJsonSchemaFileProvider.class.getResource("/com/liferay/schema/batch-engine-data.json");
		if (url != null) {
			return VfsUtil.findFileByURL(url);
		}
		return null;
	}

	@Override
	public boolean isAvailable(@NotNull VirtualFile file) {
		return file.isValid() && StringUtil.endsWith(file.getName(), ".batch-engine-data.json");
	}

	@NotNull
	@Override
	public String getName() {
		return "Liferay Batch Engine Data";
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
