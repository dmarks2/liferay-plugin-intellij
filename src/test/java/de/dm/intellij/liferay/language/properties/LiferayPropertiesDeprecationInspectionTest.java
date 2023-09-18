package de.dm.intellij.liferay.language.properties;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LiferayPropertiesDeprecationInspectionTest extends BasePlatformTestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		myFixture.enableInspections(new LiferayPropertiesDeprecationInspection());
	}

	@NotNull
	@Override
	protected LightProjectDescriptor getProjectDescriptor() {
		return new LightProjectDescriptorBuilder()
				.liferayVersion("7.4.3.55")
				.build();
	}

	@Override
	protected String getTestDataPath() {
		return "testdata/de/dm/intellij/liferay/language/properties/LiferayPropertiesDeprecationInspectionTest";
	}
	public void testPropertyDeprecation() {
		myFixture.configureByFiles(
				"portal-ext.properties"
		);

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Remove Property")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("portal-ext-expected.properties");
	}

}
