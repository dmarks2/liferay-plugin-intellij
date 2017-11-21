package de.dm.intellij.liferay.project;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.progress.ProcessCanceledException;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.util.ProgressIndicatorUtils;
import com.intellij.openapi.progress.util.ReadTask;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.FileIndexUtil;
import com.intellij.openapi.vfs.*;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import de.dm.intellij.liferay.bnd.OsgiBndFileParser;
import de.dm.intellij.liferay.hook.LiferayHookXmlParser;
import de.dm.intellij.liferay.language.jsp.LiferayJspWebContentRootListener;
import de.dm.intellij.liferay.theme.LiferayLookAndFeelXmlParser;
import de.dm.intellij.liferay.theme.LiferayPackageJSONParser;
import de.dm.intellij.liferay.util.ProjectUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * Component to handle Liferay specific information on project level
 */
public class LiferayProjectComponent implements ProjectComponent {

    private final Project project;

    private VirtualFileListener virtualFileListener;

    public LiferayProjectComponent(final Project project) {
        this.project = project;

        this.virtualFileListener = new VirtualFileAdapter() {
            @Override
            public void contentsChanged(@NotNull VirtualFileEvent event) {
                LiferayLookAndFeelXmlParser.handleChange(LiferayProjectComponent.this.project, event);
                LiferayHookXmlParser.handleChange(LiferayProjectComponent.this.project, event);
                OsgiBndFileParser.handleChange(LiferayProjectComponent.this.project, event);
                LiferayPackageJSONParser.handleChange(LiferayProjectComponent.this.project, event);
                LiferayJspWebContentRootListener.handleChange(LiferayProjectComponent.this.project, event);
            }

            @Override
            public void fileCreated(@NotNull VirtualFileEvent event) {
                LiferayLookAndFeelXmlParser.handleChange(LiferayProjectComponent.this.project, event);
                LiferayHookXmlParser.handleChange(LiferayProjectComponent.this.project, event);
                OsgiBndFileParser.handleChange(LiferayProjectComponent.this.project, event);
                LiferayPackageJSONParser.handleChange(LiferayProjectComponent.this.project, event);
                LiferayJspWebContentRootListener.handleChange(LiferayProjectComponent.this.project, event);
            }
        };
    }

    @Override
    public void projectOpened() {

        ProjectUtils.runWhenInitialized(project, new Runnable() {
            @Override
            public void run() {
                handleProjectFiles("liferay-look-and-feel.xml");
                handleProjectFiles("liferay-hook.xml");
                handleProjectFiles("bnd.bnd");
                handleProjectFiles("package.json");
                handleProjectFiles("resources");
            }
        });

    }

    @Override
    public void projectClosed() {

    }

    @Override
    public void initComponent() {
        VirtualFileManager.getInstance().addVirtualFileListener(virtualFileListener);
    }

    @Override
    public void disposeComponent() {
        VirtualFileManager.getInstance().removeVirtualFileListener(virtualFileListener);
    }

    @NotNull
    @Override
    public String getComponentName() {
        return "Liferay Project";
    }

    private void handleProjectFiles(String filenamePattern) {
        Collection<VirtualFile> virtualFilesByName = FilenameIndex.getVirtualFilesByName(project, filenamePattern, GlobalSearchScope.projectScope(project));
        for (VirtualFile virtualFile : virtualFilesByName) {

            VirtualFileEvent event = new VirtualFileEvent(null, virtualFile, filenamePattern, null);

            LiferayLookAndFeelXmlParser.handleChange(project, event);
            LiferayHookXmlParser.handleChange(project, event);
            OsgiBndFileParser.handleChange(project, event);
            LiferayPackageJSONParser.handleChange(project, event);
            LiferayJspWebContentRootListener.handleChange(project, event);
        }
    }
}
