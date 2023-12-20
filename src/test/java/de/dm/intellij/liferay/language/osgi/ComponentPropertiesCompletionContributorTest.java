package de.dm.intellij.liferay.language.osgi;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ComponentPropertiesCompletionContributorTest extends LightJavaCodeInsightFixtureTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/liferay/language/osgi/ComponentPropertiesCompletionContributorTest";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
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

    public void testComponentPropertiesCompletion() {
        myFixture.configureByFile("MyComponent.java");

        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("osgi.command.scope"));
    }

    public void testMultiServiceComponentPropertiesCompletion() {
        myFixture.configureByFiles(
                "MultiServiceComponent.java",
                "com/liferay/portal/kernel/portlet/bridges/mvc/MVCActionCommand.java",
                "com/liferay/portal/kernel/search/IndexerPostProcessor.java"
        );

        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue("should contain properties from IndexerPostProcessor", strings.contains("indexer.class.name"));
        assertTrue("should contain properties from MVCActionCommand", strings.contains("mvc.command.name"));
    }

    public void testSinglePropertyComponentPropertiesCompletion() {
        myFixture.configureByFile("SinglePropertyComponent.java");

        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue("Single property annotation should offer \"osgi.command.scope\" for java.lang.Object component", strings.contains("osgi.command.scope"));
    }

}
