package de.dm.intellij.liferay.util;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.*;
import com.intellij.openapi.roots.impl.OrderEntryUtil;
import com.intellij.openapi.roots.libraries.Library;
import com.intellij.openapi.roots.libraries.LibraryTable;
import com.intellij.openapi.roots.libraries.LibraryTablesRegistrar;
import com.intellij.openapi.startup.StartupManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.DisposeAwareRunnable;
import com.intellij.util.Processor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class ProjectUtils {
    public static void runWhenInitialized(final Project project, final Runnable runnable) {
        if (project.isDisposed()) return;

        if (isNoBackgroundMode()) {
            runnable.run();
            return;
        }

        if (!project.isInitialized()) {
            StartupManager.getInstance(project).registerPostStartupActivity(DisposeAwareRunnable.create(runnable, project));
            return;
        }

        runDumbAware(project, runnable);
    }

    public static boolean isNoBackgroundMode() {
        return (ApplicationManager.getApplication().isUnitTestMode()
                || ApplicationManager.getApplication().isHeadlessEnvironment());
    }

    public static void runDumbAware(final Project project, final Runnable r) {
        if (DumbService.isDumbAware(r)) {
            r.run();
        } else {
            DumbService.getInstance(project).runWhenSmart(DisposeAwareRunnable.create(r, project));
        }
    }

    public static Collection<Library> findLibrariesByName(final String name, Module module) {
        final Collection<Library> result = new ArrayList<Library>();

        ModuleRootManager.getInstance(module).orderEntries().forEachLibrary(
                new Processor<Library>() {
                    @Override
                    public boolean process(Library library) {
                        if (library.getName().contains(name)) {
                            result.add(library);
                        }
                        return true;
                    }
                }
        );

        return result;
    }

    private static LibraryOrderEntry getLibraryIntern(String name, ModifiableRootModel model) {
        return OrderEntryUtil.findLibraryOrderEntry(model, name);
    }

    private static Library createLibraryIntern(@NotNull Project project, String name) {
        LibraryTable libraryTable = LibraryTablesRegistrar.getInstance().getLibraryTable(project);
        Library result = libraryTable.getLibraryByName(name);
        if (result == null) {
            result = libraryTable.createLibrary(name);
        }
        return result;
    }

    public static void removeLibrary(@NotNull final Module module, String name) {
        if (!module.isDisposed()) {
            final ModifiableModelsProvider modelsProvider = ModifiableModelsProvider.SERVICE.getInstance();
            final ModifiableRootModel model = modelsProvider.getModuleModifiableModel(module);

            final LibraryOrderEntry libraryEntry = getLibraryIntern(name, model);

            if (libraryEntry != null) {
                ApplicationManager.getApplication().runWriteAction(
                        new Runnable() {
                            @Override
                            public void run() {
                                Library library = libraryEntry.getLibrary();
                                if (library != null) {
                                    LibraryTable table = library.getTable();
                                    if (table != null) {
                                        table.removeLibrary(library);
                                        model.removeOrderEntry(libraryEntry);
                                        modelsProvider.commitModuleModifiableModel(model);
                                    }
                                } else {
                                    modelsProvider.disposeModuleModifiableModel(model);
                                }
                            }
                        }
                );
            } else {
                ApplicationManager.getApplication().runWriteAction(
                        new Runnable() {
                            @Override
                            public void run() {
                                modelsProvider.disposeModuleModifiableModel(model);
                            }
                        }
                );
            }
        }
    }

    private static void fillLibraryIntern(@NotNull Module module, @NotNull Library library, @NotNull Collection<? extends VirtualFile> importRoots) {
        Library.ModifiableModel libraryModel = library.getModifiableModel();

        //remove old entries
        for (String root : library.getUrls(OrderRootType.CLASSES)) {
            libraryModel.removeRoot(root, OrderRootType.CLASSES);
        }

        for (VirtualFile importRoot : importRoots) {
            if (!ModuleUtilCore.projectContainsFile(module.getProject(), importRoot, false)) {
                libraryModel.addRoot(importRoot.getUrl(), OrderRootType.CLASSES);
            }
        }
        libraryModel.commit();
    }

    public static void updateLibrary(@NotNull final Module module, final String name, @NotNull final Collection<? extends VirtualFile> importRoots) {
        if (!module.isDisposed()) {
            final ModifiableModelsProvider modelsProvider = ModifiableModelsProvider.SERVICE.getInstance();
            final ModifiableRootModel model = modelsProvider.getModuleModifiableModel(module);
            final LibraryOrderEntry libraryEntry = getLibraryIntern(name, model);
            ApplicationManager.getApplication().runWriteAction(
                    new Runnable() {
                        @Override
                        public void run() {
                            if (libraryEntry != null) {
                                Library library = libraryEntry.getLibrary();
                                if (library != null) {
                                    fillLibraryIntern(module, library, importRoots);
                                } else {
                                    model.removeOrderEntry(libraryEntry);
                                    library = createLibraryIntern(module.getProject(), name);
                                    fillLibraryIntern(module, library, importRoots);
                                }
                            } else {
                                Library library = createLibraryIntern(module.getProject(), name);
                                fillLibraryIntern(module, library, importRoots);
                                model.addLibraryEntry(library);
                            }

                            modelsProvider.commitModuleModifiableModel(model);
                        }
                    }
            );
        }
    }


}
