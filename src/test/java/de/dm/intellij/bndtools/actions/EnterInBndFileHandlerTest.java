package de.dm.intellij.bndtools.actions;

import com.intellij.openapi.vfs.newvfs.impl.VfsRootAccess;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.intellij.util.PathUtil;

import java.io.File;

public class EnterInBndFileHandlerTest extends BasePlatformTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/bndtools/actions/EnterInBndFileHandlerTest";

    @Override
    protected String getTestDataPath() {
        return TEST_DATA_PATH;
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        File testDataDir = new File(myFixture.getTestDataPath());

        final String testDataPath = PathUtil.toSystemIndependentName(testDataDir.getAbsolutePath());

        VfsRootAccess.allowRootAccess(myFixture.getTestRootDisposable(), testDataPath);
    }

    public void testEnterInBndFileHandler() {
        myFixture.configureByFiles("bnd.bnd");

        myFixture.type('\n');

        myFixture.checkResultByFile("updated.bnd");
    }

}
