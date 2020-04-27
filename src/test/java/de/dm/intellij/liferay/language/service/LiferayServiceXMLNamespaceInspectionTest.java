package de.dm.intellij.liferay.language.service;

import com.intellij.testFramework.fixtures.BasePlatformTestCase;

public class LiferayServiceXMLNamespaceInspectionTest extends BasePlatformTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        myFixture.enableInspections(new LiferayServiceXMLNamespaceInspection());
    }

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/service/LiferayServiceXMLNamespaceInspectionTest";
    }

    public void testValidNamespaceInspection() {
        myFixture.configureByFiles("service_valid.xml");

        myFixture.checkHighlighting();
    }

    public void testInvalidNamespaceInspection() {
        myFixture.configureByFiles("service_invalid.xml");

        myFixture.checkHighlighting();
    }

}
