package de.dm.intellij.test.helper;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.LanguageLevelModuleExtension;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.vfs.newvfs.impl.VfsRootAccess;
import com.intellij.pom.java.LanguageLevel;
import com.intellij.testFramework.IdeaTestUtil;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.PsiTestUtil;
import com.intellij.testFramework.fixtures.DefaultLightProjectDescriptor;
import com.intellij.util.PathUtil;
import com.intellij.util.io.URLUtil;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class LightProjectDescriptorBuilder {

    /**
     * Resolves the local filesystem path that needs to be passed to {@link VfsRootAccess#allowRootAccess} in order
     * to access the given classpath resource. If the resource lives inside a jar (e.g. because the plugin's own
     * classes/resources are packaged as a jar in the test sandbox), the path of that jar itself is returned, since
     * that is what needs to be an allowed root, not the "jar://...!/" entry path.
     * <p>
     * This deliberately avoids resolving the resource through {@code VfsUtil}/the VFS: doing so would itself try to
     * access the (not yet allowed) jar and fail with the very same {@link VfsRootAccess} error we're trying to avoid.
     */
    public static String resolveRootAccessPath(String classpathResource) {
        return resolveRootAccessPath(LightProjectDescriptorBuilder.class, classpathResource);
    }

    public static String resolveRootAccessPath(Class<?> resourceLoader, String classpathResource) {
        URL resource = resourceLoader.getResource(classpathResource);

        if (resource == null) {
            return null;
        }

        Pair<String, String> jarUrl = URLUtil.splitJarUrl(resource.toExternalForm());

        if (jarUrl != null) {
            return PathUtil.toSystemIndependentName(jarUrl.first);
        }

        try {
            return PathUtil.toSystemIndependentName(new File(resource.toURI()).getAbsolutePath());
        } catch (URISyntaxException e) {
            return PathUtil.toSystemIndependentName(new File(resource.getFile()).getAbsolutePath());
        }
    }

    public static final LightProjectDescriptor DEFAULT_PROJECT_DESCRIPTOR = new LightProjectDescriptorBuilder().build();

    public static final LightProjectDescriptor XSD_ROOT_ACCESS_PROJECT_DESCRIPTOR = new LightProjectDescriptorBuilder()
            .rootAccess("/com/liferay/xsd")
            .build();

    public static final LightProjectDescriptor LIFERAY_SCHEMA_ROOT_ACCESS_PROJECT_DESCRIPTOR = new LightProjectDescriptorBuilder()
            .rootAccess(
                    "/com/liferay/schema",
                    "/com/liferay/schema74/configuration-json-schema.json"
            ).build();

    private static class LibraryInfo {
        private String libName;
        private String libPath;
        private String[] jars;
    }

    private String[] rootAccess;
    private String liferayVersion;
    private final Map<String, String> themeSettings = new HashMap<>();
    private final Collection<LibraryInfo> libraries = new ArrayList<>();

    private LanguageLevel languageLevel = LanguageLevel.JDK_11;

    public LightProjectDescriptorBuilder() {
    }

    public LightProjectDescriptorBuilder rootAccess(String... rootAccess) {
        this.rootAccess = rootAccess;

        return this;
    }

    public LightProjectDescriptorBuilder liferayVersion(String liferayVersion) {
        this.liferayVersion = liferayVersion;

        return this;
    }

    public LightProjectDescriptorBuilder themeSettings(String key, String value) {
        this.themeSettings.put(key, value);

        return this;
    }

    public LightProjectDescriptorBuilder library(String libName, String libPath, String... jars) {
        LibraryInfo libraryInfo = new LibraryInfo();

        libraryInfo.libName = libName;
        libraryInfo.libPath = libPath;
        libraryInfo.jars = jars;

        this.libraries.add(libraryInfo);

        return this;
    }

    public LightProjectDescriptorBuilder languageLevel(LanguageLevel languageLevel) {
        this.languageLevel = languageLevel;

        return this;
    }

    public LightProjectDescriptor build() {
        return new DefaultLightProjectDescriptor() {

            @Override
            public void configureModule(@NotNull Module module, @NotNull ModifiableRootModel model, @NotNull ContentEntry contentEntry) {
                model.getModuleExtension(LanguageLevelModuleExtension.class).setLanguageLevel(languageLevel);

                if (rootAccess != null) {
                    Collection<String> resourcePaths = new ArrayList<>();

                    for (String rootAccessUrl : rootAccess) {
                        String resourcePath = resolveRootAccessPath(rootAccessUrl);

                        if (resourcePath != null) {
                            resourcePaths.add(resourcePath);
                        }
                    }

                    VfsRootAccess.allowRootAccess( Disposer.newDisposable(), resourcePaths.toArray(new String[0]));
                }

                if (liferayVersion != null) {
                    LiferayModuleComponent.getInstance(module).setLiferayVersion(liferayVersion);
                }

                for (Map.Entry<String, String> themeSetting : themeSettings.entrySet()) {
                    LiferayModuleComponent.getInstance(module).getThemeSettings().put(themeSetting.getKey(), themeSetting.getValue());
                }

                for (LibraryInfo libraryInfo : libraries) {
                    final String absoluteLibPath = PathUtil.toSystemIndependentName(new File(libraryInfo.libPath).getAbsolutePath());
                    VfsRootAccess.allowRootAccess( Disposer.newDisposable(), absoluteLibPath );

                    PsiTestUtil.addLibrary(model, libraryInfo.libName, absoluteLibPath, libraryInfo.jars);
                }
            }

            @Override
            public Sdk getSdk() {
                return IdeaTestUtil.getMockJdk(languageLevel.toJavaVersion());
            }
        };
    }
}
