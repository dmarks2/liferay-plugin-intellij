package de.dm.intellij.liferay.project;

import com.intellij.ProjectTopics;
import com.intellij.openapi.components.AbstractProjectComponent;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.ModuleListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileEvent;
import com.intellij.openapi.vfs.VirtualFileListener;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.messages.MessageBus;
import com.intellij.util.messages.MessageBusConnection;
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
public class LiferayProjectComponent extends AbstractProjectComponent {

    private VirtualFileListener virtualFileListener;

    private MessageBusConnection messageBusConnection;

    public LiferayProjectComponent(final Project project) {
        super(project);

        this.virtualFileListener = new VirtualFileListener() {
            @Override
            public void contentsChanged(@NotNull VirtualFileEvent event) {
                LiferayLookAndFeelXmlParser.handleChange(LiferayProjectComponent.this.myProject, event);
                LiferayHookXmlParser.handleChange(LiferayProjectComponent.this.myProject, event);
                OsgiBndFileParser.handleChange(LiferayProjectComponent.this.myProject, event);
                LiferayPackageJSONParser.handleChange(LiferayProjectComponent.this.myProject, event);
                LiferayJspWebContentRootListener.handleChange(LiferayProjectComponent.this.myProject, event);
            }

            @Override
            public void fileCreated(@NotNull VirtualFileEvent event) {
                LiferayLookAndFeelXmlParser.handleChange(LiferayProjectComponent.this.myProject, event);
                LiferayHookXmlParser.handleChange(LiferayProjectComponent.this.myProject, event);
                OsgiBndFileParser.handleChange(LiferayProjectComponent.this.myProject, event);
                LiferayPackageJSONParser.handleChange(LiferayProjectComponent.this.myProject, event);
                LiferayJspWebContentRootListener.handleChange(LiferayProjectComponent.this.myProject, event);
            }
        };
    }

/*
    @Override
    public void projectOpened() {

        ProjectUtils.runWhenInitialized(project, projectFilesHandler);

        //
        project.getMessageBus().connect().subscribe(ProjectTopics.PROJECT_ROOTS, new ModuleRootAdapter() {
            @Override
            public void rootsChanged(ModuleRootEvent event) {
                ProjectUtils.runDumbAwareLater(project, projectFilesHandler);
            }
        });
        //

    }
*/

/*
    @Override
    public void projectClosed() {

    }
*/

    @Override
    public void initComponent() {
        VirtualFileManager.getInstance().addVirtualFileListener(virtualFileListener);

        MessageBus messageBus = myProject.getMessageBus();

        messageBusConnection = messageBus.connect();

        ModuleListener moduleListener = new ModuleListener() {
            @Override
            public void moduleAdded(@NotNull Project project, @NotNull Module module) {
                ProjectUtils.runDumbAware(project, () -> {
                    handleModuleFiles(module, "liferay-look-and-feel.xml");
                    handleModuleFiles(module, "liferay-hook.xml");
                    handleModuleFiles(module, "bnd.bnd");
                    handleModuleFiles(module, "package.json");
                    handleModuleFiles(module, "resources");
                });
            }
        };

        messageBusConnection.subscribe(ProjectTopics.MODULES, moduleListener);
    }

    @Override
    public void disposeComponent() {
        VirtualFileManager.getInstance().removeVirtualFileListener(virtualFileListener);

        messageBusConnection.disconnect();
    }

    @NotNull
    @Override
    public String getComponentName() {
        return "Liferay Project";
    }

    private void handleModuleFiles(Module module, String filenamePattern) {
        Collection<VirtualFile> virtualFilesByName = FilenameIndex.getVirtualFilesByName(myProject, filenamePattern, GlobalSearchScope.moduleScope(module));
        for (VirtualFile virtualFile : virtualFilesByName) {

            VirtualFileEvent event = new VirtualFileEvent(null, virtualFile, filenamePattern, null);

            LiferayLookAndFeelXmlParser.handleChange(myProject, event);
            LiferayHookXmlParser.handleChange(myProject, event);
            OsgiBndFileParser.handleChange(myProject, event);
            LiferayPackageJSONParser.handleChange(myProject, event);
            LiferayJspWebContentRootListener.handleChange(myProject, event);
        }
    }

}
