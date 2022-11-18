package de.dm.intellij.liferay.language.osgi;

import com.intellij.psi.PsiElement;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import com.intellij.usageView.UsageViewUtil;

public class MetaConfigurationElementDescriptionProviderTest extends LightJavaCodeInsightFixtureTestCase {

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/osgi/MetaConfigurationElementDescriptionProviderTest";
    }

    public void testGetShortNAme() {
        myFixture.configureByFiles(
                "de/dm/configuration/MyConfiguration.java",
                "aQute/bnd/annotation/metatype/Meta.java"
        );

        PsiElement element = myFixture.getFile().findElementAt(myFixture.getCaretOffset()).getParent();

        assertEquals("de.dm.configuration.MyConfiguration", UsageViewUtil.getShortName(element));
    }
}
