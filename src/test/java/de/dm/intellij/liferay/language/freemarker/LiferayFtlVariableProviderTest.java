package de.dm.intellij.liferay.language.freemarker;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.navigation.actions.GotoDeclarationAction;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import de.dm.intellij.liferay.theme.LiferayLookAndFeelXmlParser;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LiferayFtlVariableProviderTest extends LightJavaCodeInsightFixtureTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/liferay/language/freemarker/LiferayFtlVariableProviderTest";

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor() {
        return new LightProjectDescriptorBuilder()
                .rootAccess(
                        "/com/liferay/vtl",
                        "/com/liferay/ftl",
                        "/com/liferay/tld"
                )
                .liferayVersion("7.0.6")
                .themeSettings(LiferayLookAndFeelXmlParser.TEMPLATES_PATH, "/templates")
                .themeSettings(LiferayLookAndFeelXmlParser.TEMPLATE_EXTENSION, "ftl")
                .library("freemarker-other", TEST_DATA_PATH, "freemarker-other.jar")
                .build();
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected String getTestDataPath() {
        return TEST_DATA_PATH;
    }

    public void testStructureVariablesSimpleJson() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/simple.ftl", "WEB-INF/src/resources-importer/journal/structures/test.json");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("simple"));
    }

    public void testStructureVariablesSimpleJson_Schema_2_0() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test_2_0/simple.ftl", "WEB-INF/src/resources-importer/journal/structures/test_2_0.json");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue("Should provide fieldReference \"myReference\", because it is schema version 2.0", strings.contains("myReference"));
        assertFalse("Should not provide name \"simple\", because it is schema version 2.0",  strings.contains("simple"));
    }

    public void testStructureVariablesSimpleJsonDataDefinitionSchema() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test_data_definition/simple.ftl", "WEB-INF/src/resources-importer/journal/structures/test_data_definition.json");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue("Should provide fieldReference \"myReference\" for structure based on data definition format", strings.contains("myReference"));
        assertFalse("Should not provide name \"simple\" for structure based on data definition format",  strings.contains("simple"));
    }

    public void testStructureVariablesSimpleJsonTemplateNode() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/simple-data.ftl", "WEB-INF/src/resources-importer/journal/structures/test.json", "com/liferay/portal/kernel/templateparser/TemplateNode.java");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue("Should offer 'simple.getData()' for structure field", strings.contains("data"));
    }

    public void testStructureVariablesRepeatableJson() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/repeatable.ftl", "WEB-INF/src/resources-importer/journal/structures/test.json");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("siblings"));
    }

    public void testStructureVariablesRepeatableJsonTemplateNodeAvailable() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/repeatable.ftl", "WEB-INF/src/resources-importer/journal/structures/test.json", "com/liferay/portal/kernel/templateparser/TemplateNode.java");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("getSiblings"));
    }

    public void testStructureVariablesNestedJson() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/parent.ftl", "WEB-INF/src/resources-importer/journal/structures/test.json", "com/liferay/portal/kernel/templateparser/TemplateNode.java");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue("Should offer nested field 'child'", strings.contains("child"));
        assertTrue("Should offer 'parent.getData()' for parent field", strings.contains("data"));
    }

    public void testStructureVariablesRepeatableParentJson() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/repeatableParent.ftl", "WEB-INF/src/resources-importer/journal/structures/test.json", "com/liferay/portal/kernel/templateparser/TemplateNode.java");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue("Should offer nested field 'repeatableChild' while iterating over the siblings of the parent field", strings.contains("repeatableChild"));
        assertTrue("Should offer 'repeatableChild.getData() while iterating over the siblings of the parent field", strings.contains("data"));
    }

    public void testStructureVariablesNestedRepeatableParentJson() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/nestedRepeatableParent.ftl", "WEB-INF/src/resources-importer/journal/structures/test.json", "com/liferay/portal/kernel/templateparser/TemplateNode.java");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue("Should offer deeply nested field 'nested' while iterating over the siblings of the parent field", strings.contains("nested"));
        assertTrue("Should offer 'repeatableChild.nestedRepeatableChild.data' while iterating over the siblings of the parent field", strings.contains("data"));
    }

    public void testStructureVariablesSimpleXml() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/simple.ftl", "WEB-INF/src/resources-importer/journal/structures/test.xml");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("simple"));
    }

    public void testStructureVariablesRepeatableXml() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/repeatable.ftl", "WEB-INF/src/resources-importer/journal/structures/test.xml");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("siblings"));
    }

    public void testStructureVariablesRepeatableXmlTemplateNodeAvailable() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/repeatable.ftl", "WEB-INF/src/resources-importer/journal/structures/test.xml", "com/liferay/portal/kernel/templateparser/TemplateNode.java");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("getSiblings"));
    }

    public void testStructureVariablesNestedXml() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/parent.ftl", "WEB-INF/src/resources-importer/journal/structures/test.xml");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("child"));
    }

    public void testStructureVariablesRepeatableParentXml() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/repeatableParent.ftl", "WEB-INF/src/resources-importer/journal/structures/test.xml");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("repeatableChild"));
    }

    public void testStructureVariablesNestedRepeatableParentXml() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/nestedRepeatableParent.ftl", "WEB-INF/src/resources-importer/journal/structures/test.xml");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("nested"));
    }


    public void testJournalReservedVariables() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/journal-reserved-variables.ftl", "WEB-INF/src/resources-importer/journal/structures/test.json");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("reserved-article-id"));
    }

    public void testADTAssetEntryVariables() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/templates/application_display/asset_entry/asset-entry-template.ftl");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("assetPublisherHelper"));
    }

    public void testThemeTemplateVariables() {
        myFixture.configureByFiles("templates/portal_normal.ftl");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("company_name"));
    }

    public void testLayoutTemplateVariables() {
        myFixture.configureByFiles("layouttpl/custom/my_freemarker_layout_template.ftl");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("processor"));
    }

    public void testThemeSettingsVariables() {
        myFixture.configureByFiles("templates/theme_settings.ftl", "WEB-INF/liferay-look-and-feel.xml");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("mysetting"));
    }

    public void testThemeSettingsReferenceVariables() {
        myFixture.configureByFiles("templates/theme_reference.ftl", "WEB-INF/liferay-look-and-feel.xml");

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

    public void testServiceLocatorInJournalTemplate() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/service-locator.ftl", "WEB-INF/src/resources-importer/journal/structures/test.json", "com/liferay/portal/template/ServiceLocator.java", "de/dm/MyService.java");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
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

        assertNotNull(strings);
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

        assertNotNull(strings);
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

        assertNotNull(strings);
        assertTrue(strings.contains("FOO"));
    }

    public void testEnumUtilLookup() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/enum-util-lookup.ftl", "WEB-INF/src/resources-importer/journal/structures/test.json", "de/dm/MyEnum.java");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("de.dm.MyEnum"));
    }

    public void testObjectUtilLookup() {
        myFixture.configureByFiles(
            "WEB-INF/src/resources-importer/journal/templates/test/object-util-lookup.ftl",
            "WEB-INF/src/resources-importer/journal/structures/test.json",
            "com/liferay/portal/template/freemarker/internal/LiferayObjectConstructor.java",
            "de/dm/MyObject.java"
        );
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("de.dm.MyObject"));
    }

    public void testStaticUtil() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/static-util.ftl", "WEB-INF/src/resources-importer/journal/structures/test.json", "de/dm/MyUtil.java");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue("Static method should be available", strings.contains("sayHello"));
   }

    public void testStaticUtilLookup() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/static-util-lookup.ftl", "WEB-INF/src/resources-importer/journal/structures/test.json", "de/dm/MyUtil.java");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
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

        assertNotNull(strings);
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

        assertNotNull(strings);
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

        assertNotNull(strings);
        assertTrue(strings.contains("ADMIN_PLUGINS"));
    }

    public void testIncludeServletContextFromDependency() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/templates/test/include-servlet-context.ftl", "WEB-INF/src/resources-importer/journal/structures/test.json");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("/freemarker-other-common_SERVLET_CONTEXT_/foobar.ftl"));
    }

    public void testLiferayTaglibs() {
        myFixture.configureByFiles("templates/liferay_taglibs.ftl");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("liferay_aui"));
    }

    public void testWorkflowDefinitionTemplateContextVariables() {
        myFixture.configureByFiles("workflow-definition-freemarker.xml");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("workflowContext"));
    }

    public void testWorkflowDefinitionDescriptionContextVariables() {
        myFixture.configureByFiles("workflow-definition-freemarker-description.xml");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue("<description>-Tag should provide Freemarker variables", strings.contains("workflowContext"));
    }

    public void testFragmentHtmlContextVariables() {
        myFixture.configureByFiles("fragment/index.html", "fragment/fragment.json");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("getterUtil"));
    }

    public void testImplicitVariablesNavigationElement() {
        myFixture.configureByFiles("templates/theme_variable.ftl", "com/liferay/portal/kernel/util/GetterUtil_IW.java");

        PsiElement element = myFixture.getFile().findElementAt(myFixture.getCaretOffset());

        assertNotNull(element);

        PsiElement elementParent = element.getParent();

        PsiReference[] references = elementParent.getReferences();

        PsiElement resolve = references[0].resolve();

        assertNotNull(resolve);

        PsiElement navigationElement = resolve.getNavigationElement();

		assertNotNull(navigationElement);
        assertTrue(navigationElement instanceof PsiClass);
    }

}
