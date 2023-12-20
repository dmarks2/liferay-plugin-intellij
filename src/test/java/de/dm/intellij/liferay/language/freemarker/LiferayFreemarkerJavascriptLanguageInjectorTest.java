package de.dm.intellij.liferay.language.freemarker;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LiferayFreemarkerJavascriptLanguageInjectorTest extends BasePlatformTestCase {

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor() {
        return
                new LightProjectDescriptorBuilder()
                        .rootAccess(
                                "/com/liferay/ftl",
                                "/com/liferay/tld")
                        .build();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/freemarker/LiferayFreemarkerJavascriptLanguageInjectorTest";
    }

    public void testJavascriptAttribute() {
        myFixture.configureByFiles("attribute.ftl", "liferay-aui.tld");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("alert"));
    }

    public void testJavascriptAttributeSquareBrackets() {
        myFixture.configureByFiles("attribute_square_brackets.ftl", "liferay-aui.tld");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue("\"alert\" javascript command should be suggested inside <@liferay_aui[\"a\"] onClick=\"\">", strings.contains("alert"));
    }

    public void testJavascriptBody() {
        myFixture.configureByFiles("body.ftl", "liferay-aui.tld");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("alert"));
    }

    public void testJavascriptBodySquareBrackets() {
        myFixture.configureByFiles("body_square_brackets.ftl", "liferay-aui.tld");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue("\"alert\" javascript command should be suggested inside <@liferay_aui[\"script\"]>", strings.contains("alert"));
    }

}
