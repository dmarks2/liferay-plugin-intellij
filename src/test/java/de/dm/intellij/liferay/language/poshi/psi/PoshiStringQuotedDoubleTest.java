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

        PsiElement element = myFixture.getFile().findElementAt(myFixture.getCaretOffset()).getParent();
        PsiElement resolve = element.getReferences()[0].resolve();

        assertTrue("\"${myVar}\" should be resolvable", (resolve != null));
    }

    public void testCompletion() {
        myFixture.configureByFile("testcases/CodeComplete.testcase");

        myFixture.complete(CompletionType.BASIC, 1);

        List<String> strings = myFixture.getLookupElementStrings();

        assertTrue(strings.contains("myVar"));
    }

    public void testPathClassReference() {
        myFixture.configureByFiles("testcases/PathReference.testcase", "paths/Homepage.path", "paths/HomepageOther.path");

        PsiElement element = myFixture.getFile().findElementAt(myFixture.getCaretOffset()).getParent();
        PsiElement resolve = element.getReferences()[0].resolve();

        assertTrue("\"Homepage\" should be resolvable", (resolve != null));
        assertTrue("\"Homepage\" should resolve to a file", (resolve instanceof PsiFile));
    }

    public void testPathClassCompletion() {
        myFixture.configureByFiles("testcases/PathCompletion.testcase", "paths/Homepage.path", "paths/HomepageOther.path");

        myFixture.complete(CompletionType.BASIC, 1);

        List<String> strings = myFixture.getLookupElementStrings();

        assertTrue(strings.contains("Homepage"));
    }

    public void testPathLocationReference() {
        myFixture.configureByFiles("testcases/PathLocationReference.testcase", "paths/Homepage.path", "paths/HomepageOther.path");

        PsiElement element = myFixture.getFile().findElementAt(myFixture.getCaretOffset()).getParent();
        PsiElement resolve = element.getReferences()[0].resolve();

        assertTrue("\"Homepage#NO_RESULTS_MESSAGE\" should be resolvable", (resolve != null));
    }

    public void testPathLocationCompletion() {
        myFixture.configureByFiles("testcases/PathLocationCompletion.testcase", "paths/Homepage.path", "paths/HomepageOther.path");

        myFixture.complete(CompletionType.BASIC, 1);

        List<String> strings = myFixture.getLookupElementStrings();

        assertTrue(strings.contains("SEARCH_BAR"));
    }
}
