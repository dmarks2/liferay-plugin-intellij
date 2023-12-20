package de.dm.intellij.liferay.language.freemarker;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

import java.util.List;

public class LiferayFreemarkerTaglibSimpleAttributesCompletionContributorTest extends BasePlatformTestCase {

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/freemarker/LiferayFreemarkerTaglibSimpleAttributesCompletionContributorTest";
    }

    public void testLexiconAttributeCompletion() {
        myFixture.configureByFiles("lexicon.ftl", "liferay-aui.tld");
        myFixture.complete(CompletionType.BASIC, 1);

        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue("\"lexicon\" should have been suggested as code completion for <@liferay_aui.icon markupView=\"\">", strings.contains("lexicon"));
    }

    public void testLexiconAttributeCompletionSquareBrackets() {
        myFixture.configureByFiles("lexicon_square_brackets.ftl", "liferay-aui.tld");
        myFixture.complete(CompletionType.BASIC, 1);

        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue("\"lexicon\" should have been suggested as code completion for <@liferay_aui[\"icon\"] markupView=\"\">", strings.contains("lexicon"));
    }


}
