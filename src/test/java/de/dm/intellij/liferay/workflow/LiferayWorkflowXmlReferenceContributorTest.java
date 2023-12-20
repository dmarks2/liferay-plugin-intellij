package de.dm.intellij.liferay.workflow;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

import java.util.List;

public class LiferayWorkflowXmlReferenceContributorTest extends BasePlatformTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/liferay/workflow/LiferayWorkflowXmlReferenceContributorTest";

    @Override
    protected String getTestDataPath() {
        return TEST_DATA_PATH;
    }

    public void testTargetCodeCompletion() {
        myFixture.configureByFiles("workflow-definition.xml");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("review"));
    }

    public void testTargetResolveReference() {
        myFixture.configureByFiles("workflow-definition-reference.xml");

        PsiElement element = myFixture.getFile().findElementAt(myFixture.getCaretOffset());

        assertNotNull(element);

        PsiReference[] references = element.getReferences();

        assertTrue(references.length > 0);

        PsiReference reference = references[0];

        PsiElement resolve = reference.resolve();

        assertNotNull(resolve);
    }
}
