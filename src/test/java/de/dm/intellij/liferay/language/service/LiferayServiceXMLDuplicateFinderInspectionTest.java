package de.dm.intellij.liferay.language.service;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.openapi.vfs.newvfs.impl.VfsRootAccess;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.intellij.util.PathUtil;

import java.io.File;
import java.util.List;

public class LiferayServiceXMLDuplicateFinderInspectionTest extends BasePlatformTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        File testDataDir = new File(myFixture.getTestDataPath());

        final String testDataPath = PathUtil.toSystemIndependentName(testDataDir.getAbsolutePath());

        VfsRootAccess.allowRootAccess(myFixture.getTestRootDisposable(), testDataPath);

        myFixture.enableInspections(new LiferayServiceXMLDuplicateFinderInspection());
    }

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/service/LiferayServiceXMLDuplicateFinderInspectionTest";
    }

    public void testDuplicateFinderInspection() {
        myFixture.configureByFiles("service.xml");

        myFixture.checkHighlighting();

        List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
        for (IntentionAction quickFix : allQuickFixes) {
            if ("Remove entry".equals(quickFix.getFamilyName())) {
                myFixture.launchAction(quickFix);
                break;
            }
        }

        myFixture.checkResultByFile("service_fixed.xml", true);
    }

}
