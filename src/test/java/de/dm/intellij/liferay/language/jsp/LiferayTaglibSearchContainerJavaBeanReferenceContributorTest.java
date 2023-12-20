package de.dm.intellij.liferay.language.jsp;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LiferayTaglibSearchContainerJavaBeanReferenceContributorTest extends LightJavaCodeInsightFixtureTestCase {

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
        return "testdata/de/dm/intellij/liferay/language/jsp/LiferayTaglibSearchContainerJavaBeanReferenceContributorTest";
    }

    public void testJavaBeanReferenceLookup() {
        myFixture.configureByFiles(
                "view.jsp",
                "liferay-ui.tld",
                "de/dm/model/MyModel.java"
        );
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("description"));
    }

}
