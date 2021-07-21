package de.dm.intellij.liferay.schema;

import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.jetbrains.jsonSchema.extension.JsonSchemaFileProvider;
import com.jetbrains.jsonSchema.extension.SchemaType;
import com.jetbrains.jsonSchema.impl.JsonSchemaVersion;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;

public class LiferayJournalStructureJsonSchema_2_0_FileProvider implements JsonSchemaFileProvider {

    @NotNull private final Project project;
    @Nullable private final VirtualFile schemaFile;

    public LiferayJournalStructureJsonSchema_2_0_FileProvider(@NotNull Project project) {
        this.project = project;
        this.schemaFile = getResourceFile();
    }

    private static VirtualFile getResourceFile() {
        URL url = LiferayJournalStructureJsonSchema_2_0_FileProvider.class.getResource("/com/liferay/schema/journal-structure-schema-2-0.json");
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
                    if (LiferayFileUtil.isJournalStructureFile(psiFile)) {
                        String definitionSchemaVersion = LiferayFileUtil.getJournalStructureJsonFileDefinitionSchemaVersion(psiFile);

                        return ("2.0".equals(definitionSchemaVersion));
                    }
                }
                return false;
            }
        );

    }

    @NotNull
    @Override
    public String getName() {
        return "Liferay Journal Structure Schema 2.0";
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
