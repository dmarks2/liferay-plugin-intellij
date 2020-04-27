package de.dm.intellij.bndtools.actions;

import com.intellij.testFramework.fixtures.BasePlatformTestCase;

public class EnterInBndFileHandlerTest extends BasePlatformTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/bndtools/actions/EnterInBndFileHandlerTest";

    @Override
    protected String getTestDataPath() {
        return TEST_DATA_PATH;
    }

    public void testEnterInBndFileHandler() {
        myFixture.configureByFiles("bnd.bnd");

        myFixture.type('\n');

        myFixture.checkResultByFile("updated.bnd");
    }

}
