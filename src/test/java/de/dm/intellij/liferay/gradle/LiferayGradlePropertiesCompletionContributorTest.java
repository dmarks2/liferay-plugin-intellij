package de.dm.intellij.liferay.gradle;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

import java.util.List;

public class LiferayGradlePropertiesCompletionContributorTest extends BasePlatformTestCase {

	@Override
	protected String getTestDataPath() {
		return "testdata/de/dm/intellij/liferay/gradle/LiferayGradlePropertiesCompletionContributorTest";
	}

	public void testGradlePropertiesCompletion() {
		myFixture.configureByFiles("gradle.properties");

		myFixture.complete(CompletionType.BASIC, 1);

		List<String> strings = myFixture.getLookupElementStrings();

		assertNotNull(strings);
		assertTrue("gradle.properties should offer \"liferay.workspace.product\" as autocompletion", strings.contains("liferay.workspace.product"));
	}

}
