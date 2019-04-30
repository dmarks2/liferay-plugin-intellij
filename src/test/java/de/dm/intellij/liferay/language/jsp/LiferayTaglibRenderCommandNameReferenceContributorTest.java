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
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import com.intellij.util.PathUtil;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

public class LiferayTaglibRenderCommandNameReferenceContributorTest extends LightCodeInsightFixtureTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/liferay/language/jsp/LiferayTaglibRenderCommandNameReferenceContributorTest";

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

    public void testByParamName() {
        myFixture.configureByFiles(
            "paramRenderUrl.jsp",
            "liferay-portlet-ext.tld",
            "de/dm/render/MyMVCRenderCommand.java",
            "com/liferay/portal/kernel/portlet/bridges/mvc/MVCRenderCommand.java"
        );

        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("/my/render"));
    }

}
