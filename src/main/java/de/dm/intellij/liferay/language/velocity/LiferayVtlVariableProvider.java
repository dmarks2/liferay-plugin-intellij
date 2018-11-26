package de.dm.intellij.liferay.language.velocity;

import com.intellij.freemarker.psi.files.FtlFile;
import com.intellij.freemarker.psi.variables.FtlLightVariable;
import com.intellij.freemarker.psi.variables.FtlVariable;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.progress.ProcessCanceledException;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiType;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.xml.XmlFile;
import com.intellij.velocity.VtlGlobalVariableProvider;
import com.intellij.velocity.psi.VtlLightVariable;
import com.intellij.velocity.psi.VtlVariable;
import com.intellij.velocity.psi.files.VtlFile;
import de.dm.intellij.liferay.language.TemplateVariable;
import de.dm.intellij.liferay.language.TemplateVariableProcessor;
import de.dm.intellij.liferay.language.TemplateVariableProcessorUtil;
import de.dm.intellij.liferay.language.freemarker.enumutil.EnumUtilFtlVariable;
import de.dm.intellij.liferay.language.freemarker.servicelocator.ServiceLocatorFtlVariable;
import de.dm.intellij.liferay.language.freemarker.staticutil.StaticUtilFtlVariable;
import de.dm.intellij.liferay.language.freemarker.structure.StructureFtlVariable;
import de.dm.intellij.liferay.language.freemarker.themesettings.ThemeSettingsFtlVariable;
import de.dm.intellij.liferay.language.velocity.structure.StructureVtlVariable;
import de.dm.intellij.liferay.language.velocity.themesettings.ThemeSettingsVtlVariable;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
import de.dm.intellij.liferay.theme.LiferayLookAndFeelXmlParser;
import de.dm.intellij.liferay.util.LiferayVersions;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LiferayVtlVariableProvider extends VtlGlobalVariableProvider implements TemplateVariableProcessor<VtlFile, VtlVariable> {

    private static final Map<String, Class<? extends VtlLightVariable>> TYPE_MAPPING = new HashMap<>();
    static {
        TYPE_MAPPING.put(ThemeSettingsVtlVariable.VARIABLE_NAME, ThemeSettingsVtlVariable.class);
    }


    @NotNull
    public Collection<VtlVariable> getGlobalVariables(@NotNull VtlFile vtlFile) {
        try {

            return TemplateVariableProcessorUtil.getGlobalVariables(this, vtlFile);

        } catch (ProcessCanceledException e) {
            return Collections.emptySet();
        }
    }

    public VtlVariable createVariable(final String name, final VtlFile parent, String typeText, PsiElement navigationalElement) {
        return createVariable(name, parent, typeText, navigationalElement, null, false);
    }

    public VtlVariable createVariable(final String name, final VtlFile parent, String typeText, PsiElement navigationalElement, final Collection<VtlVariable> nestedVariables, boolean repeatable) {
        if (TYPE_MAPPING.containsKey(name)) {
            Class<? extends VtlLightVariable> clazz = TYPE_MAPPING.get(name);
            try {
                Constructor<? extends VtlLightVariable> constructor = clazz.getConstructor(VtlFile.class);

                return constructor.newInstance(parent);
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return new CustomVtlVariable(name, parent, typeText, navigationalElement, nestedVariables, false);
    }

    public VtlVariable createStructureVariable(TemplateVariable templateVariable) {
        return new StructureVtlVariable(templateVariable);
    }


    @Override
    public String[] getAdditionalLanguageSpecificResources(float liferayVersion) {
        if (
                (liferayVersion == LiferayVersions.LIFERAY_VERSION_7_0) ||
                (liferayVersion == LiferayVersions.LIFERAY_VERSION_UNKNOWN)
                ) { //Liferay 7.0
            return new String[] {
                    "/com/liferay/vtl/context_additional_velocity_70.vm"
            };
        }

        return new String[0];
    }

}
