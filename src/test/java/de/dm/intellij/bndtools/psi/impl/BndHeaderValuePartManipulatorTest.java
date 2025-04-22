package de.dm.intellij.bndtools.psi.impl;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.LanguageLevelModuleExtension;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.vfs.newvfs.impl.VfsRootAccess;
import com.intellij.pom.java.LanguageLevel;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.DefaultLightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import com.intellij.util.PathUtil;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * @author Dominik Marks
 */
public class BndHeaderValuePartManipulatorTest extends LightJavaCodeInsightFixtureTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        File testDataDir = new File(myFixture.getTestDataPath());

        final String testDataPath = PathUtil.toSystemIndependentName(testDataDir.getAbsolutePath());

        VfsRootAccess.allowRootAccess(myFixture.getTestRootDisposable(), testDataPath);
    }

    public void testRenamePackageInsideBnd() {
        myFixture.configureByFiles("testRenamePackageInsideBnd/bnd.bnd", "com/liferay/test/Foo.java");

        myFixture.renameElementAtCaret("renamed");

        myFixture.checkResultByFile("com/liferay/renamed/Foo.java", "com/liferay/test/Foo_Renamed.java", false);
    }

    public void testRenamePackageInsideBndFormatted() {
        myFixture.configureByFiles("testRenamePackageInsideBndFormatted/bnd.bnd", "com/liferay/test/Foo.java");

        myFixture.renameElementAtCaret("renamed");

        myFixture.checkResultByFile("testRenamePackageInsideBndFormatted/bnd.bnd", "testRenamePackageInsideBndFormatted/bnd_renamed.bnd", false);
    }

    public void testRenamePackageInsideClass() {
        myFixture.configureByFiles("com/liferay/test/Bar.java", "testRenamePackageInsideClass/bnd.bnd");

        myFixture.renameElementAtCaret("renamed");

        myFixture.checkResultByFile("testRenamePackageInsideClass/bnd.bnd", "testRenamePackageInsideClass/bnd_renamed.bnd", false);
    }

    public void testRenameClassInsideBnd() {
        myFixture.configureByFiles("testRenameClassInsideBnd/bnd.bnd", "com/liferay/test/Foo.java");

        myFixture.renameElementAtCaret("Bar");

        myFixture.checkResultByFile("com/liferay/test/Bar.java", "com/liferay/test/Bar_Renamed.java", false);
    }

    public void testRenameClassInsideClass() {
        myFixture.configureByFiles("com/liferay/test/Baz.java", "testRenameClassInsideClass/bnd.bnd");

        myFixture.renameElementAtCaret("Foo");

        myFixture.checkResultByFile(
                "testRenameClassInsideClass/bnd.bnd", "testRenameClassInsideClass/bnd_renamed.bnd", false);
    }

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor() {
        return _JAVA_DESCRIPTOR;
    }

    @Override
    protected String getTestDataPath() {
        return _TEST_DATA_PATH;
    }

    private static final LightProjectDescriptor _JAVA_DESCRIPTOR = new DefaultLightProjectDescriptor() {

        @Override
        public void configureModule(
                @NotNull Module module, @NotNull ModifiableRootModel modifiableRootModel,
                @NotNull ContentEntry contentEntry) {

            LanguageLevelModuleExtension extension = modifiableRootModel.getModuleExtension(
                    LanguageLevelModuleExtension.class);

            if (extension != null) {
                extension.setLanguageLevel(LanguageLevel.JDK_1_8);
            }

            File testDataDir = new File(_TEST_DATA_PATH);

            final String testDataPath = PathUtil.toSystemIndependentName(testDataDir.getAbsolutePath());

            Disposable disposable = Disposer.newDisposable();
            try {
                VfsRootAccess.allowRootAccess(disposable, testDataPath);
            } finally {
                Disposer.dispose(disposable);
            }
        }

    };

    private static final String _TEST_DATA_PATH = "testdata/de/dm/intellij/bndtools/psi/impl/BndHeaderValuePartManipulatorTest";

}
