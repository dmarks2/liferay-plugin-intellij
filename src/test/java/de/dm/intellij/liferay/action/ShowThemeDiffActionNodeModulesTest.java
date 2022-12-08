package de.dm.intellij.liferay.action;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.testFramework.TestDataProvider;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
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

        DataContext dataContext = ((EditorEx)myFixture.getEditor()).getDataContext();

        AnActionEvent event = new AnActionEvent(
                null,
                dataContext, //new VirtualFilesDataProvider(myFixture.getProject(), virtualFile),
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
        myFixture.copyFileToProject("package_styled.json", "package.json");

        PsiFile psiFile = myFixture.getFile();

        VirtualFile virtualFile = psiFile.getVirtualFile();

        ShowThemeDiffAction action = new ShowThemeDiffAction();

        DataContext dataContext = ((EditorEx)myFixture.getEditor()).getDataContext();

        AnActionEvent event = new AnActionEvent(
                null,
                dataContext, //new VirtualFilesDataProvider(myFixture.getProject(), virtualFile),
                "",
                action.getTemplatePresentation().clone(),
                ActionManager.getInstance(),
                0
        );

        assertTrue("Diff action should be available for scss file from styled theme", action.isAvailable(event));
    }

    public void testCustomBaseThemeScssFileFromNodeModules() {
        myFixture.configureByFiles(
                "css/_custom.scss",
                "node_modules/my-custom-base-theme/src/css/_custom.scss",
                "node_modules/my-custom-base-theme/package.json"
        );
        myFixture.copyFileToProject("package_custom.json", "package.json");

        PsiFile psiFile = myFixture.getFile();

        VirtualFile virtualFile = psiFile.getVirtualFile();

        ShowThemeDiffAction action = new ShowThemeDiffAction();

        DataContext dataContext = ((EditorEx)myFixture.getEditor()).getDataContext();

        AnActionEvent event = new AnActionEvent(
                null,
                dataContext, //new VirtualFilesDataProvider(myFixture.getProject(), virtualFile),
                "",
                action.getTemplatePresentation().clone(),
                ActionManager.getInstance(),
                0
        );

        assertTrue("Diff action should be available for scss file from custom base theme", action.isAvailable(event));
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