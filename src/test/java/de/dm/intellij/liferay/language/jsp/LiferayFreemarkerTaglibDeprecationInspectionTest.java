package de.dm.intellij.liferay.language.jsp;

import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import de.dm.intellij.liferay.language.freemarker.LiferayFreemarkerTaglibDeprecationInspection;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

public class LiferayFreemarkerTaglibDeprecationInspectionTest extends LightJavaCodeInsightFixtureTestCase {

	@NotNull
	@Override
	protected LightProjectDescriptor getProjectDescriptor() {
		return new LightProjectDescriptorBuilder()
				.liferayVersion("7.4.3.55")
				.build();
	}


	@Override
	protected void setUp() throws Exception {
		super.setUp();

		myFixture.enableInspections(new LiferayFreemarkerTaglibDeprecationInspection());
	}

	@Override
	protected String getTestDataPath() {
		return "testdata/de/dm/intellij/liferay/language/freemarker/LiferayFreemarkerTaglibDeprecationInspectionTest";
	}

	public void testClayAlertAttributeDeprecation() {
		myFixture.configureByFiles(
				"alert.ftl"
		);

		myFixture.checkHighlighting();
	}
}
