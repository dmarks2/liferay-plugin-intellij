package de.dm.intellij.bndtools.completion;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.documentation.DocumentationManager;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.psi.PsiElement;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

import java.util.List;

public class BndCompletionContributorTest extends BasePlatformTestCase {

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/bndtools/completion/BndCompletionContributorTest";
    }

    public void testHeaderCompletion() {
        myFixture.configureByFiles("header/bnd.bnd");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("Bundle-Version"));
    }

    public void testHeaderCompletionDocumentationLookup() {
        myFixture.configureByFiles("header/bnd.bnd");
        myFixture.complete(CompletionType.BASIC, 1);

        LookupElement[] lookupElements = myFixture.getLookupElements();
        for (LookupElement lookupElement : lookupElements) {
            if (lookupElement.getLookupString().equals("Bundle-Version")) {
                PsiElement elementFromLookup = DocumentationManager.getElementFromLookup(myFixture.getProject(), myFixture.getEditor(), myFixture.getFile(), lookupElement);

                assertNotNull(elementFromLookup);
            }
        }
    }
}
