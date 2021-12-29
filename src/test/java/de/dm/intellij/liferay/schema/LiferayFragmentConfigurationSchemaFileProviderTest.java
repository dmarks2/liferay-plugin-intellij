package de.dm.intellij.liferay.schema;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;

import java.util.List;

public class LiferayFragmentConfigurationSchemaFileProviderTest extends BasePlatformTestCase {

    protected LightProjectDescriptor getProjectDescriptor() {
        return
                new LightProjectDescriptorBuilder()
                        .rootAccess(
                                "/com/liferay/schema",
                                "/com/liferay/schema/configuration-json-schema.json")
                        .build();
    }

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/schema/LiferayFragmentConfigurationSchemaFileProviderTest";
    }

    public void testCompletion() {
        myFixture.configureByFiles("configuration.json", "fragment.json");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("\"fieldSets\""));
    }
}
