package de.dm.intellij.liferay.theme;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import de.dm.intellij.liferay.module.LiferayModuleComponent;

public class LiferayPackageJSONParserTest extends BasePlatformTestCase {

    private static final String NEW_TEXT = """
			{
			  "name": "my-theme",
			  "version": "1.0.0",
			  "main": "package.json",
			  "liferayTheme": {
			    "baseTheme": "unstyled",
			    "screenshot": "",
			    "rubySass": false,
			    "templateLanguage": "ftl",
			    "version": "7.0"
			  }
			}""";

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/theme/LiferayPackageJSONParserTest";
    }

    public void testPackageJsonParser() {
        myFixture.configureByFiles("package.json");

        assertEquals("styled", LiferayModuleComponent.getParentTheme(myFixture.getModule()));
    }

    public void testPackageJsonParserChange() {
        myFixture.configureByFiles("package.json");

        Editor editor = myFixture.getEditor();

        Document document = editor.getDocument();

        WriteCommandAction.runWriteCommandAction(myFixture.getProject(), () -> document.replaceString(0, document.getTextLength(), NEW_TEXT));

        FileDocumentManager fileDocumentManager = FileDocumentManager.getInstance();

        fileDocumentManager.saveDocument(document);

        assertEquals("unstyled", LiferayModuleComponent.getParentTheme(myFixture.getModule()));
    }

    public void testPackageCustomParentJsonParser() {
        myFixture.copyFileToProject("package_custom.json", "package.json");

        assertEquals("my-custom-base-theme", LiferayModuleComponent.getParentTheme(myFixture.getModule()));
    }
}
