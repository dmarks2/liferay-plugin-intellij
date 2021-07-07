package de.dm.intellij.liferay.language.sass;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.vfs.newvfs.impl.VfsRootAccess;
import com.intellij.psi.PsiElement;
import com.intellij.testFramework.IdeaTestUtil;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.intellij.testFramework.fixtures.DefaultLightProjectDescriptor;
import com.intellij.util.PathUtil;
import de.dm.intellij.liferay.language.freemarker.LiferayFtlVariableProviderTest;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.URL;

public class LiferayFrontendTokenDefinitionResourceBundleReferenceContributorTest extends BasePlatformTestCase {

    private static final LightProjectDescriptor MY_PROJECT_DESCRIPTOR = new DefaultLightProjectDescriptor() {

        @Override
        public void configureModule(@NotNull Module module, @NotNull ModifiableRootModel model, @NotNull ContentEntry contentEntry) {
            LiferayModuleComponent.getInstance(module).setLiferayVersion("7.0.6");

            URL resource = LiferayFrontendTokenDefinitionResourceBundleReferenceContributorTest.class.getResource("/com/liferay/schema");
            String resourcePath = PathUtil.toSystemIndependentName(new File(resource.getFile()).getAbsolutePath());
            VfsRootAccess.allowRootAccess( Disposer.newDisposable(), resourcePath );
        }

        @Override
        public Sdk getSdk() {
            return IdeaTestUtil.getMockJdk18();
        }
    };

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor() {
        return MY_PROJECT_DESCRIPTOR;
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/sass/LiferayFrontendTokenDefinitionResourceBundleReferenceContributorTest";
    }

    public void testReference() {
        myFixture.configureByFiles("WEB-INF/frontend-token-definition.json", "Language.properties");

        PsiElement element = myFixture.getFile().findElementAt(myFixture.getCaretOffset()).getParent();
        PsiElement resolve = element.getReferences()[0].resolve();

        assertTrue("\"primary\" should be resolvable, because it is in Language.properties", (resolve != null));
    }
}
