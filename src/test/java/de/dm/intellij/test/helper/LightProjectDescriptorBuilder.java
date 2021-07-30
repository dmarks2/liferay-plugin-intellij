package de.dm.intellij.test.helper;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.LanguageLevelModuleExtension;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.vfs.newvfs.impl.VfsRootAccess;
import com.intellij.pom.java.LanguageLevel;
import com.intellij.testFramework.IdeaTestUtil;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.PsiTestUtil;
import com.intellij.testFramework.fixtures.DefaultLightProjectDescriptor;
import com.intellij.util.PathUtil;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class LightProjectDescriptorBuilder {

    public static final LightProjectDescriptor DEFAULT_PROJECT_DESCRIPTOR = new LightProjectDescriptorBuilder().build();

    public static final LightProjectDescriptor XSD_ROOT_ACCESS_PROJECT_DESCRIPTOR = new LightProjectDescriptorBuilder()
            .rootAccess("/com/liferay/xsd")
            .build();

    public static final LightProjectDescriptor LIFERAY_SCHEMA_ROOT_ACCESS_PROJECT_DESCRIPTOR = new LightProjectDescriptorBuilder()
            .rootAccess("/com/liferay/schema")
            .build();

    private static class LibraryInfo {
        private String libName;
        private String libPath;
        private String[] jars;
    }

    private String[] rootAccess;
    private String liferayVersion;
    private Map<String, String> themeSettings = new HashMap<>();
    private Collection<LibraryInfo> libraries = new ArrayList<>();

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
                    for (String rootAccessUrl : rootAccess) {
                        URL resource = LightProjectDescriptorBuilder.class.getResource(rootAccessUrl);
                        String resourcePath = PathUtil.toSystemIndependentName(new File(resource.getFile()).getAbsolutePath());
                        VfsRootAccess.allowRootAccess( Disposer.newDisposable(), resourcePath );
                    }
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
