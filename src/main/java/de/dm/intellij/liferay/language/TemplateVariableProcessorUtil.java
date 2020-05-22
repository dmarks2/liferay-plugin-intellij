package de.dm.intellij.liferay.language;

import com.intellij.json.psi.JsonFile;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.xml.XmlFile;
import com.intellij.velocity.psi.files.VtlFile;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
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

        boolean isRequestBasedContextFile = isThemeTemplateFile || isLayoutTemplateFile || isApplicationDisplayTemplateFile;

        if (isJournalTemplateFile) {
            variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/journal_template.vm"));

            if (
                    (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_0) ||
                    (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_1) ||
                    (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_2) ||
                    (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_3) ||
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
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_1) ||
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_2) ||
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_3) ||
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
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_1) ||
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_2) ||
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_3) ||
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
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_1) ||
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_2) ||
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_3) ||
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
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_1) ||
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_2) ||
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_3) ||
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_UNKNOWN)
                            ) { //Liferay 7.0
                        //see https://raw.githubusercontent.com/liferay/liferay-portal/7.0.x/modules/apps/collaboration/blogs/blogs-web/src/main/java/com/liferay/blogs/web/internal/template/BlogsPortletDisplayTemplateHandler.java
                        variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/adt_blogs_entry_70.vm"));
                    }
                } else if ("bread_crumb".equals(applicationDisplayTemplateFileType)) {
                    if (
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_0) ||
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_1) ||
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_2) ||
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_3) ||
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
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_1) ||
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_2) ||
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_3) ||
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_UNKNOWN)
                            ) { //Liferay 7.0
                        //see https://raw.githubusercontent.com/liferay/liferay-portal/7.0.x/modules/apps/collaboration/document-library/document-library-web/src/main/java/com/liferay/document/library/web/internal/template/DocumentLibraryPortletDisplayTemplateHandler.java
                        variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/adt_document_library_70.vm"));
                    }
                } else if ("language_entry".equals(applicationDisplayTemplateFileType)) {
                    if (
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_0) ||
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_1) ||
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_2) ||
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_3) ||
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_UNKNOWN)
                            ) { //Liferay 7.0
                        //see https://raw.githubusercontent.com/liferay/liferay-portal/7.0.x/modules/apps/web-experience/site-navigation/site-navigation-language-web/src/main/java/com/liferay/site/navigation/language/web/internal/portlet/template/SiteNavigationLanguagePortletDisplayTemplateHandler.java
                        variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/adt_language_entry_70.vm"));
                    }
                } else if ("rss_feed".equals(applicationDisplayTemplateFileType)) {
                    if (
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_0) ||
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_1) ||
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_2) ||
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_3) ||
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_UNKNOWN)
                            ) { //Liferay 7.0
                        //see https://raw.githubusercontent.com/liferay/liferay-portal/7.0.x/modules/apps/web-experience/rss/rss-web/src/main/java/com/liferay/rss/web/internal/portlet/template/RSSPortletDisplayTemplateHandler.java
                        variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/adt_rss_feed_70.vm"));
                    }
                } else if ("site_map".equals(applicationDisplayTemplateFileType)) {
                    if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_6_1) {
                        //Liferay 6.1 does not have Application Display Templates
                    } else if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_6_2) {
                        variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/adt_site_map_62.vm"));
                    } else if (
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_0) ||
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_1) ||
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_2) ||
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_3) ||
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_UNKNOWN)
                            ) { //Liferay 7.0
                        //see https://raw.githubusercontent.com/liferay/liferay-portal/7.0.x/modules/apps/web-experience/site-navigation/site-navigation-site-map-web/src/main/java/com/liferay/site/navigation/site/map/web/internal/portlet/template/SiteNavigationSiteMapPortletDisplayTemplateHandler.java
                        variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/adt_site_map_70.vm"));
                    }
                } else if ("site_navigation".equals(applicationDisplayTemplateFileType)) {
                    if (
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_0) ||
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_1) ||
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_2) ||
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_3) ||
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
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_1) ||
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_2) ||
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_3) ||
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_UNKNOWN)
                            ) { //Liferay 7.0
                        //see https://raw.githubusercontent.com/liferay/liferay-portal/7.0.x/modules/apps/collaboration/wiki/wiki-web/src/main/java/com/liferay/wiki/web/internal/portlet/template/WikiPortletDisplayTemplateHandler.java
                        variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/adt_wiki_page_70.vm"));
                    }
                } else if ("media_gallery".equals(applicationDisplayTemplateFileType)) {
                     if (
                            (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_0) ||
                                    (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_1) ||
                                    (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_2) ||
                                    (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_3) ||
                                    (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_UNKNOWN)
                    ) {
                        variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/adt_media_gallery_70.vm"));
                    }
                } else if ("category_facet".equalsIgnoreCase(applicationDisplayTemplateFileType)) {
                    if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_3) {
                        variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/adt_category_facet_73.vm"));
                    }
                } else if ("custom_facet".equalsIgnoreCase(applicationDisplayTemplateFileType)) {
                    if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_3) {
                        variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/adt_custom_facet_73.vm"));
                    }
                } else if ("custom_filter".equalsIgnoreCase(applicationDisplayTemplateFileType)) {
                    if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_3) {
                        variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/adt_custom_filter_73.vm"));
                    }
                } else if ("folder_facet".equalsIgnoreCase(applicationDisplayTemplateFileType)) {
                    if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_3) {
                        variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/adt_folder_facet_73.vm"));
                    }
                } else if ("modified_facet".equalsIgnoreCase(applicationDisplayTemplateFileType)) {
                    if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_3) {
                        variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/adt_modified_facet_73.vm"));
                    }
                } else if ("search_bar".equalsIgnoreCase(applicationDisplayTemplateFileType)) {
                    if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_3) {
                        variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/adt_search_bar_73.vm"));
                    }
                } else if ("search_results".equalsIgnoreCase(applicationDisplayTemplateFileType)) {
                    if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_3) {
                        variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/adt_search_results_73.vm"));
                    }
                } else if ("site_facet".equalsIgnoreCase(applicationDisplayTemplateFileType)) {
                    if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_3) {
                        variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/adt_site_facet_73.vm"));
                    }
                } else if ("sort".equalsIgnoreCase(applicationDisplayTemplateFileType)) {
                    if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_3) {
                        variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/adt_sort_73.vm"));
                    }
                } else if ("tag_facet".equalsIgnoreCase(applicationDisplayTemplateFileType)) {
                    if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_3) {
                        variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/adt_tag_facet_73.vm"));
                    }
                } else if ("type_facet".equalsIgnoreCase(applicationDisplayTemplateFileType)) {
                    if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_3) {
                        variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/adt_type_facet_73.vm"));
                    }
                } else if ("user_facet".equalsIgnoreCase(applicationDisplayTemplateFileType)) {
                    if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_3) {
                        variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/adt_user_facet_73.vm"));
                    }
                }
            }
        }

        if (isThemeTemplateFile) {
            if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_6_1) {
                variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/theme_template_61.vm"));
            } else if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_6_2) {
                variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/theme_template_62.vm"));
            } else if (
                        (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_0) ||
                        (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_1) ||
                        (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_2) ||
                        (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_3) ||
                        (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_UNKNOWN)
                    ) { //Liferay 7.0
                variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/theme_template_70.vm"));
            }
        }

        if (isLayoutTemplateFile) {
            if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_6_1) {
                variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/layout_template_61.vm"));
            } else if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_6_2) {
                variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/layout_template_62_70.vm"));
            } else if (
                (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_0) ||
                (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_1) ||
                (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_2) ||
                (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_3) ||
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
                (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_1) ||
                (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_2) ||
                (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_3) ||
                (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_UNKNOWN)
                ) { //Liferay 7.0
                variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/velocity_context_70.vm"));
            }

            for (String additionalLanguageResource : templateVariableProcessor.getAdditionalLanguageSpecificResources(portalMajorVersion)) {
                variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, additionalLanguageResource));
            }
        }

        if (isRequestBasedContextFile) {
            if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_6_1) {
                variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/velocity_request_61.vm"));
            } else if (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_6_2) {
                variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/velocity_request_62.vm"));
            } else if (
                (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_0) ||
                (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_1) ||
                (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_2) ||
                (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_3) ||
                (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_UNKNOWN)
            ) { //Liferay 7.0
                variables.addAll(getImplicitVariables(templateVariableProcessor, templateFile, "/com/liferay/vtl/velocity_request_70.vm"));
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
                    (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_1) ||
                    (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_2) ||
                    (portalMajorVersion == LiferayVersions.LIFERAY_VERSION_7_3) ||
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

        if (file instanceof XmlFile) {
            TemplateVariableParser<XmlFile> templateVariableParser = new TemplateVariableXMLParser();
            List<TemplateVariable> templateVariables = templateVariableParser.getTemplateVariables((XmlFile) file, templateFile);

            for (TemplateVariable templateVariable : templateVariables) {
                result.add(templateVariableProcessor.createStructureVariable(templateVariable));
            }
        } else if (file instanceof JsonFile) {
            TemplateVariableParser<JsonFile> templateVariableParser = new TemplateVariableJsonParser();
            List<TemplateVariable> templateVariables = templateVariableParser.getTemplateVariables((JsonFile) file, templateFile);

            for (TemplateVariable templateVariable : templateVariables) {
                result.add(templateVariableProcessor.createStructureVariable(templateVariable));
            }
        }

        return result;
    }

    private static <F extends PsiFile, T extends PsiNamedElement> Collection<T> getImplicitVariables(TemplateVariableProcessor<F, T> templateVariableProcessor, F sourceFile, String resource) {
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

                                    variables.add(templateVariableProcessor.createVariable(name, sourceFile, type));
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
