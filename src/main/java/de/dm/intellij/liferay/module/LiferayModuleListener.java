package de.dm.intellij.liferay.module;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.ModuleListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import de.dm.intellij.liferay.bnd.OsgiBndFileParser;
import de.dm.intellij.liferay.hook.LiferayHookXmlParser;
import de.dm.intellij.liferay.theme.LiferayLookAndFeelXmlParser;
import de.dm.intellij.liferay.theme.LiferayPackageJSONParser;
import de.dm.intellij.liferay.util.ProjectUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public class LiferayModuleListener implements ModuleListener {

    @Override
    public void modulesAdded(@NotNull Project project, @NotNull List<? extends Module> modules) {
        for (Module module : modules) {
            ProjectUtils.runDumbAware(project, () -> {
                handleModuleFiles(project, module, "liferay-look-and-feel.xml");
                handleModuleFiles(project, module, "liferay-hook.xml");
                handleModuleFiles(project, module, "bnd.bnd");
                handleModuleFiles(project, module, "package.json");
            });
        }
    }

    private void handleModuleFiles(@NotNull Project project, @NotNull Module module, String filenamePattern) {
        if (!module.isDisposed()) {
            Collection<VirtualFile> virtualFilesByName = FilenameIndex.getVirtualFilesByName(filenamePattern, GlobalSearchScope.moduleScope(module));
            for (VirtualFile virtualFile : virtualFilesByName) {
               if (LiferayLookAndFeelXmlParser.isRelevantFile(virtualFile.getPath())) {
                    LiferayLookAndFeelXmlParser.handleChange(project, virtualFile);
                }
                if (LiferayHookXmlParser.isRelevantFile(virtualFile.getPath())) {
                    LiferayHookXmlParser.handleChange(project, virtualFile);
                }
                if (OsgiBndFileParser.isRelevantFile(virtualFile.getPath())) {
                    OsgiBndFileParser.handleChange(project, virtualFile);
                }
                if (LiferayPackageJSONParser.isRelevantFile(virtualFile.getPath())) {
                    LiferayPackageJSONParser.handleChange(project, virtualFile);
                }
            }
        }
    }

}
