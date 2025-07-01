package de.dm.intellij.liferay.language.osgi;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;

import java.util.List;

public class ReferenceUnbindReferenceContributorTest extends LightJavaCodeInsightFixtureTestCase {

	private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/liferay/language/osgi/ReferenceUnbindReferenceContributorTest";

	@Override
	protected String getTestDataPath() {
		return TEST_DATA_PATH;
	}

	public void testReferenceUnbindCompletion() {
		myFixture.configureByFiles(
				"ComponentReference.java",
				"org/osgi/service/component/annotations/Reference.java",
				"org/osgi/service/component/annotations/Component.java"
		);

		myFixture.complete(CompletionType.BASIC, 1);
		List<String> strings = myFixture.getLookupElementStrings();

		assertNotNull(strings);
		assertTrue(strings.contains("unsetObject"));
	}
}
