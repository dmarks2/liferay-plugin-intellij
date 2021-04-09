package de.dm.intellij.liferay.maven.importer;

import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.externalSystem.service.project.IdeModifiableModelsProvider;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
import de.dm.intellij.liferay.util.ProjectUtils;
import org.jdom.Element;
import org.jetbrains.idea.maven.importing.MavenImporter;
import org.jetbrains.idea.maven.importing.MavenRootModelAdapter;
import org.jetbrains.idea.maven.model.MavenPlugin;
import org.jetbrains.idea.maven.project.MavenProject;
import org.jetbrains.idea.maven.project.MavenProjectChanges;
import org.jetbrains.idea.maven.project.MavenProjectsProcessorTask;
import org.jetbrains.idea.maven.project.MavenProjectsTree;
import org.jetbrains.idea.maven.project.SupportedRequestType;

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

    private String getLiferayVersionFromBOM(MavenProject mavenProject) {
        return WriteAction.compute(
                () -> {

                    Project project = ProjectUtils.getActiveProject();
                    if (project != null) {

                        VirtualFile pomFile = mavenProject.getFile();

                        if (pomFile.isValid()) {

                            XmlFile xmlFile = (XmlFile) PsiManager.getInstance(project).findFile(pomFile);
                            if ((xmlFile != null) && (xmlFile.isValid())) {
                                XmlTag rootTag = xmlFile.getRootTag();
                                if (rootTag != null) {
                                    XmlTag dependencyManagement = rootTag.findFirstSubTag("dependencyManagement");
                                    if (dependencyManagement != null) {
                                        XmlTag[] dependenciesTags = dependencyManagement.findSubTags("dependencies");
                                        for (XmlTag dependenciesTag : dependenciesTags) {
                                            XmlTag[] dependencyTags = dependenciesTag.findSubTags("dependency");
                                            for (XmlTag dependencyTag : dependencyTags) {
                                                String groupId = dependencyTag.getSubTagText("groupId");
                                                String artifactId = dependencyTag.getSubTagText("artifactId");
                                                String version = dependencyTag.getSubTagText("version");
                                                String type = dependencyTag.getSubTagText("type");
                                                String scope = dependencyTag.getSubTagText("scope");
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
                                    }
                                }
                            }
                        }
                    }
                    return null;
                }
        );
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
    public void getSupportedPackagings(Collection<String> result) {
        result.add("war");
        result.add("jar");
        result.add("bundle");
        result.add("pom");
    }

    @Override
    public void getSupportedDependencyTypes(Collection<String> result, SupportedRequestType type) {
        getSupportedPackagings(result);
    }
}
