package de.dm.intellij.liferay.language.fragment;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInspection.htmlInspections.HtmlUnknownAttributeInspection;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

import java.util.List;

public class FragmentAttributeDescriptorsTest extends BasePlatformTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        myFixture.enableInspections(new HtmlUnknownAttributeInspection());
    }

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/fragment/FragmentAttributeDescriptorsTest";
    }

    public void testValidAttributes() {
        myFixture.configureByFiles("valid.html", "fragment.json");

        myFixture.checkHighlighting();
    }

    public void testCommonAttributes() {
        myFixture.configureByFiles("commonattributes.html", "fragment.json");

        myFixture.checkHighlighting();
    }

    public void testAttributeCodeCompletion() {
        myFixture.configureByFiles("index.html", "fragment.json");

        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("data-lfr-background-image-id"));
    }
}
