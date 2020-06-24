package de.dm.intellij.liferay.language.jsp;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

import java.util.List;

public class LiferayTaglibSimpleAttributesCompletionContributorTest extends BasePlatformTestCase {

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/jsp/LiferayTaglibSimpleAttributesCompletionContributorTest";
    }

    public void testLexiconAttributeCompletion() {
        myFixture.configureByFiles("lexicon.jsp", "liferay-aui.tld");
        myFixture.complete(CompletionType.BASIC, 1);

        List<String> strings = myFixture.getLookupElementStrings();

        assertTrue("\"lexicon\" should have been suggested as code completion for <aui:icon markupView=\"\">", strings.contains("lexicon"));
    }

    public void testLeftRightAttributeCompletion() {
        myFixture.configureByFiles("left-right.jsp", "liferay-aui.tld");
        myFixture.complete(CompletionType.BASIC, 1);

        List<String> strings = myFixture.getLookupElementStrings();

        assertTrue("\"left\" should have been suggested as code completion for <aui:input inlineLabel=\"\">", strings.contains("left"));
        assertTrue("\"right\" should have been suggested as code completion for <aui:input inlineLabel=\"\">", strings.contains("right"));
    }

    public void testTargetAttributeCompletion() {
        myFixture.configureByFiles("target.jsp", "liferay-aui.tld");
        myFixture.complete(CompletionType.BASIC, 1);

        List<String> strings = myFixture.getLookupElementStrings();

        assertTrue("\"_blank\" should have been suggested as code completion for <aui:a target=\"\">", strings.contains("_blank"));
    }

    public void testButtonTypeAttributeCompletion() {
        myFixture.configureByFiles("button.jsp", "liferay-aui.tld");
        myFixture.complete(CompletionType.BASIC, 1);

        List<String> strings = myFixture.getLookupElementStrings();

        assertTrue("\"submit\" should have been suggested as code completion for <aui:button type=\"\">", strings.contains("submit"));
    }

    public void testFormMethodAttributeCompletion() {
        myFixture.configureByFiles("form.jsp", "liferay-aui.tld");
        myFixture.complete(CompletionType.BASIC, 1);

        List<String> strings = myFixture.getLookupElementStrings();

        assertTrue("\"post\" should have been suggested as code completion for <aui:form method=\"\">", strings.contains("post"));
    }

    public void testInputTypeAttributeCompletion() {
        myFixture.configureByFiles("input.jsp", "liferay-aui.tld");
        myFixture.complete(CompletionType.BASIC, 1);

        List<String> strings = myFixture.getLookupElementStrings();

        assertTrue("\"text\" should have been suggested as code completion for <aui:input type=\"\">", strings.contains("text"));
    }

    public void testValidatorNameAttributeCompletion() {
        myFixture.configureByFiles("validator.jsp", "liferay-aui.tld");
        myFixture.complete(CompletionType.BASIC, 1);

        List<String> strings = myFixture.getLookupElementStrings();

        assertTrue("\"custom\" should have been suggested as code completion for <aui:validator name=\"\">", strings.contains("custom"));
    }
}
