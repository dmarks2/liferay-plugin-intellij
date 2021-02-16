package de.dm.intellij.liferay.gradle;

import com.intellij.codeInspection.unused.UnusedPropertyInspection;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

public class LiferayGradlePropertiesImplicitUsageProviderTest extends BasePlatformTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/liferay/gradle/LiferayGradlePropertiesImplicitUsageProviderTest";

    @Override
    protected String getTestDataPath() {
        return TEST_DATA_PATH;
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        myFixture.enableInspections(new UnusedPropertyInspection());
    }

    public void testImplicitUsageJavaxPortletTitleInLanguageProperties() {
        myFixture.configureByFiles("gradle.properties");

        //Language.properties should not show any unused warning, even if javax.portlet.title.my_portlet is not used explicitly
        myFixture.checkHighlighting();
    }

}
