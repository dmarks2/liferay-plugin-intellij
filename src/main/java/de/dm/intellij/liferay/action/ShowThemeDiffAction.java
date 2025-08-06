package de.dm.intellij.liferay.action;

import com.intellij.diff.DiffManager;
import com.intellij.diff.DiffRequestFactory;
import com.intellij.diff.actions.CompareFilesAction;
import com.intellij.diff.requests.DiffRequest;
import com.intellij.javascript.nodejs.CompletionModuleInfo;
import com.intellij.javascript.nodejs.reference.NodeModuleManager;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.OrderRootType;
import com.intellij.openapi.roots.libraries.Library;
import com.intellij.openapi.vfs.JarFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileSystem;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
import de.dm.intellij.liferay.theme.LiferayPackageJSONParser;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import de.dm.intellij.liferay.util.ProjectUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class ShowThemeDiffAction extends CompareFilesAction {

    private final static Logger log = Logger.getInstance(ShowThemeDiffAction.class);

    @Override
    public void actionPerformed(AnActionEvent actionEvent) {
        Project project = actionEvent.getProject();

        DiffRequest diffRequest = getDiffRequest(actionEvent);
        if (diffRequest == null) {
            return;
        }

        DiffManager.getInstance().showDiff(project, diffRequest);
    }

    @Override
    public void update(AnActionEvent actionEvent) {
        Presentation presentation = actionEvent.getPresentation();
        boolean isAvailable = isAvailable(actionEvent);
        presentation.setEnabled(isAvailable);
		String place = actionEvent.getPlace();

		if (place.equals(ActionPlaces.POPUP)) {
			presentation.setVisible(isAvailable);
        }
    }

    @Override
    protected DiffRequest getDiffRequest(@NotNull AnActionEvent actionEvent) {
        Project project = actionEvent.getProject();

        VirtualFile selectedFile = getSelectedFile(actionEvent);
        VirtualFile originalFile = getOriginalFile(actionEvent);

        if ((selectedFile != null) && (originalFile != null)) {
            return DiffRequestFactory.getInstance().createFromFiles(project, selectedFile, originalFile);
        }

        return null;
    }

    @Override
    protected boolean isAvailable(@NotNull AnActionEvent actionEvent) {
        VirtualFile selectedFile = getSelectedFile(actionEvent);
        if (selectedFile == null) {
            return false;
        }

        Project project = actionEvent.getProject();

        if (project != null) {
            Module module = ModuleUtil.findModuleForFile(selectedFile, project);

            if ((module != null) && (!(module.isDisposed()))) {
                String parentTheme = LiferayModuleComponent.getParentTheme(module);

                if (parentTheme != null) {
                    VirtualFile originalFile = getOriginalFile(actionEvent);

                    return originalFile != null;
                }
            }
        }

        return false;
    }

    @Nullable
    private static VirtualFile getSelectedFile(@NotNull AnActionEvent actionEvent) {
        VirtualFile[] virtualFiles = actionEvent.getData(CommonDataKeys.VIRTUAL_FILE_ARRAY);
        if (virtualFiles == null || virtualFiles.length != 1 || virtualFiles[0].isDirectory()) {
            return null;
        }

        return virtualFiles[0];
    }

    @Nullable
    private static VirtualFile getOriginalFile(@NotNull AnActionEvent actionEvent) {
        Project project = actionEvent.getProject();

        VirtualFile selectedFile = getSelectedFile(actionEvent);

        if (selectedFile != null && project != null) {
            Module module = ModuleUtil.findModuleForFile(selectedFile, project);

            if ( (module != null) && (! (module.isDisposed())) ) {
                VirtualFile virtualFile = getOriginalFileFromNodeModules(module, selectedFile);

                if (virtualFile != null) {
                    return virtualFile;
                }

                return getOriginalFileFromJar(module, selectedFile);
            }
        }
        return null;
    }

    private static VirtualFile getOriginalFileFromNodeModules(Module module, VirtualFile selectedFile) {
        String parentTheme = LiferayModuleComponent.getParentTheme(module);

        if (log.isDebugEnabled()) {
            log.debug("(node_modules) parent theme is: " + parentTheme);
        }

        try {
            Collection<String> nodeModuleNames = collectNodeModules(parentTheme, module.getProject(), selectedFile);

            Collection<String> sourceRootRelativePaths = LiferayFileUtil.getSourceRootRelativePaths(module, selectedFile);

            for (String nodeModuleName : nodeModuleNames) {
                if (log.isDebugEnabled()) {
                    log.debug("examine node module " + nodeModuleName);
                }

                VirtualFile nodeModuleDirectory = getNodeModuleDirectory(nodeModuleName, module.getProject(), selectedFile);

                if (nodeModuleDirectory != null) {
                    if (log.isDebugEnabled()) {
                        log.debug("found node module " + nodeModuleDirectory);
                    }

                    for (String relativePath : sourceRootRelativePaths) {
                        VirtualFile virtualFile = nodeModuleDirectory.findFileByRelativePath(relativePath);  //build dir

                        if (log.isDebugEnabled()) {
                            log.debug("try to find " + relativePath + " in " + nodeModuleDirectory.getPath());
                        }

                        if (virtualFile == null) {
                            virtualFile = nodeModuleDirectory.findFileByRelativePath("src/" + relativePath);   //src dir

                            if (log.isDebugEnabled()) {
                                log.debug("try to find src/" + relativePath + " in " + nodeModuleDirectory.getPath());
                            }
                        }

                        if (virtualFile != null) {
                            return virtualFile;
                        }
                    }
                }
            }
        } catch (IOException ioException) {
            if (log.isDebugEnabled()) {
                log.debug("Unable to get parent themes: " + ioException.getMessage(), ioException);
            }
        }

        return null;
    }

    private static Collection<String> collectNodeModules(String parentTheme, Project project, VirtualFile requester) throws IOException {
        Collection<String> nodeModuleNames = new ArrayList<>();

        if ("unstyled".equals(parentTheme)) {
            nodeModuleNames.add("liferay-frontend-theme-unstyled");
        } else if ("styled".equals(parentTheme)) {
            nodeModuleNames.add("liferay-frontend-theme-styled");
            nodeModuleNames.add("liferay-frontend-theme-unstyled");
        } else {
            nodeModuleNames.add(parentTheme);

            VirtualFile nodeModuleDirectory = getNodeModuleDirectory(parentTheme, project, requester); // LiferayFileUtil.getChild(nodeModules, parentTheme);

            if (nodeModuleDirectory != null) {
                if (log.isDebugEnabled()) {
                    log.debug("found node module " + nodeModuleDirectory);
                }

                VirtualFile virtualFile = nodeModuleDirectory.findFileByRelativePath("package.json");

                if (virtualFile != null) {
                    LiferayPackageJSONParser.PackageJSONInfo packageJSONInfo = LiferayPackageJSONParser.getPackageJSONInfo(virtualFile);

                    if (packageJSONInfo != null && packageJSONInfo.baseTheme != null) {
                        nodeModuleNames.addAll(collectNodeModules(packageJSONInfo.baseTheme, project, requester));
                    }
                }
            }
        }

        return nodeModuleNames;
    }

    private static VirtualFile getNodeModuleDirectory(String nodeModuleName, Project project, VirtualFile requester) {
        NodeModuleManager nodeModuleManager = NodeModuleManager.getInstance(project);

        Collection<CompletionModuleInfo> completionModuleInfos = nodeModuleManager.collectVisibleNodeModules(requester);

        CompletionModuleInfo moduleInfo = completionModuleInfos.stream().filter(
                completionModuleInfo -> completionModuleInfo.getName().equals(nodeModuleName)
        ).findFirst().orElse(null);

        if (moduleInfo != null) {
            return moduleInfo.getVirtualFile();
        }

        return null;
    }

    private static VirtualFile getOriginalFileFromJar(Module module, VirtualFile selectedFile) {
        String parentTheme = LiferayModuleComponent.getParentTheme(module);

        if (log.isDebugEnabled()) {
            log.debug("(jar) parent theme is: " + parentTheme);
        }

        Collection<Library> libraries = new ArrayList<>();

        if ("unstyled".equals(parentTheme)) {
            libraries.addAll(ProjectUtils.findLibrariesByName("com.liferay:com.liferay.frontend.theme.unstyled", module));
        } else if ("styled".equals(parentTheme)) {
            libraries.addAll(ProjectUtils.findLibrariesByName("com.liferay:com.liferay.frontend.theme.styled", module));
            libraries.addAll(ProjectUtils.findLibrariesByName("com.liferay:com.liferay.frontend.theme.unstyled", module));
        } //others are not supported

        Collection<String> sourceRootRelativePaths = LiferayFileUtil.getSourceRootRelativePaths(module, selectedFile);

        for (Library library : libraries) {
            if (log.isDebugEnabled()) {
                log.debug("examine library: " + library);
            }

            VirtualFile[] files = library.getFiles(OrderRootType.CLASSES);
            for (VirtualFile file : files) {
                VirtualFileSystem virtualFileSystem = file.getFileSystem();
                if (virtualFileSystem instanceof JarFileSystem jarFileSystem) {
					for (String relativePath : sourceRootRelativePaths) {
                        VirtualFile virtualFile = jarFileSystem.findFileByPath(file.getPath() + "META-INF/resources/_" +parentTheme + "/" + relativePath);

                        if (log.isDebugEnabled()) {
                            log.debug("try to find " + file.getPath() + "META-INF/resources/_" +parentTheme + "/" + relativePath + " = " + virtualFile);
                        }

                        if (virtualFile != null) {
                            return virtualFile;
                        }
                    }
                }
            }
        }

        return null;
    }
}
