package de.dm.intellij.liferay.language.osgi;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.impl.JavaAwareProjectJdkTableImpl;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.LanguageLevelModuleExtension;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.vfs.newvfs.impl.VfsRootAccess;
import com.intellij.pom.java.LanguageLevel;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.PsiTestUtil;
import com.intellij.testFramework.fixtures.DefaultLightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import com.intellij.util.PathUtil;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

public class ComponentServiceInheritanceInspectionTest extends LightJavaCodeInsightFixtureTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/liferay/language/osgi/ComponentServiceInheritanceInspectionTest";

    private static final LightProjectDescriptor JAVA_OSGI_LIB_DESCRIPTOR = new DefaultLightProjectDescriptor() {

        @Override
        public void configureModule(@NotNull Module module, @NotNull ModifiableRootModel model, @NotNull ContentEntry contentEntry) {
            LanguageLevelModuleExtension extension = model.getModuleExtension(LanguageLevelModuleExtension.class);
            if (extension != null) {
                extension.setLanguageLevel(LanguageLevel.JDK_1_8);
            }
            Sdk jdk = JavaAwareProjectJdkTableImpl.getInstanceEx().getInternalJdk();
            model.setSdk(jdk);

            final String testDataPath = PathUtil.toSystemIndependentName(new File(TEST_DATA_PATH).getAbsolutePath());
            VfsRootAccess.allowRootAccess( Disposer.newDisposable(), testDataPath );

            PsiTestUtil.addLibrary(model, "OSGi", testDataPath, "osgi.jar");
        }
    };

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor() {
        return JAVA_OSGI_LIB_DESCRIPTOR;
    }

    @Override
    protected String getTestDataPath() {
        return TEST_DATA_PATH;
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

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
            if (quickFixName.equals(quickFix.getFamilyName())) {
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
