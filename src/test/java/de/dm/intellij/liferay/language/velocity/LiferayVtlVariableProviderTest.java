package de.dm.intellij.liferay.language.velocity;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.LanguageLevelModuleExtension;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.vfs.newvfs.impl.VfsRootAccess;
import com.intellij.pom.java.LanguageLevel;
import com.intellij.testFramework.IdeaTestUtil;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.DefaultLightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import com.intellij.util.PathUtil;
import de.dm.intellij.liferay.language.freemarker.LiferayFtlVariableProviderTest;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
import org.jetbrains.annotations.NotNull;
import org.junit.Ignore;

import java.io.File;
import java.net.URL;
import java.util.List;

public class LiferayVtlVariableProviderTest extends LightCodeInsightFixtureTestCase {

    private static final LightProjectDescriptor MY_PROJECT_DESCRIPTOR = new DefaultLightProjectDescriptor() {

        @Override
        public void configureModule(@NotNull Module module, @NotNull ModifiableRootModel model, @NotNull ContentEntry contentEntry) {
            LanguageLevelModuleExtension extension = model.getModuleExtension(LanguageLevelModuleExtension.class);
            if (extension != null) {
                extension.setLanguageLevel(LanguageLevel.JDK_1_8);
            }
            LiferayModuleComponent.getInstance(module).setLiferayVersion("7.0.6");

            URL resource = LiferayFtlVariableProviderTest.class.getResource("/com/liferay/vtl/VM_liferay_70.vm");
            String resourcePath = PathUtil.toSystemIndependentName(new File(resource.getFile()).getAbsolutePath());
            VfsRootAccess.allowRootAccess( resourcePath );

            resource = LiferayFtlVariableProviderTest.class.getResource("/com/liferay/tld");
            resourcePath = PathUtil.toSystemIndependentName(new File(resource.getFile()).getAbsolutePath());
            VfsRootAccess.allowRootAccess( resourcePath );

        }

        @Override
        public Sdk getSdk() {
            return IdeaTestUtil.getMockJdk18();
        }
    };

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor() {
        return MY_PROJECT_DESCRIPTOR;
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/velocity/LiferayVtlVariableProviderTest";
    }

    public void testStructureVariablesSimple() {
        myFixture.configureByFiles("journal/templates/test/simple.vm", "journal/structures/test.json");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("simple"));
    }

    public void testStructureVariablesNested() {
        myFixture.configureByFiles("journal/templates/test/parent.vm", "journal/structures/test.json");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("child"));
    }

    @Ignore
    public void testStructureVariablesRepeatable() {
        myFixture.configureByFiles("journal/templates/test/repeatable.vm", "journal/structures/test.json");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("siblings"));
    }


}
