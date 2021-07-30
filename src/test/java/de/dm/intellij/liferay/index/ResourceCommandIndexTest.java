package de.dm.intellij.liferay.index;

import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import com.intellij.util.indexing.FileBasedIndex;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ResourceCommandIndexTest extends LightJavaCodeInsightFixtureTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/liferay/index/ResourceCommandIndexTest";

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

    public void testResourceCommandByString() {
        myFixture.configureByFiles(
            "de/dm/resource/MyMVCResourceCommand.java",
            "com/liferay/portal/kernel/portlet/bridges/mvc/MVCResourceCommand.java"
        );

        FileBasedIndex.getInstance().requestReindex(myFixture.getFile().getVirtualFile());

        List<String> resourceCommands = ResourceCommandIndex.getResourceCommands("de_dm_portlet_MyPortletName", myFixture.getProject(), GlobalSearchScope.moduleScope(myFixture.getModule()));

        assertTrue(resourceCommands.contains("/my/resource"));
    }

    public void testServeResourceByMethodName() {
        myFixture.configureByFiles(
            "de/dm/portlet/MyResourceMethodPortlet.java",
            "javax/portlet/Portlet.java",
            "javax/portlet/ResourceRequest.java",
            "javax/portlet/ResourceResponse.java"
        );

        FileBasedIndex.getInstance().requestReindex(myFixture.getFile().getVirtualFile());

        List<String> resourceCommands = ResourceCommandIndex.getResourceCommands("de_dm_portlet_MyResourceMethodPortletName", myFixture.getProject(), GlobalSearchScope.moduleScope(myFixture.getModule()));

        assertTrue(resourceCommands.contains("resourceMethod"));
    }

}

