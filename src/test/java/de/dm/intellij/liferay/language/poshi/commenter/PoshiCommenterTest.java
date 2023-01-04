package de.dm.intellij.liferay.language.poshi.commenter;

import com.intellij.testFramework.fixtures.BasePlatformTestCase;

public class PoshiCommenterTest extends BasePlatformTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/liferay/language/poshi/commenter/PoshiCommenterTest";

    @Override
    protected String getTestDataPath() {
        return TEST_DATA_PATH;
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