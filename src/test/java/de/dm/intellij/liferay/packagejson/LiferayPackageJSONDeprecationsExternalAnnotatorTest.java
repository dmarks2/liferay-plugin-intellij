package de.dm.intellij.liferay.packagejson;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.openapi.vfs.newvfs.impl.VfsRootAccess;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import com.intellij.util.PathUtil;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

public class LiferayPackageJSONDeprecationsExternalAnnotatorTest extends LightJavaCodeInsightFixtureTestCase {

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
		return "testdata/de/dm/intellij/liferay/packagejson/LiferayPackageJSONDeprecationsExternalAnnotatorTest";
	}

	public void testDevDependenciesVersions() {
		myFixture.configureByFiles("package.json");

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Update version")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("package_expected.json");
	}

}
