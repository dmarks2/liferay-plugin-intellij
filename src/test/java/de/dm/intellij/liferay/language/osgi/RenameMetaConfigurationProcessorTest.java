package de.dm.intellij.liferay.language.osgi;

import com.intellij.psi.PsiElement;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;

public class RenameMetaConfigurationProcessorTest extends LightJavaCodeInsightFixtureTestCase {

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/osgi/RenameMetaConfigurationProcessorTest";
    }

    public void testRenameConfigurationPid() {
        myFixture.configureByFiles(
                "de/dm/configuration/MyRenameableConfiguration.java",
                "de/dm/action/MyRenameableConfigurableAction.java",
                "aQute/bnd/annotation/metatype/Meta.java",
                "org/osgi/service/component/annotations/Component.java"
        );

        PsiElement caretElement = myFixture.getFile().findElementAt(myFixture.getCaretOffset());

        assertNotNull(caretElement);

        PsiElement element = caretElement.getParent();

        myFixture.renameElement(element, "de.dm.configuration.UpdatedConfiguration");

        myFixture.checkResultByFile(
                "de/dm/configuration/MyRenameableConfiguration.java", "de/dm/configuration/MyRenameableConfiguration_expected.java", false);

        myFixture.checkResultByFile(
                "de/dm/action/MyRenameableConfigurableAction.java", "de/dm/action/MyRenameableConfigurableAction_expected.java", false);

    }

    public void testRenameMultipleConfigurationPid() {
        myFixture.configureByFiles(
                "de/dm/configuration/MyRenameableConfiguration.java",
                "de/dm/action/MyRenameableMultipleConfigurableAction.java",
                "aQute/bnd/annotation/metatype/Meta.java",
                "org/osgi/service/component/annotations/Component.java"
        );

        PsiElement caretElement = myFixture.getFile().findElementAt(myFixture.getCaretOffset());

        assertNotNull(caretElement);

        PsiElement element = caretElement.getParent();

        myFixture.renameElement(element, "de.dm.configuration.UpdatedConfiguration");

        myFixture.checkResultByFile(
                "de/dm/configuration/MyRenameableConfiguration.java", "de/dm/configuration/MyRenameableConfiguration_expected.java", false);

        myFixture.checkResultByFile(
                "de/dm/action/MyRenameableMultipleConfigurableAction.java", "de/dm/action/MyRenameableMultipleConfigurableAction_expected.java", false);

    }
}
