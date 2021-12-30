package de.dm.intellij.liferay.language.fragment;

import com.intellij.freemarker.psi.files.FtlFileType;
import com.intellij.psi.PsiFile;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

public class FragmentFreemarkerHtmlFileTypeOverriderTest extends BasePlatformTestCase {

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/fragment/FragmentFreemarkerHtmlFileTypeOverriderTest";
    }

    public void testFreemarkerFileType() {
        myFixture.configureByFiles("index.html", "fragment.json");

        PsiFile psiFile = myFixture.getFile();

        assertEquals(psiFile.getFileType().getName(), FtlFileType.INSTANCE.getName());
    }
}
