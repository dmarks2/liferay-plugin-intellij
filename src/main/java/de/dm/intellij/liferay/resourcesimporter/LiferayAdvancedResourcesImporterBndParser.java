package de.dm.intellij.liferay.resourcesimporter;

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
 * Extracts Advanced Resources Importer Information from bnd.bnd
 */
public class LiferayAdvancedResourcesImporterBndParser extends FileChangeListenerBase {

    private static final String ADVANCED_RESOURCES_IMPORTER = "Advanced-Resources-Importer";
    private static final String ADVANCED_RESOURCES_IMPORTER_GROUP = "Advanced-Resources-Importer-Group";

    public static void handleChange(Project project, VirtualFile virtualFile) {
        final Module module = ModuleUtil.findModuleForFile(virtualFile, project);
        if (module != null) {
            LiferayModuleComponent component = module.getService(LiferayModuleComponent.class);
            if (component != null) {
                Properties properties = new Properties();
                try {
                    properties.load(virtualFile.getInputStream());

                    String advancedResourcesImporter = properties.getProperty(ADVANCED_RESOURCES_IMPORTER);
                    String advancedResourcesImporterGroup = properties.getProperty(ADVANCED_RESOURCES_IMPORTER_GROUP);

                    if ("true".equalsIgnoreCase(advancedResourcesImporter)) {
                        component.setResourcesImporterGroupName(advancedResourcesImporterGroup);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean isRelevantFile(String path) {
        return path.endsWith("bnd.bnd");
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
