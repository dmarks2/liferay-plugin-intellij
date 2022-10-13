package de.dm.intellij.liferay.gradle;

import com.intellij.openapi.externalSystem.model.DataNode;
import com.intellij.openapi.externalSystem.model.project.ModuleData;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
import org.gradle.tooling.model.idea.IdeaContentRoot;
import org.gradle.tooling.model.idea.IdeaModule;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.gradle.service.project.AbstractProjectResolverExtension;

import java.io.File;
import java.util.Collections;
import java.util.Set;

/**
 * Extract parent Theme information from a gradle based project (based on the com.liferay.gradle.plugins.theme.builder configuration)
 */
public class LiferayThemeBuilderGradleProjectResolver extends AbstractProjectResolverExtension {

    @NotNull
    @Override
    public Set<Class<?>> getExtraProjectModelClasses() {
        return Collections.singleton(LiferayThemeBuilderGradleTaskModel.class);
    }

    @NotNull
    @Override
    public Set<Class<?>> getToolingExtensionsClasses() {
        return Collections.singleton(LiferayThemeBuilderGradleTaskModelBuilder.class);
    }

    @Override
    public void populateModuleExtraModels(@NotNull IdeaModule gradleModule, @NotNull DataNode<ModuleData> ideModule) {
        LiferayThemeBuilderGradleTaskModel liferayThemeBuilderGradleTaskModel = resolverCtx.getExtraProject(gradleModule, LiferayThemeBuilderGradleTaskModel.class);
        if ( (liferayThemeBuilderGradleTaskModel != null) && (liferayThemeBuilderGradleTaskModel.isEnabled()) ) {
            // try to find the corresponding IDEA module for the Gradle Module
            IdeaContentRoot contentRoot = gradleModule.getContentRoots().getAt(0);
            File rootDirectory = contentRoot.getRootDirectory();
            VirtualFile fileByIoFile = VfsUtil.findFileByIoFile(rootDirectory, false);
            Project project = ProjectUtil.guessProjectForFile(fileByIoFile);
            Module module = ModuleUtil.findModuleForFile(fileByIoFile, project);

            //Save the parent theme information in the Liferay Module Component
            LiferayModuleComponent liferayModuleComponent = module.getService(LiferayModuleComponent.class);
            if (liferayModuleComponent != null) {
                liferayModuleComponent.setParentTheme(liferayThemeBuilderGradleTaskModel.getParentName());
            }
        }
        super.populateModuleExtraModels(gradleModule, ideModule);
    }

}
