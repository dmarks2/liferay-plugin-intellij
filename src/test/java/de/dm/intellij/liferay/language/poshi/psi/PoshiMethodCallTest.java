package de.dm.intellij.liferay.language.poshi.psi;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.PsiReference;
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

        boolean resolved = false;

        for (PsiReference psiReference : element.getReferences()) {
            if (psiReference instanceof PoshiClassReference) {
                PoshiClassReference poshiClassReference = (PoshiClassReference) psiReference;

                PsiElement resolve = poshiClassReference.resolve();

                if (resolve != null) {
                    assertTrue("MyClass should resolve to MyClass.macro", resolve instanceof PsiFile && ((PsiFile)resolve).getName().equals("MyClass.macro"));

                    resolved = true;
                }
            }
        }

        assertTrue("MyClass should be resolvable", resolved);
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

        boolean resolved = false;

        for (PsiReference psiReference : element.getReferences()) {
            if (psiReference instanceof PoshiMethodReference) {
                PoshiMethodReference poshiMethodReference = (PoshiMethodReference) psiReference;

                PsiElement resolve = poshiMethodReference.resolve();

                if (resolve != null) {
                    assertTrue("MyClass.click() should resolve to a definition", (resolve instanceof PoshiDefinitionBase));
                    resolved = true;
                }
            }
        }

        assertTrue("MyClass.click() should be resolvable", resolved);

    }

    public void testMethodCompletion() {
        myFixture.configureByFiles("testcases/MethodCompletion.testcase", "macros/MyClass.macro", "macros/MyOtherClass.macro");

        myFixture.complete(CompletionType.BASIC, 1);

        List<String> strings = myFixture.getLookupElementStrings();

        assertTrue(strings.contains("click"));
    }

    public void testDefaultFunctionReference() {
        myFixture.configureByFiles("testcases/DefaultFunction.testcase", "functions/MyFunction.function");

        PsiElement element = myFixture.getFile().findElementAt(myFixture.getCaretOffset()).getParent();

        boolean resolved = false;

        for (PsiReference psiReference : element.getReferences()) {
            if (psiReference instanceof PoshiClassReference) {
                PoshiClassReference poshiClassReference = (PoshiClassReference) psiReference;

                PsiElement resolve = poshiClassReference.resolve();

                if (resolve != null) {
                    assertTrue("MyFunction should resolve to function foo", resolve instanceof PoshiFunctionDefinition && ((PoshiFunctionDefinition)resolve).getName().equals("foo"));
                    resolved = true;
                }
            }
        }

        assertTrue("MyFunction should be resolvable", resolved);
    }

    public void testNamespaceReference() {
        myFixture.configureByFile("testcases/DefaultNamespaceReference.testcase");

        PsiElement element = myFixture.getFile().findElementAt(myFixture.getCaretOffset()).getParent();

        boolean resolved = false;

        for (PsiReference psiReference : element.getReferences()) {
            if (psiReference instanceof PoshiNamespaceReference) {
                PoshiNamespaceReference poshiNamespaceReference = (PoshiNamespaceReference) psiReference;

                PsiElement resolve = poshiNamespaceReference.resolve();

                if (resolve != null) {
                    resolved = true;
                }
            }
        }

        assertTrue("Default should be resolvable", resolved);
    }

    public void testNamespaceCompletion() {
        myFixture.configureByFiles("testcases/NamespaceCompletion.testcase", "macros/MyClass.macro", "macros/MyOtherClass.macro");

        myFixture.complete(CompletionType.BASIC, 1);

        List<String> strings = myFixture.getLookupElementStrings();

        assertTrue(strings.contains("Default"));
    }

    public void testNamespacedClassReference() {
        myFixture.configureByFiles("testcases/NamespacedClassReference.testcase", "macros/MyClass.macro");

        PsiElement element = myFixture.getFile().findElementAt(myFixture.getCaretOffset()).getParent();

        boolean resolved = false;

        for (PsiReference psiReference : element.getReferences()) {
            if (psiReference instanceof PoshiClassReference) {
                PoshiClassReference poshiClassReference = (PoshiClassReference) psiReference;

                PsiElement resolve = poshiClassReference.resolve();

                if (resolve != null) {
                    assertTrue("Default.Open should resolve to default funtion open() inside Open.function", resolve instanceof PoshiFunctionDefinition && ((PoshiFunctionDefinition)resolve).getName().equals("open"));

                    resolved = true;
                }
            }
        }

        assertTrue("Default.Open should be resolvable", resolved);
    }

    public void testNamespacedMethodReference() {
        myFixture.configureByFiles("testcases/NamespacedMethodReference.testcase", "macros/MyClass.macro");

        PsiElement element = myFixture.getFile().findElementAt(myFixture.getCaretOffset()).getParent();

        boolean resolved = false;

        for (PsiReference psiReference : element.getReferences()) {
            if (psiReference instanceof PoshiMethodReference) {
                PoshiMethodReference poshiMethodReference = (PoshiMethodReference) psiReference;

                PsiElement resolve = poshiMethodReference.resolve();

                if (resolve != null) {
                    assertTrue("Default.Open.openInTheNewTab should resolve to function openInTheNewTab", resolve instanceof PoshiFunctionDefinition && ((PoshiFunctionDefinition)resolve).getName().equals("openInTheNewTab"));

                    resolved = true;
                }
            }
        }

        assertTrue("Default.Open.openInTheNewTab should be resolvable", resolved);
    }

    public void testSeleniumReference() {
        myFixture.configureByFiles("testcases/SeleniumReference.testcase");

        PsiElement element = myFixture.getFile().findElementAt(myFixture.getCaretOffset()).getParent();

        boolean resolved = false;

        for (PsiReference psiReference : element.getReferences()) {
            if (psiReference instanceof PoshiSeleniumReference) {
                PoshiSeleniumReference seleniumReference = (PoshiSeleniumReference) psiReference;

                PsiElement psiElement = seleniumReference.resolve();

                assertNotNull(psiElement);

                if (psiElement instanceof PsiNamedElement) {
                    PsiNamedElement psiNamedElement = (PsiNamedElement) psiElement;

                    assertEquals("selenium", psiNamedElement.getName());

                    resolved = true;
                }
            }
        }

        assertTrue("selenium should be resolvable", resolved);
    }

    public void testSeleniumCompletion() {
        myFixture.configureByFiles("testcases/SeleniumCompletion.testcase");

        myFixture.complete(CompletionType.BASIC, 1);

        List<String> strings = myFixture.getLookupElementStrings();

        if (strings != null) {
            //multiple lookup items
            assertTrue(strings.contains("selenium"));
        } else {
            //one lookup item automatically inserted
            myFixture.checkResultByFile("testcases/SeleniumCompletionExpected.testcase");
        }
    }
}
