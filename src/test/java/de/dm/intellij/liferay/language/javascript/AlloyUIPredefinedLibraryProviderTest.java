package de.dm.intellij.liferay.language.javascript;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AlloyUIPredefinedLibraryProviderTest extends LightJavaCodeInsightFixtureTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/liferay/language/javascript/AlloyUIPredefinedLibraryProviderTest";

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor() {
        return new LightProjectDescriptorBuilder()
                .library("com.liferay:com.liferay.frontend.js.web", TEST_DATA_PATH, "com.liferay.frontend.js.web.jar")
                .build();
    }

    @Override
    protected String getTestDataPath() {
        return TEST_DATA_PATH;
    }

    public void testJavascriptLiferay7x() {
        myFixture.configureByFiles("view7x.jsp");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("Liferay"));
    }
}
