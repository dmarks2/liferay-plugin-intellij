package de.dm.intellij.liferay.language.service;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

import java.util.List;

public class LiferayServiceXMLDuplicateExceptionInspectionTest extends BasePlatformTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        myFixture.enableInspections(new LiferayServiceXMLDuplicateExceptionInspection());
    }

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/service/LiferayServiceXMLDuplicateExceptionInspectionTest";
    }

    public void testDuplicateExceptionInspection() {
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
