package de.dm.intellij.liferay.language.xml;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LiferayXmlFileReferenceContributorTest extends BasePlatformTestCase {

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor() {
        return LightProjectDescriptorBuilder.XSD_ROOT_ACCESS_PROJECT_DESCRIPTOR;
    }

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/liferay/language/xml/LiferayXmlFileReferenceContributorTest";

    @Override
    protected String getTestDataPath() {
        return TEST_DATA_PATH;
    }

    public void testXmlTagFileReference() {
        myFixture.configureByFiles("liferay-hook.xml", "custom_jsps/foo.txt");

        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("custom_jsps"));
    }

    public void testXmlAttributeFileReference() {
        myFixture.configureByFiles("default.xml", "my_resources.xml");

        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("my_resources.xml"));
    }

    public void testXmlAttributeRelativePathFileReference() {
        myFixture.configureByFiles("META-INF/resource-actions/default.xml", "META-INF/resource-actions/custom.xml");

        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("custom.xml"));
    }

    public void testPortletDisplayTemplateXmlTagFileReference() {
        myFixture.configureByFiles("portlet-display-templates.xml", "files/foo.ftl");

        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("files"));
    }
}
