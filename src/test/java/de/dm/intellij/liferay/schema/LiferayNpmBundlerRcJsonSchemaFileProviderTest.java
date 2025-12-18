package de.dm.intellij.liferay.schema;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.daemon.impl.HighlightInfo;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LiferayNpmBundlerRcJsonSchemaFileProviderTest extends BasePlatformTestCase {

	@NotNull
	@Override
	protected LightProjectDescriptor getProjectDescriptor() {
		return LightProjectDescriptorBuilder.LIFERAY_SCHEMA_ROOT_ACCESS_PROJECT_DESCRIPTOR;
	}

	@Override
	protected String getTestDataPath() {
		return "testdata/de/dm/intellij/liferay/schema/LiferayNpmBundlerRcJsonSchemaFileProviderTest";
	}

	public void testValidNpmBundlerRcFile() {
		myFixture.configureByFile(
				"validate/.npmbundlerrc"
		);

		myFixture.checkHighlighting();
	}

	public void testInvalidNpmBundlerRcFile() {
		myFixture.configureByFile(
				"invalid/.npmbundlerrc"
		);

		List<HighlightInfo> highlightInfos = myFixture.doHighlighting();

		//invalid/.npmbundlerrc is not a valid JSON file, so IntelliJ should show errors"
		assertNotEmpty(highlightInfos);
	}

	public void testCompletion() {
		myFixture.configureByFiles("completion/.npmbundlerrc");
		myFixture.complete(CompletionType.BASIC, 1);
		List<String> strings = myFixture.getLookupElementStrings();

		assertNotNull(strings);
		assertTrue(strings.contains("\"output\""));
	}
}
