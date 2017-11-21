package de.dm.intellij.liferay.maven.importer;

import com.intellij.openapi.externalSystem.service.project.IdeModifiableModelsProvider;
import com.intellij.openapi.module.Module;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
import org.jdom.Element;
import org.jetbrains.idea.maven.importing.MavenImporter;
import org.jetbrains.idea.maven.importing.MavenRootModelAdapter;
import org.jetbrains.idea.maven.model.MavenPlugin;
import org.jetbrains.idea.maven.project.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * A Maven Importer which tries to find out the target Liferay Version and Parent Theme (Liferay 6.x) based on the pom.xml within your project
 */
public class LiferayVersionMavenImporter extends MavenImporter {

    public static final String LIFERAY_MAVEN_GROUP_ID = "com.liferay.maven.plugins";
    public static final String LIFERAY_MAVEN_ARTIFACT_ID = "liferay-maven-plugin";

    public static final String CONFIG_LIFERAY_VERSION = "liferayVersion";
    public static final String CONFIG_PARENT_THEME = "parentTheme";

    public static final String PROPERTY_LIFERAY_VERSION = "liferay.version";

    public LiferayVersionMavenImporter() {
        super(LIFERAY_MAVEN_GROUP_ID, LIFERAY_MAVEN_ARTIFACT_ID);
    }

    public void preProcess(Module module, MavenProject mavenProject, MavenProjectChanges mavenProjectChanges, IdeModifiableModelsProvider ideModifiableModelsProvider) {

    }

    public void process(IdeModifiableModelsProvider ideModifiableModelsProvider, Module module, MavenRootModelAdapter mavenRootModelAdapter, MavenProjectsTree mavenProjectsTree, MavenProject mavenProject, MavenProjectChanges mavenProjectChanges, Map<MavenProject, String> map, List<MavenProjectsProcessorTask> list) {
        String liferayVersion = null;
        String parentTheme = null;

        MavenPlugin plugin = mavenProject.findPlugin(myPluginGroupID, myPluginArtifactID);
        if (plugin != null) {
            //found the Liferay Maven plugin
            Element configurationElement = plugin.getConfigurationElement();
            if (configurationElement != null) {
                Element configLiferayVersion = configurationElement.getChild(CONFIG_LIFERAY_VERSION);
                if (configLiferayVersion != null) {
                    liferayVersion = configLiferayVersion.getText();
                }
                Element configParentTheme = configurationElement.getChild(CONFIG_PARENT_THEME);
                if (configParentTheme != null) {
                    parentTheme = configParentTheme.getText();
                }
            }
        } else {
            //plugin not found, see if property "liferay.version" is defined
            liferayVersion = mavenProject.getProperties().getProperty(PROPERTY_LIFERAY_VERSION);
        }
        LiferayModuleComponent liferayModuleComponent = module.getComponent(LiferayModuleComponent.class);
        if (liferayModuleComponent != null) {
            if ( (liferayVersion != null) && (liferayVersion.trim().length() > 0) ) {
                liferayModuleComponent.setLiferayVersion(liferayVersion);
            }
            if ( (parentTheme != null) && (parentTheme.trim().length() > 0) ) {
                liferayModuleComponent.setParentTheme(parentTheme);
            }
        }
    }

    @Override
    public boolean isApplicable(MavenProject mavenProject) {
        boolean applicable = super.isApplicable(mavenProject);
        if (! applicable) {
            applicable = mavenProject.getProperties().containsKey(PROPERTY_LIFERAY_VERSION);
        }

        return applicable;
    }

    @Override
    public void getSupportedPackagings(Collection<String> result) {
        result.add("war");
        result.add("jar");
        result.add("bundle");
    }

    @Override
    public void getSupportedDependencyTypes(Collection<String> result, SupportedRequestType type) {
        getSupportedPackagings(result);
    }
}
