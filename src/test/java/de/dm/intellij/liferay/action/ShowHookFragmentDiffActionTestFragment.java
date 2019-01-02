package de.dm.intellij.liferay.action;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.impl.JavaAwareProjectJdkTableImpl;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.LanguageLevelModuleExtension;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.newvfs.impl.VfsRootAccess;
import com.intellij.pom.java.LanguageLevel;
import com.intellij.psi.PsiFile;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.PsiTestUtil;
import com.intellij.testFramework.TestDataProvider;
import com.intellij.testFramework.fixtures.DefaultLightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import com.intellij.util.PathUtil;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class ShowHookFragmentDiffActionTestFragment extends LightCodeInsightFixtureTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/liferay/action/ShowHookFragmentDiffActionTest";

    private static final LightProjectDescriptor JAVA_LOGIN_WEB_DESCRIPTOR = new DefaultLightProjectDescriptor() {

        @Override
        public void configureModule(@NotNull Module module, @NotNull ModifiableRootModel model, @NotNull ContentEntry contentEntry) {
            LanguageLevelModuleExtension extension = model.getModuleExtension(LanguageLevelModuleExtension.class);
            if (extension != null) {
                extension.setLanguageLevel(LanguageLevel.JDK_1_8);
            }
            Sdk jdk = JavaAwareProjectJdkTableImpl.getInstanceEx().getInternalJdk();
            model.setSdk(jdk);

            final String testDataPath = PathUtil.toSystemIndependentName(new File(TEST_DATA_PATH).getAbsolutePath());
            VfsRootAccess.allowRootAccess( testDataPath );

            PsiTestUtil.addLibrary(module, model, "com.liferay:com.liferay.login.web", testDataPath, "com.liferay.login.web.jar");
        }
    };

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor() {
        return JAVA_LOGIN_WEB_DESCRIPTOR;
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
