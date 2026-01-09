package de.dm.intellij.liferay.language.html;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.openapi.vfs.newvfs.impl.VfsRootAccess;
import com.intellij.psi.PsiReference;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import com.intellij.util.PathUtil;

import java.io.File;
import java.util.List;

public class LiferayHtmlReferenceContributorTest extends LightJavaCodeInsightFixtureTestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		File testDataDir = new File(myFixture.getTestDataPath());

		final String testDataPath = PathUtil.toSystemIndependentName(testDataDir.getAbsolutePath());

		VfsRootAccess.allowRootAccess(myFixture.getTestRootDisposable(), testDataPath);
	}

	@Override
	protected String getTestDataPath() {
		return "testdata/de/dm/intellij/liferay/language/html/LiferayHtmlReferenceContributor";
	}

	public void testValidReference() {
		myFixture.configureByFiles("META-INF/resources/index.html", "META-INF/resources/index.css", "bnd.bnd");

		PsiReference reference = myFixture.getFile().findReferenceAt(myFixture.getCaretOffset());

		assertNotNull("Reference should be found", reference);
		assertNotNull("Reference should resolve to CSS file", reference.resolve());
	}

	public void testCodeCompletionList() {
		myFixture.configureByFiles("META-INF/resources/completion.html", "META-INF/resources/index.css", "META-INF/resources/main.css", "bnd.bnd");

		myFixture.complete(CompletionType.BASIC);

		List<String> completionItems = myFixture.getLookupElementStrings();

		assertNotNull("Completion items should be available", completionItems);

		assertTrue("Should contain main.css", completionItems.contains("main.css"));
		assertTrue("Should contain index.css", completionItems.contains("index.css"));
	}


	public void testJavaCodeCompletion() {
		myFixture.configureByFiles("META-INF/resources/MyClass.java", "META-INF/resources/index.css", "META-INF/resources/main.css", "bnd.bnd");

		myFixture.complete(CompletionType.BASIC);

		List<String> completionItems = myFixture.getLookupElementStrings();

		assertNotNull("Completion items should be available", completionItems);

		assertTrue("Should contain main.css", completionItems.contains("main.css"));
		assertTrue("Should contain index.css", completionItems.contains("index.css"));
	}

	public void testNestedCodeCompletionList() {
		myFixture.configureByFiles("META-INF/resources/nested_completion.html", "META-INF/resources/index.css", "META-INF/resources/main.css", "bnd.bnd");

		myFixture.complete(CompletionType.BASIC);

		List<String> completionItems = myFixture.getLookupElementStrings();

		assertNotNull("Completion items should be available", completionItems);

		assertTrue("Should contain /o/foo/main.css", completionItems.contains("/o/foo/main.css"));
		assertTrue("Should contain /o/foo/index.css", completionItems.contains("/o/foo/index.css"));
	}

}
