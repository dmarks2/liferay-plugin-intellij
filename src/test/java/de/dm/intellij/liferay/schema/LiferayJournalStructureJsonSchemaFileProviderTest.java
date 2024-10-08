package de.dm.intellij.liferay.schema;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LiferayJournalStructureJsonSchemaFileProviderTest extends BasePlatformTestCase {

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor() {
        return LightProjectDescriptorBuilder.LIFERAY_SCHEMA_ROOT_ACCESS_PROJECT_DESCRIPTOR;
    }

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/schema/LiferayJournalStructureJsonSchemaFileProviderTest";
    }

    public void testCompletion() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/structures/test.json");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("text"));

        assertFalse("\"type\" should not contain \"rich_text\", because it is not schema 2.0", strings.contains("rich_text"));
    }

    public void testSiteInitializerCompletion() {
        myFixture.configureByFiles("site-initializer/ddm-structures/sample-structure.xml");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("text"));

        assertFalse("\"type\" should not contain \"rich_text\", because it is not schema 2.0", strings.contains("rich_text"));
    }
}
