package de.dm.intellij.liferay.language.freemarker;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.impl.JavaAwareProjectJdkTableImpl;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.LanguageLevelModuleExtension;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.vfs.newvfs.impl.VfsRootAccess;
import com.intellij.pom.java.LanguageLevel;
import com.intellij.testFramework.IdeaTestUtil;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.DefaultLightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import com.intellij.util.PathUtil;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
import de.dm.intellij.liferay.theme.LiferayLookAndFeelXmlParser;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.URL;
import java.util.List;

public class LiferayFtlVariableProviderTest extends LightCodeInsightFixtureTestCase {

    private static final LightProjectDescriptor MY_PROJECT_DESCRIPTOR = new DefaultLightProjectDescriptor() {

        @Override
        public void configureModule(@NotNull Module module, @NotNull ModifiableRootModel model, @NotNull ContentEntry contentEntry) {
            LanguageLevelModuleExtension extension = model.getModuleExtension(LanguageLevelModuleExtension.class);
            if (extension != null) {
                extension.setLanguageLevel(LanguageLevel.JDK_1_8);
            }

            Sdk jdk = JavaAwareProjectJdkTableImpl.getInstanceEx().getInternalJdk();
            model.setSdk(jdk);

            LiferayModuleComponent.getInstance(module).setLiferayVersion("7.0.6");
            LiferayModuleComponent.getInstance(module).getThemeSettings().put(LiferayLookAndFeelXmlParser.TEMPLATES_PATH, "/templates");
            LiferayModuleComponent.getInstance(module).getThemeSettings().put(LiferayLookAndFeelXmlParser.TEMPLATE_EXTENSION, "ftl");

            URL resource = LiferayFtlVariableProviderTest.class.getResource("/com/liferay/ftl");
            String resourcePath = PathUtil.toSystemIndependentName(new File(resource.getFile()).getAbsolutePath());
            VfsRootAccess.allowRootAccess( resourcePath );

            resource = LiferayFtlVariableProviderTest.class.getResource("/com/liferay/tld");
            resourcePath = PathUtil.toSystemIndependentName(new File(resource.getFile()).getAbsolutePath());
            VfsRootAccess.allowRootAccess( resourcePath );

        }

        @Override
        public Sdk getSdk() {
            return IdeaTestUtil.getMockJdk18();
        }
    };

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor() {
        return MY_PROJECT_DESCRIPTOR;
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/freemarker/LiferayFtlVariableProviderTest";
    }

    public void testStructureVariablesSimple() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/simple.ftl", "WEB-INF/src/resources-importer/journal/structures/test.json");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("simple"));
    }

    public void testStructureVariablesRepeatable() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/repeatable.ftl", "WEB-INF/src/resources-importer/journal/structures/test.json");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("siblings"));
    }

    public void testStructureVariablesNested() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/parent.ftl", "WEB-INF/src/resources-importer/journal/structures/test.json");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("child"));
    }

    public void testStructureVariablesRepeatableParent() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/repeatableParent.ftl", "WEB-INF/src/resources-importer/journal/structures/test.json");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("repeatableChild"));
    }

    public void testJournalReservedVariables() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/journal-reserved-variables.ftl", "WEB-INF/src/resources-importer/journal/structures/test.json");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("reserved-article-id"));
    }

    public void testADTAssetEntryVariables() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/templates/application_display/asset_entry/asset-entry-template.ftl");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("assetPublisherHelper"));
    }

    public void testThemeTemplateVariables() {
        myFixture.configureByFiles("templates/portal_normal.ftl");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("company_name"));
    }

    public void testThemeSettingsVariables() {
        myFixture.configureByFiles("templates/theme_settings.ftl", "WEB-INF/liferay-look-and-feel.xml");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("mysetting"));
    }

    public void testServiceLocator() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/service-locator.ftl", "WEB-INF/src/resources-importer/journal/structures/test.json", "com/liferay/portal/template/ServiceLocator.java");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("toString"));
    }

    public void testServiceLocatorClassNameLookup() {
        myFixture.configureByFiles(
            "WEB-INF/src/resources-importer/journal/templates/test/service-locator-classname.ftl",
            "WEB-INF/src/resources-importer/journal/structures/test.json",
            "com/liferay/portal/template/ServiceLocator.java",
            "com/liferay/portal/kernel/service/BaseLocalService.java",
            "com/liferay/portal/kernel/service/MyCustomService.java"
        );
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("com.liferay.portal.kernel.service.MyCustomService"));
    }


}