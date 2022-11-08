package de.dm.intellij.liferay.language.osgi;

import com.intellij.psi.PsiElement;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import com.intellij.usageView.UsageInfo;
import com.intellij.usages.UsageTarget;
import com.intellij.usages.UsageTargetUtil;

import java.util.Collection;

public class MetaConfigurationFindUsagesProviderTest extends LightJavaCodeInsightFixtureTestCase {

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/osgi/MetaConfigurationFindUsagesProviderTest";
    }

    public void testFindUsageConfigurationId() {
        myFixture.configureByFiles(
                "de/dm/configuration/MyConfiguration.java",
                "de/dm/action/MyConfigurableAction.java",
                "aQute/bnd/annotation/metatype/Meta.java",
                "org/osgi/service/component/annotations/Component.java"
        );

        PsiElement element = myFixture.getFile().findElementAt(myFixture.getCaretOffset());

        UsageTarget[] usageTargets = UsageTargetUtil.findUsageTargets(element);

        assertTrue(usageTargets.length > 0);

        usageTargets = UsageTargetUtil.findUsageTargets(myFixture.getEditor(), myFixture.getFile());

        assertTrue(usageTargets.length > 0);

        Collection<UsageInfo> usages = myFixture.findUsages(element);

        assertTrue(usages.size() > 0);
    }
}
