package de.dm.intellij.liferay.gradle;

import com.intellij.openapi.externalSystem.model.DataNode;
import com.intellij.openapi.externalSystem.model.project.ModuleData;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.ModuleRootModificationUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import org.gradle.tooling.model.idea.IdeaContentRoot;
import org.gradle.tooling.model.idea.IdeaModule;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.gradle.service.project.AbstractProjectResolverExtension;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class LiferayBundleSupportGradleProjectResolver extends AbstractProjectResolverExtension {

    @NotNull
    @Override
    public Set<Class<?>> getExtraProjectModelClasses() {
        return Collections.singleton(LiferayBundleSupportGradleTaskModel.class);
    }

    @NotNull
    @Override
    public Set<Class<?>> getToolingExtensionsClasses() {
        return Collections.singleton(LiferayBundleSupportGradleTaskModelBuilder.class);
    }

    @Override
    public void populateModuleExtraModels(@NotNull IdeaModule gradleModule, @NotNull DataNode<ModuleData> ideModule) {
        LiferayBundleSupportGradleTaskModel liferayBundleSupportGradleTaskModel = resolverCtx.getExtraProject(gradleModule, LiferayBundleSupportGradleTaskModel.class);
        if (liferayBundleSupportGradleTaskModel != null) {
            // try to find the corresponding IDEA module for the Gradle Module
            IdeaContentRoot contentRoot = gradleModule.getContentRoots().getAt(0);
            File rootDirectory = contentRoot.getRootDirectory();
            VirtualFile fileByIoFile = VfsUtil.findFileByIoFile(rootDirectory, false);
            if (fileByIoFile != null) {
                Project project = ProjectUtil.guessProjectForFile(fileByIoFile);
                if (project != null) {
                    Module module = ModuleUtil.findModuleForFile(fileByIoFile, project);

                    if (module != null) {
                        String liferayHome = liferayBundleSupportGradleTaskModel.getLiferayHome();
                        if (liferayHome != null) {
                            VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByPath(liferayHome);
                            if (virtualFile != null) {
                                String url = virtualFile.getUrl();

                                Collection<String> excludeFolders = new ArrayList<>();
                                excludeFolders.add(url);

                                ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(module);

                                for (VirtualFile sourceRoot : moduleRootManager.getContentRoots()) {
                                    ModuleRootModificationUtil.updateExcludedFolders(
                                            module,
                                            sourceRoot,
                                            Collections.emptyList(),
                                            excludeFolders
                                    );
                                }
                            }
                        }
                    }
                }

            }
        }
        super.populateModuleExtraModels(gradleModule, ideModule);
    }
}
