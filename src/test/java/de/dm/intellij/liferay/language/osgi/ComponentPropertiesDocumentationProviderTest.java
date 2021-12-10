package de.dm.intellij.liferay.language.osgi;

import com.intellij.codeInsight.documentation.DocumentationManager;
import com.intellij.lang.documentation.DocumentationProvider;
import com.intellij.psi.PsiElement;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

public class ComponentPropertiesDocumentationProviderTest extends LightJavaCodeInsightFixtureTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor() {
        return LightProjectDescriptorBuilder.DEFAULT_PROJECT_DESCRIPTOR;
    }

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/osgi/ComponentPropertiesDocumentationProviderTest";
    }

    public void testKnownDocumentation() {
        myFixture.configureByFiles("MyComponent.java", "javax/portlet/Portlet.java", "org/osgi/service/component/annotations/Component.java");

        DocumentationManager documentationManager = DocumentationManager.getInstance(getProject());
        PsiElement docElement = documentationManager.findTargetElement(myFixture.getEditor(), 149, myFixture.getFile(), null);

        DocumentationProvider provider = DocumentationManager.getProviderFromElement(docElement);

        String expectedDocumentation = "<b>javax.portlet.info.title</b><br/>\nLocale specific static title for this portlet.";

        assertEquals("Should provide proper documentation for javax.portlet.info.title inside a javax.portlet.Portlet component", expectedDocumentation, provider.generateDoc(docElement, docElement.getOriginalElement()));
    }

    public void testUnknownDocumentation() {
        myFixture.configureByFiles("MyUnknownPropertyComponent.java", "javax/portlet/Portlet.java", "org/osgi/service/component/annotations/Component.java");

        DocumentationManager documentationManager = DocumentationManager.getInstance(getProject());
        PsiElement docElement = documentationManager.findTargetElement(myFixture.getEditor(), 149, myFixture.getFile(), null);

        DocumentationProvider provider = DocumentationManager.getProviderFromElement(docElement);

        assertEquals("Should provide no documentation for unknown.property inside a javax.portlet.Portlet component", null, provider.generateDoc(docElement, docElement.getOriginalElement()));
    }
}
