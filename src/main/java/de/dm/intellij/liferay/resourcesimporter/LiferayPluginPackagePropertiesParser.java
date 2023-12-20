package de.dm.intellij.liferay.resourcesimporter;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.externalSystem.service.project.autoimport.FileChangeListenerBase;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import de.dm.intellij.liferay.module.LiferayModuleComponent;

import java.io.IOException;
import java.util.Properties;

/**
 * Extracts Resources Importer Information from liferay-plugin-package.properties
 */
public class LiferayPluginPackagePropertiesParser extends FileChangeListenerBase {

    private final static Logger log = Logger.getInstance(LiferayPluginPackagePropertiesParser.class);

    private static final String RESOURCES_IMPORTER_TARGET_CLASS_NAME = "resources-importer-target-class-name";
    private static final String RESOURCES_IMPORTER_TARGET_VALUE = "resources-importer-target-value";
    private static final String LIFERAY_KERNEL_MODEL_GROUP_CLASSNAME = "com.liferay.portal.kernel.model.Group";

    public static void handleChange(Project project, VirtualFile virtualFile) {
        final Module module = ModuleUtil.findModuleForFile(virtualFile, project);
        if (module != null) {
            LiferayModuleComponent component = module.getService(LiferayModuleComponent.class);
            if (component != null) {
                Properties properties = new Properties();
                try {
                    properties.load(virtualFile.getInputStream());

                    String targetClassName = properties.getProperty(RESOURCES_IMPORTER_TARGET_CLASS_NAME);
                    String targetValue = properties.getProperty(RESOURCES_IMPORTER_TARGET_VALUE);

                    if (LIFERAY_KERNEL_MODEL_GROUP_CLASSNAME.equals(targetClassName)) {
                        component.setResourcesImporterGroupName(targetValue);
                    }
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    public static boolean isRelevantFile(String path) {
        return path.endsWith("liferay-plugin-package.properties");
    }

    @Override
    protected boolean isRelevant(String path) {
        return isRelevantFile(path);
    }

    @Override
    protected void updateFile(VirtualFile virtualFile, VFileEvent event) {
        Project project = ProjectUtil.guessProjectForFile(virtualFile);
        if (project != null) {
            handleChange(project, virtualFile);
        }
    }

    @Override
    protected void deleteFile(VirtualFile file, VFileEvent event) {

    }

    @Override
    protected void apply() {

    }
}
