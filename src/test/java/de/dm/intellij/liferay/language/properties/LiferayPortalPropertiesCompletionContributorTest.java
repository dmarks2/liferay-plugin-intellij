package de.dm.intellij.liferay.language.properties;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LiferayPortalPropertiesCompletionContributorTest extends BasePlatformTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor() {
        return new LightProjectDescriptorBuilder()
                .liferayVersion("7.0.6")
                .rootAccess("/com/liferay/properties/70/portal.properties")
                .build();
    }

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/properties/LiferayPortalPropertiesCompletionContributorTest";
    }

    public void testPortalPropertiesCodeCompletion() {
        myFixture.configureByFiles("portal-ext.properties");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("admin.email.from.name"));
    }

}
