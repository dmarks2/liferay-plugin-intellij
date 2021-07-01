package de.dm.intellij.liferay.language.sass;

import com.intellij.codeInsight.documentation.DocumentationManager;
import com.intellij.lang.documentation.DocumentationProvider;
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

public class LiferaySassClayVariablesDocumentationProviderTest extends BasePlatformTestCase {

    private static final LightProjectDescriptor MY_PROJECT_DESCRIPTOR = new DefaultLightProjectDescriptor() {

        @Override
        public void configureModule(@NotNull Module module, @NotNull ModifiableRootModel model, @NotNull ContentEntry contentEntry) {
            LiferayModuleComponent.getInstance(module).setLiferayVersion("7.0.6");

            URL resource = LiferayFtlVariableProviderTest.class.getResource("/ClayVariablesDocumentationBundle.properties");
            String resourcePath = PathUtil.toSystemIndependentName(new File(resource.getFile()).getAbsolutePath());
            VfsRootAccess.allowRootAccess(Disposer.newDisposable(), resourcePath );
        }

        @Override
        public Sdk getSdk() {
            return IdeaTestUtil.getMockJdk18();
        }
    };

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor() {
        return MY_PROJECT_DESCRIPTOR;
    }

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/sass/LiferaySassClayVariablesDocumentationProviderTest";
    }

    public void testScssVariableDeclarationDocumentationProvider() {
        myFixture.configureByFiles("custom.scss");

        DocumentationManager documentationManager = DocumentationManager.getInstance(getProject());
        PsiElement docElement = documentationManager.findTargetElement(myFixture.getEditor(), 1, myFixture.getFile(), null);

        DocumentationProvider provider = DocumentationManager.getProviderFromElement(docElement);

        String expectedDocumentation = "<font color=#808080>Globally enable/disable decorative box-shadows. This is `true` by default. If set to `false`, decorative box shadows cannot be re-implemented through variables.</font>\n" +
                "<br>\n" +
                "<b>enable-shadows</b> [custom.scss]";

        assertEquals("Should provide proper documentation for $enable-shadows variable declaration", expectedDocumentation, provider.generateDoc(docElement, null));
    }

}
