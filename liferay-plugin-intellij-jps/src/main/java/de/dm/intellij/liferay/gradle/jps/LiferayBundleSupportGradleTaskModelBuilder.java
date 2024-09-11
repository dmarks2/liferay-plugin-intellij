package de.dm.intellij.liferay.gradle.jps;

import com.intellij.openapi.diagnostic.Logger;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.gradle.tooling.ModelBuilderContext;
import org.jetbrains.plugins.gradle.tooling.ModelBuilderService;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * Class to extract information from the gradle plugins (com.liferay.workspace in this case)
 */
public class LiferayBundleSupportGradleTaskModelBuilder implements ModelBuilderService {

    private static final Logger log = Logger.getInstance(LiferayBundleSupportGradleTaskModelBuilder.class);

    @Override
    public boolean canBuild(String modelName) {
        return LiferayBundleSupportGradleTaskModel.class.getName().equals(modelName);
    }

    @Override
    public Object buildAll(String modelName, Project project) {
        final LiferayBundleSupportGradleTaskModelImpl result = new LiferayBundleSupportGradleTaskModelImpl();

        Map<Project, Set<Task>> allTasks = project.getAllTasks(false);

        for (Map.Entry<Project, Set<Task>> tasks : allTasks.entrySet()) {
            for (Task task : tasks.getValue()) {
                if (task.getName().equals("initBundle")) {

                    try {
                        //org.gradle.api.tasks.Copy copyTask = (org.gradle.api.tasks.Copy)task;
                        //get parentName via reflection
                        Method getDestinationDir = task.getClass().getDeclaredMethod("getDestinationDir");
                        File dest = (File)getDestinationDir.invoke(task);

                        String path = "bundles";

                        if (dest != null) {
                            if (log.isDebugEnabled()) {
                                log.debug("Got \"" + dest.getPath() + "\" from initBundle.getDestinationDir().");
                            }
                            path = dest.getPath();
                        }

                        result.setLiferayHome(path);
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public void reportErrorMessage(@NotNull String modelName, @NotNull Project project, @NotNull ModelBuilderContext context, @NotNull Exception exception) {
        context.getMessageReporter().createMessage().withGroup("Gradle import error").withText("Unable to import Liferay Bundle Support Task configuration").withException(exception).reportMessage(project);
    }
}

