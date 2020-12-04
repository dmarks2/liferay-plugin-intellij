package de.dm.intellij.liferay.resourcesimporter;

import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import de.dm.intellij.liferay.module.LiferayModuleComponent;

public class LiferayAdvancedResourcesImporterBndParserTest extends BasePlatformTestCase {

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/resourcesimporter/LiferayAdvancedResourcesImporterBndParserTest";
    }

    public void testPackageJsonParser() {
        myFixture.configureByFiles("bnd.bnd");

        assertEquals("Foobar", LiferayModuleComponent.getResourcesImporterGroupName(myFixture.getModule()));
    }
}
