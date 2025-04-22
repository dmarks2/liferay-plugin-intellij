package de.dm.intellij.liferay.language.poshi.commenter;

import com.intellij.openapi.vfs.newvfs.impl.VfsRootAccess;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.intellij.util.PathUtil;

import java.io.File;

public class PoshiCommenterTest extends BasePlatformTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/liferay/language/poshi/commenter/PoshiCommenterTest";

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

    public void testToggleLineComment() {
        myFixture.configureByFiles("testcases/Liferay.testcase");

        myFixture.performEditorAction("CommentByLineComment");

        myFixture.checkResultByFile("testcases/Liferay_line_comment.testcase");
    }

    public void testToggleBlockComment() {
        myFixture.configureByFiles("testcases/Liferay.testcase");

        myFixture.performEditorAction("CommentByBlockComment");

        myFixture.checkResultByFile("testcases/Liferay_block_comment.testcase");
    }
}
