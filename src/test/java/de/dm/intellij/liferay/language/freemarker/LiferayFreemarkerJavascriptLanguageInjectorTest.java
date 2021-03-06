package de.dm.intellij.liferay.language.freemarker;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

import java.util.List;

public class LiferayFreemarkerJavascriptLanguageInjectorTest extends BasePlatformTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/freemarker/LiferayFreemarkerJavascriptLanguageInjectorTest";
    }

    public void testJavascriptAttribute() {
        myFixture.configureByFiles("attribute.ftl", "liferay-aui.tld");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("alert"));
    }

    public void testJavascriptAttributeSquareBrackets() {
        myFixture.configureByFiles("attribute_square_brackets.ftl", "liferay-aui.tld");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue("\"alert\" javascript command should be suggested inside <@liferay_aui[\"a\"] onClick=\"\">", strings.contains("alert"));
    }

    public void testJavascriptBody() {
        myFixture.configureByFiles("body.ftl", "liferay-aui.tld");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("alert"));
    }

    public void testJavascriptBodySquareBrackets() {
        myFixture.configureByFiles("body_square_brackets.ftl", "liferay-aui.tld");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue("\"alert\" javascript command should be suggested inside <@liferay_aui[\"script\"]>", strings.contains("alert"));
    }

}