package de.dm.intellij.liferay.resourcesimporter;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.ModuleListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import de.dm.intellij.liferay.util.ProjectUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public class LiferayAdvancedResourcesImporterServiceCallModuleListener implements ModuleListener {

    @Override
    public void modulesAdded(@NotNull Project project, @NotNull List<? extends Module> modules) {
        for (Module module : modules) {
            ProjectUtils.runDumbAware(project, () -> handleModuleFiles(project, module));
        }
    }

    private void handleModuleFiles(@NotNull Project project, @NotNull Module module) {
        if (!module.isDisposed()) {
            Collection<VirtualFile> virtualFilesByName = FilenameIndex.getAllFilesByExt(project, "java", GlobalSearchScope.moduleScope(module));
            for (VirtualFile virtualFile : virtualFilesByName) {
                if (LiferayAdvancedResourcesImporterServiceCallWatcher.isRelevantFile(virtualFile.getPath())) {
                    LiferayAdvancedResourcesImporterServiceCallWatcher.handleChange(project, virtualFile);
                }
            }
        }
    }
}
