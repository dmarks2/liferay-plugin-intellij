package de.dm.intellij.liferay.language.jsp;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

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
}
