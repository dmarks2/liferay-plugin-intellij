package de.dm.intellij.liferay.module;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.ModuleListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import de.dm.intellij.liferay.language.jsp.LiferayJspWebContentRootListener;
import de.dm.intellij.liferay.util.ProjectUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class LiferayJspModuleListener implements ModuleListener {

    @Override
    public void moduleAdded(@NotNull Project project, @NotNull Module module) {
        ProjectUtils.runDumbAware(project, () -> {
            handleModuleFiles(project, module, "resources");
        });
    }

    private void handleModuleFiles(@NotNull Project project, @NotNull Module module, String filenamePattern) {
        if (!module.isDisposed()) {
            Collection<VirtualFile> virtualFilesByName = FilenameIndex.getVirtualFilesByName(filenamePattern, GlobalSearchScope.moduleScope(module));
            for (VirtualFile virtualFile : virtualFilesByName) {
                if (LiferayJspWebContentRootListener.isRelevantFile(virtualFile.getPath())) {
                    LiferayJspWebContentRootListener.handleChange(project, virtualFile);
                }
            }
        }
    }
}
