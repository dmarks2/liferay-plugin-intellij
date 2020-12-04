package de.dm.intellij.liferay.resourcesimporter;

import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import de.dm.intellij.liferay.module.LiferayModuleComponent;

public class LiferayPluginPackagePropertiesParserTest extends BasePlatformTestCase {

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/resourcesimporter/LiferayPluginPackagePropertiesParserTest";
    }

    public void testPackageJsonParser() {
        myFixture.configureByFiles("liferay-plugin-package.properties");

        assertEquals("Foobar", LiferayModuleComponent.getResourcesImporterGroupName(myFixture.getModule()));
    }
}
