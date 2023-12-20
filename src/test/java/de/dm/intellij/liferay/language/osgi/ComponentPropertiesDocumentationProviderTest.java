package de.dm.intellij.liferay.language.osgi;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.documentation.DocumentationManager;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.lang.documentation.DocumentationProvider;
import com.intellij.psi.PsiElement;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

public class ComponentPropertiesDocumentationProviderTest extends LightJavaCodeInsightFixtureTestCase {

    private static final String EXPECTED_DOCUMENTATION_JAVAX_PORTLET_INFO_TITLE = "<div class='definition'><pre>javax.portlet.info.title</pre></div><div class='content'>Locale specific static title for this portlet.</div>";

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

        assertNotNull(docElement);

        DocumentationProvider provider = DocumentationManager.getProviderFromElement(docElement);

        assertEquals("Should provide proper documentation for javax.portlet.info.title inside a javax.portlet.Portlet component", EXPECTED_DOCUMENTATION_JAVAX_PORTLET_INFO_TITLE, provider.generateDoc(docElement, docElement.getOriginalElement()));
    }

    public void testUnknownDocumentation() {
        myFixture.configureByFiles("MyUnknownPropertyComponent.java", "javax/portlet/Portlet.java", "org/osgi/service/component/annotations/Component.java");

        DocumentationManager documentationManager = DocumentationManager.getInstance(getProject());
        PsiElement docElement = documentationManager.findTargetElement(myFixture.getEditor(), 149, myFixture.getFile(), null);

        assertNotNull(docElement);

        DocumentationProvider provider = DocumentationManager.getProviderFromElement(docElement);

		assertNull("Should provide no documentation for unknown.property inside a javax.portlet.Portlet component", provider.generateDoc(docElement, docElement.getOriginalElement()));
    }

    public void testComponentPropertiesCompletionDocumentation() {
        myFixture.configureByFiles("MyComponentLookup.java", "javax/portlet/Portlet.java", "org/osgi/service/component/annotations/Component.java");

        myFixture.complete(CompletionType.BASIC, 1);

        LookupElement[] lookupElements = myFixture.getLookupElements();

        if (lookupElements != null) {
            for (LookupElement lookupElement : lookupElements) {
                if (lookupElement.getLookupString().equals("javax.portlet.info.title")) {
                    PsiElement elementFromLookup = DocumentationManager.getElementFromLookup(myFixture.getProject(), myFixture.getEditor(), myFixture.getFile(), lookupElement);

                    assertNotNull(elementFromLookup);

                    DocumentationProvider provider = DocumentationManager.getProviderFromElement(elementFromLookup);

                    assertEquals("Should provide proper documentation for javax.portlet.info.title inside a javax.portlet.Portlet component in code completion lookup", EXPECTED_DOCUMENTATION_JAVAX_PORTLET_INFO_TITLE, provider.generateDoc(elementFromLookup, null));
                }
            }
        }
    }
}
