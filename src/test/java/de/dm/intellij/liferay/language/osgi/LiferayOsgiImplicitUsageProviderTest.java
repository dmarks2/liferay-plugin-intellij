package de.dm.intellij.liferay.language.osgi;

import com.intellij.codeInspection.deadCode.UnusedDeclarationInspection;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

public class LiferayOsgiImplicitUsageProviderTest extends LightJavaCodeInsightFixtureTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/liferay/language/osgi/LiferayOsgiImplicitUsageProviderTest";

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        myFixture.enableInspections(new UnusedDeclarationInspection(true));
    }

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor() {
        return new LightProjectDescriptorBuilder()
                .library("OSGi", TEST_DATA_PATH, "osgi.jar")
                .build();
    }

    @Override
    protected String getTestDataPath() {
        return TEST_DATA_PATH;
    }

    public void testOsgiImplicitUsage() {
        myFixture.configureByFile("MyComponent.java");
        myFixture.checkHighlighting();
    }

}
