package de.dm.intellij.liferay.language.jsp;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.externalSystem.service.project.autoimport.FileChangeListenerBase;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiManager;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import de.dm.intellij.liferay.util.ProjectUtils;
import de.dm.intellij.liferay.util.WebFacetUtil;

/**
 * VirtualFileListener that watches files in META-INF/resources.
 *
 * If a directory META-INF/resources containing at least one file is present, this class adds a WebFacet to the project. The WebFacet defines the directory META-INF/resources as Web Root.
 *
 * That way, absolute paths in the JSPs are resolved correctly.
 *
 * The same is done for each CustomJspBag in the project.
 */
public class LiferayJspWebContentRootListener extends FileChangeListenerBase {

    private static final Logger log = com.intellij.openapi.diagnostic.Logger.getInstance(LiferayJspWebContentRootListener.class.getName());

    private static final ThreadLocal<Boolean> changeRunning = new ThreadLocal<>();

    public static void handleChange(final Project project, VirtualFile virtualFile) {
        if (Boolean.TRUE.equals(changeRunning.get())) {
            return;
        }

        final Module module = ModuleUtil.findModuleForFile(virtualFile, project);

        if (module != null) {
            VirtualFile resources;

            if ((virtualFile.isDirectory()) && ("resources".equals(virtualFile.getName()))) {
                resources = virtualFile;
            } else {
                resources = LiferayFileUtil.getParent(virtualFile, "resources");
            }
            if (resources != null) {
                VirtualFile metaInf = LiferayFileUtil.getParent(resources, "META-INF");

                if (metaInf != null) {
                    VirtualFile parent = metaInf.getParent();

                    changeRunning.set(true);

                    WebFacetUtil.addWebFacet(resources, parent, module, WebFacetUtil.LIFERAY_RESOURCES_WEB_FACET);

                    changeRunning.set(false);
                }
            } else {
                ProjectUtils.runDumbAwareLater(project, () -> {
                    if (virtualFile.isValid()) {
                        //TODO psiFile is empty on "contentsChanged". Need to be refreshed once...
                        PsiManager psiManager = PsiManager.getInstance(project);

                        PsiFile psiFile = psiManager.findFile(virtualFile);

                        if (psiFile instanceof PsiJavaFile) {
                            PsiJavaFile psiJavaFile = (PsiJavaFile) psiFile;

                            String customJspDir = LiferayCustomJspBagUtil.getCustomJspDir(psiJavaFile);

                            if (customJspDir != null) {
                                LiferayModuleComponent liferayModuleComponent = LiferayModuleComponent.getInstance(module);

                                if (liferayModuleComponent != null) {
                                    liferayModuleComponent.setCustomJspDir(customJspDir);
                                }

                                ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(module);

                                for (VirtualFile sourceRoot : moduleRootManager.getSourceRoots()) {
                                    VirtualFile relativePath = sourceRoot.findFileByRelativePath(customJspDir);

                                    if (relativePath != null) {
                                        if (LiferayFileUtil.isParent(relativePath, sourceRoot)) {
                                            changeRunning.set(true);

                                            WebFacetUtil.addWebFacet(relativePath, sourceRoot, module, WebFacetUtil.LIFERAY_CORE_WEB_FACET);

                                            changeRunning.set(false);
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
            }
        }
    }

    public static boolean isRelevantFile(String path) {
        return (! path.endsWith(".iml"));
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
