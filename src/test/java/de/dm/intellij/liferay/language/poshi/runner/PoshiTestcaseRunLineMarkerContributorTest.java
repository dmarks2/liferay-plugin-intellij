package de.dm.intellij.liferay.language.poshi.runner;

import com.intellij.psi.PsiElement;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

public class PoshiTestcaseRunLineMarkerContributorTest extends BasePlatformTestCase {

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/poshi/runner/PoshiTestcaseRunLineMarkerContributorTest";
    }

    public void testPoshiTestcaseRunLineMarker() {
        myFixture.configureByFile("testcases/Liferay.testcase");

        PsiElement element = myFixture.getFile().findElementAt(myFixture.getCaretOffset());

        assertNotNull(new PoshiTestcaseRunLineMarkerContributor().getInfo(element));
    }
}
