package de.dm.intellij.liferay.language.gulp;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.ModifiableModelsProvider;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.SourceFolder;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

import java.util.Arrays;

public class LiferayGulpfileParserTest extends BasePlatformTestCase {

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/gulp/LiferayGulpfileParserTest";
    }

    public void testGulpfileParser() {
        myFixture.configureByFiles("gulpfile.js", "mysrc/test.txt");

        Module module = myFixture.getModule();

        ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(module);

        VirtualFile[] sourceRoots = moduleRootManager.getSourceRoots();

        assertTrue(sourceRoots.length > 0);

        assertTrue(
                Arrays.stream(sourceRoots).anyMatch(
                        virtualFile -> virtualFile.getPath().endsWith("mysrc")
                )
        );
    }

    @Override
    protected void tearDown() throws Exception {
        Module module = myFixture.getModule();

        ModuleRootManager moduleRootManager = ModuleRootManager.getInstance(module);

        VirtualFile[] sourceRoots = moduleRootManager.getSourceRoots();

        final ModifiableModelsProvider modelsProvider = ModifiableModelsProvider.getInstance();
        final ModifiableRootModel model = modelsProvider.getModuleModifiableModel(module);

        ApplicationManager.getApplication().runWriteAction(new Runnable() {

            @Override
            public void run() {
                ContentEntry[] contentEntries = model.getContentEntries();
                if (contentEntries.length > 0) {
                    ContentEntry contentEntry = contentEntries[0];
                    SourceFolder sourceFolder = Arrays.stream(contentEntry.getSourceFolders()).filter(folder -> folder.getFile().getName().endsWith("mysrc")).findFirst().orElse(null);
                    if (sourceFolder != null) {
                        contentEntry.removeSourceFolder(sourceFolder);
                    }
                }
                modelsProvider.commitModuleModifiableModel(model);
            }
        });

        super.tearDown();
    }
}
