package de.dm.intellij.liferay.language.jsp;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.psi.PsiFile;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LiferayJspHookFileReferenceHelperCoreTest extends LightJavaCodeInsightFixtureTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/liferay/language/jsp/LiferayJspHookFileReferenceHelperTest";

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor() {
        return new LightProjectDescriptorBuilder()
                .library("com.liferay.portal:com.liferay.portal.web", TEST_DATA_PATH, "com.liferay.portal.web.war")
                .library("OSGi", TEST_DATA_PATH, "osgi.jar")
                .build();
    }

    @Override
    protected String getTestDataPath() {
        return TEST_DATA_PATH;
    }

    public void testCoreJspHook() {
        //test with string return value in CustomJspBag.getCustomJspDir()
        PsiFile[] psiFiles = myFixture.configureByFiles(
                "META-INF/custom_jsps/core.jsp",
                "com/liferay/portal/deploy/hot/CustomJspBag.java",
                "com/liferay/portal/kernel/url/URLContainer.java",
                "de/dm/CoreJspHookBag.java"
        );

        String text = psiFiles[3].getText();
        myFixture.saveText(psiFiles[3].getVirtualFile(), text);

        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("namespace"));
    }

    public void testCoreJspHookConstant() {
        //test with constant return value in CustomJspBag.getCustomJspDir()
        PsiFile[] psiFiles = myFixture.configureByFiles(
                "META-INF/custom_jsps/core.jsp",
                "com/liferay/portal/deploy/hot/CustomJspBag.java",
                "com/liferay/portal/kernel/url/URLContainer.java",
                "de/dm/CoreJspHookBagConstant.java"
        );

        String text = psiFiles[3].getText();
        myFixture.saveText(psiFiles[3].getVirtualFile(), text);

        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("namespace"));
    }

    public void testCoreJspHookInclude() {
        //test with string return value in CustomJspBag.getCustomJspDir()
        PsiFile[] psiFiles = myFixture.configureByFiles(
            "META-INF/custom_jsps/include.jsp",
            "com/liferay/portal/deploy/hot/CustomJspBag.java",
            "com/liferay/portal/kernel/url/URLContainer.java",
            "de/dm/CoreJspHookBag.java"
        );

        String text = psiFiles[3].getText();
        myFixture.saveText(psiFiles[3].getVirtualFile(), text);

        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("init.jsp"));
    }

}
