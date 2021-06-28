package de.dm.intellij.liferay.schema;

import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.jetbrains.jsonSchema.extension.JsonSchemaFileProvider;
import com.jetbrains.jsonSchema.extension.SchemaType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;

public class LiferayFrontendTokenDefinitionJsonSchemaFileProvider implements JsonSchemaFileProvider {

    private static final String FRONTEND_TOKEN_DEFINITION_JSON = "frontend-token-definition.json";

    @NotNull
    private final Project project;

    @Nullable
    private final VirtualFile schemaFile;

    public LiferayFrontendTokenDefinitionJsonSchemaFileProvider(@NotNull Project project) {
        this.project = project;
        this.schemaFile = getResourceFile();
    }

    private static VirtualFile getResourceFile() {
        URL url = LiferayJournalStructureJsonSchemaFileProvider.class.getResource("/com/liferay/schema/frontend-token-definition.schema.json");
        if (url != null) {
            return VfsUtil.findFileByURL(url);
        }
        return null;
    }

    @Override
    public boolean isAvailable(@NotNull VirtualFile file) {
        return ReadAction.compute(
                () -> {
                    PsiManager psiManager = PsiManager.getInstance(project);
                    PsiFile psiFile = psiManager.findFile(file);
                    if (psiFile != null) {
                        return FRONTEND_TOKEN_DEFINITION_JSON.equals(psiFile.getName());
                    }
                    return false;
                }
        );

    }

    @NotNull
    @Override
    public String getName() {
        return "Liferay Frontend Token Definition";
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
