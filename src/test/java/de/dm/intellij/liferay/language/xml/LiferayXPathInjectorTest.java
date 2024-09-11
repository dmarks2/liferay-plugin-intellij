package de.dm.intellij.liferay.language.xml;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import de.dm.intellij.liferay.theme.LiferayLookAndFeelXmlParser;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LiferayXPathInjectorTest extends LightJavaCodeInsightFixtureTestCase {

	private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/liferay/language/xml/LiferayXPathInjectorTest";

	@Override
	protected String getTestDataPath() {
		return TEST_DATA_PATH;
	}

	@Override
	protected @NotNull LightProjectDescriptor getProjectDescriptor() {
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

	public void testCompletionInJavaMethod() {
		myFixture.configureByFiles(
				"de/dm/helper/MyHelper.java",
				"com/liferay/portal/kernel/xml/Node.java"
		);

		myFixture.complete(CompletionType.BASIC, 1);

		List<String> strings = myFixture.getLookupElementStrings();

		assertNotNull(strings);
		assertTrue("com.liferay.portal.kernel.xml.Node.selectNodes() in Java code should support XPath expressions like text()", strings.contains("text()"));
	}

	public void testCompletionInFreemarkerMethod() {
		myFixture.configureByFiles(
				"templates/portal_normal.ftl",
				"com/liferay/portal/kernel/xml/Node.java",
				"com/liferay/portal/kernel/xml/Document.java",
				"com/liferay/portal/kernel/xml/SAXReaderUtil.java"
		);

		myFixture.complete(CompletionType.BASIC, 1);

		List<String> strings = myFixture.getLookupElementStrings();

		assertNotNull(strings);
		assertTrue("com.liferay.portal.kernel.xml.Document.valueOf() in Freemarker code should support XPath expressions like text()", strings.contains("text()"));
	}

	public void testCompletionInGroovyScript() {
		myFixture.configureByFiles(
				"sample.groovy",
				"com/liferay/portal/kernel/xml/Node.java",
				"com/liferay/portal/kernel/xml/Document.java",
				"com/liferay/portal/kernel/xml/SAXReaderUtil.java"
		);

		myFixture.complete(CompletionType.BASIC, 1);

		List<String> strings = myFixture.getLookupElementStrings();

		assertNotNull(strings);
		assertTrue("com.liferay.portal.kernel.xml.Document.valueOf() in Groovy script should support XPath expressions like text()", strings.contains("text()"));

	}
}
