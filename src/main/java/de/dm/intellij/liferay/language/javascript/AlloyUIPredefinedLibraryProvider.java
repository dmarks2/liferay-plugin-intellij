package de.dm.intellij.liferay.language.javascript;

import com.intellij.lang.javascript.library.JSPredefinedLibraryProvider;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.OrderRootType;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.roots.libraries.Library;
import com.intellij.openapi.vfs.JarFileSystem;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileSystem;
import com.intellij.openapi.vfs.VirtualFileVisitor;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiManager;
import com.intellij.webcore.libraries.ScriptingLibraryModel;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
import de.dm.intellij.liferay.util.LiferayVersions;
import de.dm.intellij.liferay.util.ProjectUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Provides a predefined JavaScript Library for AlloyUI 1.5, 2.0 and 3.0 and Liferay Barebone JS files
 */
public class AlloyUIPredefinedLibraryProvider extends JSPredefinedLibraryProvider {

    private static final String FRONTEND_JS_WEB = "com.liferay:com.liferay.frontend.js.web";
    private static final String FRONTEND_JS_AUI_WEB = "com.liferay:com.liferay.frontend.js.aui.web";

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

            //TODO how to re-run import when maven project is refreshed?

            Collection<ScriptingLibraryModel> result = new ArrayList<>();

            Collection<Library> frontendJsWebLibraries = ProjectUtils.findLibrariesByName(FRONTEND_JS_WEB, project);
            Collection<Library> frontendJsAuiWebLibraries = ProjectUtils.findLibrariesByName(FRONTEND_JS_AUI_WEB, project);

            Set<VirtualFile> frontendJsWebJavascriptFiles = new HashSet<VirtualFile>();
            for (Library frontendJsWebLibrary : frontendJsWebLibraries) {
                addJavascriptFilesFromLibrary(frontendJsWebLibrary, frontendJsWebJavascriptFiles);
            }
            if (! (frontendJsWebJavascriptFiles.isEmpty()) ) {
                result.add(ScriptingLibraryModel.createPredefinedLibrary("Liferay Frontend JS Web Scripts", frontendJsWebJavascriptFiles.toArray(new VirtualFile[frontendJsWebJavascriptFiles.size()]), true));
            }

            Set<VirtualFile> frontendJsAuiWebJavascriptFiles = new HashSet<VirtualFile>();
            for (Library frontendJsAuiWebLibrary : frontendJsAuiWebLibraries) {
                addJavascriptFilesFromLibrary(frontendJsAuiWebLibrary, frontendJsAuiWebJavascriptFiles);
            }
            if (! (frontendJsAuiWebJavascriptFiles.isEmpty()) ) {
                result.add(ScriptingLibraryModel.createPredefinedLibrary("Liferay Frontend JS AUI Web Scripts", frontendJsAuiWebJavascriptFiles.toArray(new VirtualFile[frontendJsAuiWebJavascriptFiles.size()]), true));
            }

            return result.toArray(new ScriptingLibraryModel[result.size()]);
        }

        return new ScriptingLibraryModel[0];
    }

    private void addJavascriptFilesFromLibrary(Library library, final Set<VirtualFile> result) {
        VirtualFile[] files = library.getFiles(OrderRootType.CLASSES);
        for (VirtualFile file : files) {
            VirtualFileSystem virtualFileSystem = file.getFileSystem();
            if (virtualFileSystem instanceof JarFileSystem) {
                JarFileSystem jarFileSystem = (JarFileSystem) virtualFileSystem;

                VirtualFile root = jarFileSystem.getRootByEntry(file);

                if (root != null) {
                    VirtualFile[] children = root.getChildren();
                    for (VirtualFile child : children) {
                        if (child.isDirectory()) {
                            addJavascriptFilesFromDirectory(child, result);
                        }
                    }
                }
            }
        }
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
                        result.add(file);
                    }

                    return true;
                }
            }
        );

    }

}
