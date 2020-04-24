package de.dm.intellij.liferay.theme;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import com.sun.org.apache.bcel.internal.generic.NEW;
import de.dm.intellij.liferay.module.LiferayModuleComponent;

public class LiferayPackageJSONParserTest extends BasePlatformTestCase {

    private static final String NEW_TEXT = "{\n" +
            "  \"name\": \"my-theme\",\n" +
            "  \"version\": \"1.0.0\",\n" +
            "  \"main\": \"package.json\",\n" +
            "  \"liferayTheme\": {\n" +
            "    \"baseTheme\": \"unstyled\",\n" +
            "    \"screenshot\": \"\",\n" +
            "    \"rubySass\": false,\n" +
            "    \"templateLanguage\": \"ftl\",\n" +
            "    \"version\": \"7.0\"\n" +
            "  }\n" +
            "}";

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

        WriteCommandAction.runWriteCommandAction(myFixture.getProject(), () -> {
            document.replaceString(0, document.getTextLength(), NEW_TEXT);
        });

        FileDocumentManager fileDocumentManager = FileDocumentManager.getInstance();

        fileDocumentManager.saveDocument(document);

        assertEquals("unstyled", LiferayModuleComponent.getParentTheme(myFixture.getModule()));
    }
}
