package de.dm.intellij.liferay.action;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
import de.dm.intellij.test.helper.LightProjectDescriptorBuilder;
import org.jetbrains.annotations.NotNull;

public class ShowThemeDiffActionJarTest extends LightJavaCodeInsightFixtureTestCase {

    private static final String TEST_DATA_PATH = "testdata/de/dm/intellij/liferay/action/ShowThemeDiffActionTest";

    @Override
    protected String getTestDataPath() {
        return TEST_DATA_PATH;
    }

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor() {
        return new LightProjectDescriptorBuilder()
                .library("com.liferay:com.liferay.frontend.theme.unstyled", TEST_DATA_PATH, "com.liferay.frontend.theme.unstyled.jar")
                .library("com.liferay:com.liferay.frontend.theme.styled", TEST_DATA_PATH, "com.liferay.frontend.theme.styled.jar")
                .build();
    }

    public void testUnstyledTemplateFileFromJar() {
        myFixture.configureByFiles(
                "templates/portlet.ftl",
                "package.json"
        );

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

    public void testStyledScssFileFromJar() {
        myFixture.configureByFiles(
                "css/_taglib.scss"
        );

        LiferayModuleComponent liferayModuleComponent = myFixture.getModule().getService(LiferayModuleComponent.class);

        liferayModuleComponent.setParentTheme("styled");

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

}
