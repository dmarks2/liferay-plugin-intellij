package de.dm.intellij.liferay.language.fragment;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

import java.util.List;

public class FragmentFreemarkerHtmlLanguageSubstitutorTest extends BasePlatformTestCase {

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/fragment/FragmentFreemarkerHtmlLanguageSubstitutorTest";
    }

    public void testFreemarkerFileType() {
        myFixture.configureByFiles("index.html", "fragment.json");

        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("assign"));
    }

}
