package de.dm.intellij.liferay.language.clientextension;

import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.jetbrains.jsonSchema.extension.JsonSchemaFileProvider;
import com.jetbrains.jsonSchema.extension.SchemaType;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.util.function.Predicate;

public class GenericClientExtensionYamlSchemaFileProvider implements JsonSchemaFileProvider {

	private final String name;
	private final Project project;
	private final String schemaFile;
	private final Predicate<String> clientExtensionType;

	public GenericClientExtensionYamlSchemaFileProvider(String name, Project project, String schemaFile, Predicate<String> clientExtensionType) {
		this.name = name;
		this.project = project;
		this.schemaFile = schemaFile;
		this.clientExtensionType = clientExtensionType;
	}

	@Override
	public boolean isAvailable(@NotNull VirtualFile file) {
		return ReadAction.compute(
				() -> {
					PsiManager psiManager = PsiManager.getInstance(project);
					PsiFile psiFile = psiManager.findFile(file);

					if (psiFile != null) {
						if (ClientExtensionUtil.isClientExtensionFile(file)) {
							return clientExtensionType.test(ClientExtensionUtil.getClientExtensionType(psiFile));
						}
					}
					return false;
				}
		);
	}

	@Override
	public @NotNull @Nls String getName() {
		return name;
	}

	@Override
	public @Nullable VirtualFile getSchemaFile() {
		URL url = GenericClientExtensionYamlSchemaFileProvider.class.getResource("/com/liferay/schema/" + schemaFile);

		if (url != null) {
			return VfsUtil.findFileByURL(url);
		}

		return null;
	}

	@NotNull
	@Override
	public SchemaType getSchemaType() {
		return SchemaType.schema;
	}
}
