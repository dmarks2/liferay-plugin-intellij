package de.dm.intellij.liferay.language.javascript;

import com.intellij.ProjectTopics;
import com.intellij.lang.javascript.library.JSLibraryManager;
import com.intellij.lang.javascript.library.JSPredefinedLibraryProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootEvent;
import com.intellij.openapi.roots.ModuleRootListener;
import com.intellij.openapi.roots.OrderRootType;
import com.intellij.openapi.roots.libraries.Library;
import com.intellij.openapi.vfs.JarFileSystem;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileSystem;
import com.intellij.openapi.vfs.VirtualFileVisitor;
import com.intellij.webcore.libraries.ScriptingLibraryModel;
import de.dm.intellij.liferay.util.ProjectUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Provides a predefined JavaScript Library for AlloyUI 1.5, 2.0 and 3.0 and Liferay Barebone JS files
 */
public class AlloyUIPredefinedLibraryProvider extends JSPredefinedLibraryProvider {

    private static final String PORTAL_WEB = "com.liferay.portal:portal-web";

    private static final String FRONTEND_JS_WEB = "com.liferay:com.liferay.frontend.js.web";
    private static final String FRONTEND_JS_AUI_WEB = "com.liferay:com.liferay.frontend.js.aui.web";

    private static boolean rootsChangeListenerRegistered = false;

    private static final Predicate<VirtualFile> JS_LIFERAY_6_X_FILTER = file ->  {
        String extension = file.getExtension();

        if ("js".equals(extension)) {
            if (! ( file.getName().endsWith("-min.js") || file.getName().endsWith("-min-debug.js") || file.getName().endsWith("-coverage.js") ) ) {

                String parentName = null;
                String grandParentName = null;

                VirtualFile parent = file.getParent();
                if (parent != null) {
                    parentName = parent.getName();

                    VirtualFile grandParent = parent.getParent();

                    if (grandParent != null) {
                        grandParentName = grandParent.getName();
                    }
                }

                if ("liferay".equals(parentName)) {
                    return true;
                }
                if ("aui".equals(grandParentName)) {
                    return true;
                }
            }
        }

        return false;
    };

    private static final Predicate<VirtualFile> JS_LIFERAY_7_0_FILTER = file ->  {
        String extension = file.getExtension();

        if ("js".equals(extension)) {
            if (! ( file.getName().endsWith("-min.js") || file.getName().endsWith("-min-debug.js") || file.getName().endsWith("-coverage.js") ) ) {
                return true;
            }
        }

        return false;
    };


    @NotNull
    @Override
    public ScriptingLibraryModel[] getPredefinedLibraries(@NotNull Project project) {
        Collection<ScriptingLibraryModel> result = new ArrayList<>();

        Collection<Library> portalWebLibraries = ProjectUtils.findLibrariesByName(PORTAL_WEB, project);

        Collection<Library> frontendJsWebLibraries = ProjectUtils.findLibrariesByName(FRONTEND_JS_WEB, project);
        Collection<Library> frontendJsAuiWebLibraries = ProjectUtils.findLibrariesByName(FRONTEND_JS_AUI_WEB, project);

        if (portalWebLibraries.size() > 0) {
            Library portalWebLibrary = portalWebLibraries.iterator().next();

            addScriptingModelFromLibrary(portalWebLibrary, "Liferay Portal Web Scripts", result, JS_LIFERAY_6_X_FILTER);
        }

        if (frontendJsWebLibraries.size() > 0) {
            Library frontendJsWebLibrary = frontendJsWebLibraries.iterator().next();

            addScriptingModelFromLibrary(frontendJsWebLibrary, "Liferay Frontend JS Web Scripts", result, JS_LIFERAY_7_0_FILTER);
        }

        if (frontendJsAuiWebLibraries.size() > 0) {
            Library frontendJsAuiWebLibrary = frontendJsAuiWebLibraries.iterator().next();

            addScriptingModelFromLibrary(frontendJsAuiWebLibrary, "Liferay Frontend JS AUI Web Scripts", result, JS_LIFERAY_7_0_FILTER);
        }

        if (!rootsChangeListenerRegistered) {
            project.getMessageBus().connect().subscribe(ProjectTopics.PROJECT_ROOTS, new ModuleRootListener() {
                @Override
                public void rootsChanged(ModuleRootEvent event) {
                    ProjectUtils.runDumbAwareLater(project, () -> JSLibraryManager.getInstance(project).rebuildPredefinedLibraryFilesAndGet());
                }
            });

            rootsChangeListenerRegistered = true;
        }

        return result.toArray(new ScriptingLibraryModel[result.size()]);
    }

    private void addScriptingModelFromLibrary(Library library, String name, final Collection<ScriptingLibraryModel> result, Predicate<VirtualFile> includeFile) {
        Set<VirtualFile> virtualFiles = new HashSet<>();

        addJavascriptFilesFromLibrary(library, virtualFiles, includeFile);

        if (! virtualFiles.isEmpty()) {
            ScriptingLibraryModel scriptingLibraryModel = ScriptingLibraryModel.createPredefinedLibrary(name, virtualFiles.toArray(new VirtualFile[virtualFiles.size()]), true);

            result.add(scriptingLibraryModel);
        }
    }

    private void addJavascriptFilesFromLibrary(Library library, final Set<VirtualFile> result, Predicate<VirtualFile> includeFile) {
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
                            addJavascriptFilesFromDirectory(child, result, includeFile);
                        }
                    }
                }
            }
        }
    }

    private void addJavascriptFilesFromDirectory(VirtualFile directory, final Set<VirtualFile> result, Predicate<VirtualFile> includeFile) {
        VfsUtilCore.visitChildrenRecursively(directory,
                new VirtualFileVisitor() {
                    @Override
                    public boolean visitFile(@NotNull VirtualFile file) {

                        if (file.isDirectory()) {
                            return true;
                        }

                        if (includeFile.test(file)) {
                            result.add(file);
                        }
                        return true;
                    }
                }
        );

    }

}
