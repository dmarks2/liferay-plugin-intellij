package de.dm.intellij.liferay.language.properties;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.documentation.DocumentationManager;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.lang.documentation.DocumentationProvider;
import com.intellij.psi.PsiElement;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

public class LiferayPortalPropertiesDocumentationProviderTest extends BasePlatformTestCase {

    private static final String EXPECTED_DOCUMENTATION_LIFERAY_HOME = "<div class='definition'><pre>liferay.home</pre></div><div class='content'>Specify the Liferay home directory.<br></div>" +
            "<table class='sections'><p>" +
            "<tr><td valign='top' class='section'><p>Env: </td><td valign='top'>LIFERAY_LIFERAY_PERIOD_HOME</td>" +
            "<tr><td valign='top' class='section'><p>Default: </td><td valign='top'>${resource.repositories.root}</td>" +
            "</table>";


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

        assertEquals("Should provide proper documentation for liferay.home property", EXPECTED_DOCUMENTATION_LIFERAY_HOME, provider.generateDoc(docElement, null));
    }

    public void testPortalPropertiesCodeCompletion() {
        myFixture.configureByFiles("portal-setup-wizard.properties");
        myFixture.complete(CompletionType.BASIC, 1);

        LookupElement[] lookupElements = myFixture.getLookupElements();
        for (LookupElement lookupElement : lookupElements) {
            if (lookupElement.getLookupString().equals("liferay.home")) {
                PsiElement elementFromLookup = DocumentationManager.getElementFromLookup(myFixture.getProject(), myFixture.getEditor(), myFixture.getFile(), lookupElement);

                assertNotNull(elementFromLookup);

                DocumentationProvider provider = DocumentationManager.getProviderFromElement(elementFromLookup);

                assertEquals("Should provide proper documentation for liferay.home property in code completion lookup", EXPECTED_DOCUMENTATION_LIFERAY_HOME, provider.generateDoc(elementFromLookup, null));
            }
        }
    }

}
