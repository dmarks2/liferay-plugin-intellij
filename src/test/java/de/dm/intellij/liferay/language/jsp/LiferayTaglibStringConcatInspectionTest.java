package de.dm.intellij.liferay.language.jsp;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.openapi.vfs.newvfs.impl.VfsRootAccess;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import com.intellij.util.PathUtil;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

public class LiferayTaglibStringConcatInspectionTest extends LightJavaCodeInsightFixtureTestCase {

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor() {
        return LightProjectDescriptorBuilder.DEFAULT_PROJECT_DESCRIPTOR;
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();

        File testDataDir = new File(myFixture.getTestDataPath());

        final String testDataPath = PathUtil.toSystemIndependentName(testDataDir.getAbsolutePath());

        VfsRootAccess.allowRootAccess(myFixture.getTestRootDisposable(), testDataPath);

        myFixture.enableInspections(new LiferayTaglibStringConcatInspection());
    }

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/jsp/LiferayTaglibStringConcatInspectionTest";
    }

    public void testStringConcatInspection() {
        myFixture.configureByFiles(
                "view.jsp",
                "liferay-aui.tld"
        );
        myFixture.checkHighlighting();

        List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
        for (IntentionAction quickFix : allQuickFixes) {
            if ("Wrap in JSP expression".equals(quickFix.getFamilyName())) {
                myFixture.launchAction(quickFix);
            }
        }
        myFixture.checkResultByFile("view_fixed.jsp");
    }
}
