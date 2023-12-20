package de.dm.intellij.liferay.language.jsp;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.PlatformTestUtil;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import com.intellij.util.indexing.FileBasedIndex;
import de.dm.intellij.liferay.index.ActionCommandIndex;
import de.dm.intellij.liferay.index.PortletJspIndex;
import de.dm.intellij.liferay.index.PortletNameIndex;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LiferayTaglibActionCommandNameReferenceContributorTest extends LightJavaCodeInsightFixtureTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/liferay/language/jsp/LiferayTaglibActionCommandNameReferenceContributorTest";

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

    public void testByActionURLName() {
        myFixture.configureByFiles(
            "actionUrl.jsp",
            "liferay-portlet-ext.tld",
            "de/dm/action/MyMVCActionCommand.java",
            "com/liferay/portal/kernel/portlet/bridges/mvc/MVCActionCommand.java"
        );

        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("/my/action"));
    }

    public void testByActionKeyReferenceConstantURLName() {
        myFixture.configureByFiles(
                "actionUrl.jsp",
                "liferay-portlet-ext.tld",
                "de/dm/action/MyActionKeyMVCActionCommand.java",
                "de/dm/action/ActionKeys.java",
                "com/liferay/portal/kernel/portlet/bridges/mvc/MVCActionCommand.java"
        );

        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("/my/actionkey"));
    }

    public void testByParamName() {
        myFixture.configureByFiles(
            "paramActionUrl.jsp",
            "liferay-portlet-ext.tld",
            "de/dm/action/MyMVCActionCommand.java",
            "com/liferay/portal/kernel/portlet/bridges/mvc/MVCActionCommand.java"
        );

        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("/my/action"));
    }

    public void testByProcessActionAnnotation() {
        myFixture.configureByFiles(
            "actionUrl.jsp",
            "liferay-portlet-ext.tld",
            "de/dm/portlet/MyPortlet.java",
            "javax/portlet/Portlet.java",
            "javax/portlet/ProcessAction.java"
        );

        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("/my/portletaction"));
    }

    public void testByJspPath() {
        myFixture.configureByFiles(
            "META-INF/resources/html/view.jsp",
            "liferay-portlet-ext.tld",
            "de/dm/portlet/MyJspPortlet.java",
            "javax/portlet/Portlet.java",
            "javax/portlet/ProcessAction.java"
        );


        FileBasedIndex.getInstance().requestRebuild(PortletNameIndex.NAME);
        FileBasedIndex.getInstance().requestRebuild(ActionCommandIndex.NAME);
        FileBasedIndex.getInstance().requestRebuild(PortletJspIndex.NAME);

        PlatformTestUtil.dispatchAllEventsInIdeEventQueue(); //wait for reindex

        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("/my/jspaction"));
    }

    public void testByMVCRenderCommand() {
        myFixture.configureByFiles(
            "META-INF/resources/html/render.jsp",
            "liferay-portlet-ext.tld",
            "de/dm/portlet/MyMVCRenderCommand.java",
            "de/dm/portlet/MyJspPortlet.java",
            "javax/portlet/Portlet.java",
            "javax/portlet/ProcessAction.java",
            "javax/portlet/RenderRequest.java",
            "javax/portlet/RenderResponse.java",
            "com/liferay/portal/kernel/portlet/bridges/mvc/MVCRenderCommand.java"
        );


        FileBasedIndex.getInstance().requestRebuild(PortletNameIndex.NAME);
        FileBasedIndex.getInstance().requestRebuild(ActionCommandIndex.NAME);
        FileBasedIndex.getInstance().requestRebuild(PortletJspIndex.NAME);

        PlatformTestUtil.dispatchAllEventsInIdeEventQueue(); //wait for reindex

        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("/my/jspaction"));
    }

    public void testByMVCRenderCommandPortletKeys() {
        myFixture.configureByFiles(
            "META-INF/resources/html/render.jsp",
            "liferay-portlet-ext.tld",
            "de/dm/portlet/MyMVCRenderCommandPortletKeys.java",
            "de/dm/portlet/PortletKeys.java",
            "de/dm/portlet/MyJspPortlet.java",
            "javax/portlet/Portlet.java",
            "javax/portlet/ProcessAction.java",
            "javax/portlet/RenderRequest.java",
            "javax/portlet/RenderResponse.java",
            "com/liferay/portal/kernel/portlet/bridges/mvc/MVCRenderCommand.java"
        );


        FileBasedIndex.getInstance().requestRebuild(PortletNameIndex.NAME);
        FileBasedIndex.getInstance().requestRebuild(ActionCommandIndex.NAME);
        FileBasedIndex.getInstance().requestRebuild(PortletJspIndex.NAME);

        PlatformTestUtil.dispatchAllEventsInIdeEventQueue(); //wait for reindex

        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("/my/jspaction"));
    }

    public void testByMVCRenderCommandActionCommandPortletKeys() {
        myFixture.configureByFiles(
                "META-INF/resources/html/render.jsp",
                "liferay-portlet-ext.tld",
                "de/dm/portlet/MyMVCRenderCommandPortletKeys.java",
                "de/dm/portlet/PortletKeys.java",
                "de/dm/action/MyMVCActionCommandPortletKeys.java",
                "de/dm/portlet/SimplePortlet.java",
                "javax/portlet/Portlet.java",
                "javax/portlet/ProcessAction.java",
                "javax/portlet/RenderRequest.java",
                "javax/portlet/RenderResponse.java",
                "com/liferay/portal/kernel/portlet/bridges/mvc/MVCRenderCommand.java"
        );


        FileBasedIndex.getInstance().requestRebuild(PortletNameIndex.NAME);
        FileBasedIndex.getInstance().requestRebuild(ActionCommandIndex.NAME);
        FileBasedIndex.getInstance().requestRebuild(PortletJspIndex.NAME);

        PlatformTestUtil.dispatchAllEventsInIdeEventQueue(); //wait for reindex

        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("/my/action"));
    }

    public void testByMVCRenderCommandActionCommandPortletPortletKeys() {
        myFixture.configureByFiles(
                "META-INF/resources/html/render.jsp",
                "liferay-portlet-ext.tld",
                "de/dm/portlet/MyMVCRenderCommandPortletKeys.java",
                "de/dm/portlet/PortletKeys.java",
                "de/dm/action/MyMVCActionCommandPortletKeys.java",
                "de/dm/portlet/SimplePortletPortletKeys.java",
                "javax/portlet/Portlet.java",
                "javax/portlet/ProcessAction.java",
                "javax/portlet/RenderRequest.java",
                "javax/portlet/RenderResponse.java",
                "com/liferay/portal/kernel/portlet/bridges/mvc/MVCRenderCommand.java"
        );


        FileBasedIndex.getInstance().requestRebuild(PortletNameIndex.NAME);
        FileBasedIndex.getInstance().requestRebuild(ActionCommandIndex.NAME);
        FileBasedIndex.getInstance().requestRebuild(PortletJspIndex.NAME);

        PlatformTestUtil.dispatchAllEventsInIdeEventQueue(); //wait for reindex

        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("/my/action"));
    }
}
