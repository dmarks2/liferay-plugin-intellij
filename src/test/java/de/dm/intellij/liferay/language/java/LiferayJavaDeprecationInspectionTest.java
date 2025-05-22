package de.dm.intellij.liferay.language.java;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.openapi.vfs.newvfs.impl.VfsRootAccess;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import com.intellij.util.PathUtil;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

public class LiferayJavaDeprecationInspectionTest extends LightJavaCodeInsightFixtureTestCase {

	@NotNull
	@Override
	protected LightProjectDescriptor getProjectDescriptor() {
		return new LightProjectDescriptorBuilder()
				.liferayVersion("7.4.3.120")
				.build();
	}


	@Override
	protected void setUp() throws Exception {
		super.setUp();

		File testDataDir = new File(myFixture.getTestDataPath());

		final String testDataPath = PathUtil.toSystemIndependentName(testDataDir.getAbsolutePath());

		VfsRootAccess.allowRootAccess(myFixture.getTestRootDisposable(), testDataPath);

		myFixture.enableInspections(new LiferayJavaDeprecationInspection());
	}

	@Override
	protected String getTestDataPath() {
		return "testdata/de/dm/intellij/liferay/language/java/LiferayJavaDeprecationInspectionTest";
	}

	public void testActionCommandImportDeprecation() {
		myFixture.configureByFiles(
				"MyActionCommand.java"
		);

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Rename Import Statement")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("MyActionCommand_expected.java");
	}
	public void testUserImporterUtilDeprecationRemove() {
		myFixture.configureByFiles(
				"MyUserImporterUtil.java"
		);

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Remove Import Statement")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("MyUserImporterUtil_expected.java");
	}

	public void testPhoneMethodCallChange() {
		myFixture.configureByFiles(
				"MyPhoneUtil.java",
				"com/liferay/portal/kernel/model/Phone.java"
		);

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Rename Method Call")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("MyPhoneUtil_expected.java");
	}

	public void testPhoneMethodCallSetterChange() {
		myFixture.configureByFiles(
				"MyPhoneUtilSetter.java",
				"com/liferay/portal/kernel/model/Phone.java"
		);

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Rename Method Call")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("MyPhoneUtilSetter_expected.java");
	}

	public void testStaticMethodCallChange() {
		myFixture.configureByFiles(
				"MyStaticMethodDeprecation.java",
				"com/liferay/portal/kernel/service/AddressLocalServiceUtil.java"
		);

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Rename Method Call")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("MyStaticMethodDeprecation_expected.java");
	}
	public void testStaticPackageCallChange() {
		myFixture.configureByFiles(
				"MyStaticPackageCall.java",
				"com/liferay/portal/kernel/util/HttpComponentsUtil.java"
		);

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Rename Method Call")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("MyStaticPackageCall_expected.java");
	}

	public void testInstancePackageCallChange() {
		myFixture.configureByFiles(
				"MyInstancePackageCall.java",
				"com/liferay/portal/kernel/util/HttpComponentsUtil.java"
		);

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Rename Method Call")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("MyInstancePackageCall_expected.java");
	}

	public void testMethodDeprecationRemove() {
		myFixture.configureByFiles(
				"MyPortalFlashDeprecation.java",
				"com/liferay/portal/kernel/theme/ThemeDisplay.java"
		);

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Remove Method Call")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("MyPortalFlashDeprecation_expected.java");
	}

	public void testLPS178619Deprecation() {
		myFixture.configureByFiles(
				"MyStructureKeyDeprecation.java",
				"com/liferay/journal/model/JournalArticle.java"
		);

		myFixture.checkHighlighting();
	}

}
