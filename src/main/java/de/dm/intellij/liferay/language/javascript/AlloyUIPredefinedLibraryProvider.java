package de.dm.intellij.liferay.language.javascript;

import com.intellij.lang.javascript.library.JSPredefinedLibraryProvider;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.webcore.libraries.ScriptingLibraryModel;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
import de.dm.intellij.liferay.util.LiferayVersions;
import org.jetbrains.annotations.NotNull;

/**
 * Provides a predefined JavaScript Library for AlloyUI 1.5, 2.0 and 3.0 and Liferay Barebone JS files
 */
public class AlloyUIPredefinedLibraryProvider extends JSPredefinedLibraryProvider {

    @NotNull
    @Override
    public ScriptingLibraryModel[] getPredefinedLibraries(@NotNull Project project) {
        float liferayVersion = -1.0f;
        for (Module module : ModuleManager.getInstance(project).getModules()) {
            LiferayModuleComponent component = module.getComponent(LiferayModuleComponent.class);
            if (component != null) {
                float portalMajorVersion = component.getPortalMajorVersion();

                liferayVersion = Math.max(liferayVersion, portalMajorVersion);
            }
        }
        if (liferayVersion == LiferayVersions.LIFERAY_VERSION_6_1) {
            return new ScriptingLibraryModel[]{
                    ScriptingLibraryModel.createPredefinedLibrary("Alloy UI 1.5", new VirtualFile[]{VfsUtil.findFileByURL(AlloyUIPredefinedLibraryProvider.class.getResource("/alloy-1.5.1/"))}, "http://alloyui.com/versions/1.5.x/api/", true),
                    ScriptingLibraryModel.createPredefinedLibrary("Liferay 6.1 Barebone Scripts", new VirtualFile[]{VfsUtil.findFileByURL(AlloyUIPredefinedLibraryProvider.class.getResource("/com/liferay/js/61/"))}, true)
            };
        } else if (liferayVersion == LiferayVersions.LIFERAY_VERSION_6_2) {
            return new ScriptingLibraryModel[] {
                    ScriptingLibraryModel.createPredefinedLibrary("Alloy UI 2.0", new VirtualFile[]{VfsUtil.findFileByURL(AlloyUIPredefinedLibraryProvider.class.getResource("/alloy-2.0.0/"))}, "http://alloyui.com/versions/2.0.x/api/", true),
                    ScriptingLibraryModel.createPredefinedLibrary("Liferay 6.2 Barebone Scripts", new VirtualFile[]{VfsUtil.findFileByURL(AlloyUIPredefinedLibraryProvider.class.getResource("/com/liferay/js/62/"))}, true),
            };
        } else if (
                    (liferayVersion == LiferayVersions.LIFERAY_VERSION_7_0) ||
                    (liferayVersion == LiferayVersions.LIFERAY_VERSION_UNKNOWN)
                ) {
            return new ScriptingLibraryModel[] {
                    ScriptingLibraryModel.createPredefinedLibrary("Alloy UI 3.0", new VirtualFile[]{VfsUtil.findFileByURL(AlloyUIPredefinedLibraryProvider.class.getResource("/alloy-3.0.1/"))}, "http://alloyui.com/api/", true),
                    ScriptingLibraryModel.createPredefinedLibrary("Liferay 7.0 Barebone Scripts", new VirtualFile[]{VfsUtil.findFileByURL(AlloyUIPredefinedLibraryProvider.class.getResource("/com/liferay/js/70/"))}, true)
            };
        }

        return new ScriptingLibraryModel[0];
    }
}
