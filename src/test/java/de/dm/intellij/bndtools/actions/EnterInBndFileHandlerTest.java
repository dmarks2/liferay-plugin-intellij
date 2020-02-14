package de.dm.intellij.bndtools.actions;

import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase;

public class EnterInBndFileHandlerTest extends LightPlatformCodeInsightFixtureTestCase {

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
