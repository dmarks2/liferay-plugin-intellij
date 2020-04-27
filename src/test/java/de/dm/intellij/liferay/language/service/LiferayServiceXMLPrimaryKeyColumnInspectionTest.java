package de.dm.intellij.liferay.language.service;

import com.intellij.testFramework.fixtures.BasePlatformTestCase;

public class LiferayServiceXMLPrimaryKeyColumnInspectionTest extends BasePlatformTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        myFixture.enableInspections(new LiferayServiceXMLPrimaryKeyColumnInspection());
    }

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/service/LiferayServiceXMLPrimaryKeyColumnInspectionTest";
    }

    public void testValidPrimaryKeyInspection() {
        myFixture.configureByFiles("service_valid.xml");

        myFixture.checkHighlighting();
    }

    public void testInvalidPrimaryKeyInspection() {
        myFixture.configureByFiles("service_invalid.xml");

        myFixture.checkHighlighting();
    }
}
