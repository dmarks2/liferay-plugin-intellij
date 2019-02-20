package de.dm.intellij.liferay.index;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.impl.JavaAwareProjectJdkTableImpl;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.LanguageLevelModuleExtension;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.vfs.newvfs.impl.VfsRootAccess;
import com.intellij.pom.java.LanguageLevel;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.PsiTestUtil;
import com.intellij.testFramework.fixtures.DefaultLightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import com.intellij.util.PathUtil;
import com.intellij.util.indexing.FileBasedIndex;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

public class ActionCommandIndexTest extends LightCodeInsightFixtureTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/liferay/index/ActionCommandIndexTest";

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
            VfsRootAccess.allowRootAccess( testDataPath );

            PsiTestUtil.addLibrary(module, model, "OSGi", testDataPath, "osgi.jar");
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

    public void testActionCommandByString() {
        myFixture.configureByFiles(
            "de/dm/action/MyMVCAction.java",
            "com/liferay/portal/kernel/portlet/bridges/mvc/MVCActionCommand.java"
        );

        FileBasedIndex.getInstance().requestReindex(myFixture.getFile().getVirtualFile());

        List<String> actionCommands = ActionCommandIndex.getActionCommands("de_dm_portlet_MyPortletName", myFixture.getProject(), GlobalSearchScope.moduleScope(myFixture.getModule()));

        assertTrue(actionCommands.contains("/my/action"));
    }

    public void testActionCommandByActionKeyReferenceConstant() {
        myFixture.configureByFiles(
                "de/dm/action/MyActionKeyReferenceMVCAction.java",
                "de/dm/action/ActionKeys.java",
                "com/liferay/portal/kernel/portlet/bridges/mvc/MVCActionCommand.java"
        );

        FileBasedIndex.getInstance().requestReindex(myFixture.getFile().getVirtualFile());

        List<String> actionCommands = ActionCommandIndex.getActionCommands("de_dm_portlet_MyPortletName", myFixture.getProject(), GlobalSearchScope.moduleScope(myFixture.getModule()));

        assertTrue(actionCommands.contains("/my/actionkey"));
    }

    public void testActionCommandByStringWithPortletKeyReferenceConstant() {
        myFixture.configureByFiles(
                "de/dm/action/MyPortletReferenceMVCAction.java",
                "de/dm/portlet/PortletKeys.java",
                "com/liferay/portal/kernel/portlet/bridges/mvc/MVCActionCommand.java"
        );

        FileBasedIndex.getInstance().requestReindex(myFixture.getFile().getVirtualFile());

        List<String> actionCommands = ActionCommandIndex.getActionCommands("de_dm_portlet_MyPortletKey", myFixture.getProject(), GlobalSearchScope.moduleScope(myFixture.getModule()));

        assertTrue(actionCommands.contains("/my/portletref/action"));
    }

    public void testActionCommandByActionKeyWithPortletKeyReferenceConstant() {
        myFixture.configureByFiles(
                "de/dm/action/MyPortletReferenceActionKeyReferenceMVCAction.java",
                "de/dm/action/ActionKeys.java",
                "de/dm/portlet/PortletKeys.java",
                "com/liferay/portal/kernel/portlet/bridges/mvc/MVCActionCommand.java"
        );

        FileBasedIndex.getInstance().requestReindex(myFixture.getFile().getVirtualFile());

        List<String> actionCommands = ActionCommandIndex.getActionCommands("de_dm_portlet_MySecondPortletKey", myFixture.getProject(), GlobalSearchScope.moduleScope(myFixture.getModule()));

        assertTrue(actionCommands.contains("/my/secondkey"));
    }

    public void testMultiplePortletsActionCommandByString() {
        myFixture.configureByFiles(
            "de/dm/action/MultiplePortletsMVCAction.java",
            "com/liferay/portal/kernel/portlet/bridges/mvc/MVCActionCommand.java"
        );

        FileBasedIndex.getInstance().requestReindex(myFixture.getFile().getVirtualFile());

        List<String> actionCommands = ActionCommandIndex.getActionCommands("de_dm_portlet_MyPortletName", myFixture.getProject(), GlobalSearchScope.moduleScope(myFixture.getModule()));
        assertTrue(actionCommands.contains("/my/action"));

        actionCommands = ActionCommandIndex.getActionCommands("de_dm_portlet_MyOtherPortletName", myFixture.getProject(), GlobalSearchScope.moduleScope(myFixture.getModule()));
        assertTrue(actionCommands.contains("/my/action"));
    }

    public void testProcessActionAnnotationByString() {
        myFixture.configureByFiles(
            "de/dm/portlet/MyPortlet.java",
            "javax/portlet/Portlet.java",
            "javax/portlet/ProcessAction.java"
        );

        FileBasedIndex.getInstance().requestReindex(myFixture.getFile().getVirtualFile());

        List<String> actionCommands = ActionCommandIndex.getActionCommands("de_dm_portlet_MyPortletName", myFixture.getProject(), GlobalSearchScope.moduleScope(myFixture.getModule()));

        assertTrue(actionCommands.contains("/my/action"));
    }

    public void testProcessActionAnnotationByConstant() {
        myFixture.configureByFiles(
            "de/dm/portlet/MyConstantPortlet.java",
            "javax/portlet/Portlet.java",
            "javax/portlet/ProcessAction.java"
        );

        FileBasedIndex.getInstance().requestReindex(myFixture.getFile().getVirtualFile());

        List<String> actionCommands = ActionCommandIndex.getActionCommands("de_dm_portlet_MyPortletName", myFixture.getProject(), GlobalSearchScope.moduleScope(myFixture.getModule()));

        assertTrue(actionCommands.contains("/my/action"));
    }

    public void testProcessActionByMethodName() {
        myFixture.configureByFiles(
            "de/dm/portlet/MyActionMethodPortlet.java",
            "javax/portlet/Portlet.java",
            "javax/portlet/ActionRequest.java",
            "javax/portlet/ActionResponse.java"
        );

        FileBasedIndex.getInstance().requestReindex(myFixture.getFile().getVirtualFile());

        List<String> actionCommands = ActionCommandIndex.getActionCommands("de_dm_portlet_MyActionMethodPortletName", myFixture.getProject(), GlobalSearchScope.moduleScope(myFixture.getModule()));

        assertTrue(actionCommands.contains("actionMethod"));
    }

}
