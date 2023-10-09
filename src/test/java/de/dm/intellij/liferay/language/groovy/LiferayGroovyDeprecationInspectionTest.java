package de.dm.intellij.liferay.language.groovy;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LiferayGroovyDeprecationInspectionTest extends LightJavaCodeInsightFixtureTestCase {

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

		myFixture.enableInspections(new LiferayGroovyDeprecationInspection());
	}

	@Override
	protected String getTestDataPath() {
		return "testdata/de/dm/intellij/liferay/language/groovy/LiferayGroovyDeprecationInspectionTest";
	}

	public void testActionCommandImportDeprecation() {
		myFixture.configureByFiles(
				"script.groovy"
		);

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Rename Import Statement")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("script_expected.groovy");
	}

	public void testUserImporterUtilDeprecationRemove() {
		myFixture.configureByFiles(
				"myimport.groovy"
		);

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Remove Import Statement")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("myimport_expected.groovy");
	}

	public void testPhoneMethodCallChange() {
		myFixture.configureByFiles(
				"myphone.groovy",
				"com/liferay/portal/kernel/model/Phone.java"
		);

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Rename Method Call")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("myphone_expected.groovy");
	}

	public void testPhoneMethodCallSetterChange() {
		myFixture.configureByFiles(
				"myphonesetter.groovy",
				"com/liferay/portal/kernel/model/Phone.java"
		);

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Rename Method Call")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("myphonesetter_expected.groovy");
	}
	public void testStaticMethodCallChange() {
		myFixture.configureByFiles(
				"mystaticmethod.groovy",
				"com/liferay/portal/kernel/service/AddressLocalServiceUtil.java"
		);

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Rename Method Call")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("mystaticmethod_expected.groovy");
	}

	public void testStaticPackageCallChange() {
		myFixture.configureByFiles(
				"mystaticpackagecall.groovy",
				"com/liferay/portal/kernel/util/HttpComponentsUtil.java"
		);

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Rename Method Call")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("mystaticpackagecall_expected.groovy");
	}
}
