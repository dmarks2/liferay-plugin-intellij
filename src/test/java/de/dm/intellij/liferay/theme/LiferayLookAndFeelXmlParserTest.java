package de.dm.intellij.liferay.theme;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import de.dm.intellij.liferay.module.LiferayModuleComponent;

public class LiferayLookAndFeelXmlParserTest extends BasePlatformTestCase {

    private static final String NEW_TEXT = "<?xml version=\"1.0\"?>\n" +
            "<!DOCTYPE look-and-feel PUBLIC \"-//Liferay//DTD Look and Feel 7.0.0//EN\" \"http://www.liferay.com/dtd/liferay-look-and-feel_7_0_0.dtd\">\n" +
            "\n" +
            "<look-and-feel>\n" +
            "    <compatibility>\n" +
            "        <version>7.0.6+</version>\n" +
            "    </compatibility>\n" +
            "    <theme id=\"test-theme\" name=\"test-theme\">\n" +
            "        <root-path>/my_root</root-path>\n" +
            "    </theme>\n" +
            "</look-and-feel>";

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/theme/LiferayLookAndFeelXmlParserTest";
    }

    public void testLookAndFeelXmlParserCustom() {
        myFixture.configureByFiles("custom/liferay-look-and-feel.xml");

        LiferayModuleComponent liferayModuleComponent = LiferayModuleComponent.getInstance(myFixture.getModule());

        assertEquals("/my_root", liferayModuleComponent.getThemeSettings().get(LiferayLookAndFeelXmlParser.ROOT_PATH));
        assertEquals("/my_css", liferayModuleComponent.getThemeSettings().get(LiferayLookAndFeelXmlParser.CSS_PATH));
        assertEquals("/my_images", liferayModuleComponent.getThemeSettings().get(LiferayLookAndFeelXmlParser.IMAGES_PATH));
        assertEquals("/my_js", liferayModuleComponent.getThemeSettings().get(LiferayLookAndFeelXmlParser.JAVASCRIPT_PATH));
        assertEquals("/my_templates", liferayModuleComponent.getThemeSettings().get(LiferayLookAndFeelXmlParser.TEMPLATES_PATH));
        assertEquals("vtl", liferayModuleComponent.getThemeSettings().get(LiferayLookAndFeelXmlParser.TEMPLATE_EXTENSION));
    }

    public void testLookAndFeelXmlParserDefault() {
        myFixture.configureByFiles("default/liferay-look-and-feel.xml");

        LiferayModuleComponent liferayModuleComponent = LiferayModuleComponent.getInstance(myFixture.getModule());

        assertEquals("/", liferayModuleComponent.getThemeSettings().get(LiferayLookAndFeelXmlParser.ROOT_PATH));
        assertEquals("/css", liferayModuleComponent.getThemeSettings().get(LiferayLookAndFeelXmlParser.CSS_PATH));
        assertEquals("/images", liferayModuleComponent.getThemeSettings().get(LiferayLookAndFeelXmlParser.IMAGES_PATH));
        assertEquals("/js", liferayModuleComponent.getThemeSettings().get(LiferayLookAndFeelXmlParser.JAVASCRIPT_PATH));
        assertEquals("/templates", liferayModuleComponent.getThemeSettings().get(LiferayLookAndFeelXmlParser.TEMPLATES_PATH));
        assertEquals("ftl", liferayModuleComponent.getThemeSettings().get(LiferayLookAndFeelXmlParser.TEMPLATE_EXTENSION));
    }

    public void testLookAndFeelXmlParserChange() {
        myFixture.configureByFiles("default/liferay-look-and-feel.xml");

        Editor editor = myFixture.getEditor();

        Document document = editor.getDocument();

        WriteCommandAction.runWriteCommandAction(myFixture.getProject(), () -> {
            document.replaceString(0, document.getTextLength(), NEW_TEXT);
        });

        FileDocumentManager fileDocumentManager = FileDocumentManager.getInstance();

        fileDocumentManager.saveDocument(document);

        LiferayModuleComponent liferayModuleComponent = LiferayModuleComponent.getInstance(myFixture.getModule());

        assertEquals("/my_root", liferayModuleComponent.getThemeSettings().get(LiferayLookAndFeelXmlParser.ROOT_PATH));
    }

}
