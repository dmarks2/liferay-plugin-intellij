package de.dm.intellij.liferay.language;

import com.intellij.codeInsight.template.TemplateActionContext;
import com.intellij.codeInsight.template.impl.TemplateImpl;
import com.intellij.codeInsight.template.impl.TemplateManagerImpl;
import com.intellij.psi.PsiFile;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

import java.util.List;

public class LiferayApplicationDisplayTemplateLiveTemplatesProviderTest extends BasePlatformTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/liferay/language/freemarker/LiferayApplicationDisplayTemplateLiveTemplatesProviderTest";

    @Override
    protected String getTestDataPath() {
        return TEST_DATA_PATH;
    }

    public void testJournalTemplatesLiveTemplatesCodeCompletion() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/templates/application_display/asset_entry/asset-entry-template.ftl");

        PsiFile psiFile = myFixture.getFile();

        TemplateActionContext templateActionContext = TemplateActionContext.create(psiFile, null, 0, 0, false);

        final List<TemplateImpl> availableTemplates = TemplateManagerImpl.listApplicableTemplates(templateActionContext);

        assertTrue(availableTemplates.stream().anyMatch(t -> "AssetEntryLocalService".equals(t.getKey())));
    }
}
