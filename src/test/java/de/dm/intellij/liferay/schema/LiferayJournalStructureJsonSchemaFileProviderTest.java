package de.dm.intellij.liferay.schema;

import com.intellij.testFramework.fixtures.BasePlatformTestCase;

public class LiferayJournalStructureJsonSchemaFileProviderTest extends BasePlatformTestCase {

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/schema/LiferayJournalStructureJsonSchemaFileProviderTest";
    }

    //currently ignored due to https://youtrack.jetbrains.com/issue/IDEA-221552
    public void testCompletion() {
        /*
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/structures/test.json");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("text"));
         */
    }
}
