package de.dm.intellij.liferay.index;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.impl.JavaAwareProjectJdkTableImpl;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.LanguageLevelModuleExtension;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.vfs.newvfs.impl.VfsRootAccess;
import com.intellij.pom.java.LanguageLevel;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.PsiTestUtil;
import com.intellij.testFramework.fixtures.DefaultLightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import com.intellij.util.PathUtil;
import com.intellij.util.indexing.FileBasedIndex;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

public class PortletNameIndexTest extends LightJavaCodeInsightFixtureTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/liferay/index/PortletNameIndexTest";

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

            Disposable disposable = Disposer.newDisposable();
            try {
                VfsRootAccess.allowRootAccess(disposable, testDataPath);
            } finally {
                Disposer.dispose(disposable);
            }

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
