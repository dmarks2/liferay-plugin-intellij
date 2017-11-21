package de.dm.intellij.liferay.language.sass;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFileSystemItem;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceHelper;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import de.dm.intellij.liferay.util.LiferayThemes;
import de.dm.intellij.liferay.util.LiferayVersions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.scss.SCSSFileType;

import java.util.ArrayList;
import java.util.Collection;

public class LiferaySassFileReferenceHelper extends FileReferenceHelper {

    @NotNull
    @Override
    public Collection<PsiFileSystemItem> getContexts(Project project, @NotNull VirtualFile file) {
        final Collection<PsiFileSystemItem> result = new ArrayList<PsiFileSystemItem>();

        final Module module = ModuleUtil.findModuleForFile(file, project);
        if (module != null) {

            LiferayFileUtil.addLibraryRoot(result, this, module, "com.liferay:com.liferay.frontend.css.common", "META-INF/resources");

            String parentTheme = LiferayModuleComponent.getParentTheme(module);
            float liferayVersion = LiferayModuleComponent.getPortalMajorVersion(module);

            if ( (parentTheme != null) && (parentTheme.trim().length() > 0) ) {
                if (liferayVersion == LiferayVersions.LIFERAY_VERSION_6_2) {
                    if (LiferayThemes.THEME_UNSTYLED_UNDERSCORE.equals(parentTheme)) {
                        LiferayFileUtil.addLibraryRoot(result, this, module, "com.liferay.portal:portal-web", "html/themes/_unstyled/css");
                    } else if (LiferayThemes.THEME_STYLED_UNDERSCORE.equals(parentTheme)) {
                        LiferayFileUtil.addLibraryRoot(result, this, module, "com.liferay.portal:portal-web", "html/themes/_unstyled/css");
                        LiferayFileUtil.addLibraryRoot(result, this, module, "com.liferay.portal:portal-web", "html/themes/_styled/css");
                    }
                } else if (
                            (liferayVersion == LiferayVersions.LIFERAY_VERSION_7_0) ||
                            (liferayVersion == LiferayVersions.LIFERAY_VERSION_UNKNOWN)
                        ) {
                    if (
                            (LiferayThemes.THEME_UNSTYLED_UNDERSCORE.equals(parentTheme)) ||
                            (LiferayThemes.THEME_UNSTYLED.equals(parentTheme))
                        ) {
                        LiferayFileUtil.addLibraryRoot(result, this, module, "com.liferay:com.liferay.frontend.theme.unstyled", "META-INF/resources/_unstyled/css");
                    } else if (
                            (LiferayThemes.THEME_STYLED_UNDERSCORE.equals(parentTheme)) ||
                            (LiferayThemes.THEME_STYLED.equals(parentTheme))
                        ) {
                        LiferayFileUtil.addLibraryRoot(result, this, module, "com.liferay:com.liferay.frontend.theme.unstyled", "META-INF/resources/_unstyled/css");
                        LiferayFileUtil.addLibraryRoot(result, this, module, "com.liferay:com.liferay.frontend.theme.styled", "META-INF/resources/_styled/css");
                    }
                }
            }

        }

        return result;
    }

    @Override
    public boolean isMine(Project project, @NotNull VirtualFile file) {
        return SCSSFileType.SCSS.equals(file.getFileType());
    }

}
