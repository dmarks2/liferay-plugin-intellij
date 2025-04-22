package de.dm.intellij.liferay.maven;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.openapi.vfs.newvfs.impl.VfsRootAccess;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import com.intellij.util.PathUtil;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

public class LiferayMavenDeprecationsExternalAnnotatorTest extends LightJavaCodeInsightFixtureTestCase {
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
	}

	@Override
	protected String getTestDataPath() {
		return "testdata/de/dm/intellij/liferay/maven/LiferayMavenDeprecationsExternalAnnotatorTest";
	}

	public void testServiceBuilderVersion() {
		myFixture.configureByFiles("service-builder/pom.xml");

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Update version")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("service-builder/pom-expected.xml");
	}

	public void testServiceBuilderPropertyVersion() {
		myFixture.configureByFiles("service-builder-property/pom.xml");

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Update version")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("service-builder-property/pom-expected.xml");
	}

	public void testServiceBuilderPluginManagementVersion() {
		myFixture.configureByFiles("service-builder-plugin-management/pom.xml");

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Update version")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("service-builder-plugin-management/pom-expected.xml");
	}

	public void testServiceBuilderPluginManagementPropertyVersion() {
		myFixture.configureByFiles("service-builder-plugin-management-property/pom.xml");

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Update version")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("service-builder-plugin-management-property/pom-expected.xml");
	}

	public void testPortalWebVersion() {
		myFixture.configureByFiles("portal-web/pom.xml");

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Update version")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("portal-web/pom-expected.xml");
	}

	public void testBizaQuteBndVersion() {
		myFixture.configureByFiles("biz-aqute-bnd/pom.xml");

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Update version")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("biz-aqute-bnd/pom-expected.xml");
	}

	public void testPluginDependencyVersion() {
		myFixture.configureByFiles("plugin-dependency/pom.xml");

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Update version")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("plugin-dependency/pom-expected.xml");
	}

}
