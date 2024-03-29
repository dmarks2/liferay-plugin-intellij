package de.dm.intellij.liferay.bnd;

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
 * Class to parse bnd.bnd (if present) and to save information into LiferayModuleComponent
 */
public class OsgiBndFileParser extends FileChangeListenerBase {

    private final static Logger log = Logger.getInstance(OsgiBndFileParser.class);

    public static void handleChange(Project project, VirtualFile virtualFile) {
        final Module module = ModuleUtil.findModuleForFile(virtualFile, project);
        if (module != null) {
            LiferayModuleComponent component = module.getService(LiferayModuleComponent.class);
            if (component != null) {
                Properties properties = new Properties();
                try {
                    properties.load(virtualFile.getInputStream());

                    String fragmentHost = properties.getProperty("Fragment-Host");

                    component.setOsgiFragmentHost(fragmentHost);
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
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
    protected void updateFile(VirtualFile virtualFile, VFileEvent vFileEvent) {
        Project project = ProjectUtil.guessProjectForFile(virtualFile);
        if (project != null) {
            handleChange(project, virtualFile);
        }
    }

    @Override
    protected void deleteFile(VirtualFile virtualFile, VFileEvent vFileEvent) {
    }

    @Override
    protected void apply() {
    }
}
