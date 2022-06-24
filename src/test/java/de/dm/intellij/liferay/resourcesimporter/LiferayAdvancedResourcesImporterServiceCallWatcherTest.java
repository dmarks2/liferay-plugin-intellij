package de.dm.intellij.liferay.resourcesimporter;

import com.intellij.psi.PsiFile;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

public class LiferayAdvancedResourcesImporterServiceCallWatcherTest extends LightJavaCodeInsightFixtureTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/liferay/resourcesimporter/LiferayAdvancedResourcesImporterServiceCallWatcherTest";

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor() {
        return LightProjectDescriptorBuilder.DEFAULT_PROJECT_DESCRIPTOR;
    }

    @Override
    protected String getTestDataPath() {
        return TEST_DATA_PATH;
    }

    public void testAdvancedResourcesImporterServiceCall() {
        PsiFile[] psiFiles = myFixture.configureByFiles("MyImporter.java", "de/dm/toolbox/liferay/resources/importer/service/AdvancedResourcesImporterService.java", "com/liferay/portal/kernel/model/Company.java", "javax/servlet/ServletContext.java");

        String text = psiFiles[0].getText();
        myFixture.saveText(psiFiles[0].getVirtualFile(), text);

        //TODO test not yet working because value is set "later" in non-dumb mode..
        //assertEquals("MY_GROUP", LiferayModuleComponent.getResourcesImporterGroupName(myFixture.getModule()));
    }

}
