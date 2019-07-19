package de.dm.intellij.liferay.language.freemarker;

import com.intellij.freemarker.psi.files.FtlFile;
import com.intellij.freemarker.psi.files.FtlFileType;
import com.intellij.freemarker.psi.files.FtlGlobalVariableProvider;
import com.intellij.freemarker.psi.files.FtlXmlNamespaceType;
import com.intellij.freemarker.psi.variables.FtlLightVariable;
import com.intellij.freemarker.psi.variables.FtlTemplateType;
import com.intellij.freemarker.psi.variables.FtlVariable;
import com.intellij.javaee.web.WebRoot;
import com.intellij.javaee.web.facet.WebFacet;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.progress.ProcessCanceledException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.xml.XmlDocument;
import com.intellij.psi.xml.XmlFile;
import com.intellij.util.Processor;
import com.intellij.xml.XmlNSDescriptor;
import de.dm.intellij.liferay.language.TemplateMacroProcessor;
import de.dm.intellij.liferay.language.TemplateMacroProcessorUtil;
import de.dm.intellij.liferay.language.TemplateVariable;
import de.dm.intellij.liferay.language.TemplateVariableProcessor;
import de.dm.intellij.liferay.language.TemplateVariableProcessorUtil;
import de.dm.intellij.liferay.language.freemarker.custom.CustomFtlVariable;
import de.dm.intellij.liferay.language.freemarker.enumutil.EnumUtilFtlVariable;
import de.dm.intellij.liferay.language.freemarker.servicelocator.ServiceLocatorFtlVariable;
import de.dm.intellij.liferay.language.freemarker.staticfieldgetter.StaticFieldGetterFtlVariable;
import de.dm.intellij.liferay.language.freemarker.staticutil.StaticUtilFtlVariable;
import de.dm.intellij.liferay.language.freemarker.structure.StructureFtlVariable;
import de.dm.intellij.liferay.language.freemarker.themereference.ThemeReferenceFtlVariable;
import de.dm.intellij.liferay.language.freemarker.themesettings.ThemeSettingsFtlVariable;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
import de.dm.intellij.liferay.theme.LiferayThemeTemplateVariables;
import de.dm.intellij.liferay.util.LiferayVersions;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LiferayFtlVariableProvider extends FtlGlobalVariableProvider implements TemplateVariableProcessor<FtlFile, FtlVariable>, TemplateMacroProcessor<FtlFile, FtlFile> {

    private static final Map<String, Class<? extends FtlLightVariable>> TYPE_MAPPING = new HashMap<>();
    static {
        TYPE_MAPPING.put(ThemeSettingsFtlVariable.VARIABLE_NAME, ThemeSettingsFtlVariable.class);
        TYPE_MAPPING.put(ServiceLocatorFtlVariable.VARIABLE_NAME, ServiceLocatorFtlVariable.class);
        TYPE_MAPPING.put(EnumUtilFtlVariable.VARIABLE_NAME, EnumUtilFtlVariable.class);
        TYPE_MAPPING.put(StaticUtilFtlVariable.VARIABLE_NAME, StaticUtilFtlVariable.class);
        TYPE_MAPPING.put(StaticFieldGetterFtlVariable.VARIABLE_NAME, StaticFieldGetterFtlVariable.class);
        for (String key : LiferayThemeTemplateVariables.THEME_TEMPLATE_VARIABLE_DIRECTORY_REFERENCES.keySet()) {
            TYPE_MAPPING.put(key, ThemeReferenceFtlVariable.class);
        }
    }

    @NotNull
    public List<? extends FtlVariable> getGlobalVariables(FtlFile file) {
        try {

            List<FtlVariable> result = new ArrayList<FtlVariable>();
            result.addAll(TemplateVariableProcessorUtil.getGlobalVariables(this, file));

            FtlFile ftlFile = file;
            if (file.getOriginalFile() != null) {
                ftlFile = (FtlFile)file.getOriginalFile();
            }

            final Module module = ModuleUtil.findModuleForPsiElement(ftlFile);
            if (module != null) {
                float liferayVersion = LiferayModuleComponent.getPortalMajorVersion(module);

                Collection<FtlFile> macros = TemplateMacroProcessorUtil.getGlobalMacros(this, ftlFile);
                for (FtlFile macro : macros) {
                    //Provide FTL_liferay.ftl as predefined variables in the Freemarker namespace "liferay"
                    result.addAll(getTemplateTypeVariables(macro, "liferay"));
                }

                //Provide Liferay Taglibs as predefined variables in their corresponding Freemarker namespaces
                result.addAll(getTaglibSupportVariables("/com/liferay/tld/liferay-aui.tld", module, "liferay_aui"));
                result.addAll(getTaglibSupportVariables("/com/liferay/tld/liferay-portlet-ext.tld", module, "liferay_portlet"));
                result.addAll(getTaglibSupportVariables("/com/liferay/tld/liferay-portlet.tld", module, "portlet"));
                result.addAll(getTaglibSupportVariables("/com/liferay/tld/liferay-security.tld", module, "liferay_security"));
                result.addAll(getTaglibSupportVariables("/com/liferay/tld/liferay-theme.tld", module, "liferay_theme"));
                result.addAll(getTaglibSupportVariables("/com/liferay/tld/liferay-ui.tld", module, "liferay_ui"));
                result.addAll(getTaglibSupportVariables("/com/liferay/tld/liferay-util.tld", module, "liferay_util"));

                if (
                        (liferayVersion == LiferayVersions.LIFERAY_VERSION_7_0) ||
                        (liferayVersion == LiferayVersions.LIFERAY_VERSION_7_1) ||
                        (liferayVersion == LiferayVersions.LIFERAY_VERSION_7_2) ||
                        (liferayVersion == LiferayVersions.LIFERAY_VERSION_UNKNOWN)
                        ) { //Liferay 7.0
                    result.addAll(getTaglibSupportVariables("/com/liferay/tld/liferay-product-navigation.tld", module, "liferay_product_navigation"));
                    result.addAll(getTaglibSupportVariables("/com/liferay/tld/liferay-journal.tld", module, "liferay_journal"));
                    result.addAll(getTaglibSupportVariables("/com/liferay/tld/liferay-flags.tld", module, "liferay_flags"));
                    result.addAll(getTaglibSupportVariables("/com/liferay/tld/liferay-layout.tld", module, "liferay_layout"));
                    result.addAll(getTaglibSupportVariables("/com/liferay/tld/liferay-site-navigation.tld", module, "liferay_site_navigation"));
                    result.addAll(getTaglibSupportVariables("/com/liferay/tld/liferay-asset.tld", module, "liferay_asset"));
                    result.addAll(getTaglibSupportVariables("/com/liferay/tld/liferay-map.tld", module, "liferay_map"));
                    result.addAll(getTaglibSupportVariables("/com/liferay/tld/liferay-item-selector.tld", module, "liferay_item_selector"));
                    result.addAll(getTaglibSupportVariables("/com/liferay/tld/liferay-expando.tld", module, "liferay_expando"));
                    result.addAll(getTaglibSupportVariables("/com/liferay/tld/liferay-frontend.tld", module, "liferay_frontend"));
                    result.addAll(getTaglibSupportVariables("/com/liferay/tld/liferay-trash.tld", module, "liferay_trash"));
                }
            }

            return result;
        } catch (ProcessCanceledException e) {
            return Collections.emptyList();
        }
    }

    @NotNull
    @Override
    public Map<String, FtlFile> getSpecialFileReferenceValues(@NotNull FtlFile file) {
        try {
            FtlFile ftlFile = file;
            if (file.getOriginalFile() != null) {
                ftlFile = (FtlFile)file.getOriginalFile();
            }

            Collection<FtlFile> macros = TemplateMacroProcessorUtil.getGlobalMacros(this, ftlFile);
            Map<String, FtlFile> result = new HashMap<String, FtlFile>();
            for (FtlFile macro : macros) {
                result.put("FTL_liferay.ftl", macro);
            }

            Project project = file.getProject();
            ModuleManager moduleManager = ModuleManager.getInstance(project);
            Module[] modules = moduleManager.getModules();

            for (Module module : modules) {
                String servletContextName = module.getName() + "_SERVLET_CONTEXT_";

                Collection<WebFacet> webFacets = WebFacet.getInstances(module);

                for (WebFacet webFacet : webFacets) {

                    List<WebRoot> webRoots = webFacet.getWebRoots();

                    for (WebRoot webRoot : webRoots) {
                        if ("/".equals(webRoot.getRelativePath())) {

                            if (webRoot.getFile() != null) {
                                VirtualFile webRootFile = webRoot.getFile();

                                VfsUtilCore.processFilesRecursively(webRootFile, virtualFile -> {
                                    if (FtlFileType.INSTANCE.equals(virtualFile.getFileType())) {

                                        FtlFile webContextFtlFile = (FtlFile)PsiManager.getInstance(module.getProject()).findFile(virtualFile);

                                        String relativePath = VfsUtilCore.getRelativePath(virtualFile, webRootFile);

                                        String filePath = "/" + servletContextName + "/" + relativePath;

                                        result.put(filePath, webContextFtlFile);
                                    }

                                    return true;
                                });
                            }
                        }
                    }
                }
            }

            return result;

        } catch (ProcessCanceledException e) {
            return Collections.emptyMap();
        }
    }

    public FtlVariable createVariable(String name, FtlFile parent, String typeText) {
        if (TYPE_MAPPING.containsKey(name)) {
            Class<? extends FtlLightVariable> clazz = TYPE_MAPPING.get(name);
            try {
                Constructor<? extends FtlLightVariable> constructor = clazz.getConstructor(String.class, PsiElement.class);

                return constructor.newInstance(name, parent);
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                try {
                    Constructor<? extends FtlLightVariable> constructor = clazz.getConstructor(PsiElement.class);

                    return constructor.newInstance(parent);
                } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e2) {
                    e2.printStackTrace();
                }
            }
        }
        return new CustomFtlVariable(name, parent, typeText);
    }

    public FtlVariable createStructureVariable(TemplateVariable templateVariable) {
        return new StructureFtlVariable(templateVariable);
    }


    @Override
    public String[] getAdditionalLanguageSpecificResources(float liferayVersion) {
        if (
                (liferayVersion == LiferayVersions.LIFERAY_VERSION_7_0) ||
                (liferayVersion == LiferayVersions.LIFERAY_VERSION_7_1) ||
                (liferayVersion == LiferayVersions.LIFERAY_VERSION_7_2) ||
                (liferayVersion == LiferayVersions.LIFERAY_VERSION_UNKNOWN)
                ) { //Liferay 7.0
            return new String[] {
                    "/com/liferay/vtl/context_additional_freemarker_70.vm"
            };
        }

        return new String[0];
    }

    public Collection<FtlFile> getMacrosFromFile(PsiFile psiFile) {
        return Arrays.asList((FtlFile)psiFile);
    }

    public String getMacroFileName(float liferayVersion) {
        if (liferayVersion == LiferayVersions.LIFERAY_VERSION_6_1) {
            return "/com/liferay/ftl/FTL_liferay_61.ftl";
        } else if (liferayVersion == LiferayVersions.LIFERAY_VERSION_6_2) {
            return "/com/liferay/ftl/FTL_liferay_62.ftl";
        } else if
                (
                (liferayVersion == LiferayVersions.LIFERAY_VERSION_7_0) ||
                (liferayVersion == LiferayVersions.LIFERAY_VERSION_UNKNOWN)
                ) {
            return "/com/liferay/ftl/FTL_liferay_70.ftl";
        } else if (liferayVersion == LiferayVersions.LIFERAY_VERSION_7_1) {
            return "/com/liferay/ftl/FTL_liferay_71.ftl";
        } else if (liferayVersion == LiferayVersions.LIFERAY_VERSION_7_2) {
            return "/com/liferay/ftl/FTL_liferay_72.ftl";
        }

        return null;
    }

    private List<? extends FtlVariable> getTaglibSupportVariables(@NotNull final String resource,  @NotNull final Module module, @NotNull @NonNls final String taglibPrefix) {
        URL url = TemplateMacroProcessorUtil.class.getResource(resource);
        VirtualFile macroFile = VfsUtil.findFileByURL(url);
        XmlFile xmlFile = (XmlFile)PsiManager.getInstance(module.getProject()).findFile(macroFile);

        if (xmlFile == null) {
            return Collections.emptyList();
        }

        final XmlDocument document = xmlFile.getDocument();
        if (document == null) {
            return Collections.emptyList();
        }

        final XmlNSDescriptor descriptor = (XmlNSDescriptor) document.getMetaData();
        if (descriptor == null) {
            return Collections.emptyList();
        }

        PsiElement declaration = descriptor.getDeclaration();
        if (declaration == null) {
            declaration = xmlFile;
        }

        return Arrays.asList(new CustomFtlVariable(taglibPrefix, declaration, new FtlXmlNamespaceType(descriptor)));
    }

    private List<? extends FtlVariable> getTemplateTypeVariables(final FtlFile ftlFile,  @NotNull @NonNls final String taglibPrefix) {
        if (ftlFile == null) {
            return Collections.emptyList();
        }

        return Arrays.asList(new CustomFtlVariable(taglibPrefix, ftlFile, new FtlTemplateType(ftlFile)));
    }

}
