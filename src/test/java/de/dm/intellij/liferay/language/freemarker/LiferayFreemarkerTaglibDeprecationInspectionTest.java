package de.dm.intellij.liferay.language.freemarker;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LiferayFreemarkerTaglibDeprecationInspectionTest extends LightJavaCodeInsightFixtureTestCase {

	@NotNull
	@Override
	protected LightProjectDescriptor getProjectDescriptor() {
		return new LightProjectDescriptorBuilder()
				.liferayVersion("7.4.3.55")
				.build();
	}


	@Override
	protected void setUp() throws Exception {
		super.setUp();

		myFixture.enableInspections(new LiferayFreemarkerTaglibDeprecationInspection());
	}

	@Override
	protected String getTestDataPath() {
		return "testdata/de/dm/intellij/liferay/language/freemarker/LiferayFreemarkerTaglibDeprecationInspectionTest";
	}

	public void testClayAlertAttributeDeprecation() {
		myFixture.configureByFiles(
				"alert.ftl"
		);

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Remove Attribute")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("alert-expected.ftl");
	}
}
