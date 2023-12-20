package de.dm.intellij.liferay.language.sass;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

import java.util.List;

public class LiferayFrontendTokenDefinitionCssVariableReferenceProviderTest extends BasePlatformTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/liferay/language/sass/LiferayFrontendTokenDefinitionCssVariableReferenceProviderTest";

    @Override
    protected String getTestDataPath() {
        return TEST_DATA_PATH;
    }

    public void testReference() {
        myFixture.configureByFiles("sample.css", "WEB-INF/frontend-token-definition.json");

        PsiElement element = myFixture.getFile().findElementAt(myFixture.getCaretOffset());

        boolean referenceResolved = false;

        if (element != null) {
            PsiReference[] references = element.getReferences();

            for (PsiReference reference : references) {
                if (reference instanceof LiferayFrontendTokenDefinitionVariableReference) {
                    PsiElement resolve = reference.resolve();

                    assertTrue("\"--primary\" should be resolvable, because it is in frontend-token-definition.json", (resolve != null));

                    referenceResolved = true;
                }
            }
        }

        assertTrue(referenceResolved);
    }

    public void testCompletion() {
        myFixture.configureByFiles("completion.css", "WEB-INF/frontend-token-definition.json");

        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("--primary"));
    }

}
