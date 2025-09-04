package de.dm.intellij.liferay.language.osgi;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.openapi.vfs.newvfs.impl.VfsRootAccess;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import com.intellij.util.PathUtil;

import java.io.File;
import java.util.List;

public class OsgiCommandReferenceContributorTest extends LightJavaCodeInsightFixtureTestCase {

	private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/liferay/language/osgi/OsgiCommandReferenceContributorTest";

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		File testDataDir = new File(myFixture.getTestDataPath());

		final String testDataPath = PathUtil.toSystemIndependentName(testDataDir.getAbsolutePath());

		VfsRootAccess.allowRootAccess(myFixture.getTestRootDisposable(), testDataPath);
	}

	protected String getTestDataPath() {
		return TEST_DATA_PATH;
	}

	public void testOsgiCommandFunctionCodeCompletion() {
		myFixture.configureByFile("MyOsgiCommand.java");

		myFixture.complete(CompletionType.BASIC, 1);

		List<String> strings = myFixture.getLookupElementStrings();

		assertNotNull(strings);

		assertTrue("Code completion for osgi.command.function should suggest upgrade() method", strings.contains("upgrade"));

	}

	public void testOsgiCommandFunctionReference() {
		myFixture.configureByFiles("MyValidCommand.java");

		PsiElement caretElement = myFixture.getFile().findElementAt(myFixture.getCaretOffset());

		assertNotNull(caretElement);

		PsiElement element = caretElement.getParent();

		boolean referenceResolved = false;

		if (element != null) {
			PsiReference[] references = element.getReferences();

			for (PsiReference reference : references) {
				if (reference instanceof OsgiCommandReference) {
					PsiElement resolve = reference.resolve();
					if (resolve != null) {
						referenceResolved = true;

						break;
					}
				}
			}
		}

		assertTrue("osgi.command.function=bar should resolve to the method bar()", referenceResolved);
	}

	public void testRenameMethod() {
		myFixture.configureByFiles(
				"MyRenameCommand.java"
		);

		PsiElement caretElement = myFixture.getFile().findElementAt(myFixture.getCaretOffset());

		assertNotNull(caretElement);

		PsiElement element = caretElement.getParent();

		myFixture.renameElement(element, "renamed");

		myFixture.checkResultByFile(
				"MyRenameCommand.java", "MyRenameCommand_expected.java", false);
	}


}
