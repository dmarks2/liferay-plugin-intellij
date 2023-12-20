package de.dm.intellij.liferay.maven.importer;

import com.intellij.openapi.externalSystem.service.project.IdeModifiableModelsProvider;
import com.intellij.openapi.module.Module;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.idea.maven.importing.MavenImporter;
import org.jetbrains.idea.maven.importing.MavenRootModelAdapter;
import org.jetbrains.idea.maven.model.MavenPlugin;
import org.jetbrains.idea.maven.project.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * A Maven Importer which tries to find out the Parent Theme in Liferay 7.x projects based on the pom.xml within your project
 */
public class LiferayThemeBuilderMavenImporter extends MavenImporter {

    public static final String LIFERAY_MAVEN_GROUP_ID = "com.liferay";
    public static final String LIFERAY_MAVEN_ARTIFACT_ID = "com.liferay.portal.tools.theme.builder";

    public static final String CONFIG_PARENT_NAME = "parentName";

    public LiferayThemeBuilderMavenImporter() {
        super(LIFERAY_MAVEN_GROUP_ID, LIFERAY_MAVEN_ARTIFACT_ID);
    }

    @Override
    public void preProcess(Module module, MavenProject mavenProject, MavenProjectChanges mavenProjectChanges, IdeModifiableModelsProvider ideModifiableModelsProvider) {

    }

    @Override
    public void process(@NotNull IdeModifiableModelsProvider ideModifiableModelsProvider, @NotNull Module module, @NotNull MavenRootModelAdapter mavenRootModelAdapter, @NotNull MavenProjectsTree mavenProjectsTree, MavenProject mavenProject, @NotNull MavenProjectChanges mavenProjectChanges, @NotNull Map<MavenProject, String> map, @NotNull List<MavenProjectsProcessorTask> list) {
        MavenPlugin plugin = mavenProject.findPlugin(myPluginGroupID, myPluginArtifactID);
        if (plugin != null) {
            //found the Liferay Maven plugin
            Element configurationElement = plugin.getConfigurationElement();
            if (configurationElement == null) {
                for (MavenPlugin.Execution execution : plugin.getExecutions()) {
                    configurationElement = execution.getConfigurationElement();
                    break;
                }
            }
            if (configurationElement != null) {
                Element configParentName = configurationElement.getChild(CONFIG_PARENT_NAME);
                if (configParentName != null) {
                    String parentName = configParentName.getText();
                    if ( (parentName != null) && (!parentName.trim().isEmpty()) ) {
                        LiferayModuleComponent liferayModuleComponent = module.getService(LiferayModuleComponent.class);
                        if (liferayModuleComponent != null) {
                            liferayModuleComponent.setParentTheme(parentName);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void getSupportedPackagings(Collection<? super String> result) {
        result.add("war");
        result.add("jar");
        result.add("bundle");
    }

    @Override
    public void getSupportedDependencyTypes(Collection<? super String> result, SupportedRequestType type) {
        getSupportedPackagings(result);
    }
}
