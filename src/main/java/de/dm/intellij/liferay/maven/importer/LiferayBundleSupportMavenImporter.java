package de.dm.intellij.liferay.maven.importer;

import com.intellij.openapi.externalSystem.service.project.IdeModifiableModelsProvider;
import com.intellij.openapi.module.Module;
import org.jdom.Element;
import org.jetbrains.idea.maven.importing.MavenImporter;
import org.jetbrains.idea.maven.importing.MavenRootModelAdapter;
import org.jetbrains.idea.maven.model.MavenPlugin;
import org.jetbrains.idea.maven.project.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * A Maven Importer which resolves the Liferay Home directory if the bundle-support plugin is present and will "exclude" the bundles folder from indexing in IntelliJ IDEA
 */
public class LiferayBundleSupportMavenImporter extends MavenImporter {

    private static final String LIFERAY_BUNDLE_SUPPORT_MAVEN_GROUP_ID = "com.liferay";
    private static final String LIFERAY_BUNDLE_SUPPORT_MAVEN_ARTIFACT_ID = "com.liferay.portal.tools.bundle.support";

    private static final String CONFIG_BUNDLE_SUPPORT_LIFERAY_HOME = "liferayHome";
    private static final String PROPERTY_BUNDLE_SUPPORT_LIFERAY_HOME = "liferayHome";

    public LiferayBundleSupportMavenImporter() {
        super(LIFERAY_BUNDLE_SUPPORT_MAVEN_GROUP_ID, LIFERAY_BUNDLE_SUPPORT_MAVEN_ARTIFACT_ID);
    }

    @Override
    public void preProcess(Module module, MavenProject mavenProject, MavenProjectChanges mavenProjectChanges, IdeModifiableModelsProvider ideModifiableModelsProvider) {

    }

    @Override
    public void process(IdeModifiableModelsProvider ideModifiableModelsProvider, Module module, MavenRootModelAdapter mavenRootModelAdapter, MavenProjectsTree mavenProjectsTree, MavenProject mavenProject, MavenProjectChanges mavenProjectChanges, Map<MavenProject, String> map, List<MavenProjectsProcessorTask> list) {
        MavenPlugin plugin = mavenProject.findPlugin(myPluginGroupID, myPluginArtifactID);
        if (plugin != null) {
            String liferayHome = null;

            Element configurationElement = plugin.getConfigurationElement();
            if (configurationElement != null) {
                Element configBundleSupportLiferayHome = configurationElement.getChild(CONFIG_BUNDLE_SUPPORT_LIFERAY_HOME);
                if (configBundleSupportLiferayHome != null) {
                    liferayHome = configBundleSupportLiferayHome.getText();

                    if (liferayHome.contains(":")) {
                        //absolute path
                        String mavenDirectory = mavenProject.getDirectory();

                        mavenDirectory = mavenDirectory.replace('/', '\\');

                        if (liferayHome.startsWith(mavenDirectory)) {
                            liferayHome = liferayHome.substring(mavenDirectory.length());
                        }

                        if (liferayHome.startsWith("/")) {
                            liferayHome = liferayHome.substring(1);
                        }
                    }
                }
            }

            if (liferayHome == null) {
                liferayHome = mavenProject.getProperties().getProperty(PROPERTY_BUNDLE_SUPPORT_LIFERAY_HOME);
            }

            if (liferayHome == null) {
                liferayHome = "bundles"; //default value from com.liferay.portal.tools.bundle.support plugin
            }

            if (! ( liferayHome.startsWith("/") || liferayHome.contains(":") ) ) {
                mavenRootModelAdapter.addExcludedFolder(liferayHome);
            }
        }
    }

    @Override
    public boolean isApplicable(MavenProject mavenProject) {
        return
            super.isApplicable(mavenProject) &&
            (
                (mavenProject.getParentId() == null) ||
                (mavenProject.getParentId().getGroupId() == null)
            );
    }

    @Override
    public void getSupportedPackagings(Collection<String> result) {
        result.add("pom");
    }

    @Override
    public void getSupportedDependencyTypes(Collection<String> result, SupportedRequestType type) {
        getSupportedPackagings(result);
    }
}
