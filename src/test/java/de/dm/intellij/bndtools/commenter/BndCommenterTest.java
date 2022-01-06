package de.dm.intellij.bndtools.commenter;

import com.intellij.testFramework.fixtures.BasePlatformTestCase;

public class BndCommenterTest extends BasePlatformTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/bndtools/commenter/BndCommenterTest";

    @Override
    protected String getTestDataPath() {
        return TEST_DATA_PATH;
    }

    public void testEnterInBndFileHandler() {
        myFixture.configureByFiles("bnd.bnd");

        myFixture.performEditorAction("CommentByLineComment");

        myFixture.checkResultByFile("expected.bnd");
    }
}