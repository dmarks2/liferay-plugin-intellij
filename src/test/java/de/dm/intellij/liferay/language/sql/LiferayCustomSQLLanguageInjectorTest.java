package de.dm.intellij.liferay.language.sql;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

import java.util.List;

public class LiferayCustomSQLLanguageInjectorTest extends BasePlatformTestCase {

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/sql/LiferayCustomSQLLanguageInjectorTest";
    }

    public void testSqlInjection() {
        myFixture.configureByFiles("custom-sql/default.xml");

        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("SELECT"));
    }

}
