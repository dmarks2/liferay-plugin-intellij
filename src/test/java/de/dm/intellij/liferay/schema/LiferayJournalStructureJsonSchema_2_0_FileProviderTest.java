package de.dm.intellij.liferay.schema;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.vfs.newvfs.impl.VfsRootAccess;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.intellij.testFramework.fixtures.DefaultLightProjectDescriptor;
import com.intellij.util.PathUtil;
import de.dm.intellij.liferay.language.freemarker.LiferayFtlVariableProviderTest;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.URL;
import java.util.List;

public class LiferayJournalStructureJsonSchema_2_0_FileProviderTest extends BasePlatformTestCase {

    private static final LightProjectDescriptor MY_PROJECT_DESCRIPTOR = new DefaultLightProjectDescriptor() {
        @Override
        public void configureModule(@NotNull Module module, @NotNull ModifiableRootModel model, @NotNull ContentEntry contentEntry) {
            URL resource = LiferayFtlVariableProviderTest.class.getResource("/com/liferay/schema");
            String resourcePath = PathUtil.toSystemIndependentName(new File(resource.getFile()).getAbsolutePath());
            VfsRootAccess.allowRootAccess( Disposer.newDisposable(), resourcePath );
        }
    };

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor() {
        return MY_PROJECT_DESCRIPTOR;
    }

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/schema/LiferayJournalStructureJsonSchema_2_0_FileProviderTest";
    }

    public void testCompletion() {
        myFixture.configureByFiles("WEB-INF/src/resources-importer/journal/structures/test.json");
        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("rich_text"));

        assertFalse("\"type\" should not contain \"ddm-journal-article\", because it is schema 2.0", strings.contains("ddm-journal-article"));
    }
}