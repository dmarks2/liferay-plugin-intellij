package de.dm.intellij.liferay.language.clientextension;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.schema.YamlJsonSchemaHighlightingInspection;

import java.util.List;

public class ClientExtensionYamlSchemaFileProviderTest extends BasePlatformTestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		myFixture.enableInspections(new YamlJsonSchemaHighlightingInspection());
	}

	@NotNull
	@Override
	protected LightProjectDescriptor getProjectDescriptor() {
		return LightProjectDescriptorBuilder.LIFERAY_SCHEMA_ROOT_ACCESS_PROJECT_DESCRIPTOR;
	}

	@Override
	protected String getTestDataPath() {
		return "testdata/de/dm/intellij/liferay/language/clientextension/ClientExtensionYamlSchemaFileProviderTest";
	}

	public void testValidClientExtensionYamlFile() {
		myFixture.configureByFile(
				"customElement/client-extension.yaml"
		);

		myFixture.checkHighlighting();
	}

	public void testAssembleCompletion() {
		myFixture.configureByFiles("customElement/client-extension-complete.yaml");
		myFixture.complete(CompletionType.BASIC, 1);
		List<String> strings = myFixture.getLookupElementStrings();
		assertTrue(strings.contains("from"));
	}

	public void testTypeCompletion() {
		myFixture.configureByFiles("customElement/client-extension-type-completion.yaml");
		myFixture.complete(CompletionType.BASIC, 1);
		List<String> strings = myFixture.getLookupElementStrings();
		assertTrue(strings.contains("customElement"));
	}

}
