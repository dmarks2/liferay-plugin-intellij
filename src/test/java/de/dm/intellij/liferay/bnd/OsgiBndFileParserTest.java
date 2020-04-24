package de.dm.intellij.liferay.bnd;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import de.dm.intellij.liferay.module.LiferayModuleComponent;

public class OsgiBndFileParserTest extends BasePlatformTestCase {

    private static final String NEW_TEXT = "Fragment-Host: com.liferay.login.web;bundle-version=\"[1.1.10,1.1.10]\"";

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/bnd/OsgiBndFileParserTest";
    }

    public void testOsgiBndFileParser() {
        myFixture.configureByFiles("bnd.bnd");

        assertEquals("com.liferay.dynamic.data.lists.form.web", LiferayModuleComponent.getOsgiFragmentHostPackageName(myFixture.getModule()));
    }

    public void testOsgiBndFileParserChange() {
        myFixture.configureByFiles("bnd.bnd");

        Editor editor = myFixture.getEditor();

        Document document = editor.getDocument();

        WriteCommandAction.runWriteCommandAction(myFixture.getProject(), () -> {
            document.replaceString(0, document.getTextLength(), NEW_TEXT);
        });

        FileDocumentManager fileDocumentManager = FileDocumentManager.getInstance();

        fileDocumentManager.saveDocument(document);

        assertEquals("com.liferay.login.web", LiferayModuleComponent.getOsgiFragmentHostPackageName(myFixture.getModule()));
    }

}