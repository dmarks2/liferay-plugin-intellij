package de.dm.intellij.bndtools.parser;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.LanguageLevelModuleExtension;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.vfs.newvfs.impl.VfsRootAccess;
import com.intellij.pom.java.LanguageLevel;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.PsiTestUtil;
import com.intellij.testFramework.fixtures.DefaultLightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import com.intellij.util.PathUtil;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

public class BndHeaderParsersTest extends LightJavaCodeInsightFixtureTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/bndtools/parser/BndHeaderParsersTest";

    private static final LightProjectDescriptor JAVA_OSGI_DESCRIPTOR = new DefaultLightProjectDescriptor() {

        @Override
        public void configureModule(@NotNull Module module, @NotNull ModifiableRootModel model, @NotNull ContentEntry contentEntry) {
            LanguageLevelModuleExtension extension = model.getModuleExtension(LanguageLevelModuleExtension.class);
            if (extension != null) {
                extension.setLanguageLevel(LanguageLevel.JDK_1_8);
            }

            final String testDataPath = PathUtil.toSystemIndependentName(new File(TEST_DATA_PATH).getAbsolutePath());
            VfsRootAccess.allowRootAccess( Disposer.newDisposable(), testDataPath );

            PsiTestUtil.addLibrary(model, "org.osgi:org.osgi.core", testDataPath, "org.osgi.core-6.0.0.jar");
        }
    };

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor() {
        return JAVA_OSGI_DESCRIPTOR;
    }

    @Override
    protected String getTestDataPath() {
        return TEST_DATA_PATH;
    }

    public void testHeadersCodeCompletion() {
        myFixture.configureByFiles("testHeadersCodeCompletion/bnd.bnd");

        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("Bundle-Activator"));
    }

    public void testOptionsCodeCompletion() {
        myFixture.configureByFiles("testOptionsCodeCompletion/bnd.bnd");

        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();

        assertNotNull(strings);
        assertTrue(strings.contains("-baseline"));
    }

}
