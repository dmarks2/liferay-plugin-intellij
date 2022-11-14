package de.dm.intellij.liferay.language.osgi;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;

import java.util.List;

public class MetaConfigurationInvalidIdInspectionTest extends LightJavaCodeInsightFixtureTestCase {

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/osgi/MetaConfigurationInvalidIdInspectionTest";
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        myFixture.enableInspections(new MetaConfigurationInvalidIdInspection());
    }

    public void testInvalidIdInspection() {
        myFixture.configureByFiles(
                "de/dm/configuration/MyConfiguration.java",
                "aQute/bnd/annotation/metatype/Meta.java"
        );

        myFixture.checkHighlighting();

        String quickFixName = "Rename to 'de.dm.configuration.MyConfiguration'";

        List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
        for (IntentionAction quickFix : allQuickFixes) {
            if (quickFixName.equals(quickFix.getFamilyName())) {
                myFixture.launchAction(quickFix);
            }
        }
        myFixture.checkResultByFile("de/dm/configuration/MyConfiguration_fixed.java");

    }

    public void testValidIdInspection() {
        myFixture.configureByFiles(
                "de/dm/configuration/MyValidConfiguration.java",
                "aQute/bnd/annotation/metatype/Meta.java"
        );

        myFixture.checkHighlighting();
    }


}
