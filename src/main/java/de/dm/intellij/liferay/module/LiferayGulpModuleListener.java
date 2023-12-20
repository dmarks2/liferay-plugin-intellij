package de.dm.intellij.liferay.module;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.ModuleListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import de.dm.intellij.liferay.language.gulp.LiferayGulpfileParser;
import de.dm.intellij.liferay.util.ProjectUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public class LiferayGulpModuleListener implements ModuleListener {

    @Override
    public void modulesAdded(@NotNull Project project, @NotNull List<? extends Module> modules) {
        for (Module module : modules) {
            ProjectUtils.runDumbAware(project, () -> handleModuleFiles(project, module, "gulpfile.js"));
        }
    }

    private void handleModuleFiles(@NotNull Project project, @NotNull Module module, String filenamePattern) {
        if (!module.isDisposed()) {
            Collection<VirtualFile> virtualFilesByName = FilenameIndex.getVirtualFilesByName(filenamePattern, GlobalSearchScope.moduleScope(module));
            for (VirtualFile virtualFile : virtualFilesByName) {
                if (LiferayGulpfileParser.isRelevantFile(virtualFile.getPath())) {
                    LiferayGulpfileParser.handleChange(project, virtualFile);
                }
            }
        }
    }
}
