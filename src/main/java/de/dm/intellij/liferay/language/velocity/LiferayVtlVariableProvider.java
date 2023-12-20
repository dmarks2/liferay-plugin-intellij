package de.dm.intellij.liferay.language.velocity;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.progress.ProcessCanceledException;
import com.intellij.velocity.VtlGlobalVariableProvider;
import com.intellij.velocity.psi.VtlLightVariable;
import com.intellij.velocity.psi.VtlVariable;
import com.intellij.velocity.psi.files.VtlFile;
import de.dm.intellij.liferay.language.TemplateVariable;
import de.dm.intellij.liferay.language.TemplateVariableProcessor;
import de.dm.intellij.liferay.language.TemplateVariableProcessorUtil;
import de.dm.intellij.liferay.language.velocity.structure.StructureVtlVariable;
import de.dm.intellij.liferay.language.velocity.themereference.ThemeReferenceVtlVariable;
import de.dm.intellij.liferay.language.velocity.themesettings.ThemeSettingsVtlVariable;
import de.dm.intellij.liferay.theme.LiferayThemeTemplateVariables;
import de.dm.intellij.liferay.util.LiferayVersions;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LiferayVtlVariableProvider extends VtlGlobalVariableProvider implements TemplateVariableProcessor<VtlFile, VtlVariable> {

    private final static Logger log = Logger.getInstance(LiferayVtlVariableProvider.class);

    private static final Map<String, Class<? extends VtlLightVariable>> TYPE_MAPPING = new HashMap<>();
    static {
        TYPE_MAPPING.put(ThemeSettingsVtlVariable.VARIABLE_NAME, ThemeSettingsVtlVariable.class);

        for (String key : LiferayThemeTemplateVariables.THEME_TEMPLATE_VARIABLE_DIRECTORY_REFERENCES.keySet()) {
            TYPE_MAPPING.put(key, ThemeReferenceVtlVariable.class);
        }
    }


    @NotNull
    public Collection<VtlVariable> getGlobalVariables(@NotNull VtlFile vtlFile) {
        try {

            return TemplateVariableProcessorUtil.getGlobalVariables(this, vtlFile);

        } catch (ProcessCanceledException e) {
            return Collections.emptySet();
        }
    }

    public VtlVariable createVariable(final String name, final VtlFile parent, String typeText) {
        if (TYPE_MAPPING.containsKey(name)) {
            Class<? extends VtlLightVariable> clazz = TYPE_MAPPING.get(name);
            try {
                Constructor<? extends VtlLightVariable> constructor = clazz.getConstructor(String.class, VtlFile.class);

                return constructor.newInstance(name, parent);
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                try {
                    Constructor<? extends VtlLightVariable> constructor = clazz.getConstructor(VtlFile.class);

                    return constructor.newInstance(parent);
                } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e2) {
                    log.error(e2.getMessage(), e2);
                }
            }
        }

        return new CustomVtlVariable(name, parent, typeText);
    }

    public VtlVariable createStructureVariable(TemplateVariable templateVariable) {
        return new StructureVtlVariable(templateVariable);
    }


    @Override
    public String[] getAdditionalLanguageSpecificResources(float liferayVersion) {
        if (
                (liferayVersion == LiferayVersions.LIFERAY_VERSION_7_0) ||
                (liferayVersion == LiferayVersions.LIFERAY_VERSION_7_1) ||
                (liferayVersion == LiferayVersions.LIFERAY_VERSION_7_2) ||
                (liferayVersion == LiferayVersions.LIFERAY_VERSION_7_3) ||
                (liferayVersion == LiferayVersions.LIFERAY_VERSION_7_4) ||
                (liferayVersion == LiferayVersions.LIFERAY_VERSION_UNKNOWN)
                ) { //Liferay 7.0
            return new String[] {
                    "/com/liferay/vtl/context_additional_velocity_70.vm"
            };
        }

        return new String[0];
    }

}
