package de.dm.intellij.liferay.language.osgi;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.psi.PsiElement;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MetaConfigurationReferenceContributorTest extends LightJavaCodeInsightFixtureTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/liferay/language/osgi/MetaConfigurationReferenceContributorTest";

    @Override
    protected String getTestDataPath() {
        return TEST_DATA_PATH;
    }

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor() {
        return new LightProjectDescriptorBuilder()
                .library("com.liferay:com.liferay.document.library.api", TEST_DATA_PATH, "com.liferay.document.library.api.jar")
                .build();
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

    public void testConfigurationPidFromLibraryCompletion() {
        myFixture.configureByFiles(
                "de/dm/action/MyConfigurableAction.java",
                "aQute/bnd/annotation/metatype/Meta.java",
                "org/osgi/service/component/annotations/Component.java"
        );

        myFixture.complete(CompletionType.BASIC, 1);
        List<String> strings = myFixture.getLookupElementStrings();
        assertTrue(strings.contains("com.liferay.document.library.configuration.DLConfiguration"));
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

    public void testResolvePidReferenceFromLibrary() {
        myFixture.configureByFiles(
                "de/dm/action/MyLibraryConfigurableReferenceAction.java",
                "aQute/bnd/annotation/metatype/Meta.java",
                "org/osgi/service/component/annotations/Component.java"
        );

        PsiElement element = myFixture.getFile().findElementAt(myFixture.getCaretOffset()).getParent();
        PsiElement resolve = element.getReferences()[0].resolve();

        assertNotNull(resolve);
    }


    /*
    public void testRenameConfigurationPid() {
        myFixture.configureByFiles(
                "de/dm/configuration/MyRenameableConfiguration.java",
                "de/dm/action/MyRenameableConfigurableAction.java",
                "aQute/bnd/annotation/metatype/Meta.java",
                "org/osgi/service/component/annotations/Component.java"
        );

        PsiElement element = myFixture.getFile().findElementAt(myFixture.getCaretOffset()).getParent();

        myFixture.renameElement(element, "de.dm.configuration.UpdatedConfiguration");

        myFixture.checkResultByFile(
                "de/dm/action/MyRenameableConfigurableAction.java", "de/dm/action/MyRenameableConfigurableAction_expected.java", false);

    }
     */

}
