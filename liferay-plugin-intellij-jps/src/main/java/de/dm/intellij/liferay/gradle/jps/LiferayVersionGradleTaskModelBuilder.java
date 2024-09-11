package de.dm.intellij.liferay.gradle.jps;

import com.intellij.openapi.diagnostic.Logger;
import org.gradle.api.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.gradle.tooling.ModelBuilderContext;
import org.jetbrains.plugins.gradle.tooling.ModelBuilderService;

public class LiferayVersionGradleTaskModelBuilder implements ModelBuilderService {

	private final static Logger log = Logger.getInstance(LiferayVersionGradleTaskModelBuilder.class);

	@Override
	public boolean canBuild(String modelName) {
		return LiferayVersionGradleTaskModel.class.getName().equals(modelName);
	}

	@Override
	public Object buildAll(String modelName, Project project) {
		if (log.isDebugEnabled()) {
			log.debug("building " + modelName + " for " + project);
		}

		final LiferayVersionGradleTaskModelmpl result = new LiferayVersionGradleTaskModelmpl();

		if (project.hasProperty("liferay.workspace.product")) {
			String liferayWorkspaceProduct = String.valueOf(project.findProperty("liferay.workspace.product"));

			if (log.isDebugEnabled()) {
				log.debug("Found liferay.workspace.product with value " + liferayWorkspaceProduct);
			}

			result.setLiferayWorkspaceProduct(liferayWorkspaceProduct);
		}

		return result;
	}

	public void reportErrorMessage(@NotNull String modelName, @NotNull Project project, @NotNull ModelBuilderContext context, @NotNull Exception exception) {
		context.getMessageReporter().createMessage().withGroup("Gradle import error").withText("Unable to import Liferay Version configuration").withException(exception).reportMessage(project);
	}
}
