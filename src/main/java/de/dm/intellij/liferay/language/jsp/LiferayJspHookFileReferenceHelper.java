package de.dm.intellij.liferay.language.jsp;

import com.intellij.jsp.highlighter.NewJspFileType;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFileSystemItem;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceHelper;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Reference helper to provide root contexts while developing JSP (including hooks or Fragment Host Modules)
 */
public class LiferayJspHookFileReferenceHelper extends FileReferenceHelper {

    @NotNull
    @Override
    public Collection<PsiFileSystemItem> getRoots(@NotNull final Module module) {
        final Collection<PsiFileSystemItem> result = new ArrayList<PsiFileSystemItem>();

        String fragmentHostPackageName = LiferayModuleComponent.getOsgiFragmentHostPackageName(module);

        LiferayFileUtil.addLibraryRoot(result, this, module, "com.liferay.portal:portal-web", null);

        if (fragmentHostPackageName != null) {
            LiferayFileUtil.addLibraryRoot(result, this, module, /*"com.liferay:" +*/ fragmentHostPackageName, "META-INF/resources");
        }

        boolean hasCustomJspBags = LiferayCustomJspBagUtil.hasCustomJspBags(module);
        if (hasCustomJspBags) {
            LiferayFileUtil.addLibraryRoot(result, this, module, "com.liferay.portal:com.liferay.portal.web", null);
        }

        return result;
    }

    @NotNull
    @Override
    public Collection<PsiFileSystemItem> getContexts(Project project, @NotNull VirtualFile file) {
        PsiFileSystemItem result = getPsiFileSystemItem(project, file);
        if (result == null) {
            return Collections.emptyList();
        } else {
            return Collections.singleton(result);
        }
    }

    @Override
    public boolean isMine(Project project, @NotNull VirtualFile file) {
        return NewJspFileType.INSTANCE.equals(file.getFileType());
    }
}
