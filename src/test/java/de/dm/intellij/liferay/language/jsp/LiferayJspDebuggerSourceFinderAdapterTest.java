package de.dm.intellij.liferay.language.jsp;

import com.intellij.debugger.engine.SourcesFinder;
import com.intellij.javaee.facet.JavaeeFacet;
import com.intellij.javaee.facet.JavaeeFacetUtil;
import com.intellij.psi.PsiFile;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

public class LiferayJspDebuggerSourceFinderAdapterTest extends LightJavaCodeInsightFixtureTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/liferay/language/jsp/LiferayJspDebuggerSourceFinderAdapterTest";

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor() {
        return new LightProjectDescriptorBuilder()
                .library("com.liferay:com.liferay.login.web", TEST_DATA_PATH, "com.liferay.login.web.jar")
                .build();
    }

    @Override
    protected String getTestDataPath() {
        return TEST_DATA_PATH;
    }

    public void testSourceFinderInLibrary() {
        SourcesFinder<JavaeeFacet[]> sourcesFinder = new LiferayJspDebuggerSourceFinderAdapter();

        PsiFile sourceFile = sourcesFinder.findSourceFile("init.jsp", myFixture.getProject(), new JavaeeFacet[0]);

        assertNotNull("SourcesFinder should have found \"init.jsp\" inside \"com.liferay.login.web.jar\"", sourceFile);
    }

    public void testSourceFinderInWebContexts() {
        myFixture.configureByFile("META-INF/resources/file.jsp");

        SourcesFinder<JavaeeFacet[]> sourcesFinder = new LiferayJspDebuggerSourceFinderAdapter();

        JavaeeFacet[] allJavaeeFacets = JavaeeFacetUtil.getInstance().getAllJavaeeFacets(myFixture.getProject());

        PsiFile sourceFile = sourcesFinder.findSourceFile("file.jsp", myFixture.getProject(), allJavaeeFacets);

        assertNotNull("SourcesFinder should have found \"META-INF/resources/file.jsp\" inside the module", sourceFile);
    }



}
