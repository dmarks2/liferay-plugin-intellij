package de.dm.intellij.liferay.gradle;

import org.gradle.api.Project;
import org.gradle.api.Task;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.gradle.tooling.ModelBuilderContext;
import org.jetbrains.plugins.gradle.tooling.ModelBuilderService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * Class to extract information from gradle plugins (com.liferay.portal.tools.theme.builder in this case)
 */
public class LiferayThemeBuilderGradleTaskModelBuilder implements ModelBuilderService {

    @Override
    public boolean canBuild(String modelName) {
        return LiferayThemeBuilderGradleTaskModel.class.getName().equals(modelName);
    }

    @Override
    public Object buildAll(String modelName, Project project) {
        final LiferayThemeBuilderGradleTaskModelImpl result = new LiferayThemeBuilderGradleTaskModelImpl();
        result.setEnabled(false);

        //try to find the com.liferay.portal.tools.theme.builder plugin
        if (project.getPlugins().hasPlugin("com.liferay.portal.tools.theme.builder")) {
            result.setEnabled(true);

            Map<Project, Set<Task>> allTasks = project.getAllTasks(false);
            for (Map.Entry<Project, Set<Task>> tasks : allTasks.entrySet()) {
                //try to find the buildTheme task
                for (Task task : tasks.getValue()) {
                    if (
                            (task.getClass().getName().equals("com.liferay.gradle.plugins.theme.builder.BuildThemeTask")) ||
                                    (task.getClass().getName().equals("com.liferay.gradle.plugins.theme.builder.BuildThemeTask_Decorated"))
                            ) {
                        try {
                            //get parentName via reflection
                            Method getParentName = task.getClass().getDeclaredMethod("getParentName");
                            String parentName = (String)getParentName.invoke(task);
                            result.setParentName(parentName);
                        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                            //ignore
                        }
                    }
                }
            }
        }
        return result;
    }

    @Override
    public void reportErrorMessage(@NotNull String modelName, @NotNull Project project, @NotNull ModelBuilderContext context, @NotNull Exception exception) {
        context.getMessageReporter().createMessage().withGroup("Gradle import error").withText("Unable to import Liferay Theme Task configuration").withException(exception).reportMessage(project);
    }
}
