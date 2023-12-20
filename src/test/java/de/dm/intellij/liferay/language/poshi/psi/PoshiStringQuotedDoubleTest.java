package de.dm.intellij.liferay.language.poshi.psi;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

import java.util.List;

public class PoshiStringQuotedDoubleTest extends BasePlatformTestCase {

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/poshi/psi/PoshiStringQuotedDoubleTest";
    }

    public void testReference() {
        myFixture.configureByFile("testcases/Liferay.testcase");

        PsiElement caretElement = myFixture.getFile().findElementAt(myFixture.getCaretOffset());

        assertNotNull(caretElement);

        PsiElement element = caretElement.getParent();
        PsiElement resolve = element.getReferences()[0].resolve();

        assertTrue("\"${myVar}\" should be resolvable", (resolve != null));
    }

    public void testCompletion() {
        myFixture.configureByFile("testcases/CodeComplete.testcase");

        myFixture.complete(CompletionType.BASIC, 1);

        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue("myVar should be found, is declared at root level.", strings.contains("myVar"));
        assertTrue("myInsideVar should be found, is declared on method level.", strings.contains("myVarInside"));
        assertFalse("myOtherVar should not be found, because it is declared after the current instruction.", strings.contains("myOtherVar"));
    }

    public void testPathClassReference() {
        myFixture.configureByFiles("testcases/PathReference.testcase", "paths/Homepage.path", "paths/HomepageOther.path");

        PsiElement caretElement = myFixture.getFile().findElementAt(myFixture.getCaretOffset());

        assertNotNull(caretElement);

        PsiElement element = caretElement.getParent();
        PsiElement resolve = element.getReferences()[0].resolve();

        assertTrue("\"Homepage\" should be resolvable", (resolve != null));
        assertTrue("\"Homepage\" should resolve to a file", (resolve instanceof PsiFile));
    }

    public void testPathClassCompletion() {
        myFixture.configureByFiles("testcases/PathCompletion.testcase", "paths/Homepage.path", "paths/HomepageOther.path");

        myFixture.complete(CompletionType.BASIC, 1);

        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("Homepage"));
    }

    public void testPathLocationReference() {
        myFixture.configureByFiles("testcases/PathLocationReference.testcase", "paths/Homepage.path", "paths/HomepageOther.path");

        PsiElement caretElement = myFixture.getFile().findElementAt(myFixture.getCaretOffset());

        assertNotNull(caretElement);

        PsiElement element = caretElement.getParent();
        PsiElement resolve = element.getReferences()[0].resolve();

        assertTrue("\"Homepage#NO_RESULTS_MESSAGE\" should be resolvable", (resolve != null));
    }

    public void testPathLocationCompletion() {
        myFixture.configureByFiles("testcases/PathLocationCompletion.testcase", "paths/Homepage.path", "paths/HomepageOther.path");

        myFixture.complete(CompletionType.BASIC, 1);

        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("SEARCH_BAR"));
    }
}
