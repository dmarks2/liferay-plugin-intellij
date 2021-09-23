package de.dm.intellij.liferay.workflow;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

import java.util.List;

public class LiferayWorkflowScriptLanguageInjectorTest extends BasePlatformTestCase {

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/workflow/LiferayWorkflowScriptLanguageInjectorTest";
    }

    public void testScriptGroovy() {
        myFixture.configureByFiles("workflow-definition-groovy.xml");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("def"));
    }

    public void testTemplateFreemarker() {
        myFixture.configureByFiles("workflow-definition-freemarker.xml");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("assign"));
    }

}
