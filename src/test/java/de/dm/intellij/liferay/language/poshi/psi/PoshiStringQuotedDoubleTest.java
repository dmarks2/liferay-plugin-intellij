package de.dm.intellij.liferay.language.poshi.psi;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.psi.PsiElement;
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
}
