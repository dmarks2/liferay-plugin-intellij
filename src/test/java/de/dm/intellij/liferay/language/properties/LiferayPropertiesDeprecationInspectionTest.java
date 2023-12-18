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

	public void testPluginPackageLiferayVersion() {
		myFixture.configureByFiles(
				"liferay-plugin-package.properties"
		);

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Update Value")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("liferay-plugin-package-expected.properties");
	}

	public void testSystemPropertiesObsolete() {
		myFixture.configureByFiles(
				"system-ext.properties"
		);

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Remove Property")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("system-ext-expected.properties");
	}

	public void testSystemPropertiesMigration() {
		myFixture.configureByFiles(
				"system-migrated.properties"
		);

		myFixture.checkHighlighting();
	}

	public void testSystemPropertiesRename() {
		myFixture.configureByFiles(
				"system-renamed.properties"
		);

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Rename Property")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("system-renamed-expected.properties");
	}

	public void testSystemPropertiesModularized() {
		myFixture.configureByFiles(
				"system-modularized.properties"
		);

		myFixture.checkHighlighting();
	}

	public void testPortalPropertiesMigration() {
		myFixture.configureByFiles(
				"portal-migrated.properties"
		);

		myFixture.checkHighlighting();
	}

	public void testPortalPropertiesRename() {
		myFixture.configureByFiles(
				"portal-renamed.properties"
		);

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Rename Property")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("portal-renamed-expected.properties");
	}

	public void testPortalPropertiesObsolete() {
		myFixture.configureByFiles(
				"portal-obsolete.properties"
		);

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Remove Property")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("portal-obsolete-expected.properties");
	}

	public void testPortalPropertiesModularized() {
		myFixture.configureByFiles(
				"portal-modularized.properties"
		);

		myFixture.checkHighlighting();
	}
}
