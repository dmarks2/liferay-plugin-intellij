package de.dm.intellij.liferay.theme;

import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import de.dm.intellij.liferay.module.LiferayModuleComponent;

public class LiferayLookAndFeelXmlParserTest extends LightCodeInsightFixtureTestCase {

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/theme/LiferayLookAndFeelXmlParserTest";
    }

    public void testCompletion() {
        myFixture.configureByFiles("liferay-look-and-feel.xml");

        //workaround?
        String text = myFixture.getFile().getText();
        myFixture.saveText(myFixture.getFile().getVirtualFile(), text);

        LiferayModuleComponent liferayModuleComponent = LiferayModuleComponent.getInstance(myFixture.getModule());

        assertEquals("/my_root", liferayModuleComponent.getThemeSettings().get(LiferayLookAndFeelXmlParser.ROOT_PATH));
        assertEquals("/my_css", liferayModuleComponent.getThemeSettings().get(LiferayLookAndFeelXmlParser.CSS_PATH));
        assertEquals("/my_images", liferayModuleComponent.getThemeSettings().get(LiferayLookAndFeelXmlParser.IMAGES_PATH));
        assertEquals("/my_js", liferayModuleComponent.getThemeSettings().get(LiferayLookAndFeelXmlParser.JAVASCRIPT_PATH));
        assertEquals("/my_templates", liferayModuleComponent.getThemeSettings().get(LiferayLookAndFeelXmlParser.TEMPLATES_PATH));
        assertEquals("vtl", liferayModuleComponent.getThemeSettings().get(LiferayLookAndFeelXmlParser.TEMPLATE_EXTENSION));
    }

}
