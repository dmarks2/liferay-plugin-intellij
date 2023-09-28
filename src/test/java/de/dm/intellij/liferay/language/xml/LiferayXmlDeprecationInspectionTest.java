package de.dm.intellij.liferay.language.xml;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LiferayXmlDeprecationInspectionTest extends BasePlatformTestCase {

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

		myFixture.enableInspections(new LiferayXmlDeprecationInspection());
	}

	@Override
	protected String getTestDataPath() {
		return "testdata/de/dm/intellij/liferay/language/xml/LiferayXmlDeprecationInspectionTest";
	}

	public void testDtdDeprecation() {
		myFixture.configureByFiles(
				"default.xml"
		);

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Update DTD Definition")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("default_expected.xml");
	}

	public void testXmlSchemaDeprecation() {
		myFixture.configureByFiles(
				"workflow.xml"
		);

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Update Namespace")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("workflow_expected.xml");
	}
}
