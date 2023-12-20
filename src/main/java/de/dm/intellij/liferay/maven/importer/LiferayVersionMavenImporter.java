package de.dm.intellij.liferay.maven.importer;

import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.openapi.externalSystem.service.project.IdeModifiableModelsProvider;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.module.ModuleTypeManager;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.DependencyManagement;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.idea.maven.importing.MavenImporter;
import org.jetbrains.idea.maven.importing.MavenRootModelAdapter;
import org.jetbrains.idea.maven.model.MavenPlugin;
import org.jetbrains.idea.maven.project.MavenProject;
import org.jetbrains.idea.maven.project.MavenProjectChanges;
import org.jetbrains.idea.maven.project.MavenProjectsProcessorTask;
import org.jetbrains.idea.maven.project.MavenProjectsTree;
import org.jetbrains.idea.maven.project.SupportedRequestType;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * A Maven Importer which tries to find out the target Liferay Version and Parent Theme (Liferay 6.x) based on the pom.xml within your project
 */
public class LiferayVersionMavenImporter extends MavenImporter {
    public static final String LIFERAY_MAVEN_GROUP_ID = "com.liferay.maven.plugins";
    public static final String LIFERAY_MAVEN_ARTIFACT_ID = "liferay-maven-plugin";

    public static final String CONFIG_LIFERAY_VERSION = "liferayVersion";
    public static final String CONFIG_PARENT_THEME = "parentTheme";

    public static final String PROPERTY_LIFERAY_VERSION = "liferay.version";

    public static Collection<String> LIFERAY_BOMS = Arrays.asList(
            "com.liferay.ce.portal.bom",
            "com.liferay.ce.portal.compile.only",
            "release.dxp.bom",
            "release.dxp.bom.compile.only",
            "release.dxp.distro",
            "release.portal.bom",
            "release.portal.bom.compile.only",
            "release.portal.distro"
    );

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
        if (liferayVersion == null) {
            liferayVersion = getLiferayVersionFromBOM(mavenProject);
        }
        LiferayModuleComponent liferayModuleComponent = module.getService(LiferayModuleComponent.class);
        if (liferayModuleComponent != null) {
            if ( (liferayVersion != null) && (liferayVersion.trim().length() > 0) ) {
                liferayModuleComponent.setLiferayVersion(liferayVersion);
            }
            if ( (parentTheme != null) && (parentTheme.trim().length() > 0) ) {
                liferayModuleComponent.setParentTheme(parentTheme);
            }
        }
    }

    private String getLiferayVersionFromBOM(MavenProject mavenProject) {
        MavenXpp3Reader mavenXpp3Reader = new MavenXpp3Reader();
        try ( Reader reader = new InputStreamReader(mavenProject.getFile().getInputStream()) ){
            Model model = mavenXpp3Reader.read(reader);
            DependencyManagement dependencyManagement = model.getDependencyManagement();
            if (dependencyManagement != null) {
                for (Dependency dependency : dependencyManagement.getDependencies()) {
                    String groupId = dependency.getGroupId();
                    String artifactId = dependency.getArtifactId();
                    String version = dependency.getVersion();
                    String type = dependency.getType();
                    String scope = dependency.getScope();

                    if (
                            "import".equals(scope) &&
                                    "pom".equals(type) &&
                                    (
                                            "com.liferay".equals(groupId) ||
                                                    "com.liferay.portal".equals(groupId)
                                    ) &&
                                    artifactId != null &&
                                    LIFERAY_BOMS.contains(artifactId) &&
                                    version != null
                    ) {
                        if (version.startsWith("${") && version.endsWith("}")) {
                            String versionProperty = version.substring(2, version.length() - 1);
                            Properties properties = mavenProject.getProperties();
                            version = properties.getProperty(versionProperty);
                        }
                        return version;
                    }
                }
            }
        } catch (IOException | XmlPullParserException e) {
            //unable to read the xml, ignore
        }

        return null;
    }

    @Override
    public boolean isApplicable(MavenProject mavenProject) {
        boolean applicable = super.isApplicable(mavenProject);
        if (! applicable) {
            applicable = mavenProject.getProperties().containsKey(PROPERTY_LIFERAY_VERSION);
        }
        if (!applicable) {
            applicable = (getLiferayVersionFromBOM(mavenProject) != null);
        }

        return applicable;
    }

    @Override
    public void getSupportedPackagings(Collection<? super String> result) {
        result.add("war");
        result.add("jar");
        result.add("bundle");
        result.add("pom");
    }

    @Override
    public void getSupportedDependencyTypes(Collection<? super String> result, SupportedRequestType type) {
        getSupportedPackagings(result);
    }

    @Override
    public @NotNull ModuleType<? extends ModuleBuilder> getModuleType() {
        return ModuleTypeManager.getInstance().findByID("JAVA_MODULE");
    }
}
