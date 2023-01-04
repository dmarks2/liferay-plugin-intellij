package de.dm.intellij.liferay.language.poshi.highlighting;

import com.intellij.testFramework.fixtures.BasePlatformTestCase;

public class PoshiBraceMatcherTest extends BasePlatformTestCase {

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/poshi/highlighting/PoshiBraceMatcherTest";
    }

    public void testBraceMatcherPoshiTestcaseFile() {
        myFixture.configureByFiles("testcases/Liferay.testcase");

        myFixture.type("{");

        myFixture.checkResultByFile("testcases/expected.testcase");
    }
}
