package de.dm.intellij.liferay.resourcesimporter;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.ModuleListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.indexing.FileBasedIndex;
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
            Application application = ApplicationManager.getApplication();

            application.executeOnPooledThread(
                    () -> {
                        application.runReadAction(
                                () -> {
                                    Collection<VirtualFile> javaFiles = FileTypeIndex.getFiles(
                                            JavaFileType.INSTANCE,
                                            GlobalSearchScope.moduleScope(module)
                                    );

                                    for (VirtualFile javaFile : javaFiles) {
                                        if (LiferayAdvancedResourcesImporterServiceCallWatcher.isRelevantFile(javaFile.getPath())) {
                                            LiferayAdvancedResourcesImporterServiceCallWatcher.handleChange(project, module, javaFile);
                                        }
                                    }
                                }
                        );
                    }
            );
        }
    }
}
