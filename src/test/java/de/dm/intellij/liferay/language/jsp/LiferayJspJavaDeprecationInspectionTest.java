package de.dm.intellij.liferay.language.jsp;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.openapi.vfs.newvfs.impl.VfsRootAccess;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import com.intellij.util.PathUtil;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

public class LiferayJspJavaDeprecationInspectionTest extends LightJavaCodeInsightFixtureTestCase {

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

		File testDataDir = new File(myFixture.getTestDataPath());

		final String testDataPath = PathUtil.toSystemIndependentName(testDataDir.getAbsolutePath());

		VfsRootAccess.allowRootAccess(myFixture.getTestRootDisposable(), testDataPath);

		myFixture.enableInspections(new LiferayJspJavaDeprecationInspection());
	}

	@Override
	protected String getTestDataPath() {
		return "testdata/de/dm/intellij/liferay/language/jsp/LiferayJspJavaDeprecationInspectionTest";
	}

	public void testMVCPortletInsideJSPImportDeprecation() {
		myFixture.configureByFiles(
				"view.jsp"
		);

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Rename Import Statement")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("view_expected.jsp");
	}

	public void testUserImporterUtilDeprecationRemove() {
		myFixture.configureByFiles(
				"myimport.jsp"
		);

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Remove Import Statement")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("myimport_expected.jsp");
	}

	public void testPhoneMethodCallChange() {
		myFixture.configureByFiles(
				"myphone.jsp",
				"com/liferay/portal/kernel/model/Phone.java"
		);

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Rename Method Call")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("myphone_expected.jsp");
	}

	public void testStaticMethodCallChange() {
		myFixture.configureByFiles(
				"mystaticmethoddeprecation.jsp",
				"com/liferay/portal/kernel/service/AddressLocalServiceUtil.java"
		);

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Rename Method Call")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("mystaticmethoddeprecation_expected.jsp");
	}

	public void testStaticPackageCallChange() {
		myFixture.configureByFiles(
				"mystaticpackagecall.jsp",
				"com/liferay/portal/kernel/util/HttpComponentsUtil.java"
		);

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Rename Method Call")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("mystaticpackagecall_expected.jsp");
	}

	public void testMethodDeprecationRemove() {
		myFixture.configureByFiles(
				"myportalflashdeprecation.jsp",
				"com/liferay/portal/kernel/theme/ThemeDisplay.java"
		);

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Remove Method Call")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("myportalflashdeprecation_expected.jsp");
	}
}
