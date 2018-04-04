package de.dm.intellij.liferay.library;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.libraries.*;
import com.intellij.openapi.roots.libraries.ui.LibraryEditorComponent;
import com.intellij.openapi.roots.libraries.ui.LibraryPropertiesEditor;
import com.intellij.openapi.roots.libraries.ui.LibraryRootsComponentDescriptor;
import com.intellij.openapi.roots.ui.configuration.FacetsProvider;
import com.intellij.openapi.roots.ui.configuration.libraryEditor.DefaultLibraryRootsComponentDescriptor;
import com.intellij.openapi.vfs.VirtualFile;
import de.dm.intellij.liferay.util.Icons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class LiferayLibraryType extends LibraryType<DummyLibraryProperties> {

    public static final PersistentLibraryKind<DummyLibraryProperties> LIBRARY_KIND =
            new PersistentLibraryKind<DummyLibraryProperties>("Liferay") {
                @NotNull
                @Override
                public DummyLibraryProperties createDefaultProperties() {
                    return new DummyLibraryProperties();
                }
            };

    protected LiferayLibraryType() {
        super(LIBRARY_KIND);
    }

    public static LiferayLibraryType getInstance() {
        return LibraryType.EP_NAME.findExtension(LiferayLibraryType.class);
    }

    @Nullable
    @Override
    public String getCreateActionName() {
        return "Liferay Sources";
    }

    @Override
    public boolean isSuitableModule(@NotNull Module module, @NotNull FacetsProvider facetsProvider) {
        //TODO determine to which modules you can attach the library
        return super.isSuitableModule(module, facetsProvider);
    }

    @Nullable
    @Override
    public LibraryRootsComponentDescriptor createLibraryRootsComponentDescriptor() {
        //TODO describe the available roots (classes, sources, javadoc etc.)
        return new DefaultLibraryRootsComponentDescriptor();
    }

    @Nullable
    @Override
    public NewLibraryConfiguration createNewLibrary(@NotNull JComponent parentComponent, @Nullable VirtualFile contextDirectory, @NotNull Project project) {
        return LibraryTypeService.getInstance().createLibraryFromFiles(
                createLibraryRootsComponentDescriptor(),
                parentComponent,
                contextDirectory,
                this,
                project
        );
    }

    @Nullable
    @Override
    public LibraryPropertiesEditor createPropertiesEditor(@NotNull LibraryEditorComponent<DummyLibraryProperties> editorComponent) {
        return null;
    }

    // TODO API Change in 2016 to 2017
    /*
    @Nullable
    @Override
    public Icon getIcon(@Nullable LibraryProperties properties) {
        return Icons.LIFERAY_ICON;
    }
    */
}
