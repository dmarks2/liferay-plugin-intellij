package de.dm.intellij.liferay.language.jsp;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.impl.JavaAwareProjectJdkTableImpl;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.LanguageLevelModuleExtension;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.pom.java.LanguageLevel;
import com.intellij.testFramework.IdeaTestUtil;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.DefaultLightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LiferayTaglibStrictQuoteEscapingInspectionTest extends LightJavaCodeInsightFixtureTestCase {

    private static final LightProjectDescriptor MY_PROJECT_DESCRIPTOR = new DefaultLightProjectDescriptor() {

        @Override
        public void configureModule(@NotNull Module module, @NotNull ModifiableRootModel model, @NotNull ContentEntry contentEntry) {
            LanguageLevelModuleExtension extension = model.getModuleExtension(LanguageLevelModuleExtension.class);
            if (extension != null) {
                extension.setLanguageLevel(LanguageLevel.JDK_1_8);
            }

            Sdk jdk = JavaAwareProjectJdkTableImpl.getInstanceEx().getInternalJdk();
            model.setSdk(jdk);
        }

        @Override
        public Sdk getSdk() {
            return IdeaTestUtil.getMockJdk18();
        }
    };

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor() {
        return MY_PROJECT_DESCRIPTOR;
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();

        myFixture.enableInspections(new LiferayTaglibStrictQuoteEscapingInspection());
    }

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/jsp/LiferayTaglibStrictQuoteEscapingInspectionTest";
    }

    public void testStrictQuoteEscapingInspection() {
        myFixture.configureByFiles(
            "view.jsp",
            "liferay-ui.tld"
        );
        myFixture.checkHighlighting();

        List<IntentionAction> allQuickFixes = myFixture.getAllQuickFixes();
        for (IntentionAction quickFix : allQuickFixes) {
            if ("Use single quotes".equals(quickFix.getFamilyName())) {
                myFixture.launchAction(quickFix);
            }
        }
        myFixture.checkResultByFile("view_fixed.jsp");

    }

}

