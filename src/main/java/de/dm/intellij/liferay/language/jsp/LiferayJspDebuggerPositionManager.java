package de.dm.intellij.liferay.language.jsp;

import com.intellij.debugger.DebuggerBundle;
import com.intellij.debugger.NoDataException;
import com.intellij.debugger.SourcePosition;
import com.intellij.debugger.engine.DebugProcess;
import com.intellij.debugger.engine.JSR45PositionManager;
import com.intellij.javaee.facet.JavaeeFacet;
import com.intellij.javaee.facet.JavaeeFacetUtil;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.util.Computable;
import com.intellij.psi.PsiFile;
import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.ClassNotPreparedException;
import com.sun.jdi.Location;
import com.sun.jdi.ObjectCollectedException;
import com.sun.jdi.ReferenceType;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class LiferayJspDebuggerPositionManager extends JSR45PositionManager<JavaeeFacet[]> {

    public LiferayJspDebuggerPositionManager(DebugProcess myDebugProcess) {
        this(myDebugProcess, JavaeeFacetUtil.getInstance().getAllJavaeeFacets(myDebugProcess.getProject()));
    }

    public LiferayJspDebuggerPositionManager(DebugProcess myDebugProcess, JavaeeFacet[] scope) {
        super(myDebugProcess, scope, "JSP", new LanguageFileType[]{StdFileTypes.JSP, StdFileTypes.JSPX}, new LiferayJspDebuggerSourceFinderAdapter());
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

                    LiferayJspDebuggerSourceFinderAdapter liferaySourceFinderAdapter = (LiferayJspDebuggerSourceFinderAdapter)mySourcesFinder;

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
                    //TODO moved to JavaDebuggerBundle in 2020.1
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

}
