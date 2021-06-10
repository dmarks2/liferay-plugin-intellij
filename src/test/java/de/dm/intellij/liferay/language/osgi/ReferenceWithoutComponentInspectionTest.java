package de.dm.intellij.liferay.language.osgi;

import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;

public class ReferenceWithoutComponentInspectionTest extends LightJavaCodeInsightFixtureTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/liferay/language/osgi/ReferenceWithoutComponentInspectionTest";

    @Override
    protected String getTestDataPath() {
        return TEST_DATA_PATH;
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        myFixture.enableInspections(new ReferenceWithoutComponentInspection());
    }

    public void testClassWithoutComponentInspection() {
        myFixture.configureByFiles(
                "ClassWithoutComponent.java",
                "MyObject.java",
                "org/osgi/service/component/annotations/Reference.java",
                "org/osgi/service/component/annotations/Component.java"
        );

        myFixture.checkHighlighting();
    }

    public void testClassWithoutComponentMethodInspection() {
        myFixture.configureByFiles(
                "ClassWithoutComponentMethod.java",
                "MyObject.java",
                "org/osgi/service/component/annotations/Reference.java",
                "org/osgi/service/component/annotations/Component.java"
        );

        myFixture.checkHighlighting();
    }

    public void testClassWithComponentInspection() {
        myFixture.configureByFiles(
                "ClassWithComponent.java",
                "MyObject.java",
                "org/osgi/service/component/annotations/Component.java",
                "org/osgi/service/component/annotations/Reference.java"
        );

        myFixture.checkHighlighting();
    }

}
