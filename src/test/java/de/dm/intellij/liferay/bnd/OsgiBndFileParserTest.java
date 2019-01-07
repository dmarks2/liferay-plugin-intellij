package de.dm.intellij.liferay.bnd;

import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import de.dm.intellij.liferay.module.LiferayModuleComponent;

public class OsgiBndFileParserTest extends LightCodeInsightFixtureTestCase {

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/bnd/OsgiBndFileParserTest";
    }

    public void testOsgiBndFileParser() {
        myFixture.configureByFiles("bnd.bnd");

        assertEquals("com.liferay.dynamic.data.lists.form.web", LiferayModuleComponent.getOsgiFragmentHostPackageName(myFixture.getModule()));
    }

}