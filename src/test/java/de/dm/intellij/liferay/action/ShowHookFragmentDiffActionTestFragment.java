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
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

public class ShowHookFragmentDiffActionTestFragment extends LightJavaCodeInsightFixtureTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/liferay/action/ShowHookFragmentDiffActionTest";

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor() {
        return new LightProjectDescriptorBuilder()
                .library("com.liferay:com.liferay.login.web", TEST_DATA_PATH, "com.liferay.login.web.jar")
                .build();
    }

    @Override
    protected String getTestDataPath() {
        return TEST_DATA_PATH;
    }

    public void testFragmentJspHook() {
        myFixture.configureByFiles("META-INF/resources/init.jsp", "bnd.bnd");

        PsiFile psiFile = myFixture.getFile();

        VirtualFile initJsp = psiFile.getVirtualFile();

        ShowHookFragmentDiffAction action = new ShowHookFragmentDiffAction();

        AnActionEvent event = new AnActionEvent(
                null,
                new VirtualFilesDataProvider(myFixture.getProject(), initJsp),
                "",
                action.getTemplatePresentation().clone(),
                ActionManager.getInstance(),
                0
        );

        assertTrue("Diff action should be available for existing JSP file", action.isAvailable(event));
    }

    public void testFragmentJspHookAdditionalFile() {
        myFixture.configureByFiles("META-INF/resources/additional.jsp", "bnd.bnd");

        PsiFile psiFile = myFixture.getFile();

        VirtualFile additionalJsp = psiFile.getVirtualFile();

        ShowHookFragmentDiffAction action = new ShowHookFragmentDiffAction();

        AnActionEvent event = new AnActionEvent(
                null,
                new VirtualFilesDataProvider(myFixture.getProject(), additionalJsp),
                "",
                action.getTemplatePresentation().clone(),
                ActionManager.getInstance(),
                0
        );

        assertFalse("Diff action should not be available for additional JSP file", action.isAvailable(event));
    }

    public void testFragmentJspHookArbitraryFile() {
        myFixture.configureByFiles("META-INF/resources/foo.txt", "bnd.bnd");

        PsiFile psiFile = myFixture.getFile();

        VirtualFile arbitraryFile = psiFile.getVirtualFile();

        ShowHookFragmentDiffAction action = new ShowHookFragmentDiffAction();

        AnActionEvent event = new AnActionEvent(
                null,
                new VirtualFilesDataProvider(myFixture.getProject(), arbitraryFile),
                "",
                action.getTemplatePresentation().clone(),
                ActionManager.getInstance(),
                0
        );

        assertFalse("Diff action should not be available for arbitrary file", action.isAvailable(event));
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
