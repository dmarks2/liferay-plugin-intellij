package de.dm.intellij.liferay.schema;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LiferayXmlSchemaProviderTest extends BasePlatformTestCase {

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor() {
        return LightProjectDescriptorBuilder.XSD_ROOT_ACCESS_PROJECT_DESCRIPTOR;
    }

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/schema/LiferayXmlSchemaProviderTest";
    }

    public void testCompletion() {
        myFixture.configureByFiles("portlet-model-hints.xml");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("max-length"));
    }

    public void testPortletDisplayTemplatesCompletion() {
        myFixture.configureByFiles("portlet-display-templates.xml");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("template-key"));
    }

}
