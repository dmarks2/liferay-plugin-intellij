package de.dm.intellij.liferay.hook;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileEvent;
import de.dm.intellij.liferay.module.LiferayModuleComponent;

/**
 * Class to parse liferay-look-and-feel.xml (if present) and to save information into LiferayModuleComponent
 */
public class LiferayHookXmlParser {

    public static void handleChange(Project project, VirtualFileEvent event) {
        if ("liferay-hook.xml".equals(event.getFileName())) {
            VirtualFile virtualFile = event.getFile();
            final Module module = ModuleUtil.findModuleForFile(virtualFile, project);
            if (module != null) {
                LiferayModuleComponent component = module.getComponent(LiferayModuleComponent.class);
                if (component != null) {
                    component.setLiferayHookXml(virtualFile.getPath());
                }
            }
        }
    }

}
