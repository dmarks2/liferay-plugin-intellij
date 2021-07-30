package de.dm.intellij.liferay.language.groovy;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GroovyScriptingImplicitMembersContributorTest extends LightJavaCodeInsightFixtureTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/liferay/language/groovy/GroovyScriptingImplicitMembersContributorTest";

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor() {
        return LightProjectDescriptorBuilder.DEFAULT_PROJECT_DESCRIPTOR;
    }


    @Override
    protected String getTestDataPath() {
        return TEST_DATA_PATH;
    }

    public void testGroovyImplicitVariables() {
        myFixture.configureByFiles("test.groovy");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("userInfo"));
    }

    public void testGroovyImplicitVariableMemberCodeCompletions() {
        myFixture.configureByFiles("member.groovy");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue("Code completion should offer out.close()", strings.contains("close"));
        assertFalse("Code completion should not offer implicit variables as members (like out.out)", strings.contains("out"));
    }

}
