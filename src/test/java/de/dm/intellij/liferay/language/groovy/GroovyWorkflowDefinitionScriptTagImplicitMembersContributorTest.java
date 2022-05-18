package de.dm.intellij.liferay.language.groovy;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GroovyWorkflowDefinitionScriptTagImplicitMembersContributorTest extends LightJavaCodeInsightFixtureTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/liferay/language/groovy/GroovyWorkflowDefinitionScriptTagImplicitMembersContributorTest";

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor() {
        return LightProjectDescriptorBuilder.DEFAULT_PROJECT_DESCRIPTOR;
    }


    @Override
    protected String getTestDataPath() {
        return TEST_DATA_PATH;
    }

    public void testGroovyInputImplicitVariables() {
        myFixture.configureByFiles("workflow-definition-groovy.xml");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("taskName"));
    }

    public void testGroovyOutputImplicitVariables() {
        myFixture.configureByFiles("workflow-definition-scripted-assignment-groovy.xml");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue("Workflow groovy script for <scripted-assignment> should offer output variable \"roles\"", strings.contains("roles"));
    }

}
