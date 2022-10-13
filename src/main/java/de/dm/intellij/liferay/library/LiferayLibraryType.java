package de.dm.intellij.liferay.library;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.libraries.*;
import com.intellij.openapi.roots.libraries.ui.LibraryEditorComponent;
import com.intellij.openapi.roots.libraries.ui.LibraryPropertiesEditor;
import com.intellij.openapi.roots.libraries.ui.LibraryRootsComponentDescriptor;
import com.intellij.openapi.roots.ui.configuration.FacetsProvider;
import com.intellij.openapi.vfs.VirtualFile;
import de.dm.intellij.liferay.util.Icons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class LiferayLibraryType extends LibraryType<LibraryProperties> {

    public static final PersistentLibraryKind<LibraryProperties> LIBRARY_KIND =
            new PersistentLibraryKind<>("Liferay") {
                @NotNull
                @Override
                public LibraryProperties createDefaultProperties() {
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
        return "Liferay sources";
    }

    @Override
    public boolean isSuitableModule(@NotNull Module module, @NotNull FacetsProvider facetsProvider) {
        //TODO determine to which modules you can attach the library
        return super.isSuitableModule(module, facetsProvider);
    }

    @Nullable
    @Override
    public LibraryRootsComponentDescriptor createLibraryRootsComponentDescriptor() {
        return new LiferayLibraryRootsComponentDescriptor();
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
    public LibraryPropertiesEditor createPropertiesEditor(@NotNull LibraryEditorComponent<LibraryProperties> editorComponent) {
        return null;
    }


    @Nullable
    @Override
    public Icon getIcon(@Nullable LibraryProperties properties) {
        return Icons.LIFERAY_ICON;
    }
}
