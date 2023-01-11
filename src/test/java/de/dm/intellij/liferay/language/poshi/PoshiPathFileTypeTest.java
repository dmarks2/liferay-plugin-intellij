package de.dm.intellij.liferay.language.poshi;

import com.intellij.ide.highlighter.HtmlFileType;
import com.intellij.psi.PsiFile;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

public class PoshiPathFileTypeTest extends BasePlatformTestCase {

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/poshi/PoshiPathFileTypeTest";
    }

    public void testPoshiPathFileType() {
        myFixture.configureByFile("paths/LiferayLearn.path");

        PsiFile psiFile = myFixture.getFile();

        assertEquals(psiFile.getFileType().getName(), HtmlFileType.INSTANCE.getName());
    }
}
