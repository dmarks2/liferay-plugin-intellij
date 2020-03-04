package de.dm.intellij.liferay.language.freemarker;

import com.intellij.codeInsight.template.impl.TemplateImpl;
import com.intellij.codeInsight.template.impl.TemplateManagerImpl;
import com.intellij.psi.PsiFile;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

import java.util.List;

public class LiferayJournalTemplateLiveTemplatesProviderTest extends BasePlatformTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/liferay/language/freemarker/LiferayJournalTemplateLiveTemplatesProviderTest";

    @Override
    protected String getTestDataPath() {
        return TEST_DATA_PATH;
    }

    public void testJournalTemplatesLiveTemplatesCodeCompletion() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/simple.ftl");

        PsiFile psiFile = myFixture.getFile();
        final List<TemplateImpl> availableTemplates = TemplateManagerImpl.listApplicableTemplates(psiFile, 0, false);

        assertTrue(availableTemplates.stream().anyMatch(t -> "JournalArticleLocalService".equals(t.getKey())));
    }
}
