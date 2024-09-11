package de.dm.intellij.liferay.gradle;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.externalSystem.model.DataNode;
import com.intellij.openapi.externalSystem.model.project.ModuleData;
import com.intellij.openapi.externalSystem.model.project.ProjectData;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import de.dm.intellij.liferay.gradle.jps.LiferayVersionGradleTaskModel;
import de.dm.intellij.liferay.gradle.jps.LiferayVersionGradleTaskModelBuilder;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
import de.dm.intellij.liferay.workspace.LiferayWorkspaceUtil;
import org.gradle.tooling.model.idea.IdeaContentRoot;
import org.gradle.tooling.model.idea.IdeaModule;
import org.gradle.tooling.model.idea.IdeaProject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.gradle.service.project.AbstractProjectResolverExtension;

import java.io.File;
import java.util.Collections;
import java.util.Set;

public class LiferayVersionGradleProjectResolver extends AbstractProjectResolverExtension {

	private final static Logger log = Logger.getInstance(LiferayVersionGradleProjectResolver.class);

	@NotNull
	@Override
	public Set<Class<?>> getExtraProjectModelClasses() {
		return Collections.singleton(LiferayVersionGradleTaskModel.class);
	}

	@NotNull
	@Override
	public Set<Class<?>> getToolingExtensionsClasses() {
		return Collections.singleton(LiferayVersionGradleTaskModelBuilder.class);
	}

	@Override
	public void populateProjectExtraModels(@NotNull IdeaProject gradleProject, @NotNull DataNode<ProjectData> ideProject) {
		super.populateProjectExtraModels(gradleProject, ideProject);
	}

	@Override
	public @Nullable DataNode<ModuleData> createModule(@NotNull IdeaModule gradleModule, @NotNull DataNode<ProjectData> projectDataNode) {
		return super.createModule(gradleModule, projectDataNode);
	}

	@Override
	public void populateModuleExtraModels(@NotNull IdeaModule gradleModule, @NotNull DataNode<ModuleData> ideModule) {
		LiferayVersionGradleTaskModel liferayVersionGradleTaskModel = resolverCtx.getExtraProject(gradleModule, LiferayVersionGradleTaskModel.class);

		if (liferayVersionGradleTaskModel != null) {
			IdeaContentRoot contentRoot = gradleModule.getContentRoots().getAt(0);

			File rootDirectory = contentRoot.getRootDirectory();

			VirtualFile fileByIoFile = VfsUtil.findFileByIoFile(rootDirectory, false);

			if (fileByIoFile != null) {
				Project project = ProjectUtil.guessProjectForFile(fileByIoFile);

				if (project != null) {
					Module module = ModuleUtil.findModuleForFile(fileByIoFile, project);

					if (module != null) {
						String liferayWorkspaceProductValue = liferayVersionGradleTaskModel.getLiferayWorkspaceProduct();

						if (log.isDebugEnabled()) {
							log.debug("Found liferay.workspace.product: " + liferayWorkspaceProductValue);
						}

						String liferayVersion = LiferayWorkspaceUtil.getLiferayVersion(liferayWorkspaceProductValue);

						if (log.isDebugEnabled()) {
							log.debug("Found liferay version: " + liferayVersion);
						}

						if (liferayVersion != null) {
							LiferayModuleComponent liferayModuleComponent = module.getService(LiferayModuleComponent.class);

							liferayModuleComponent.setLiferayVersion(liferayVersion);
						}
					}
				}
			}
		}

		super.populateModuleExtraModels(gradleModule, ideModule);
	}

}
