package de.dm.intellij.liferay.language.poshi.psi;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.psi.PsiElement;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

import java.util.List;

public class PoshiVariableRefTest extends BasePlatformTestCase {

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/poshi/psi/PoshiVariableRefTest";
    }

    public void testReference() {
        myFixture.configureByFile("testcases/VariableReference.testcase");

        PsiElement caretElement = myFixture.getFile().findElementAt(myFixture.getCaretOffset());

        assertNotNull(caretElement);

        PsiElement element = caretElement.getParent();
        PsiElement resolve = element.getReferences()[0].resolve();

        assertTrue("\"${myVar}\" should be resolvable", (resolve != null));
    }

    public void testCompletion() {
        myFixture.configureByFile("testcases/VariableComplete.testcase");

        myFixture.complete(CompletionType.BASIC, 1);

        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue("myVar should be found, is declared at root level.", strings.contains("myVar"));
        assertTrue("myInsideVar should be found, is declared on method level.", strings.contains("myVarInside"));
        assertFalse("myOtherVar should not be found, because it is declared after the current instruction.", strings.contains("myOtherVar"));
    }

}
