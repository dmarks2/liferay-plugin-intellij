package de.dm.intellij.liferay.site.initializer;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SiteInitializerDDMStructureJSONLanguageInjectorTest extends BasePlatformTestCase {

	@NotNull
	@Override
	protected LightProjectDescriptor getProjectDescriptor() {
		return LightProjectDescriptorBuilder.LIFERAY_SCHEMA_ROOT_ACCESS_PROJECT_DESCRIPTOR;
	}

	@Override
	protected String getTestDataPath() {
		return "testdata/de/dm/intellij/liferay/site/initializer/SiteInitializerDDMStructureJSONLanguageInjectorTest";
	}

	public void testJSONLanguageInjectionCompletion() {
		myFixture.configureByFile(
				"site-initializer/ddm-structures/sample-structure.xml"
		);

		myFixture.complete(CompletionType.BASIC, 1);
		List<String> strings = myFixture.getLookupElementStrings();

		assertNotNull(strings);
		assertTrue(strings.contains("true"));
	}
}
