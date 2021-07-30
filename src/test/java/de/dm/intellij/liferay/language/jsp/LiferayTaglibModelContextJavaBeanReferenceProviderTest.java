package de.dm.intellij.liferay.language.jsp;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LiferayTaglibModelContextJavaBeanReferenceProviderTest extends LightJavaCodeInsightFixtureTestCase {

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor() {
        return LightProjectDescriptorBuilder.DEFAULT_PROJECT_DESCRIPTOR;
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/jsp/LiferayTaglibModelContextJavaBeanReferenceProviderTest";
    }

    public void testJavaBeanReferenceLookupByString() {
        myFixture.configureByFiles(
            "view.jsp",
            "liferay-aui.tld",
            "de/dm/model/MyModel.java"
        );
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("description"));
    }

    public void testJavaBeanReferenceLookupModelAttribute() {
        myFixture.configureByFiles(
            "model-attribute.jsp",
            "liferay-aui.tld",
            "de/dm/model/MyModel.java"
        );
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("description"));
    }

    public void testJavaBeanReferenceLookupModelAttributeOrder() {
        myFixture.configureByFiles(
            "model-attribute-precedence.jsp",
            "liferay-aui.tld",
            "de/dm/model/MyModel.java",
            "de/dm/model/MySecondModel.java"
        );
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue("model attribute should take precedence before model-context tag", strings.contains("secondaryDescription"));
    }

}
