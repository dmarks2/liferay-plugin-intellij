package de.dm.intellij.liferay.language.osgi;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.openapi.vfs.newvfs.impl.VfsRootAccess;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import com.intellij.util.PathUtil;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

public class ComponentServiceInheritanceInspectionTest extends LightJavaCodeInsightFixtureTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/liferay/language/osgi/ComponentServiceInheritanceInspectionTest";

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor() {
        return new LightProjectDescriptorBuilder()
                .library("OSGi", TEST_DATA_PATH, "osgi.jar")
                .build();
    }

    @Override
    protected String getTestDataPath() {
        return TEST_DATA_PATH;
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        File testDataDir = new File(myFixture.getTestDataPath());

        final String testDataPath = PathUtil.toSystemIndependentName(testDataDir.getAbsolutePath());

        VfsRootAccess.allowRootAccess(myFixture.getTestRootDisposable(), testDataPath);

        myFixture.enableInspections(new ComponentServiceInheritanceInspection());
    }

    public void testComponentServiceInheritanceInspection() {
        myFixture.configureByFiles(
                "MyComponent.java",
                "com/liferay/portal/kernel/portlet/bridges/mvc/MVCActionCommand.java"
        );

        myFixture.checkHighlighting();

        String quickFixName = "Make implements 'com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand'";

        List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
        for (IntentionAction quickFix : allQuickFixes) {
            if (quickFixName.equals(quickFix.getText())) {
                myFixture.launchAction(quickFix);
            }
        }
        myFixture.checkResultByFile("MyComponent_fixed.java");
    }

    public void testMultiComponentServiceInheritanceInspection() {
        myFixture.configureByFiles(
                "MultiServiceComponent.java",
                "com/liferay/portal/kernel/portlet/bridges/mvc/MVCActionCommand.java",
                "com/liferay/portal/kernel/search/IndexerPostProcessor.java"
        );

        myFixture.checkHighlighting();
    }

    public void testValidSelfComponentInspection() {
        myFixture.configureByFiles(
                "ValidSelfComponent.java"
        );

        myFixture.checkHighlighting();
    }

    public void testValidInheritedComponentInspection() {
        myFixture.configureByFiles(
                "ValidInheritedComponent.java",
                "com/liferay/portal/kernel/portlet/bridges/mvc/MVCActionCommand.java",
                "com/liferay/portal/kernel/portlet/bridges/mvc/BaseMVCActionCommand.java"
        );

        myFixture.checkHighlighting();
    }
}
