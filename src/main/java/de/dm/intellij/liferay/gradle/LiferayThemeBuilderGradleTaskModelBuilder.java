package de.dm.intellij.liferay.gradle;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.gradle.tooling.ErrorMessageBuilder;
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
        Plugin plugin = project.getPlugins().findPlugin("com.liferay.portal.tools.theme.builder");
        if (plugin != null) {
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

    @NotNull
    @Override
    public ErrorMessageBuilder getErrorMessageBuilder(@NotNull Project project, @NotNull Exception e) {
        return ErrorMessageBuilder.create(project, e, "Gradle import error").withDescription("Unable to import Liferay Theme Task configuration");
    }
}
