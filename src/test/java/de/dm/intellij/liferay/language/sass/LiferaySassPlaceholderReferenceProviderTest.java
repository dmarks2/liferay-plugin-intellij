package de.dm.intellij.liferay.language.sass;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import de.dm.intellij.liferay.module.LiferayModuleComponent;

import java.util.List;

import de.dm.intellij.liferay.theme.LiferayLookAndFeelXmlParser;

public class LiferaySassPlaceholderReferenceProviderTest extends LightCodeInsightFixtureTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/liferay/language/sass/LiferaySassPlaceholderReferenceProviderTest";

    @Override
    protected String getTestDataPath() {
        return TEST_DATA_PATH;
    }

    public void testScssThemeImagePath() {
        myFixture.configureByFiles("css/test.scss");
        myFixture.copyFileToProject("images/liferay.png", "images/liferay.png");

        LiferayModuleComponent liferayModuleComponent = LiferayModuleComponent.getInstance(myFixture.getModule());
        liferayModuleComponent.getThemeSettings().put(LiferayLookAndFeelXmlParser.IMAGES_PATH, "/images");

        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("liferay.png"));
    }

    public void testCssThemeImagePath() {
        myFixture.configureByFiles("css/foo.css");
        myFixture.copyFileToProject("images/liferay.png", "images/liferay.png");

        LiferayModuleComponent liferayModuleComponent = LiferayModuleComponent.getInstance(myFixture.getModule());
        liferayModuleComponent.getThemeSettings().put(LiferayLookAndFeelXmlParser.IMAGES_PATH, "/images");

        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("liferay.png"));
    }

}
