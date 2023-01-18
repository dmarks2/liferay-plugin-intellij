package de.dm.intellij.liferay.language.poshi.psi;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

import java.util.List;

public class PoshiMethodCallTest extends BasePlatformTestCase {

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/poshi/psi/PoshiMethodCallTest";
    }

    public void testClassReference() {
        myFixture.configureByFiles("testcases/ClassReference.testcase", "macros/MyClass.macro");

        PsiElement element = myFixture.getFile().findElementAt(myFixture.getCaretOffset()).getParent();
        PsiElement resolve = element.getReferences()[0].resolve();

        assertTrue("MyClass should be resolvable", (resolve != null));
        assertTrue("MyClass should resolve to MyClass.macro", resolve instanceof PsiFile && ((PsiFile)resolve).getName().equals("MyClass.macro"));
    }

    public void testClassCompletion() {
        myFixture.configureByFiles("testcases/ClassComplete.testcase", "macros/MyClass.macro", "macros/MyOtherClass.macro");

        myFixture.complete(CompletionType.BASIC, 1);

        List<String> strings = myFixture.getLookupElementStrings();

        assertTrue(strings.contains("MyClass"));
    }

    public void testMethodReference() {
        myFixture.configureByFiles("testcases/MethodReference.testcase", "macros/MyClass.macro");

        PsiElement element = myFixture.getFile().findElementAt(myFixture.getCaretOffset()).getParent();
        PsiElement resolve = element.getReferences()[1].resolve();

        assertTrue("MyClass.click() should be resolvable", (resolve != null));
        assertTrue("MyClass.click() should resolve to a definition", (resolve instanceof PoshiDefinitionBase));
    }

    public void testMethodCompletion() {
        myFixture.configureByFiles("testcases/MethodCompletion.testcase", "macros/MyClass.macro", "macros/MyOtherClass.macro");

        myFixture.complete(CompletionType.BASIC, 1);

        List<String> strings = myFixture.getLookupElementStrings();

        assertTrue(strings.contains("click"));
    }
}
