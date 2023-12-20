package de.dm.intellij.liferay.language.jsp;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

import java.util.List;

public class LiferayTaglibJavascriptLanguageInjectorTest extends BasePlatformTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/jsp/LiferayTaglibJavascriptLanguageInjectorTest";
    }

    public void testJavascriptAttribute() {
        myFixture.configureByFiles("view.jsp", "liferay-aui.tld");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("alert"));
    }

    public void testJavascriptBody() {
        myFixture.configureByFiles("custom.jsp", "liferay-aui.tld");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("alert"));
    }

    public void testLookAndFeelXmlJavascriptCodeCompletion() {
        myFixture.configureByFiles("liferay-look-and-feel.xml");

        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("alert"));
    }

}
