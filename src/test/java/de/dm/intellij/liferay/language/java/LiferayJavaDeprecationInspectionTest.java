package de.dm.intellij.liferay.language.java;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import de.dm.intellij.liferay.language.jsp.LiferayJspJavaDeprecationInspection;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LiferayJavaDeprecationInspectionTest extends LightJavaCodeInsightFixtureTestCase {

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

		myFixture.enableInspections(new LiferayJavaDeprecationInspection(), new LiferayJspJavaDeprecationInspection());
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
}
