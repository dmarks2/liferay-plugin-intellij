package de.dm.intellij.liferay.library;

import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.roots.OrderRootType;
import com.intellij.openapi.roots.libraries.ui.AttachRootButtonDescriptor;
import com.intellij.openapi.roots.libraries.ui.RootDetector;
import com.intellij.openapi.roots.ui.configuration.libraryEditor.DefaultLibraryRootsComponentDescriptor;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileVisitor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class LiferayLibraryRootsComponentDescriptor extends DefaultLibraryRootsComponentDescriptor {

    @NotNull
    @Override
    public List<? extends RootDetector> getRootDetectors() {
        return Arrays.asList(new RootDetector[]{new LiferayRootDetector(OrderRootType.CLASSES)});
    }

    @NotNull
    @Override
    public List<? extends AttachRootButtonDescriptor> createAttachButtons() {
        //TODO Attach Sources
        //TODO Attach Javadoc
        return Collections.emptyList();
    }

    private class LiferayRootDetector extends RootDetector {

        public LiferayRootDetector(OrderRootType rootType) {
            super(rootType, true, "classes");
        }

        @NotNull
        @Override
        public Collection<VirtualFile> detectRoots(@NotNull final VirtualFile rootCandidate, @NotNull final ProgressIndicator progressIndicator) {
            final List<VirtualFile> result = new ArrayList<>();

                VfsUtilCore.visitChildrenRecursively(rootCandidate, new VirtualFileVisitor(new VirtualFileVisitor.Option[0]) {
                    public boolean visitFile(@NotNull VirtualFile file) {
                        if (file.isDirectory()) {
                            progressIndicator.setText2(file.getPath());
                            progressIndicator.checkCanceled();
                        } else if ("jar".equals(file.getExtension())) {
                            //TODO add only specific jars (not all)
                            //TODO add jars inside marketplace lpkg files
                            //TODO does add jars and not classes inside as library root ??

                            //final VirtualFile jarRoot = JarFileSystem.getInstance().getJarRootForLocalFile(file);

                            result.add(file);
                        }

                        return true;
                    }
                });

            return result;
        }
    }
}
