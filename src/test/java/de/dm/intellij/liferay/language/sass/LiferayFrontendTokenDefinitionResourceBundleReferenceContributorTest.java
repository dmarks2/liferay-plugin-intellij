package de.dm.intellij.liferay.language.sass;

import com.intellij.psi.PsiElement;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

public class LiferayFrontendTokenDefinitionResourceBundleReferenceContributorTest extends BasePlatformTestCase {

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor() {
        return LightProjectDescriptorBuilder.LIFERAY_SCHEMA_ROOT_ACCESS_PROJECT_DESCRIPTOR;
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
