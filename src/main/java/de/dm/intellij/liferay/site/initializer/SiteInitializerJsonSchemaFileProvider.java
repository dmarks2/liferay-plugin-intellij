package de.dm.intellij.liferay.site.initializer;

import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.jetbrains.jsonSchema.extension.JsonSchemaFileProvider;
import com.jetbrains.jsonSchema.extension.SchemaType;
import com.liferay.poshi.core.util.StringUtil;
import de.dm.intellij.liferay.schema.LiferayAssetsJsonSchemaFileProvider;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.util.function.Predicate;

public class SiteInitializerJsonSchemaFileProvider implements JsonSchemaFileProvider  {

	private final String name;
	private Predicate<VirtualFile> isAvailable;

	@Nullable private final VirtualFile schemaFile;

	public SiteInitializerJsonSchemaFileProvider(String name) {
		this.name = name;

		this.isAvailable = (virtualFile) -> SiteInitializerUtil.isSiteInitializerFile(virtualFile, name);

		this.schemaFile = getResourceFile(name);
	}

	public SiteInitializerJsonSchemaFileProvider(String name, Predicate<VirtualFile> isAvailable) {
		this(name);

		this.isAvailable = isAvailable;
	}

	@Override
	public boolean isAvailable(@NotNull VirtualFile file) {
		return isAvailable.test(file);
	}

	@Override
	public @NotNull @Nls String getName() {
		return "Site Initializer " + StringUtil.capitalize(name);
	}

	@Override
	public @Nullable VirtualFile getSchemaFile() {
		return schemaFile;
	}

	@Override
	public @NotNull SchemaType getSchemaType() {
		return SchemaType.schema;
	}

	private static VirtualFile getResourceFile(String name) {
		URL url = LiferayAssetsJsonSchemaFileProvider.class.getResource("/com/liferay/schema/site/initializer/" + name + "-schema.json");
		if (url != null) {
			return VfsUtil.findFileByURL(url);
		}
		return null;
	}
}
