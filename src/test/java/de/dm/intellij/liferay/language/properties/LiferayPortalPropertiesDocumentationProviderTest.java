package de.dm.intellij.liferay.language.properties;

import com.intellij.codeInsight.documentation.DocumentationManager;
import com.intellij.lang.documentation.DocumentationProvider;
import com.intellij.psi.PsiElement;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

public class LiferayPortalPropertiesDocumentationProviderTest extends BasePlatformTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor() {
        return new LightProjectDescriptorBuilder()
                .liferayVersion("7.0.6")
                .rootAccess("/com/liferay/properties/70/portal.properties")
                .build();
    }

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/properties/LiferayPortalPropertiesDocumentationProviderTest";
    }

    public void testPortalPropertiesDocumentationProvider() {
        myFixture.configureByFiles("portal-ext.properties");

        DocumentationManager documentationManager = DocumentationManager.getInstance(getProject());
        PsiElement docElement = documentationManager.findTargetElement(myFixture.getEditor(), myFixture.getFile());

        DocumentationProvider provider = DocumentationManager.getProviderFromElement(docElement);

        String expectedDocumentation = "<font color=#808080>Specify the Liferay home directory.<br>Env: LIFERAY_LIFERAY_PERIOD_HOME</font>\n" +
                "<br>\n" +
                "<b>liferay.home</b>=\"/foo\" [portal-ext.properties]";

        assertEquals("Should provide proper documentation for liferay.home property", expectedDocumentation, provider.generateDoc(docElement, null));
    }

}
