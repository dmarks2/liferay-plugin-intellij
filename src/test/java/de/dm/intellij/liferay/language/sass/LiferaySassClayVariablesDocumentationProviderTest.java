package de.dm.intellij.liferay.language.sass;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.documentation.DocumentationManager;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.lang.documentation.DocumentationProvider;
import com.intellij.psi.PsiElement;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

public class LiferaySassClayVariablesDocumentationProviderTest extends BasePlatformTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor() {
        return new LightProjectDescriptorBuilder()
                .liferayVersion("7.0.6")
                .rootAccess("/ClayVariablesDocumentationBundle.properties")
                .build();
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

        String expectedDocumentation = "<div class='definition'><pre>enable-shadows</pre></div><div class='content'>Globally enable/disable decorative box-shadows. This is `true` by default. If set to `false`, decorative box shadows cannot be re-implemented through variables.</div>";

        assertEquals("Should provide proper documentation for $enable-shadows variable declaration", expectedDocumentation, provider.generateDoc(docElement, null));
    }

    public void testScssVariableCompletionDocumentationLookup() {
        myFixture.configureByFiles("variables.scss");
        myFixture.complete(CompletionType.BASIC, 1);

        LookupElement[] lookupElements = myFixture.getLookupElements();
        for (LookupElement lookupElement : lookupElements) {
            if (lookupElement.getLookupString().equals("enable-shadows")) {
                PsiElement elementFromLookup = DocumentationManager.getElementFromLookup(myFixture.getProject(), myFixture.getEditor(), myFixture.getFile(), lookupElement);

                assertNotNull(elementFromLookup);
            }
        }
    }

}
