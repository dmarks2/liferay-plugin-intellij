package de.dm.intellij.liferay.resourcesimporter;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LiferayResourcesImporterPlaceholderReferenceContributorTest extends BasePlatformTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/liferay/resourcesimporter/LiferayResourcesImporterPlaceholderReferenceContributorTest";

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor() {
        return LightProjectDescriptorBuilder.DEFAULT_PROJECT_DESCRIPTOR;
    }

    @Override
    protected String getTestDataPath() {
        return TEST_DATA_PATH;
    }

    public void testWebContentFileReference() {
        myFixture.configureByFile("WEB-INF/src/resources-importer/journal/articles/Basic Web Content/Text and Image.xml");

        myFixture.copyFileToProject("WEB-INF/src/resources-importer/document_library/documents/footer/footer.png", "WEB-INF/src/resources-importer/document_library/documents/footer/footer.png");

        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("footer.png"));
    }

    public void testWebContentLayoutReference() {
        myFixture.configureByFile("WEB-INF/src/resources-importer/journal/articles/Basic Web Content/Text and Link.xml");

        myFixture.copyFileToProject("WEB-INF/src/resources-importer/sitemap.json", "WEB-INF/src/resources-importer/sitemap.json");

        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("/home"));
    }

}
