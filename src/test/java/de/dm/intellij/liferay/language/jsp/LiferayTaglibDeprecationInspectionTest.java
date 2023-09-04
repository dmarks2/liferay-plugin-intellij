package de.dm.intellij.liferay.language.jsp;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LiferayTaglibDeprecationInspectionTest extends LightJavaCodeInsightFixtureTestCase {

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

		myFixture.enableInspections(new LiferayTaglibDeprecationInspection());
	}

	@Override
	protected String getTestDataPath() {
		return "testdata/de/dm/intellij/liferay/language/jsp/LiferayTaglibDeprecationInspectionTest";
	}

	public void testClayAlertAttributeDeprecation() {
		myFixture.configureByFiles(
				"view.jsp",
				"liferay-clay.tld"
		);

		myFixture.checkHighlighting();
	}

	public void testClayButtonAttributeDeprecationQuickFix() {
		myFixture.configureByFiles(
				"quickfix-button.jsp",
				"liferay-clay.tld"
		);

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Rename Attribute")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("quickfix-button-expected.jsp");
	}

	public void testAuiButtonItemDeprecationQuickFix() {
		myFixture.configureByFiles(
				"button-item.jsp",
				"liferay-aui.tld"
		);

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Rename Tag")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("button-item-expected.jsp");
	}

	public void testLiferayUiAssetCategoriesNavigationDeprecationQuickFix() {
		myFixture.configureByFiles(
				"asset-categories-navigation.jsp",
				"liferay-ui.tld",
				"liferay-asset.tld"
		);

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Change Namespace")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("asset-categories-navigation-expected.jsp");
	}

}
