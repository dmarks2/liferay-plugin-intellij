package de.dm.intellij.liferay.language.java;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.openapi.vfs.newvfs.impl.VfsRootAccess;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import com.intellij.util.PathUtil;

import java.io.File;
import java.util.List;

public class LiferayLocalServiceImplUserIdModificationInspectionTest extends LightJavaCodeInsightFixtureTestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		File testDataDir = new File(myFixture.getTestDataPath());

		final String testDataPath = PathUtil.toSystemIndependentName(testDataDir.getAbsolutePath());

		VfsRootAccess.allowRootAccess(myFixture.getTestRootDisposable(), testDataPath);

		myFixture.enableInspections(new LiferayLocalServiceImplUserIdModificationInspection());
	}

	@Override
	protected String getTestDataPath() {
		return "testdata/de/dm/intellij/liferay/language/java/LiferayLocalServiceImplUserIdModificationInspectionTest";
	}

	public void testUserIdModificationInUpdateMethod() {
		myFixture.configureByFiles(
				"MyLocalServiceImpl.java",
				"MyModel.java",
				"com/liferay/portal/kernel/model/BaseModel.java"
		);

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();

		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Remove setUserId call")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("MyLocalServiceImpl_expected.java");
	}
}
