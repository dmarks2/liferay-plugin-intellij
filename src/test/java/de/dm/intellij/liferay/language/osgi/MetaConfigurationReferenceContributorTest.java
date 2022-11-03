package de.dm.intellij.liferay.language.osgi;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.psi.PsiElement;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import com.intellij.usageView.UsageInfo;

import java.util.Collection;
import java.util.List;

public class MetaConfigurationReferenceContributorTest extends LightJavaCodeInsightFixtureTestCase {

    @Override
    protected String getTestDataPath() {
        return "testdata/de/dm/intellij/liferay/language/osgi/MetaConfigurationReferenceContributorTest";
    }

    public void testConfigurationPidCompletion() {
        myFixture.configureByFiles(
                "de/dm/action/MyConfigurableAction.java",
                "de/dm/configuration/MyConfiguration.java",
                "aQute/bnd/annotation/metatype/Meta.java",
                "org/osgi/service/component/annotations/Component.java"
        );

        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("de.dm.configuration.MyConfiguration"));
    }

    public void testMultipleConfigurationPidCompletion() {
        myFixture.configureByFiles(
                "de/dm/action/MyMultipleConfigurableAction.java",
                "de/dm/configuration/MyConfiguration.java",
                "aQute/bnd/annotation/metatype/Meta.java",
                "org/osgi/service/component/annotations/Component.java"
        );

        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("de.dm.configuration.MyConfiguration"));
    }

    public void testResolvePidReference() {
        myFixture.configureByFiles(
                "de/dm/action/MyConfigurableReferenceAction.java",
                "de/dm/configuration/MyConfiguration.java",
                "aQute/bnd/annotation/metatype/Meta.java",
                "org/osgi/service/component/annotations/Component.java"
        );

        PsiElement element = myFixture.getFile().findElementAt(myFixture.getCaretOffset()).getParent();
        PsiElement resolve = element.getReferences()[0].resolve();

        assertNotNull(resolve);
    }

    public void testResolveMultiplePidReference() {
        myFixture.configureByFiles(
                "de/dm/action/MyMultipleConfigurableReferenceAction.java",
                "de/dm/configuration/MyConfiguration.java",
                "aQute/bnd/annotation/metatype/Meta.java",
                "org/osgi/service/component/annotations/Component.java"
        );

        PsiElement element = myFixture.getFile().findElementAt(myFixture.getCaretOffset()).getParent();
        PsiElement resolve = element.getReferences()[0].resolve();

        assertNotNull(resolve);
    }

}