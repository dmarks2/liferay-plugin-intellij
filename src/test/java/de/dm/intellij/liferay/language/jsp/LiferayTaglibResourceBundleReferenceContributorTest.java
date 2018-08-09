package de.dm.intellij.liferay.language.jsp;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.psi.PsiElement;
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;

import java.util.List;

public class LiferayTaglibResourceBundleReferenceContributorTest extends LightCodeInsightFixtureTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/jsp/LiferayTaglibResourceBundleReferenceContributorTest";
    }

    public void testCompletion() {
        myFixture.configureByFiles("view.jsp", "liferay-ui.tld", "Language.properties", "foo.properties");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue("\"lang\" should have been resolved in Language.properties", strings.contains("lang"));
        assertFalse("\"foo\" should not be resolved, because it is in a non-Language file", strings.contains("foo"));
    }

    public void testReference() {
        myFixture.configureByFiles("edit.jsp", "liferay-ui.tld", "Language.properties", "foo.properties");

        PsiElement element = myFixture.getFile().findElementAt(myFixture.getCaretOffset()).getParent();
        PsiElement resolve = element.getReferences()[0].resolve();

        assertTrue("\"lang\" should be resolvable, because it is in Language.properties", (resolve != null));
    }

    public void testInvalidReference() {
        myFixture.configureByFiles("invalid.jsp", "liferay-ui.tld", "Language.properties", "foo.properties");

        PsiElement element = myFixture.getFile().findElementAt(myFixture.getCaretOffset()).getParent();
        PsiElement resolve = element.getReferences()[0].resolve();

        assertTrue("\"foo\" should not be resolvable, because it is in a non-Language file", (resolve == null));
    }

}