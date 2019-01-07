package de.dm.intellij.liferay.theme;

import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import de.dm.intellij.liferay.module.LiferayModuleComponent;

public class LiferayPackageJSONParserTest extends LightCodeInsightFixtureTestCase {

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/theme/LiferayPackageJSONParserTest";
    }

    public void testPackageJsonParser() {
        myFixture.configureByFiles("package.json");

        assertEquals("styled", LiferayModuleComponent.getParentTheme(myFixture.getModule()));
    }
}
