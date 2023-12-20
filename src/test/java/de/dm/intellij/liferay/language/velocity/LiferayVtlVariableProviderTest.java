package de.dm.intellij.liferay.language.velocity;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import de.dm.intellij.liferay.theme.LiferayLookAndFeelXmlParser;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LiferayVtlVariableProviderTest extends BasePlatformTestCase {

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor() {
        return
                new LightProjectDescriptorBuilder()
                        .rootAccess(
                                "/com/liferay/vtl",
                                "/com/liferay/tld",
                                "/com/liferay/vtl/VM_liferay_70.vm"
                        )
                        .liferayVersion("7.0.6")
                        .themeSettings(LiferayLookAndFeelXmlParser.TEMPLATES_PATH, "/templates")
                        .themeSettings(LiferayLookAndFeelXmlParser.TEMPLATE_EXTENSION, "vm")
                        .build();
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/velocity/LiferayVtlVariableProviderTest";
    }

    public void testStructureVariablesSimpleJson() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/simple.vm", "WEB-INF/src/resources-importer/journal/structures/test.json");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("simple"));
    }

    public void testStructureVariablesNestedJson() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/parent.vm", "WEB-INF/src/resources-importer/journal/structures/test.json");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("child"));
    }

    public void testStructureVariablesSimpleXml() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/simple.vm", "WEB-INF/src/resources-importer/journal/structures/test.xml");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("simple"));
    }

    public void testStructureVariablesNestedXml() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/parent.vm", "WEB-INF/src/resources-importer/journal/structures/test.xml");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("child"));
    }

    //TODO repeatable and nested repeatable structure variables?

    public void testJournalReservedVariables() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/journal-reserved-variables.vm", "WEB-INF/src/resources-importer/journal/structures/test.json");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("reserved-article-id"));
    }

    public void testADTAssetEntryVariables() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/templates/application_display/asset_entry/asset-entry-template.vm");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("assetPublisherHelper"));
    }

    public void testThemeTemplateVariables() {
        myFixture.configureByFiles("templates/portal_normal.vm");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("company_name"));
    }

    public void testLayoutTemplateVariablesTpl() {
        myFixture.configureByFiles("layouttpl/custom/my_liferay_layout_template.tpl");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("processor"));
    }

    public void testLayoutTemplateVariablesVm() {
        myFixture.configureByFiles("layouttpl/custom/my_velocity_layout_template.vm");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("processor"));
    }

    public void testThemeSettingsVariables() {
        myFixture.configureByFiles("templates/theme_settings.vm", "WEB-INF/liferay-look-and-feel.xml");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("mysetting"));
    }

    public void testThemeSettingsReferenceVariables() {
        myFixture.configureByFiles("templates/theme_reference.vm", "WEB-INF/liferay-look-and-feel.xml");

        PsiElement caretElement = myFixture.getFile().findElementAt(myFixture.getCaretOffset());

        assertNotNull(caretElement);

        PsiElement element = caretElement.getParent();
        PsiElement resolve = element.getReferences()[0].resolve();

		assertNotNull(resolve);

        PsiElement navigationElement = resolve.getNavigationElement();

		assertNotNull(navigationElement);
        assertTrue(navigationElement instanceof PsiDirectory);

        VirtualFile virtualFile = ((PsiDirectory) resolve.getNavigationElement()).getVirtualFile();
		assertNotNull(virtualFile);
        assertTrue(virtualFile.exists());
    }


}
