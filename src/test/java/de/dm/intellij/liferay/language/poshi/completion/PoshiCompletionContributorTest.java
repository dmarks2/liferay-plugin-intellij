package de.dm.intellij.liferay.language.poshi.completion;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

import java.util.List;

public class PoshiCompletionContributorTest extends BasePlatformTestCase {

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/poshi/completion/PoshiCompletionContributorTest";
    }

    public void testDefinitionRootCodeCompletion() {
        myFixture.configureByFiles("testcases/Liferay.testcase");

        myFixture.complete(CompletionType.BASIC, 1);

        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("definition"));
    }

    public void testStructureChildCodeCompletion() {
        myFixture.configureByFiles("testcases/DefinitionChild.testcase");

        myFixture.complete(CompletionType.BASIC, 1);

        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("setUp"));
    }
}
