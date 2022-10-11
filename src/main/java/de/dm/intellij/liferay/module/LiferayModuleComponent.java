package de.dm.intellij.liferay.module;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.components.StoragePathMacros;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleComponent;
import de.dm.intellij.liferay.util.LiferayVersions;
import de.dm.intellij.liferay.util.ProjectUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Component to mark an IntelliJ module as being a "Liferay" Module
 */
@State(
        name = "LiferayModuleComponent",
        storages = {
                @Storage(StoragePathMacros.MODULE_FILE)
        }
)
public class LiferayModuleComponent implements PersistentStateComponent<LiferayModuleComponentStateWrapper> {

    public static final Pattern LIFERAY_MAJOR_VERSION_PATTERN = Pattern.compile("(\\d+[.]\\d+)");

    private final Module module;

    private String liferayVersion = "";
    private Map<String, String> themeSettings = new HashMap<String, String>();
    private String liferayLookAndFeelXml = "";
    private String liferayHookXml = "";
    private String osgiFragmentHost = "";
    private String parentTheme = "";
    private String customJspDir = "";
    private String resourcesImporterGroupName = "";

    public LiferayModuleComponent(@NotNull Module module) {
        this.module = module;
    }

    @Nullable
    public LiferayModuleComponentStateWrapper getState() {
        LiferayModuleComponentStateWrapper state = new LiferayModuleComponentStateWrapper();
        state.liferayVersion = this.liferayVersion;
        state.themeSettings = this.themeSettings;
        state.liferayLookAndFeelXml = this.liferayLookAndFeelXml;
        state.liferayHookXml = this.liferayHookXml;
        state.osgiFragmentHost = this.osgiFragmentHost;
        state.parentTheme = this.parentTheme;
        state.customJspDir = this.customJspDir;
        state.resourcesImporterGroupName = this.resourcesImporterGroupName;
        return state;
    }

    public void loadState(LiferayModuleComponentStateWrapper state) {
        this.liferayVersion = state.liferayVersion;
        this.themeSettings = state.themeSettings;
        this.liferayLookAndFeelXml = state.liferayLookAndFeelXml;
        this.liferayHookXml = state.liferayHookXml;
        this.osgiFragmentHost = state.osgiFragmentHost;
        this.parentTheme = state.parentTheme;
        this.customJspDir = state.customJspDir;
        this.resourcesImporterGroupName = state.resourcesImporterGroupName;
    }

    @NotNull
    public String getComponentName() {
        return "Liferay Module";
    }

    public String getLiferayVersion() {
        if ( (liferayVersion != null ) && (! liferayVersion.equals("")) ) {
            return liferayVersion;
        }

        Module parentModule = ProjectUtils.getParentModule(module);
        while (parentModule != null) {
            String parentLiferayVersion = getLiferayVersion(parentModule);
            if ( (parentLiferayVersion != null) && (! parentLiferayVersion.equals(""))) {
                return parentLiferayVersion;
            }

            parentModule = ProjectUtils.getParentModule(parentModule);
        }

        return "";
    }

    public static String getLiferayVersion(Module module) {
        LiferayModuleComponent component = getInstance(module);
        if (component != null) {
            return component.getLiferayVersion();
        }
        return "";
    }

    public void setLiferayVersion(String liferayVersion) {
        this.liferayVersion = liferayVersion;
    }

    public Map<String, String> getThemeSettings() {
        return themeSettings;
    }

    public float getPortalMajorVersion() {
        float majorVersion = 0;

        Matcher matcher = LIFERAY_MAJOR_VERSION_PATTERN.matcher(getLiferayVersion());

        if (matcher.find()) {
            majorVersion = Float.parseFloat(matcher.group(1));
        }

        return majorVersion;
    }

    public static float getPortalMajorVersion(Module module) {
        LiferayModuleComponent component = getInstance(module);
        if (component != null) {
            return component.getPortalMajorVersion();
        }
        return LiferayVersions.LIFERAY_VERSION_UNKNOWN;
    }

    public static LiferayModuleComponent getInstance(Module module) {
        return module.getService(LiferayModuleComponent.class);
    }

    public static String getThemeSetting(Module module, String setting) {
        LiferayModuleComponent component = getInstance(module);
        if (component != null) {
            return component.getThemeSettings().get(setting);
        }
        return null;
    }

    public static String getLiferayLookAndFeelXml(Module module) {
        LiferayModuleComponent component = getInstance(module);
        if (component != null) {
            return component.getLiferayLookAndFeelXml();
        }
        return null;
    }

    public String getLiferayLookAndFeelXml() {
        return liferayLookAndFeelXml;
    }

    public void setLiferayLookAndFeelXml(String liferayLookAndFeelXml) {
        this.liferayLookAndFeelXml = liferayLookAndFeelXml;
    }

    public String getLiferayHookXml() {
        return liferayHookXml;
    }

    public void setLiferayHookXml(String liferayHookXml) {
        this.liferayHookXml = liferayHookXml;
    }

    public String getOsgiFragmentHost() {
        return osgiFragmentHost;
    }

    public void setOsgiFragmentHost(String osgiFragmentHost) {
        this.osgiFragmentHost = osgiFragmentHost;
    }

    public static String getLiferayHookXml(Module module) {
        LiferayModuleComponent component = getInstance(module);
        if (component != null) {
            return component.getLiferayHookXml();
        }
        return null;
    }

    public static String getOsgiFragmentHost(Module module) {
        LiferayModuleComponent component = getInstance(module);
        if (component != null) {
            return component.getOsgiFragmentHost();
        }
        return null;
    }

    public static String getOsgiFragmentHostPackageName(Module module) {
        String fragmentHostPackageName;

        String fragmentHost = getOsgiFragmentHost(module);
        if (fragmentHost != null) {
            int index = fragmentHost.indexOf(';');
            if (index > -1) {
                String packageName = fragmentHost.substring(0, index);
                fragmentHostPackageName = packageName;
            } else {
                fragmentHostPackageName = null;
            }
        } else {
            fragmentHostPackageName = null;
        }

        return fragmentHostPackageName;
    }

    public String getParentTheme() {
        return parentTheme;
    }

    public void setParentTheme(String parentTheme) {
        this.parentTheme = parentTheme;
    }

    public static String getParentTheme(Module module) {
        LiferayModuleComponent component = getInstance(module);
        if (component != null) {
            return component.getParentTheme();
        }
        return null;
    }

    public String getCustomJspDir() {
        return customJspDir;
    }

    public void setCustomJspDir(String customJspDir) {
        this.customJspDir = customJspDir;
    }

    public static String getCustomJspDir(Module module) {
        LiferayModuleComponent component = getInstance(module);
        if (component != null) {
            return component.getCustomJspDir();
        }
        return null;
    }

    public String getResourcesImporterGroupName() {
        return resourcesImporterGroupName;
    }

    public void setResourcesImporterGroupName(String resourcesImporterGroupName) {
        this.resourcesImporterGroupName = resourcesImporterGroupName;
    }

    public static String getResourcesImporterGroupName(Module module) {
        LiferayModuleComponent component = getInstance(module);
        if (component != null) {
            return component.getResourcesImporterGroupName();
        }
        return null;
    }
}
