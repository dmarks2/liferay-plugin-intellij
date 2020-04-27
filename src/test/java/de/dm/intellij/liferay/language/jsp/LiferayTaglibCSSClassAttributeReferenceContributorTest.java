package de.dm.intellij.liferay.language.jsp;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

import java.util.List;

public class LiferayTaglibCSSClassAttributeReferenceContributorTest extends BasePlatformTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/jsp/LiferayTaglibCSSClassAttributeReferenceContributorTest";
    }

    public void testCompletionCSS() {
        myFixture.configureByFiles("view.jsp", "main.css", "liferay-aui.tld");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("foo"));
    }

    public void testCompletionSCSS() {
        myFixture.configureByFiles("view.jsp", "custom.scss", "liferay-aui.tld");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("foo"));
    }
}
