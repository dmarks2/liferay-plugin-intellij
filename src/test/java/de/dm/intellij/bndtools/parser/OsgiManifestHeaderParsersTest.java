package de.dm.intellij.bndtools.parser;

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

public class OsgiManifestHeaderParsersTest extends LightCodeInsightFixtureTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/bndtools/parser/OsgiManifestHeaderParsersTest";

    private static final LightProjectDescriptor JAVA_OSGI_DESCRIPTOR = new DefaultLightProjectDescriptor() {

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

            PsiTestUtil.addLibrary(model, "org.osgi:org.osgi.core", testDataPath, "org.osgi.core-6.0.0.jar");
        }
    };

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor() {
        return JAVA_OSGI_DESCRIPTOR;
    }

    @Override
    protected String getTestDataPath() {
        return TEST_DATA_PATH;
    }

    public void testBundleActivatorHighlighting() {
        myFixture.configureByFiles("testBundleActivatorHighlighting/bnd.bnd", "de/dm/Foo.java");

        myFixture.checkHighlighting(false, false, true, true);
    }

    public void testImportPackageHighlighting() {
        myFixture.configureByFiles("testImportPackageHighlighting/bnd.bnd", "de/dm/Foo.java");

        myFixture.checkHighlighting(false, false, true, true);
    }

    public void testExportPackageHighlighting() {
        myFixture.configureByFiles("testExportPackageHighlighting/bnd.bnd", "de/dm/Foo.java");

        myFixture.checkHighlighting(false, false, true, true);
    }
}