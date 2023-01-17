package de.dm.intellij.liferay.language.poshi.injection;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

import java.util.List;

public class PoshiPathFileXPathInjectorTest extends BasePlatformTestCase {

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/poshi/injection/PoshiPathFileXPathInjectorTest";
    }
    public void testCompletion() {
        myFixture.configureByFile("paths/Liferay.path");

        myFixture.complete(CompletionType.BASIC, 1);

        List<String> strings = myFixture.getLookupElementStrings();

        assertTrue("<td> inside Poshi path file should support XPath expressions like text()", strings.contains("text()"));
    }
}
