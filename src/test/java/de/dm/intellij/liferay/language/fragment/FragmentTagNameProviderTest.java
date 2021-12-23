package de.dm.intellij.liferay.language.fragment;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInspection.htmlInspections.HtmlUnknownTagInspection;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

import java.util.List;

public class FragmentTagNameProviderTest extends BasePlatformTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        myFixture.enableInspections(new HtmlUnknownTagInspection());
    }

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/fragment/FragmentTagNameProviderTest";
    }

    public void testFragmentCodeCompletion() {
        myFixture.configureByFiles("index.html", "fragment.json");

        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("lfr-drop-zone"));
    }

    public void testValidCustomTag() {
        myFixture.configureByFiles("valid.html", "fragment.json");

        myFixture.checkHighlighting();
    }
}
