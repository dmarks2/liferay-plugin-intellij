package de.dm.intellij.liferay.site.initializer;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SiteInitializerSchemaProviderFactoryTest extends BasePlatformTestCase {

	@NotNull
	@Override
	protected LightProjectDescriptor getProjectDescriptor() {
		return LightProjectDescriptorBuilder.LIFERAY_SCHEMA_ROOT_ACCESS_PROJECT_DESCRIPTOR;
	}

	@Override
	protected String getTestDataPath() {
		return "testdata/de/dm/intellij/liferay/site/initializer/SiteInitializerSchemaProviderFactoryTest";
	}

	public void testRolesCompletion() {
		myFixture.configureByFile(
				"site-initializer/roles.json"
		);

		myFixture.complete(CompletionType.BASIC, 1);
		List<String> strings = myFixture.getLookupElementStrings();

		assertNotNull(strings);
		assertTrue(strings.contains("\"name\""));
	}

	public void testDDMTemplateCompletion() {
		myFixture.configureByFile(
				"site-initializer/ddm-templates/chapters-navigation/ddm-template.json"
		);

		myFixture.complete(CompletionType.BASIC, 1);
		List<String> strings = myFixture.getLookupElementStrings();

		assertNotNull(strings);
		assertTrue(strings.contains("\"resourceClassName\""));
	}

	public void testFragmentCollectionCompletion() {
		myFixture.configureByFiles("site-initializer/fragments/group/learning-center/collection.json");
		myFixture.complete(CompletionType.BASIC, 1);
		List<String> strings = myFixture.getLookupElementStrings();

		assertNotNull(strings);
		assertTrue(strings.contains("\"name\""));
	}

	public void testLayoutSetCompletion() {
		myFixture.configureByFiles("site-initializer/layout-set/public/metadata.json");
		myFixture.complete(CompletionType.BASIC, 1);
		List<String> strings = myFixture.getLookupElementStrings();

		assertNotNull(strings);
		assertTrue(strings.contains("\"themeName\""));
	}

	public void testLayoutCompletion() {
		myFixture.configureByFiles("site-initializer/layouts/1_welcome/page.json");
		myFixture.complete(CompletionType.BASIC, 1);
		List<String> strings = myFixture.getLookupElementStrings();

		assertNotNull(strings);
		assertTrue(strings.contains("\"hidden\""));
	}

	public void testDocumentFolderCompletion() {
		myFixture.configureByFiles("site-initializer/documents/group/Policy.metadata.json", "site-initializer/documents/group/Policy/sample.txt");
		myFixture.complete(CompletionType.BASIC, 1);
		List<String> strings = myFixture.getLookupElementStrings();

		assertNotNull(strings);
		assertTrue(strings.contains("\"externalReferenceCode\""));
	}

	public void testDocumentCompletion() {
		myFixture.configureByFiles("site-initializer/documents/group/Policy/sample.txt.metadata.json", "site-initializer/documents/group/Policy/sample.txt");
		myFixture.complete(CompletionType.BASIC, 1);
		List<String> strings = myFixture.getLookupElementStrings();

		assertNotNull(strings);
		assertTrue(strings.contains("\"externalReferenceCode\""));
	}
	public void testListTypeDefinitionsCompletion() {
		myFixture.configureByFiles("site-initializer/list-type-definitions/application-status.json");
		myFixture.complete(CompletionType.BASIC, 1);
		List<String> strings = myFixture.getLookupElementStrings();

		assertNotNull(strings);
		assertTrue(strings.contains("\"externalReferenceCode\""));
	}

	public void testListTypeEntriesCompletion() {
		myFixture.configureByFiles("site-initializer/list-type-definitions/application-status.list-type-entries.json");
		myFixture.complete(CompletionType.BASIC, 1);
		List<String> strings = myFixture.getLookupElementStrings();

		assertNotNull(strings);
		assertTrue(strings.contains("\"key\""));
	}

	public void testTaxonomyVocabularyCompletion() {
		myFixture.configureByFiles("site-initializer/taxonomy-vocabularies/group/sample.json");
		myFixture.complete(CompletionType.BASIC, 1);
		List<String> strings = myFixture.getLookupElementStrings();

		assertNotNull(strings);
		assertTrue(strings.contains("\"description_i18n\""));
	}

	public void testTaxonomyCategoryCompletion() {
		myFixture.configureByFiles("site-initializer/taxonomy-vocabularies/group/sample/category1.json");
		myFixture.complete(CompletionType.BASIC, 1);
		List<String> strings = myFixture.getLookupElementStrings();

		assertNotNull(strings);
		assertTrue(strings.contains("\"taxonomyCategoryProperties\""));
	}

	public void testSubTaxonomyCategoryCompletion() {
		myFixture.configureByFiles("site-initializer/taxonomy-vocabularies/group/sample/category1/subcategory1.json");
		myFixture.complete(CompletionType.BASIC, 1);
		List<String> strings = myFixture.getLookupElementStrings();

		assertNotNull(strings);
		assertTrue(strings.contains("\"taxonomyCategoryProperties\""));
	}

}
