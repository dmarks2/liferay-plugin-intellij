package de.dm.intellij.bndtools.formatting;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.vfs.newvfs.impl.VfsRootAccess;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.intellij.util.PathUtil;

import java.io.File;
import java.util.Collections;

public class BndFormattingTest extends BasePlatformTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/bndtools/formatting/BndFormattingTest";

    @Override
    protected String getTestDataPath() {
        return TEST_DATA_PATH;
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        File testDataDir = new File(myFixture.getTestDataPath());

        final String testDataPath = PathUtil.toSystemIndependentName(testDataDir.getAbsolutePath());

        VfsRootAccess.allowRootAccess(myFixture.getTestRootDisposable(), testDataPath);
    }

    public void testFormatter() {
        myFixture.configureByFiles("bnd.bnd");
        WriteCommandAction.writeCommandAction(getProject()).run(
                () -> {
                    CodeStyleManager codeStyleManager = CodeStyleManager.getInstance(getProject());

                    codeStyleManager.reformatText(
                            myFixture.getFile(),
                            Collections.singletonList(myFixture.getFile().getTextRange())
                    );
                }
        );

        myFixture.checkResultByFile("formatted.bnd");
    }

}
