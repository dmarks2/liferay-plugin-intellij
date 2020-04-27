package de.dm.intellij.liferay.language.jsp;

import com.intellij.debugger.engine.SourcesFinder;
import com.intellij.javaee.facet.JavaeeFacet;
import com.intellij.javaee.facet.JavaeeFacetUtil;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.impl.JavaAwareProjectJdkTableImpl;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.LanguageLevelModuleExtension;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.vfs.newvfs.impl.VfsRootAccess;
import com.intellij.pom.java.LanguageLevel;
import com.intellij.psi.PsiFile;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.PsiTestUtil;
import com.intellij.testFramework.fixtures.DefaultLightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import com.intellij.util.PathUtil;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class LiferayJspDebuggerSourceFinderAdapterTest extends LightJavaCodeInsightFixtureTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/liferay/language/jsp/LiferayJspDebuggerSourceFinderAdapterTest";

    private static final LightProjectDescriptor JAVA_LOGIN_WEB_DESCRIPTOR = new DefaultLightProjectDescriptor() {

        @Override
        public void configureModule(@NotNull Module module, @NotNull ModifiableRootModel model, @NotNull ContentEntry contentEntry) {
            LanguageLevelModuleExtension extension = model.getModuleExtension(LanguageLevelModuleExtension.class);
            if (extension != null) {
                extension.setLanguageLevel(LanguageLevel.JDK_1_8);
            }
            Sdk jdk = JavaAwareProjectJdkTableImpl.getInstanceEx().getInternalJdk();
            model.setSdk(jdk);

            final String testDataPath = PathUtil.toSystemIndependentName(new File(TEST_DATA_PATH).getAbsolutePath());
            VfsRootAccess.allowRootAccess( Disposer.newDisposable(), testDataPath );

            PsiTestUtil.addLibrary(model, "com.liferay:com.liferay.login.web", testDataPath, "com.liferay.login.web.jar");
        }
    };

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor() {
        return JAVA_LOGIN_WEB_DESCRIPTOR;
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
