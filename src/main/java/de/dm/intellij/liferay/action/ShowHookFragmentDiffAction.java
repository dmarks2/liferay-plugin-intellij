package de.dm.intellij.liferay.action;

import com.intellij.diff.DiffManager;
import com.intellij.diff.DiffRequestFactory;
import com.intellij.diff.actions.CompareFilesAction;
import com.intellij.diff.requests.DiffRequest;
import com.intellij.jsp.highlighter.NewJspFileType;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.OrderRootType;
import com.intellij.openapi.roots.libraries.Library;
import com.intellij.openapi.vfs.JarFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileSystem;
import de.dm.intellij.liferay.language.jsp.LiferayCustomJspBagUtil;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import de.dm.intellij.liferay.util.ProjectUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;

public class ShowHookFragmentDiffAction extends CompareFilesAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();

        DiffRequest diffRequest = getDiffRequest(e);
        if (diffRequest == null) {
            return;
        }

        DiffManager.getInstance().showDiff(project, diffRequest);

    }

    @Override
    public void update(AnActionEvent e) {
        Presentation presentation = e.getPresentation();
        boolean isAvailable = isAvailable(e);
        presentation.setEnabled(isAvailable);
        if (ActionPlaces.isPopupPlace(e.getPlace())) {
            presentation.setVisible(isAvailable);
        }
    }

    @Override
    protected DiffRequest getDiffRequest(@NotNull AnActionEvent e) {
        Project project = e.getProject();

        VirtualFile selectedFile = getSelectedFile(e);
        VirtualFile originalFile = getOriginalFile(e);

        if ((selectedFile != null) && (originalFile != null)) {
            return DiffRequestFactory.getInstance().createFromFiles(project, selectedFile, originalFile);
        }

        return null;
    }

    @Override
    protected boolean isAvailable(@NotNull AnActionEvent e) {
        VirtualFile selectedFile = getSelectedFile(e);
        if (selectedFile == null) {
            return false;
        }
        if (NewJspFileType.INSTANCE.equals(selectedFile.getFileType())) {
            VirtualFile originalFile = getOriginalFile(e);

            return originalFile != null;
        }

        return false;
    }

    @Nullable
    private static VirtualFile getSelectedFile(@NotNull AnActionEvent e) {
        VirtualFile[] array = e.getData(CommonDataKeys.VIRTUAL_FILE_ARRAY);
        if (array == null || array.length != 1 || array[0].isDirectory()) {
            return null;
        }

        return array[0];
    }

    @Nullable
    private static VirtualFile getOriginalFile(@NotNull AnActionEvent e) {
        Project project = e.getProject();

        if (project != null) {
            VirtualFile selectedFile = getSelectedFile(e);
            if (selectedFile != null) {
                Module module = ModuleUtil.findModuleForFile(selectedFile, project);
                if ((module != null) && (!(module.isDisposed()))) {

                    Collection<String> webRootsRelativePaths = LiferayFileUtil.getWebRootsRelativePaths(module, selectedFile);

                    String fragmentHostPackageName = LiferayModuleComponent.getOsgiFragmentHostPackageName(module);

                    String customJspDir = LiferayFileUtil.getCustomJspDir(module);
                    if ((customJspDir != null) && (customJspDir.startsWith("/")) && customJspDir.length() > 1) {
                        customJspDir = customJspDir.substring(1);
                    }

					Collection<Library> libraries = new ArrayList<>(ProjectUtils.findLibrariesByName("com.liferay.portal:portal-web", module));

                    if (fragmentHostPackageName != null) {
                        libraries.addAll(ProjectUtils.findLibrariesByName(/*"com.liferay:" +*/ fragmentHostPackageName, module));
                    }

                    if (LiferayCustomJspBagUtil.hasCustomJspBags(module)) {
                        libraries.addAll(ProjectUtils.findLibrariesByName("com.liferay.portal:com.liferay.portal.web", module));
                    }

                    for (Library library : libraries) {
                        VirtualFile[] files = library.getFiles(OrderRootType.CLASSES);
                        for (VirtualFile file : files) {
                            VirtualFileSystem virtualFileSystem = file.getFileSystem();
                            if (virtualFileSystem instanceof JarFileSystem jarFileSystem) {
								for (String relativePath : webRootsRelativePaths) {
                                    VirtualFile virtualFile;
                                    if (fragmentHostPackageName != null) {
                                        virtualFile = jarFileSystem.findFileByPath(file.getPath() + "META-INF/resources/" + relativePath);
                                    } else {
                                        if ((customJspDir != null) && (!customJspDir.isEmpty())) {
                                            if (relativePath.startsWith(customJspDir)) {
                                                relativePath = relativePath.substring((customJspDir + "/").length());
                                            }
                                        }
                                        virtualFile = jarFileSystem.findFileByPath(file.getPath() + relativePath);
                                    }

                                    if (virtualFile != null) {
                                        return virtualFile;
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
}
