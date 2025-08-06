package de.dm.intellij.liferay.language.osgi;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.openapi.vfs.newvfs.impl.VfsRootAccess;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import com.intellij.util.PathUtil;

import java.io.File;
import java.util.List;

public class MetaConfigurationInvalidIdInspectionTest extends LightJavaCodeInsightFixtureTestCase {

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/osgi/MetaConfigurationInvalidIdInspectionTest";
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        File testDataDir = new File(myFixture.getTestDataPath());

        final String testDataPath = PathUtil.toSystemIndependentName(testDataDir.getAbsolutePath());

        VfsRootAccess.allowRootAccess(myFixture.getTestRootDisposable(), testDataPath);

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
            if (quickFixName.equals(quickFix.getText())) {
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
