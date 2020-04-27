package de.dm.intellij.liferay.language.jsp;

import com.intellij.codeInsight.completion.CompletionType;
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
import com.intellij.util.indexing.FileBasedIndex;
import de.dm.intellij.liferay.index.ActionCommandIndex;
import de.dm.intellij.liferay.index.PortletJspIndex;
import de.dm.intellij.liferay.index.PortletNameIndex;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

public class LiferayTaglibActionCommandNameReferenceContributorTest extends LightJavaCodeInsightFixtureTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/liferay/language/jsp/LiferayTaglibActionCommandNameReferenceContributorTest";

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

    public void testByActionURLName() {
        myFixture.configureByFiles(
            "actionUrl.jsp",
            "liferay-portlet-ext.tld",
            "de/dm/action/MyMVCActionCommand.java",
            "com/liferay/portal/kernel/portlet/bridges/mvc/MVCActionCommand.java"
        );

        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
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

        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
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

        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("/my/jspaction"));
    }

}
