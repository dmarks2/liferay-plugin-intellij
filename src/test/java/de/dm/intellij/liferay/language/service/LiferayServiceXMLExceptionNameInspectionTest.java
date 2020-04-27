package de.dm.intellij.liferay.language.service;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

import java.util.List;

public class LiferayServiceXMLExceptionNameInspectionTest extends BasePlatformTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        myFixture.enableInspections(new LiferayServiceXMLExceptionNameInspection());
    }

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/service/LiferayServiceXMLExceptionNameInspectionTest";
    }

    public void testExceptionNameInspection() {
        myFixture.configureByFiles("service.xml");

        myFixture.checkHighlighting();

        List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
        for (IntentionAction quickFix : allQuickFixes) {
            if ("Remove Exception suffix".equals(quickFix.getFamilyName())) {
                myFixture.launchAction(quickFix);
            }
        }

        myFixture.checkResultByFile("service_fixed.xml");
    }

}
