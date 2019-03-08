package de.dm.intellij.liferay.language.jsp;

import com.intellij.debugger.DebuggerBundle;
import com.intellij.debugger.NoDataException;
import com.intellij.debugger.SourcePosition;
import com.intellij.debugger.engine.DebugProcess;
import com.intellij.debugger.engine.JSR45PositionManager;
import com.intellij.debugger.engine.SourcesFinder;
import com.intellij.javaee.deployment.JspDeploymentManager;
import com.intellij.javaee.facet.JavaeeFacet;
import com.intellij.javaee.facet.JavaeeFacetUtil;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileTypes.FileNameMatcher;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.OrderRootType;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.ClassNotPreparedException;
import com.sun.jdi.Location;
import com.sun.jdi.ObjectCollectedException;
import com.sun.jdi.ReferenceType;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class LiferayJspDebuggerPositionManager extends JSR45PositionManager<JavaeeFacet[]> {

    public LiferayJspDebuggerPositionManager(DebugProcess myDebugProcess) {
        this(myDebugProcess, JavaeeFacetUtil.getInstance().getAllJavaeeFacets(myDebugProcess.getProject()));
    }

    public LiferayJspDebuggerPositionManager(DebugProcess myDebugProcess, JavaeeFacet[] scope) {
        super(myDebugProcess, scope, "JSP", new LanguageFileType[]{StdFileTypes.JSP, StdFileTypes.JSPX}, new LiferayJspDebuggerPositionManager.SourceFinderAdapter());
    }

    @Override
    protected String getGeneratedClassesPackage() {
        return "org.apache.jsp";
    }

    @NotNull
    @Override
    public List<Location> locationsOfLine(@NotNull ReferenceType type, @NotNull SourcePosition position) throws NoDataException {
        List<Location> locations = locationsOfClassAt(type, position);
        return locations != null ? locations : Collections.emptyList();
    }

    private List<Location> locationsOfClassAt(final ReferenceType type, final SourcePosition position) throws NoDataException {
        checkSourcePositionFileType(position);

        return ApplicationManager.getApplication().runReadAction(new Computable<List<Location>>() {
            @Override
            public List<Location> compute() {
                try {
                    final List<String> relativePaths = getRelativeSourePathsByType(type);

                    LiferayJspDebuggerPositionManager.SourceFinderAdapter liferaySourceFinderAdapter = (LiferayJspDebuggerPositionManager.SourceFinderAdapter)mySourcesFinder;

                    for (String relativePath : relativePaths) {
                        final Collection<PsiFile> files = liferaySourceFinderAdapter.findSourceFiles(relativePath, myDebugProcess.getProject(), myScope);
                        for (PsiFile file : files) {
                            if (file != null && file.equals(position.getFile())) {
                                return getLocationsOfLine(type, getSourceName(file.getName(), type), relativePath, position.getLine() + 1);
                            }
                        }
                    }
                }
                catch(ObjectCollectedException | ClassNotPreparedException | AbsentInformationException ignored) {
                }
                catch (InternalError ignored) {
                    myDebugProcess.printToConsole(
                        DebuggerBundle.message("internal.error.locations.of.line", type.name()));
                }
                return null;
            }

            // Finds exact server file name (from available in type)
            // This is needed because some servers (e.g. WebSphere) put not exact file name such as 'A.jsp  '
            private String getSourceName(final String name, final ReferenceType type) throws AbsentInformationException {
                return type.sourceNames(getStratumId()).stream()
                    .filter(sourceNameFromType -> sourceNameFromType.contains(name))
                    .findFirst().orElse(name);
            }
        });
    }

    private void checkSourcePositionFileType(final SourcePosition classPosition) throws NoDataException {
        final FileType fileType = classPosition.getFile().getFileType();
        if(!getAcceptedFileTypes().contains(fileType)) {
            throw NoDataException.INSTANCE;
        }
    }

    private static final class SourceFinderAdapter implements SourcesFinder<JavaeeFacet[]> {
        private final JspDeploymentManager myJspDeploymentManager = JspDeploymentManager.getInstance();

        SourceFinderAdapter() {
        }

        @NotNull
        public Collection<PsiFile> findSourceFiles(String relPath, Project project, JavaeeFacet[] scope) {
            Collection<PsiFile> results = new ArrayList<>();

            if (isJava(relPath)) {
                return results;
            }

            //If you create a local Tomcat Run Configuration, JSPs inside "Web Context" have been found already, so this LifereayJspDebuggerPositionManager is not called at all.
            //However if you use a different Run Configuration (e.g. Command Line starting of Tomcat or a Remote Debugger Connection to a running Tomcat instance) the "Web Contexts" are not searched.
            //Therefore this PositionManager looks at the "Web Context" in this case, too.
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
            Iterator var2 = FileTypeManager.getInstance().getAssociations(StdFileTypes.JAVA).iterator();

            FileNameMatcher matcher;
            do {
                if (!var2.hasNext()) {
                    return false;
                }

                matcher = (FileNameMatcher)var2.next();
            } while(!matcher.accept(relPath));

            return true;
        }
    }

}
