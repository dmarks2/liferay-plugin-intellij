package de.dm.intellij.liferay.bnd;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileEvent;
import de.dm.intellij.liferay.module.LiferayModuleComponent;

import java.io.IOException;
import java.util.Properties;

/**
 * Class to parse bnd.bnd (if present) and to save information into LiferayModuleComponent
 */
public class OsgiBndFileParser {

    public static void handleChange(Project project, VirtualFileEvent event) {
        if ("bnd.bnd".equals(event.getFileName())) {
            VirtualFile virtualFile = event.getFile();
            final Module module = ModuleUtil.findModuleForFile(virtualFile, project);
            if (module != null) {
                LiferayModuleComponent component = module.getComponent(LiferayModuleComponent.class);
                if (component != null) {
                    Properties properties = new Properties();
                    try {
                        properties.load(virtualFile.getInputStream());

                        String fragmentHost = properties.getProperty("Fragment-Host");

                        component.setOsgiFragmentHost(fragmentHost);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
