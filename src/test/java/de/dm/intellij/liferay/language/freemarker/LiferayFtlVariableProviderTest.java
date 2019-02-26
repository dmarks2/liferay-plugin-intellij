package de.dm.intellij.liferay.language.freemarker;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.navigation.actions.GotoDeclarationAction;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.impl.JavaAwareProjectJdkTableImpl;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.LanguageLevelModuleExtension;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.newvfs.impl.VfsRootAccess;
import com.intellij.pom.java.LanguageLevel;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
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

    public void testStructureVariablesSimpleJson() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/simple.ftl", "WEB-INF/src/resources-importer/journal/structures/test.json");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("simple"));
    }

    public void testStructureVariablesRepeatableJson() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/repeatable.ftl", "WEB-INF/src/resources-importer/journal/structures/test.json");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("siblings"));
    }

    public void testStructureVariablesRepeatableJsonTemplateNodeAvailable() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/repeatable.ftl", "WEB-INF/src/resources-importer/journal/structures/test.json", "com/liferay/portal/kernel/templateparser/TemplateNode.java");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("getSiblings"));
    }

    public void testStructureVariablesNestedJson() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/parent.ftl", "WEB-INF/src/resources-importer/journal/structures/test.json");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("child"));
    }

    public void testStructureVariablesRepeatableParentJson() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/repeatableParent.ftl", "WEB-INF/src/resources-importer/journal/structures/test.json");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("repeatableChild"));
    }

    public void testStructureVariablesNestedRepeatableParentJson() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/nestedRepeatableParent.ftl", "WEB-INF/src/resources-importer/journal/structures/test.json");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("nested"));
    }

    public void testStructureVariablesSimpleXml() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/simple.ftl", "WEB-INF/src/resources-importer/journal/structures/test.xml");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("simple"));
    }

    public void testStructureVariablesRepeatableXml() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/repeatable.ftl", "WEB-INF/src/resources-importer/journal/structures/test.xml");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("siblings"));
    }

    public void testStructureVariablesRepeatableXmlTemplateNodeAvailable() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/repeatable.ftl", "WEB-INF/src/resources-importer/journal/structures/test.xml", "com/liferay/portal/kernel/templateparser/TemplateNode.java");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("getSiblings"));
    }

    public void testStructureVariablesNestedXml() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/parent.ftl", "WEB-INF/src/resources-importer/journal/structures/test.xml");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("child"));
    }

    public void testStructureVariablesRepeatableParentXml() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/repeatableParent.ftl", "WEB-INF/src/resources-importer/journal/structures/test.xml");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("repeatableChild"));
    }

    public void testStructureVariablesNestedRepeatableParentXml() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/nestedRepeatableParent.ftl", "WEB-INF/src/resources-importer/journal/structures/test.xml");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("nested"));
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

    public void testLayoutTemplateVariables() {
        myFixture.configureByFiles("layouttpl/custom/my_freemarker_layout_template.ftl");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("processor"));
    }

    public void testThemeSettingsVariables() {
        myFixture.configureByFiles("templates/theme_settings.ftl", "WEB-INF/liferay-look-and-feel.xml");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("mysetting"));
    }

    public void testThemeSettingsReferenceVariables() {
        myFixture.configureByFiles("templates/theme_reference.ftl", "WEB-INF/liferay-look-and-feel.xml");

        PsiElement element = myFixture.getFile().findElementAt(myFixture.getCaretOffset()).getParent();
        PsiElement resolve = element.getReferences()[0].resolve();

        assertTrue(resolve != null);

        PsiElement navigationElement = resolve.getNavigationElement();

        assertTrue(navigationElement != null);
        assertTrue(navigationElement instanceof PsiDirectory);

        VirtualFile virtualFile = ((PsiDirectory) resolve.getNavigationElement()).getVirtualFile();
        assertTrue(virtualFile != null);
        assertTrue(virtualFile.exists());
    }

    public void testServiceLocatorInJournalTemplate() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/service-locator.ftl", "WEB-INF/src/resources-importer/journal/structures/test.json", "com/liferay/portal/template/ServiceLocator.java", "de/dm/MyService.java");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("helloService"));
    }

    public void testServiceLocatorInApplicationDisplayTemplate() {
        myFixture.configureByFiles(
            "WEB-INF/src/resources-importer/templates/application_display/asset_category/adt-service-locator.ftl",
             "com/liferay/portal/template/ServiceLocator.java",
            "de/dm/MyService.java"
        );
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("helloService"));
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

    public void testServiceLocatorGotoDeclaration() {
        myFixture.configureByFiles(
            "WEB-INF/src/resources-importer/journal/templates/test/service-locator-goto-declaration.ftl",
            "WEB-INF/src/resources-importer/journal/structures/test.json",
            "com/liferay/portal/template/ServiceLocator.java",
            "com/liferay/portal/kernel/service/BaseLocalService.java",
            "com/liferay/portal/kernel/service/MyCustomService.java"
        );
        int caretOffset = myFixture.getCaretOffset();
        PsiElement targetElement = GotoDeclarationAction.findTargetElement(getProject(), myFixture.getEditor(), caretOffset);

        assertNotNull(targetElement);

        assertTrue(targetElement instanceof PsiClass);

        assertEquals("com.liferay.portal.kernel.service.MyCustomService", ((PsiClass) targetElement).getQualifiedName());
    }


    public void testEnumUtil() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/enum-util.ftl", "WEB-INF/src/resources-importer/journal/structures/test.json", "de/dm/MyEnum.java");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("FOO"));
    }

    public void testEnumUtilLookup() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/enum-util-lookup.ftl", "WEB-INF/src/resources-importer/journal/structures/test.json", "de/dm/MyEnum.java");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("de.dm.MyEnum"));
    }

    public void testStaticUtil() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/static-util.ftl", "WEB-INF/src/resources-importer/journal/structures/test.json", "de/dm/MyUtil.java");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue("Static method should be available", strings.contains("sayHello"));
   }

    public void testStaticUtilLookup() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/static-util-lookup.ftl", "WEB-INF/src/resources-importer/journal/structures/test.json", "de/dm/MyUtil.java");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("de.dm.MyUtil"));
    }

    public void testStaticFieldGetter() {
        myFixture.configureByFiles(
                "WEB-INF/src/resources-importer/journal/templates/test/static-field-getter.ftl",
                "WEB-INF/src/resources-importer/journal/structures/test.json",
                "com/liferay/portal/kernel/util/StaticFieldGetter.java",
                "com/liferay/portal/kernel/util/PortletKeys.java"
        );
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("length"));
    }

    public void testStaticFieldGetterGotoDeclaration() {
        myFixture.configureByFiles(
                "WEB-INF/src/resources-importer/journal/templates/test/static-field-getter-goto-declaration.ftl",
                "WEB-INF/src/resources-importer/journal/structures/test.json",
                "com/liferay/portal/kernel/util/StaticFieldGetter.java",
                "com/liferay/portal/kernel/util/PortletKeys.java"
        );

        int caretOffset = myFixture.getCaretOffset();
        PsiElement targetElement = GotoDeclarationAction.findTargetElement(getProject(), myFixture.getEditor(), caretOffset);

        assertNotNull(targetElement);

        assertTrue(targetElement instanceof PsiField);

        assertEquals("ADMIN_PLUGINS", ((PsiField) targetElement).getName());
    }

    public void testStaticFieldGetterClassNameUtilLookup() {
        myFixture.configureByFiles(
            "WEB-INF/src/resources-importer/journal/templates/test/static-field-getter-class-name-lookup.ftl",
            "WEB-INF/src/resources-importer/journal/structures/test.json",
            "com/liferay/portal/kernel/util/StaticFieldGetter.java",
            "com/liferay/portal/kernel/util/PortletKeys.java"
        );
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("com.liferay.portal.kernel.util.PortletKeys"));
    }

    public void testStaticFieldGetterFieldNameUtilLookup() {
        myFixture.configureByFiles(
            "WEB-INF/src/resources-importer/journal/templates/test/static-field-getter-field-name-lookup.ftl",
            "WEB-INF/src/resources-importer/journal/structures/test.json",
            "com/liferay/portal/kernel/util/StaticFieldGetter.java",
            "com/liferay/portal/kernel/util/PortletKeys.java"
        );
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("ADMIN_PLUGINS"));
    }




}