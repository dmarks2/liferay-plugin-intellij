package de.dm.intellij.liferay.language.jsp;

import com.intellij.facet.impl.FacetUtil;
import com.intellij.javaee.web.WebRoot;
import com.intellij.javaee.web.facet.WebFacet;
import com.intellij.javaee.web.facet.WebFacetType;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileEvent;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiManager;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import de.dm.intellij.liferay.util.ProjectUtils;

import java.util.Collection;
import java.util.List;

/**
 * VirtualFileListener that watches files in META-INF/resources.
 *
 * If a directory META-INF/resources containing at least one file is present, this class adds a WebFacet to the project. The WebFacet defines the directory META-INF/resources as Web Root.
 *
 * That way, absolute paths in the JSPs are resolved correctly.
 *
 * The same is done for each CustomJspBag in the project.
 */
public class LiferayJspWebContentRootListener {

    private static final Logger log = com.intellij.openapi.diagnostic.Logger.getInstance(LiferayJspWebContentRootListener.class.getName());

    public static void handleChange(final Project project, VirtualFileEvent event) {
        final VirtualFile virtualFile = event.getFile();
        if (virtualFile != null) {
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
                        if (module != null) {
                            addWebFacet(resources, parent, module);
                        }
                    }
                } else {
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            if (virtualFile.isValid()) {
                                //TODO psiFile is empty on "contentsChanged". Need to be refreshed once...
                                PsiFile psiFile = PsiManager.getInstance(project).findFile(virtualFile);
                                if (psiFile instanceof PsiJavaFile) {
                                    PsiJavaFile psiJavaFile = (PsiJavaFile) psiFile;
                                    String customJspDir = LiferayCustomJspBagUtil.getCustomJspDir(psiJavaFile);
                                    if (customJspDir != null) {
                                        ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(module);

                                        for (VirtualFile sourceRoot : moduleRootManager.getSourceRoots()) {
                                            VirtualFile relativePath = sourceRoot.findFileByRelativePath(customJspDir);
                                            if (relativePath != null) {
                                                if (LiferayFileUtil.isParent(relativePath, sourceRoot)) {
                                                    addWebFacet(relativePath, sourceRoot, module);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    };

                    ProjectUtils.runDumbAwareLater(project, runnable);
                }
            }
        }
    }

    private static void addWebFacet(VirtualFile resources, VirtualFile parent, Module module) {
        ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(module);

        //Add web facet only if at <source-root>/<dir>
        for (VirtualFile sourceRoot : moduleRootManager.getSourceRoots()) {
            if (sourceRoot.equals(parent)) {
                boolean facetPresent = false;
                Collection<WebFacet> webFacets = WebFacet.getInstances(module);

                outer:
                for (WebFacet webFacet : webFacets) {
                    List<WebRoot> webRoots = webFacet.getWebRoots();
                    for (WebRoot webRoot : webRoots) {
                        if ( (webRoot.getFile() != null) && (webRoot.getFile().equals(resources)) ) {
                            facetPresent = true;
                            break outer;
                        }
                    }
                }

                if (!facetPresent) {
                    final WebFacet webFacet = FacetUtil.addFacet(module, WebFacetType.getInstance());
                    webFacet.addWebRoot(resources, "/");
                }

                break;
            }
        }
    }

}
