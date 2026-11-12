package de.dm.intellij.liferay.language.freemarker;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.openapi.vfs.newvfs.impl.VfsRootAccess;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import com.intellij.util.PathUtil;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

public class LiferayFreemarkerLayoutTemplateTaglibInspectionTest extends LightJavaCodeInsightFixtureTestCase {

	@NotNull
	@Override
	protected LightProjectDescriptor getProjectDescriptor() {
		return new LightProjectDescriptorBuilder()
				.liferayVersion("7.4.3.55")
				.rootAccess("/com/liferay/ftl/FTL_liferay_74.ftl")
				.build();
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		File testDataDir = new File(myFixture.getTestDataPath());

		final String testDataPath = PathUtil.toSystemIndependentName(testDataDir.getAbsolutePath());

		VfsRootAccess.allowRootAccess(myFixture.getTestRootDisposable(), testDataPath);

		myFixture.enableInspections(new LiferayFreemarkerLayoutTemplateTaglibInspection());
	}

	@Override
	protected String getTestDataPath() {
		return "testdata/de/dm/intellij/liferay/language/freemarker/LiferayFreemarkerLayoutTemplateTaglibInspectionTest";
	}

	public void testUnguardedTaglibQuickFix() {
		myFixture.configureByFiles("layouttpl/custom/unguarded.ftl");

		myFixture.checkHighlighting();

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();

		assertFalse(allQuickFixes.isEmpty());

		for (IntentionAction quickFix : allQuickFixes) {
			if (quickFix.getFamilyName().startsWith("Surround with existence check")) {
				myFixture.launchAction(quickFix);
			}
		}

		myFixture.checkResultByFile("layouttpl/custom/unguarded-expected.ftl");
	}

	public void testGuardedTaglib() {
		myFixture.configureByFiles("layouttpl/custom/guarded.ftl");

		myFixture.checkHighlighting();
	}

	public void testTaglibInElseBranch() {
		myFixture.configureByFiles("layouttpl/custom/else-branch.ftl");

		myFixture.checkHighlighting();
	}

	public void testNoLayoutTemplate() {
		myFixture.configureByFiles("other/no-layout-template.ftl");

		myFixture.checkHighlighting();
	}

	public void testLiferayMacroLibraryUsingTaglibs() {
		myFixture.configureByFiles("layouttpl/custom/liferay-macros.ftl");

		myFixture.checkHighlighting();
	}

	public void testLiferayMacroLibraryQuickFix() {
		myFixture.configureByFiles("layouttpl/custom/liferay-macros-quickfix.ftl");

		List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();

		assertEquals(2, allQuickFixes.size());

		for (IntentionAction quickFix : allQuickFixes) {
			myFixture.launchAction(quickFix);
		}

		myFixture.checkResultByFile("layouttpl/custom/liferay-macros-quickfix-expected.ftl");
	}
}
