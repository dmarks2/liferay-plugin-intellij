package de.dm.intellij.liferay.index;

import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import com.intellij.util.indexing.FileBasedIndex;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PortletJspIndexTest extends LightJavaCodeInsightFixtureTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/liferay/index/PortletJspIndexTest";

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

    public void testJspPathByString() {
        myFixture.configureByFiles(
            "de/dm/portlet/MyPortlet.java",
            "javax/portlet/Portlet.java"
        );

        FileBasedIndex.getInstance().requestReindex(myFixture.getFile().getVirtualFile());

        List<String> portletNames = PortletJspIndex.getPortletNames("/html/view.jsp", myFixture.getProject(), GlobalSearchScope.moduleScope(myFixture.getModule()));

        assertTrue(portletNames.contains("de_dm_portlet_MyPortletName"));
    }

    public void testJspPathByConstantPortlet() {
        myFixture.configureByFiles(
            "de/dm/portlet/MyConstantPortlet.java",
            "javax/portlet/Portlet.java"
        );

        FileBasedIndex.getInstance().requestReindex(myFixture.getFile().getVirtualFile());

        List<String> portletNames = PortletJspIndex.getPortletNames("/html/constant.jsp", myFixture.getProject(), GlobalSearchScope.moduleScope(myFixture.getModule()));

        assertTrue(portletNames.contains("de_dm_portlet_MyConstantPortletName"));
    }

    public void testJspPathByMVCRenderCommand() {
        myFixture.configureByFiles(
            "de/dm/portlet/MyMVCRenderCommand.java",
            "javax/portlet/RenderRequest.java",
            "javax/portlet/RenderResponse.java",
            "com/liferay/portal/kernel/portlet/bridges/mvc/MVCRenderCommand.java"
        );

        FileBasedIndex.getInstance().requestReindex(myFixture.getFile().getVirtualFile());

        List<String> portletNames = PortletJspIndex.getPortletNames("/html/render.jsp", myFixture.getProject(), GlobalSearchScope.moduleScope(myFixture.getModule()));

        assertTrue(portletNames.contains("de_dm_portlet_MyRenderPortlet"));
    }

}
