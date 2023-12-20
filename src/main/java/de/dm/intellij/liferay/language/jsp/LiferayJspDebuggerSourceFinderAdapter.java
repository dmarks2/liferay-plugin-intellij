package de.dm.intellij.liferay.language.jsp;

import com.intellij.debugger.engine.SourcesFinder;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.javaee.facet.JavaeeFacet;
import com.intellij.javaee.web.deployment.JspDeploymentManager;
import com.intellij.openapi.fileTypes.FileNameMatcher;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.OrderRootType;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class LiferayJspDebuggerSourceFinderAdapter implements SourcesFinder<JavaeeFacet[]> {
    private final JspDeploymentManager myJspDeploymentManager = JspDeploymentManager.getInstance();

    @NotNull
    public Collection<PsiFile> findSourceFiles(String relPath, Project project, JavaeeFacet[] scope) {
        Collection<PsiFile> results = new ArrayList<>();

        if (project.isDisposed()) {
            return results;
        }

        if (isJava(relPath)) {
            return results;
        }

        //If you create a local Tomcat Run Configuration, JSPs inside "Web Context" have been found already, so this LiferayJspDebuggerPositionManager is not called at all.
        //However, if you use a different Run Configuration (e.g. Command Line starting of Tomcat or a Remote Debugger Connection to a running Tomcat instance) the "Web Contexts" are not searched.
        //Therefore, this PositionManager looks at the "Web Context" in this case, too.
        PsiFile deployedJspSourceFromFacets = this.myJspDeploymentManager.getDeployedJspSource(relPath, project, scope);
        if (deployedJspSourceFromFacets != null) {
            results.add(deployedJspSourceFromFacets);
        }

        ProjectRootManager.getInstance(project).orderEntries().forEachLibrary(
            library -> {
                VirtualFile[] files = library.getFiles(OrderRootType.CLASSES);
                for (VirtualFile file : files) {
                    VirtualFile root = LiferayFileUtil.getJarRoot(file);
                    if (root != null) {
                        VirtualFile child = LiferayFileUtil.getChild(root, "META-INF/resources");
                        if (child != null) {
                            VirtualFile virtualFile = LiferayFileUtil.getChild(child, relPath);
                            if (virtualFile != null) {
                                PsiFile targetFile = PsiManager.getInstance(project).findFile(virtualFile);
                                if (targetFile != null) {
                                    results.add(targetFile);
                                }
                            }
                        }
                    }
                }
                return true;
            }
        );

        return results;
    }

    @Nullable
    public PsiFile findSourceFile(String relPath, Project project, JavaeeFacet[] scope) {
        Collection<PsiFile> results = findSourceFiles(relPath, project, scope);

        if (! results.isEmpty()) {
            return results.iterator().next();
        }

        return null;
    }

    private boolean isJava(String relPath) {
        Iterator<FileNameMatcher> iterator = FileTypeManager.getInstance().getAssociations(JavaFileType.INSTANCE).iterator();

        FileNameMatcher matcher;
        do {
            if (!iterator.hasNext()) {
                return false;
            }

            matcher = iterator.next();
        } while(!matcher.acceptsCharSequence(relPath));

        return true;
    }
}
