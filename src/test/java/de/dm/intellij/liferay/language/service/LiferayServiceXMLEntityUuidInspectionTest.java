package de.dm.intellij.liferay.language.service;

import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;

public class LiferayServiceXMLEntityUuidInspectionTest extends LightCodeInsightFixtureTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        myFixture.enableInspections(new LiferayServiceXMLEntityUuidInspection());
    }

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/service/LiferayServiceXMLEntityUuidInspectionTest";
    }

    public void testValidUuidInspection() {
        myFixture.configureByFiles("service_valid.xml");

        myFixture.checkHighlighting();
    }

    public void testInvalidUuidInspection() {
        myFixture.configureByFiles("service_invalid.xml");

        myFixture.checkHighlighting();
    }
}
