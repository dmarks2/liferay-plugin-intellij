package de.dm.intellij.liferay.hook;

import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import de.dm.intellij.liferay.module.LiferayModuleComponent;

public class LiferayHookXmlParserTest extends BasePlatformTestCase {

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/hook/LiferayHookXmlParserTest";
    }

    public void testLiferayHookXmlParser() {
        myFixture.configureByFiles("liferay-hook.xml");

        LiferayModuleComponent liferayModuleComponent = LiferayModuleComponent.getInstance(myFixture.getModule());

        assertEquals(myFixture.getFile().getVirtualFile().getPath(), liferayModuleComponent.getLiferayHookXml());
    }

}