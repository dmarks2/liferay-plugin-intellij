package de.dm.intellij.liferay.packagejson;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LiferayPackageJSONDeprecationsExternalAnnotatorTest extends LightJavaCodeInsightFixtureTestCase {

	@NotNull
	@Override
	protected LightProjectDescriptor getProjectDescriptor() {
		return new LightProjectDescriptorBuilder()
				.liferayVersion("7.4.3.55")
				.build();
	}

	@Override
	protected String getTestDataPath() {
		return "testdata/de/dm/intellij/liferay/packagejson/LiferayPackageJSONDeprecationsExternalAnnotatorTest";
	}

	public void testDevDependenciesVersions() {
		myFixture.configureByFiles("package.json");

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Update version")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("package_expected.json");
	}

}
