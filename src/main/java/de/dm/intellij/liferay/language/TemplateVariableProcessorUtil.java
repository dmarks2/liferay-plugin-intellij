package de.dm.intellij.liferay.language;

import com.intellij.json.psi.JsonArray;
import com.intellij.json.psi.JsonBooleanLiteral;
import com.intellij.json.psi.JsonFile;
import com.intellij.json.psi.JsonObject;
import com.intellij.json.psi.JsonProperty;
import com.intellij.json.psi.JsonValue;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlDocument;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.velocity.psi.files.VtlFile;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
import de.dm.intellij.liferay.theme.LiferayThemeTemplateVariableReferenceFinder;
import de.dm.intellij.liferay.util.LiferayFileUtil;
import de.dm.intellij.liferay.util.LiferayVersions;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateVariableProcessorUtil {
    public static final Pattern IMPLICIT_VAR_DECL_PATTERN = Pattern.compile("(.*?)@vtlvariable [ ]*name=\"([^\"]+)\"[ \t\n]+type=\"([^\"]*)\"([ \t\n]+file=\"([^\"]*)\")?(.*)");

    public static <F extends PsiFile, T extends PsiNamedElement> List<T> getGlobalVariables(TemplateVariableProcessor<F, T> templateVariableProcessor, F file) {
        //During Completion Contribution the file is copied and a placeholder is added at the current cursor position.
        //So examine the original file in this case...
        F templateFile = file;
        if (file.getOriginalFile() != null) {
            templateFile = (F) file.getOriginalFile();
        }

        final Module module = ModuleUtil.findModuleForPsiElement(templateFile);
        if (module == null) {
            return Collections.emptyList();
        }

        float portalMajorVersion = -1.0f;

        LiferayModuleComponent component = module.getComponent(LiferayModuleComponent.class);
        if (component != null) {
            portalMajorVersion = component.getPortalMajorVersion();
        }

        final List<T> variables = new ArrayList<T>();

        boolean isJournalTemplateFile = LiferayFileUtil.isJournalTemplateFile(templateFile);

        boolean isThemeTemplateFile = LiferayFileUtil.isThemeTemplateFile(templateFile);

        boolean isLayoutTemplateFile = LiferayFileUtil.isLayoutTemplateFile(templateFile);

        boolean isApplicationDisplayTemplateFile = LiferayFileUtil.isApplicationDisplayTemplateFile(templateFile);

        boolean isBaseDDMTemplateFile = isJournalTemplateFile || isApplicationDisplayTemplateFile;

        boolean isStandardVelocityContextFile = isJournalTemplateFile || isThemeTemplateFile || isLayoutTemplateFile || isApplicationDisplayTemplateFile;

        if (isJournalTemplateFile) {
            variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/journal_template.vm"));

            if (
                    (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_0) ||
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_UNKNOWN)
                    ) { //Liferay 7.0
                variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/journal_template_70.vm"));
            }
            VirtualFile journalStructureFile = LiferayFileUtil.getJournalStructureFile(templateFile);
            if (journalStructureFile != null) {
                variables.addAll(getStructureVariables(templateVariableProcessor, journalStructureFile, templateFile, module.getProject()));
            }
        }

        if (isApplicationDisplayTemplateFile) {
            String applicationDisplayTemplateFileType = LiferayFileUtil.getApplicationDisplayTemplateType(templateFile);
            if (applicationDisplayTemplateFileType != null) {
                //type mapping see https://github.com/liferay/liferay-portal/blob/master/modules/apps/web-experience/export-import/export-import-resources-importer/src/main/java/com/liferay/exportimport/resources/importer/internal/util/FileSystemImporter.java
                //additional variables added based on tests with dump script (see https://web.liferay.com/de/web/james.falkner/blog/-/blogs/the-magic-template-variable-dumper-script-for-liferay-7)

                if ("asset_entry".equals(applicationDisplayTemplateFileType)) {
                    if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_6_1) {
                        //Liferay 6.1 does not have Application Display Templates
                    } else if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_6_2) {
                        variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/adt_asset_entry_62.vm"));
                    } else if (
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_0) ||
                                    (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_UNKNOWN)
                            ) { //Liferay 7.0
                        //see https://raw.githubusercontent.com/liferay/liferay-portal/7.0.x/modules/apps/web-experience/asset/asset-publisher-web/src/main/java/com/liferay/asset/publisher/web/internal/portlet/template/AssetPublisherPortletDisplayTemplateHandler.java
                        variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/adt_asset_entry_70.vm"));
                    }
                } else if ("asset_category".equals(applicationDisplayTemplateFileType)) {
                    if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_6_1) {
                        //Liferay 6.1 does not have Application Display Templates
                    } else if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_6_2) {
                        variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/adt_asset_category_62.vm"));
                    } else if (
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_0) ||
                                    (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_UNKNOWN)
                            ) { //Liferay 7.0
                        //see https://raw.githubusercontent.com/liferay/liferay-portal/7.0.x/modules/apps/web-experience/asset/asset-categories-navigation-web/src/main/java/com/liferay/asset/categories/navigation/web/internal/portlet/template/AssetCategoriesNavigationPortletDisplayTemplateHandler.java
                        variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/adt_asset_category_70.vm"));
                    }
                } else if ("asset_tag".equals(applicationDisplayTemplateFileType)) {
                    if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_6_1) {
                        //Liferay 6.1 does not have Application Display Templates
                    } else if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_6_2) {
                        variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/adt_asset_tag_62.vm"));
                    } else if (
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_0) ||
                                    (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_UNKNOWN)
                            ) { //Liferay 7.0
                        //see https://raw.githubusercontent.com/liferay/liferay-portal/7.0.x/modules/apps/web-experience/asset/asset-tags-navigation-web/src/main/java/com/liferay/asset/tags/navigation/web/internal/portlet/template/AssetTagsNavigationPortletDisplayTemplateHandler.java
                        variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/adt_asset_tag_70.vm"));
                    }
                } else if ("blogs_entry".equals(applicationDisplayTemplateFileType)) {
                    if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_6_1) {
                        //Liferay 6.1 does not have Application Display Templates
                    } else if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_6_2) {
                        variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/adt_blogs_entry_62.vm"));
                    } else if (
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_0) ||
                                    (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_UNKNOWN)
                            ) { //Liferay 7.0
                        //see https://raw.githubusercontent.com/liferay/liferay-portal/7.0.x/modules/apps/collaboration/blogs/blogs-web/src/main/java/com/liferay/blogs/web/internal/template/BlogsPortletDisplayTemplateHandler.java
                        variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/adt_blogs_entry_70.vm"));
                    }
                } else if ("bread_crumb".equals(applicationDisplayTemplateFileType)) {
                    if (
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_0) ||
                                    (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_UNKNOWN)
                            ) { //Liferay 7.0
                        //see https://raw.githubusercontent.com/liferay/liferay-portal/7.0.x/modules/apps/web-experience/site-navigation/site-navigation-breadcrumb-web/src/main/java/com/liferay/site/navigation/breadcrumb/web/internal/portlet/template/SiteNavigationBreadcrumbPortletDisplayTemplateHandler.java
                        variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/adt_bread_crumb_70.vm"));
                    }
                } else if ("document_library".equals(applicationDisplayTemplateFileType)) {
                    if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_6_1) {
                        //Liferay 6.1 does not have Application Display Templates
                    } else if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_6_2) {
                        variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/adt_document_library_62.vm"));
                    } else if (
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_0) ||
                                    (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_UNKNOWN)
                            ) { //Liferay 7.0
                        //see https://raw.githubusercontent.com/liferay/liferay-portal/7.0.x/modules/apps/collaboration/document-library/document-library-web/src/main/java/com/liferay/document/library/web/internal/template/DocumentLibraryPortletDisplayTemplateHandler.java
                        variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/adt_document_library_70.vm"));
                    }
                } else if ("language_entry".equals(applicationDisplayTemplateFileType)) {
                    if (
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_0) ||
                                    (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_UNKNOWN)
                            ) { //Liferay 7.0
                        //see https://raw.githubusercontent.com/liferay/liferay-portal/7.0.x/modules/apps/web-experience/site-navigation/site-navigation-language-web/src/main/java/com/liferay/site/navigation/language/web/internal/portlet/template/SiteNavigationLanguagePortletDisplayTemplateHandler.java
                        variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/adt_language_entry_70.vm"));
                    }
                } else if ("rss_feed".equals(applicationDisplayTemplateFileType)) {
                    if (
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_0) ||
                                    (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_UNKNOWN)
                            ) { //Liferay 7.0
                        //see https://raw.githubusercontent.com/liferay/liferay-portal/7.0.x/modules/apps/web-experience/rss/rss-web/src/main/java/com/liferay/rss/web/internal/portlet/template/RSSPortletDisplayTemplateHandler.java
                        variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/adt_rss_feed_70.vm"));
                    }
                    //TODO
                } else if ("site_map".equals(applicationDisplayTemplateFileType)) {
                    if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_6_1) {
                        //Liferay 6.1 does not have Application Display Templates
                    } else if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_6_2) {
                        variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/adt_site_map_62.vm"));
                    } else if (
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_0) ||
                                    (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_UNKNOWN)
                            ) { //Liferay 7.0
                        //see https://raw.githubusercontent.com/liferay/liferay-portal/7.0.x/modules/apps/web-experience/site-navigation/site-navigation-site-map-web/src/main/java/com/liferay/site/navigation/site/map/web/internal/portlet/template/SiteNavigationSiteMapPortletDisplayTemplateHandler.java
                        variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/adt_site_map_70.vm"));
                    }
                } else if ("site_navigation".equals(applicationDisplayTemplateFileType)) {
                    if (
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_0) ||
                                    (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_UNKNOWN)
                            ) { //Liferay 7.0
                        //see https://raw.githubusercontent.com/liferay/liferay-portal/7.0.x/modules/apps/web-experience/site-navigation/site-navigation-menu-web/src/main/java/com/liferay/site/navigation/menu/web/internal/portlet/template/SiteNavigationMenuPortletDisplayTemplateHandler.java
                        variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/adt_site_navigation_70.vm"));
                    }
                } else if ("wiki_page".equals(applicationDisplayTemplateFileType)) {
                    if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_6_1) {
                        //Liferay 6.1 does not have Application Display Templates
                    } else if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_6_2) {
                        variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/adt_wiki_page_62.vm"));
                    } else if (
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_0) ||
                                    (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_UNKNOWN)
                            ) { //Liferay 7.0
                        //see https://raw.githubusercontent.com/liferay/liferay-portal/7.0.x/modules/apps/collaboration/wiki/wiki-web/src/main/java/com/liferay/wiki/web/internal/portlet/template/WikiPortletDisplayTemplateHandler.java
                        variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/adt_wiki_page_70.vm"));
                    }
                }
            }
        }

        if (isThemeTemplateFile) {
            TemplateVariableReferenceFinder templateVariableReferenceFinder = new LiferayThemeTemplateVariableReferenceFinder();

            if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_6_1) {
                variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/theme_template_61.vm", templateVariableReferenceFinder));
            } else if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_6_2) {
                variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/theme_template_62.vm", templateVariableReferenceFinder));
            } else if (
                        (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_0) ||
                        (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_UNKNOWN)
                    ) { //Liferay 7.0
                variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/theme_template_70.vm", templateVariableReferenceFinder));
            }
        }

        if (isLayoutTemplateFile) {
            if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_6_1) {
                variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/layout_template_61.vm"));
            } else if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_6_2) {
                variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/layout_template_62_70.vm"));
            } else if (
                (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_0) ||
                        (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_UNKNOWN)
                ) { //Liferay 7.0
                variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/layout_template_62_70.vm"));
            }
        }

        if (isStandardVelocityContextFile) {
            if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_6_1) {
                variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/velocity_context_61.vm"));
            } else if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_6_2) {
                variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/velocity_context_62.vm"));
            } else if (
                (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_0) ||
                        (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_UNKNOWN)
                ) { //Liferay 7.0
                variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/velocity_context_70.vm"));
            }

            for (String additionalLanguageResource : templateVariableProcessor.getAdditionalLanguageSpecificResources(portalMajorVersion)) {
                variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, additionalLanguageResource));
            }

        }

        if (isBaseDDMTemplateFile) {
            if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_6_1) {
                //Liferay 6.1 does not have DDM
            } else if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_6_2) {
                //Liferay 6.2, see https://github.com/liferay/liferay-portal/blob/6.2.x/portal-impl/src/com/liferay/portlet/dynamicdatamapping/template/BaseDDMTemplateHandler.java

                variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/base_ddm_template_62.vm"));
            } else if (
                    (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_0) ||
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_UNKNOWN)
                    ) { //Liferay 7.0
                //Liferay 7.0, see https://github.com/liferay/com-liferay-dynamic-data-mapping/blob/master/dynamic-data-mapping-service/src/main/java/com/liferay/dynamic/data/mapping/template/BaseDDMTemplateHandler.java

                variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/base_ddm_template_70.vm"));
            }
        }
        return variables;
    }

    private static <T extends PsiNamedElement, F extends PsiFile> Collection<T> getStructureVariables(TemplateVariableProcessor<F, T> templateVariableProcessor, VirtualFile journalStructureFile, F templateFile, Project project) {
        Collection<T> result = new ArrayList<T>();

        PsiFile file = PsiManager.getInstance(project).findFile(journalStructureFile);

        String className = "com.liferay.portal.kernel.templateparser.TemplateNode";

        if (file instanceof XmlFile) {
            XmlFile xmlFile = (XmlFile) file;

            XmlDocument xmlDocument = xmlFile.getDocument();

            XmlTag rootTag = xmlDocument.getRootTag();

            for (XmlTag xmlTag : rootTag.getSubTags()) {
                if ("dynamic-element".equals(xmlTag.getLocalName())) {
                    XmlAttribute xmlAttribute = xmlTag.getAttribute("name");
                    if (xmlAttribute != null) {
                        String name = xmlAttribute.getValue();
                        boolean repeatable = false;
                        if ( (xmlTag.getAttribute("repeatable") != null) && "true".equalsIgnoreCase(xmlTag.getAttributeValue("repeatable")) ) {
                            repeatable = true;
                        }

                        XmlTag[] subTags = xmlTag.findSubTags("dynamic-element");

                        Collection<T> nestedVariables = null;

                        if ( (subTags != null) && (subTags.length > 0) && (! repeatable))  {
                            nestedVariables = new ArrayList<T>();
                            for (XmlTag subTag : subTags) {
                                String subName = subTag.getAttributeValue("name");
                                if (subName != null) {
                                    nestedVariables.add(templateVariableProcessor.createVariable(subName, templateFile, className, subTag.getNavigationElement(), null));
                                }
                            }
                        }
                        result.add(templateVariableProcessor.createVariable(name, templateFile, className, xmlTag.getNavigationElement(), nestedVariables));
                    }
                }
            }
        } else if (file instanceof JsonFile) {
            JsonFile jsonFile = (JsonFile)file;
            JsonValue root = jsonFile.getTopLevelValue();
            for (PsiElement value : root.getChildren()) {
                if (value instanceof JsonProperty) {
                    JsonProperty property = (JsonProperty)value;
                    if ("fields".equals(property.getName())) {
                        JsonArray jsonArray = (JsonArray)property.getValue();
                        if (jsonArray != null) {
                            for (JsonValue jsonValue : jsonArray.getValueList()) {
                                if (jsonValue instanceof JsonObject) {
                                    JsonObject jsonObject = (JsonObject)jsonValue;

                                    Collection<T> nestedVariables = null;

                                    JsonProperty nameProperty = jsonObject.findProperty("name");
                                    if (nameProperty != null) {
                                        String name = nameProperty.getValue().getText();
                                        if ((name != null) && (name.trim().length() > 0)) {
                                            boolean repeatable = false;
                                            if ( (jsonObject.findProperty("repeatable") != null) && (jsonObject.findProperty("repeatable").getValue() instanceof JsonBooleanLiteral) ) {
                                                repeatable = ((JsonBooleanLiteral) jsonObject.findProperty("repeatable").getValue()).getValue();
                                            }

                                            JsonProperty subproperty = jsonObject.findProperty("nestedFields");
                                            if ( (subproperty != null) && (! repeatable) ) {
                                                nestedVariables = new ArrayList<T>();

                                                JsonArray subjsonArray = (JsonArray) subproperty.getValue();
                                                if (subjsonArray != null) {
                                                    for (JsonValue subjsonValue : subjsonArray.getValueList()) {
                                                        if (subjsonValue instanceof JsonObject) {
                                                            JsonObject subjsonObject = (JsonObject) subjsonValue;
                                                            JsonProperty subnameProperty = subjsonObject.findProperty("name");
                                                            if (subnameProperty != null) {
                                                                String subname = subnameProperty.getValue().getText();
                                                                if ((subname != null) && (subname.trim().length() > 0)) {
                                                                    subname = StringUtil.unquoteString(subname);

                                                                    nestedVariables.add(templateVariableProcessor.createVariable(subname, templateFile, className, subnameProperty.getValue(), null));
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }

                                            name = StringUtil.unquoteString(name);

                                            result.add(templateVariableProcessor.createVariable(name, templateFile, className, nameProperty.getValue(),nestedVariables));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return result;
    }

    private static <F extends PsiFile, T extends PsiNamedElement> Collection<T> getImplicitVariables(TemplateVariableProcessor<F, T> templateVariableProcessor, F sourceFile, String resource) {
        return getImplicitVariables(templateVariableProcessor, sourceFile, resource, null);
    }

    private static <F extends PsiFile, T extends PsiNamedElement> Collection<T> getImplicitVariables(TemplateVariableProcessor<F, T> templateVariableProcessor, F sourceFile, String resource, TemplateVariableReferenceFinder templateVariableReferenceFinder) {
        final List<T> variables = new ArrayList<T>();

        URL url = TemplateVariableProcessorUtil.class.getResource(resource);
        if (url != null) {
            VirtualFile virtualFile = VfsUtil.findFileByURL(url);
            if (virtualFile != null) {
                PsiFile psiFile = PsiManager.getInstance(sourceFile.getProject()).findFile(virtualFile);
                if (psiFile != null) {
                    if (psiFile instanceof VtlFile) {
                        VtlFile vtlFile = (VtlFile) psiFile;
                        for (PsiElement element : vtlFile.getChildren()) {
                            if (element instanceof PsiComment) {
                                String commentText = element.getText();
                                Matcher matcher = IMPLICIT_VAR_DECL_PATTERN.matcher(commentText);
                                if (matcher.matches()) {
                                    String name = matcher.group(2);
                                    String type = matcher.group(3);

                                    PsiElement navigationalElement = null;
                                    if (templateVariableReferenceFinder != null) {
                                        navigationalElement = templateVariableReferenceFinder.getNavigationalElement(name, sourceFile);

                                    }
                                    variables.add(templateVariableProcessor.createVariable(name, sourceFile, type, navigationalElement, null));
                                }
                            }
                        }
                    }
                }
            }
        }
        return variables;
    }

}
