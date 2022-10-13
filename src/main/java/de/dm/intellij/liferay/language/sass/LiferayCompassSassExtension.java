package de.dm.intellij.liferay.language.sass;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.containers.ContainerUtil;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
import de.dm.intellij.liferay.util.LiferayVersions;
import de.dm.intellij.liferay.util.ProjectUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.sass.extensions.SassExtension;
import org.jetbrains.plugins.sass.extensions.SassExtensionFunctionInfo;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Provides Compass SASS library to (S)CSS files. Compass is used in Liferay 6.1 and 6.2
 */
public class LiferayCompassSassExtension extends SassExtension {

    private static final Set<String> APPROPRIATE_MODULE_NAMES = ContainerUtil.newHashSet(new String[]{"Compass::SassExtensions::Functions"});

    public static final String COMPASS_LIBRARY_NAME = "Compass 0.12.2";

    private Map<String, SassExtensionFunctionInfo> customFunctions = new HashMap<String, SassExtensionFunctionInfo>();

    @Override
    public String getName() {
        return COMPASS_LIBRARY_NAME;
    }

    @Override
    protected boolean isAvailableInModule(@NotNull Module module) {
        return true;
    }

    @Nullable
    @Override
    public SassExtensionFunctionInfo findCustomFunctionByName(@NotNull String name) {
        return customFunctions.get(name);
    }

    @NotNull
    @Override
    public Collection<SassExtensionFunctionInfo> getCustomFunctions() {
        return customFunctions.values();
    }

    @NotNull
    @Override
    public Collection<String> getRubyModulesWithFunctionExtensions() {
        return APPROPRIATE_MODULE_NAMES;
    }

    @NotNull
    @Override
    public Collection<? extends VirtualFile> getStylesheetsRoots(@NotNull Module module) {
        Collection<VirtualFile> result = new ArrayList<VirtualFile>();

        URL url = LiferayCompassSassExtension.class.getResource("/compass-0.12.2/stylesheets");
        VirtualFile virtualFile = VfsUtil.findFileByURL(url);
        result.add(virtualFile);

        url = LiferayCompassSassExtension.class.getResource("/compass-0.12.2/sass_extensions");
        virtualFile = VfsUtil.findFileByURL(url);
        result.add(virtualFile);

        return result;
    }

    protected void startActivity(@NotNull Module module) {
        if (!module.isDisposed()) {
            float liferayVersion = LiferayModuleComponent.getPortalMajorVersion(module);
            if (
                    (liferayVersion == LiferayVersions.LIFERAY_VERSION_6_1) ||
                    (liferayVersion == LiferayVersions.LIFERAY_VERSION_6_2)
                    ) {
                Collection<? extends VirtualFile> importRoots = getStylesheetsRoots(module);

                ProjectUtils.updateLibrary(module, COMPASS_LIBRARY_NAME, importRoots);
            }
        }
    }

    protected void stopActivity(@NotNull final Module module) {
        ApplicationManager.getApplication().invokeLater(
                () -> ProjectUtils.removeLibrary(module, COMPASS_LIBRARY_NAME)
        );
    }
}
