package de.dm.intellij.liferay.schema;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.schema.YamlJsonSchemaHighlightingInspection;

import java.util.List;

public class LiferayRestConfigSchemaProviderTest extends BasePlatformTestCase {

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
		return "testdata/de/dm/intellij/liferay/schema/LiferayRestConfigSchemaProviderTest";
	}

	public void testValidRestConfigYamlFile() {
		myFixture.configureByFile(
				"validate/rest-config.yaml"
		);

		myFixture.checkHighlighting();
	}

	public void testCompletion() {
		myFixture.configureByFiles("completion/rest-config.yaml");
		myFixture.complete(CompletionType.BASIC, 1);
		List<String> strings = myFixture.getLookupElementStrings();
		assertTrue(strings.contains("apiDir"));
	}

}
