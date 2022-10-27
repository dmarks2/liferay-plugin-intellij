package de.dm.intellij.liferay.action;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.TestDataProvider;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

public class ShowThemeDiffActionNodeModulesTest extends LightJavaCodeInsightFixtureTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/liferay/action/ShowThemeDiffActionTest";

    @Override
    protected String getTestDataPath() {
        return TEST_DATA_PATH;
    }


    public void testUnstyledTemplateFileFromNodeModules() {
        myFixture.configureByFiles(
                "templates/portlet.ftl",
                "package.json",
                "node_modules/liferay-frontend-theme-unstyled/templates/portlet.ftl"
        );

        PsiFile psiFile = myFixture.getFile();

        VirtualFile virtualFile = psiFile.getVirtualFile();

        ShowThemeDiffAction action = new ShowThemeDiffAction();

        AnActionEvent event = new AnActionEvent(
                null,
                new VirtualFilesDataProvider(myFixture.getProject(), virtualFile),
                "",
                action.getTemplatePresentation().clone(),
                ActionManager.getInstance(),
                0
        );

        assertTrue("Diff action should be available for template file from unstyled theme", action.isAvailable(event));
    }

    public void testStyledScssFileFromNodeModules() {
        myFixture.configureByFiles(
                "css/_taglib.scss",
                "node_modules/liferay-frontend-theme-styled/css/_taglib.scss"
        );

        LiferayModuleComponent liferayModuleComponent = myFixture.getModule().getService(LiferayModuleComponent.class);

        liferayModuleComponent.setParentTheme("styled");

        PsiFile psiFile = myFixture.getFile();

        VirtualFile virtualFile = psiFile.getVirtualFile();

        ShowThemeDiffAction action = new ShowThemeDiffAction();

        AnActionEvent event = new AnActionEvent(
                null,
                new VirtualFilesDataProvider(myFixture.getProject(), virtualFile),
                "",
                action.getTemplatePresentation().clone(),
                ActionManager.getInstance(),
                0
        );

        assertTrue("Diff action should be available for scss file from styled theme", action.isAvailable(event));
    }

    private static final class VirtualFilesDataProvider extends TestDataProvider {
        private VirtualFile[] virtualFiles;

        private VirtualFilesDataProvider(@NotNull Project project, VirtualFile... virtualFiles) {
            super(project);

            this.virtualFiles = virtualFiles;
        }

        @Override
        public Object getData(String dataId) {
            if (CommonDataKeys.VIRTUAL_FILE_ARRAY.is(dataId)) {
                return virtualFiles;
            }

            return super.getData(dataId);
        }
    }
}