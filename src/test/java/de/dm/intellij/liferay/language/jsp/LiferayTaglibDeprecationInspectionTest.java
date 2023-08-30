package de.dm.intellij.liferay.language.jsp;

import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

public class LiferayTaglibDeprecationInspectionTest extends LightJavaCodeInsightFixtureTestCase {

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

		myFixture.enableInspections(new LiferayTaglibDeprecationInspection());
	}

	@Override
	protected String getTestDataPath() {
		return "testdata/de/dm/intellij/liferay/language/jsp/LiferayTaglibDeprecationInspectionTest";
	}

	public void testClayAttributeDeprecation() {
		myFixture.configureByFiles(
				"view.jsp",
				"liferay-clay.tld"
		);

		myFixture.checkHighlighting();
	}

}
