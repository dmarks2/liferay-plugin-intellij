package de.dm.intellij.liferay.index;

import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import com.intellij.util.indexing.FileBasedIndex;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ActionCommandIndexTest extends LightJavaCodeInsightFixtureTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/liferay/index/ActionCommandIndexTest";

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
