package de.dm.intellij.liferay.index;

import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import com.intellij.util.indexing.FileBasedIndex;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RenderCommandIndexTest extends LightJavaCodeInsightFixtureTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/liferay/index/RenderCommandIndexTest";

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

    public void testRenderCommandByString() {
        myFixture.configureByFiles(
            "de/dm/render/MyMVCRenderCommand.java",
            "com/liferay/portal/kernel/portlet/bridges/mvc/MVCRenderCommand.java"
        );

        FileBasedIndex.getInstance().requestReindex(myFixture.getFile().getVirtualFile());

        List<String> renderCommands = RenderCommandIndex.getRenderCommands("de_dm_portlet_MyPortletName", myFixture.getProject(), GlobalSearchScope.moduleScope(myFixture.getModule()));

        assertTrue(renderCommands.contains("/my/render"));
    }
}
