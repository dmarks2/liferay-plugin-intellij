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

	public void testAuiToolTagDeprecation() {
		myFixture.configureByFiles(
				"aui-tool.ftl"
		);

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Remove Tag")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("aui-tool-expected.ftl");
	}

	public void testClayButtonAttributeDeprecationQuickFix() {
		myFixture.configureByFiles(
				"quickfix-button.ftl"
		);

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Rename Attribute")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("quickfix-button-expected.ftl");
	}

	public void testAuiButtonItemDeprecationQuickFix() {
		myFixture.configureByFiles(
				"button-item.ftl"
		);

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Rename Tag")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("button-item-expected.ftl");
	}
	public void testLiferayUiAssetCategoriesNavigationDeprecationQuickFix() {
		myFixture.configureByFiles(
				"asset-categories-navigation.ftl"
		);

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Change Namespace")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("asset-categories-navigation-expected.ftl");
	}
}
