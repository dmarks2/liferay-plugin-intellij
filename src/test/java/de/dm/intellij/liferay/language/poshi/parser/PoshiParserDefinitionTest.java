package de.dm.intellij.liferay.language.poshi.parser;

import com.intellij.codeInsight.daemon.impl.HighlightInfo;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import de.dm.intellij.liferay.language.poshi.PoshiFileType;

import java.util.List;

public class PoshiParserDefinitionTest extends BasePlatformTestCase {

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/poshi/parser/PoshiParserDefinitionTest";
    }

    public void testValidPoshiTestcaseFile() {
        myFixture.configureByFiles("testcases/Liferay.testcase");

        FileType fileType = myFixture.getFile().getFileType();

        assertTrue("Liferay.testcase should be detected as Poshi File", fileType instanceof PoshiFileType);

        List<HighlightInfo> highlightInfos = myFixture.doHighlighting();

        assertTrue("Liferay.testcases should show no syntax errors", highlightInfos.isEmpty());
    }

    public void testPoshiScriptTestcaseFile() {
        //see https://github.com/liferay/liferay-portal/blob/master/modules/test/poshi/poshi-core/src/test/resources/com/liferay/poshi/core/dependencies/elements/PoshiScript.testcase
        myFixture.configureByFiles("testcases/PoshiScript.testcase");

        List<HighlightInfo> highlightInfos = myFixture.doHighlighting();

        assertTrue("Official PoshiScript.testcase file should be parseable without syntax errors.", highlightInfos.isEmpty());
    }


    public void testValidPoshiFunctionFile() {
        myFixture.configureByFiles("functions/Sample.function");

        FileType fileType = myFixture.getFile().getFileType();

        assertTrue("Sample.function should be detected as Poshi File", fileType instanceof PoshiFileType);

        List<HighlightInfo> highlightInfos = myFixture.doHighlighting();

        assertTrue("Sample.function should show no syntax errors", highlightInfos.isEmpty());
    }

    public void testValidPoshiMacroFile() {
        myFixture.configureByFiles("macros/Sample.macro");

        FileType fileType = myFixture.getFile().getFileType();

        assertTrue("Sample.macro should be detected as Poshi File", fileType instanceof PoshiFileType);

        List<HighlightInfo> highlightInfos = myFixture.doHighlighting();

        assertTrue("Sample.macro should show no syntax errors", highlightInfos.isEmpty());
    }


}
