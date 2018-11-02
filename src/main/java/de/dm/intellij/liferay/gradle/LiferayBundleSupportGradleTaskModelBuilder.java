package de.dm.intellij.liferay.gradle;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.dsl.DependencyHandler;
import org.gradle.api.internal.plugins.ExtensionContainerInternal;
import org.gradle.api.plugins.ExtensionContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.gradle.tooling.ErrorMessageBuilder;
import org.jetbrains.plugins.gradle.tooling.ModelBuilderService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * Class to extract information from the gradle plugins (com.liferay.workspace in this case)
 */
public class LiferayBundleSupportGradleTaskModelBuilder implements ModelBuilderService {

    private static ServiceLoader<ModelBuilderService> buildersLoader =
            ServiceLoader.load(ModelBuilderService.class, LiferayBundleSupportGradleTaskModelBuilder.class.getClassLoader());


    public LiferayBundleSupportGradleTaskModelBuilder() {
    }

    @Override
    public boolean canBuild(String modelName) {
        return LiferayBundleSupportGradleTaskModel.class.getName().equals(modelName);
    }

    @Override
    public Object buildAll(String modelName, Project project) {
        Path log = Paths.get("F:\\output.txt");

        try(BufferedWriter writer = Files.newBufferedWriter(log)) {

        final LiferayBundleSupportGradleTaskModelImpl result = new LiferayBundleSupportGradleTaskModelImpl();

            for (Iterator<Plugin> i = project.getPlugins().iterator(); i.hasNext(); ) {
                Plugin plugin = i.next();
                writer.write("Found plugin: " + plugin + "\n");
            }
            Plugin plugin = project.getPlugins().findPlugin("com.liferay.workspace");

            writer.write("P: " + plugin + "\n");

            ExtensionContainer extensions = project.getExtensions();
            if (extensions instanceof ExtensionContainerInternal) {
                Map<String, Object> map = ((ExtensionContainerInternal) extensions).getAsMap();
                writer.write("Ex: " + map + "\n");
            }

            Map<String, ?> properties = project.getProperties();
            for (Map.Entry<String, ?> entry : properties.entrySet()) {
                writer.write("PROP - " + entry.getKey() + ": " + String.valueOf(entry.getValue()) + "\n");
                if ("liferay.workspace.target.platform.version".equals(entry.getKey())) {
                    String liferayVersion = (String) entry.getValue();
                    //TODO save Version to .iml
                }
            }

            /*if (plugin != null) {*/
        Map<Project, Set<Task>> allTasks = project.getAllTasks(false);
        for (Map.Entry<Project, Set<Task>> tasks : allTasks.entrySet()) {
            for (Task task : tasks.getValue()) {
                        writer.write("T: " + task.getName() + ", type = " + task.getClass().getName() + "\n");
                if (task.getName().equals("initBundle")) {
                    try {
                                //org.gradle.api.tasks.Copy copyTask = (org.gradle.api.tasks.Copy)task;

                                Field[] fields = task.getClass().getDeclaredFields();
                                for (Field field : fields) {
                                    writer.write(field.toString() + "\n");
                                }

                                //get parentName via reflection
                        Method getDestinationDir = task.getClass().getDeclaredMethod("getDestinationDir");
                        File dest = (File)getDestinationDir.invoke(task);

                                writer.write("destinationDir = " + dest + "\n");

                        String path = "bundles";

                        if (dest != null) {
                            path = dest.getPath();
                        }

                        result.setLiferayHome(path);
                    } catch (Exception e) {
                        //ignore
                                e.printStackTrace();
                    }
                }
            }
        }
            /*}*/
        return result;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    @NotNull
    @Override
    public ErrorMessageBuilder getErrorMessageBuilder(@NotNull Project project, @NotNull Exception e) {
        return ErrorMessageBuilder.create(project, e, "Gradle import error").withDescription("Unable to import Liferay Bundle Support Task configuration");
    }
}

