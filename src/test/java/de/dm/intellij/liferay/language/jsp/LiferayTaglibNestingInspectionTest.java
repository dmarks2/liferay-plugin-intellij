package de.dm.intellij.liferay.language.jsp;

import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

public class LiferayTaglibNestingInspectionTest extends LightJavaCodeInsightFixtureTestCase {

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor() {
        return LightProjectDescriptorBuilder.DEFAULT_PROJECT_DESCRIPTOR;
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();

        myFixture.enableInspections(new LiferayTaglibNestingInspection());
    }

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/jsp/LiferayTaglibNestingInspectionTest";
    }

    public void testInvalidNestingInspection() {
        myFixture.configureByFiles(
            "view.jsp",
            "liferay-ui.tld"
        );

        myFixture.checkHighlighting();

        /*
        List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
        for (IntentionAction quickFix : allQuickFixes) {
            if ("Wrap in http://liferay.com/tld/ui <search-container> tag".equals(quickFix.getFamilyName())) {
                myFixture.launchAction(quickFix);
            }
        }
        myFixture.checkResultByFile("view_fixed.jsp");
         */
    }

    public void testValidNestingInspection() {
        myFixture.configureByFiles(
            "view_valid.jsp",
            "liferay-ui.tld"
        );
        myFixture.checkHighlighting();
    }

}
