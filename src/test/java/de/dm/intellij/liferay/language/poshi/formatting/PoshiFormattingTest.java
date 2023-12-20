package de.dm.intellij.liferay.language.poshi.formatting;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

import java.util.Collections;

public class PoshiFormattingTest extends BasePlatformTestCase {

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/poshi/formatting/PoshiFormattingTest";
    }

    public void testFormatter() {
        myFixture.configureByFiles("testcases/Liferay.testcase");
        WriteCommandAction.writeCommandAction(getProject()).run(
                () -> {
                    CodeStyleManager codeStyleManager = CodeStyleManager.getInstance(getProject());

                    codeStyleManager.reformatText(
                            myFixture.getFile(),
                            Collections.singletonList(myFixture.getFile().getTextRange())
                    );
                }
        );

        myFixture.checkResultByFile("testcases/expected.testcase");
    }
}
