package de.dm.intellij.liferay.language.jsp;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

import java.util.List;

public class LiferayTaglibJavascriptFileReferenceContributorTest extends BasePlatformTestCase {

	@Override
	protected String getTestDataPath() {
		return "testdata/de/dm/intellij/liferay/language/jsp/LiferayTaglibJavascriptFileReferenceContributorTest";
	}

	public void testCompletion() {
		myFixture.configureByFiles("view.jsp", "react.tld", "js/MyModule.js");
		myFixture.complete(CompletionType.BASIC, 1);
		List<String> strings = myFixture.getLookupElementStrings();
		assertTrue(strings.contains("MyModule"));
	}

	public void testReference() {
		myFixture.configureByFiles("ref.jsp", "react.tld", "js/MyModule.js");

		PsiElement element = myFixture.getFile().findElementAt(myFixture.getCaretOffset()).getParent();

		boolean referenceResolved = false;

		if (element != null) {
			PsiReference[] references = element.getReferences();

			for (PsiReference reference : references) {
				PsiElement resolve = reference.resolve();

				if (resolve == null) {
					referenceResolved = false;

					break;
				} else {
					referenceResolved = true;
				}
			}
		}

		assertTrue(referenceResolved);
	}

}
