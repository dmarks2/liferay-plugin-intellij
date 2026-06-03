package de.dm.intellij.liferay.schema;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;

import java.util.List;

public class LiferayBatchEngineDataSchemaFileProviderTest extends BasePlatformTestCase {

	@Override
	protected LightProjectDescriptor getProjectDescriptor() {
		return LightProjectDescriptorBuilder.LIFERAY_SCHEMA_ROOT_ACCESS_PROJECT_DESCRIPTOR;
	}

	@Override
	protected String getTestDataPath() {
		return "testdata/de/dm/intellij/liferay/schema/LiferayBatchEngineDataSchemaFileProviderTest";
	}

	public void testValidBatchEngineDataFile() {
		myFixture.configureByFile(
				"01-sample.batch-engine-data.json"
		);

		myFixture.checkHighlighting();
	}

	public void testCompletion() {
		myFixture.configureByFile(
				"02-sample.batch-engine-data.json"
		);

		myFixture.complete(CompletionType.BASIC, 1);
		List<String> strings = myFixture.getLookupElementStrings();

		assertNotNull(strings);
		assertTrue(strings.contains("\"className\""));
	}

}
