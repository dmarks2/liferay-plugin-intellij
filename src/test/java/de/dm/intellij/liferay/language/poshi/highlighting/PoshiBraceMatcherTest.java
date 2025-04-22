package de.dm.intellij.liferay.language.poshi.highlighting;

import com.intellij.openapi.vfs.newvfs.impl.VfsRootAccess;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.intellij.util.PathUtil;

import java.io.File;

public class PoshiBraceMatcherTest extends BasePlatformTestCase {

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/poshi/highlighting/PoshiBraceMatcherTest";
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        File testDataDir = new File(myFixture.getTestDataPath());

        final String testDataPath = PathUtil.toSystemIndependentName(testDataDir.getAbsolutePath());

        VfsRootAccess.allowRootAccess(myFixture.getTestRootDisposable(), testDataPath);
    }

    public void testBraceMatcherPoshiTestcaseFile() {
        myFixture.configureByFiles("testcases/Liferay.testcase");

        myFixture.type("{");

        myFixture.checkResultByFile("testcases/expected.testcase");
    }
}
