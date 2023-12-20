package de.dm.intellij.liferay.language.jsp;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LiferayTaglibPortletNameReferenceContributorTest extends LightJavaCodeInsightFixtureTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/liferay/language/jsp/LiferayTaglibPortletNameReferenceContributorTest";

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

    public void testPortletNameString() {
        myFixture.configureByFiles(
                "portletName.jsp",
                "liferay-portlet-ext.tld",
                "de/dm/portlet/MyPortlet.java",
                "javax/portlet/Portlet.java"
        );

        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("de_dm_portlet_MyPortlet"));
    }

    public void testPortletNameConstant() {
        myFixture.configureByFiles(
                "portletName.jsp",
                "liferay-portlet-ext.tld",
                "de/dm/portlet/MyConstantPortlet.java",
                "javax/portlet/Portlet.java"
        );

        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("de_dm_portlet_MyConstantPortlet"));
    }

}
