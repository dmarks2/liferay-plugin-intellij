package de.dm.intellij.liferay.maven;

import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

public class LiferayMavenDeprecationsExternalAnnotatorTest extends LightJavaCodeInsightFixtureTestCase {
	@NotNull
	@Override
	protected LightProjectDescriptor getProjectDescriptor() {
		return new LightProjectDescriptorBuilder()
				.liferayVersion("7.4.3.55")
				.build();
	}

	@Override
	protected String getTestDataPath() {
		return "testdata/de/dm/intellij/liferay/maven/LiferayMavenDeprecationsExternalAnnotatorTest";
	}

	public void testServiceBuilderVersion() {
		myFixture.configureByFiles("service-builder/pom.xml");

		myFixture.checkHighlighting();
	}

	public void testServiceBuilderPropertyVersion() {
		myFixture.configureByFiles("service-builder-property/pom.xml");

		myFixture.checkHighlighting();
	}

	public void testServiceBuilderPluginManagementVersion() {
		myFixture.configureByFiles("service-builder-plugin-management/pom.xml");

		myFixture.checkHighlighting();
	}

	public void testServiceBuilderPluginManagementPropertyVersion() {
		myFixture.configureByFiles("service-builder-plugin-management-property/pom.xml");

		myFixture.checkHighlighting();
	}

	public void testPortalWebVersion() {
		myFixture.configureByFiles("portal-web/pom.xml");

		myFixture.checkHighlighting();
	}

}
