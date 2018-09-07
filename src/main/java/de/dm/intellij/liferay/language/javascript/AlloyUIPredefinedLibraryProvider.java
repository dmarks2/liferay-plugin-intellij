package de.dm.intellij.liferay.language.javascript;

import com.intellij.lang.javascript.library.JSPredefinedLibraryProvider;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileVisitor;
import com.intellij.webcore.libraries.ScriptingLibraryModel;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
import de.dm.intellij.liferay.util.LiferayVersions;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

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

            Set<VirtualFile> frontendJsFiles = new HashSet<VirtualFile>();
            addJavascriptFilesFromDirectory(VfsUtil.findFileByURL(AlloyUIPredefinedLibraryProvider.class.getResource("/com/liferay/js/70/frontend")), frontendJsFiles);

            Set<VirtualFile> frontendJsAuiFiles = new HashSet<VirtualFile>();
            addJavascriptFilesFromDirectory(VfsUtil.findFileByURL(AlloyUIPredefinedLibraryProvider.class.getResource("/com/liferay/js/70/aui")), frontendJsAuiFiles);

            return new ScriptingLibraryModel[] {
                ScriptingLibraryModel.createPredefinedLibrary("Liferay Frontend JS Web 1.0.79", frontendJsFiles.toArray(new VirtualFile[frontendJsFiles.size()]), true),
                ScriptingLibraryModel.createPredefinedLibrary("Liferay Frontend JS AUI Web 1.0.33", frontendJsAuiFiles.toArray(new VirtualFile[frontendJsAuiFiles.size()]), true)
            };
        }

        return new ScriptingLibraryModel[0];
    }

    private void addJavascriptFilesFromDirectory(VirtualFile directory, final Set<VirtualFile> result) {
        VfsUtilCore.visitChildrenRecursively(directory,
            new VirtualFileVisitor() {
                @Override
                public boolean visitFile(@NotNull VirtualFile file) {
                    if (file.isDirectory()) {
                        return true;
                    }

                    String extension = file.getExtension();
                    if ("js".equals(extension)) {
                        String nameWithoutExtension = file.getNameWithoutExtension();
                        if (! (
                            (nameWithoutExtension.endsWith("-min")) ||
                            (nameWithoutExtension.endsWith("-debug")) ||
                            (nameWithoutExtension.endsWith("-coverage"))
                        )) {
                            result.add(file);
                        }
                    }

                    return true;
                }
            }
        );

    }

}
