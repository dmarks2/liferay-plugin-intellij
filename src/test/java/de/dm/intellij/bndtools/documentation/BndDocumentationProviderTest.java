package de.dm.intellij.bndtools.documentation;

import com.intellij.codeInsight.documentation.DocumentationManager;
import com.intellij.lang.documentation.DocumentationProvider;
import com.intellij.psi.PsiElement;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

public class BndDocumentationProviderTest extends BasePlatformTestCase {

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/bndtools/documentation/BndDocumentationProviderTest";
    }

    public void testLineCommentHighlighting() {
        myFixture.configureByFiles("bnd.bnd");

        DocumentationManager documentationManager = DocumentationManager.getInstance(getProject());
        PsiElement docElement = documentationManager.findTargetElement(myFixture.getEditor(), myFixture.getCaretOffset(), myFixture.getFile(), null);

        DocumentationProvider provider = DocumentationManager.getProviderFromElement(docElement);

        String expectedDocumentation = "<div class='definition'><pre>Bundle-SymbolicName</pre></div><div class='content'>The Bundle-SymbolicName header specifies a non-localizable name for this bundle. The bundle symbolic name together with a version must identify a unique bundle though it can be installed multiple times in a framework. The bundle symbolic name should be based on the reverse domain name convention, s.</div>";

        assertEquals("Should provide proper documentation for Bundle-SymbolicName", expectedDocumentation, provider.generateDoc(docElement, docElement.getOriginalElement()));

    }
}
