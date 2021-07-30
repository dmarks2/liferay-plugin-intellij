package de.dm.intellij.liferay.index;

import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import com.intellij.util.indexing.FileBasedIndex;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PortletNameIndexTest extends LightJavaCodeInsightFixtureTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/liferay/index/PortletNameIndexTest";

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

    public void testPortletNameString() {
        myFixture.configureByFiles(
            "de/dm/portlet/MyPortlet.java",
            "javax/portlet/Portlet.java"
        );

        FileBasedIndex.getInstance().requestReindex(myFixture.getFile().getVirtualFile());

        List<String> portletNames = PortletNameIndex.getPortletNames(myFixture.getProject(), GlobalSearchScope.moduleScope(myFixture.getModule()));

        assertTrue(portletNames.contains("de_dm_portlet_MyPortletName"));
    }

    public void testPortletNameConstant() {
        myFixture.configureByFiles(
            "de/dm/portlet/MyConstantPortlet.java",
            "javax/portlet/Portlet.java"
        );

        FileBasedIndex.getInstance().requestReindex(myFixture.getFile().getVirtualFile());

        List<String> portletNames = PortletNameIndex.getPortletNames(myFixture.getProject(), GlobalSearchScope.moduleScope(myFixture.getModule()));

        assertTrue(portletNames.contains("de_dm_portlet_MyConstantPortletName"));
    }

    public void testPortletNameReferenceConstant() {
        myFixture.configureByFiles(
            "de/dm/portlet/MyReferenceConstantPortlet.java",
            "de/dm/portlet/PortletKeys.java",
            "javax/portlet/Portlet.java"
        );

        FileBasedIndex.getInstance().requestReindex(myFixture.getFile().getVirtualFile());

        List<String> portletNames = PortletNameIndex.getPortletNames(myFixture.getProject(), GlobalSearchScope.moduleScope(myFixture.getModule()));

        assertTrue("Portlet Name should be resolved from de.dm.portlet.PortletKeys, because it is in the same package", portletNames.contains("de_dm_portlet_MyReferencePortletKey"));
    }

    public void testPortletNameReferenceImportConstant() {
        myFixture.configureByFiles(
            "de/dm/portlet/MyReferenceConstantImportPortlet.java",
            "de/dm/util/MyPortletKeys.java",
            "javax/portlet/Portlet.java"
        );

        FileBasedIndex.getInstance().requestReindex(myFixture.getFile().getVirtualFile());

        List<String> portletNames = PortletNameIndex.getPortletNames(myFixture.getProject(), GlobalSearchScope.moduleScope(myFixture.getModule()));

        assertTrue("Portlet Name should be resolved from import de.dm.util.MyPortletKeys", portletNames.contains("de_dm_util_MyPortletKey"));
    }

    public void testPortletNameStaticImportConstant() {
        myFixture.configureByFiles(
            "de/dm/portlet/MyStaticImportConstantPortlet.java",
            "de/dm/portlet/PortletKeys.java",
            "javax/portlet/Portlet.java"
        );

        FileBasedIndex.getInstance().requestReindex(myFixture.getFile().getVirtualFile());

        List<String> portletNames = PortletNameIndex.getPortletNames(myFixture.getProject(), GlobalSearchScope.moduleScope(myFixture.getModule()));

        assertTrue(portletNames.contains("de_dm_portlet_MyPortletKey"));
    }

    public void testUnnamedPortlet() {
        myFixture.configureByFiles(
            "de/dm/portlet/UnnamedPortlet.java",
            "javax/portlet/Portlet.java"
        );

        FileBasedIndex.getInstance().requestReindex(myFixture.getFile().getVirtualFile());

        List<String> portletNames = PortletNameIndex.getPortletNames(myFixture.getProject(), GlobalSearchScope.moduleScope(myFixture.getModule()));

        assertTrue(portletNames.contains("de_dm_portlet_UnnamedPortlet"));
    }


}
