package de.dm.intellij.liferay.client;

import java.util.HashMap;
import java.util.Map;

public class Constants {

    public static final String ENDPOINT_JSONWS = "/api/jsonws";
    public static final String PATH_INVOKE = "/invoke";

    public static final String CLASS_NAME_JOURNAL_ARTICLE_7_0 = "com.liferay.journal.model.JournalArticle";
    public static final String CLASS_NAME_DDM_STRUCTURE_7_0 = "com.liferay.dynamic.data.mapping.model.DDMStructure";
    public static final String CLASS_NAME_PORTLET_DISPLAY_TEMPLATE_7_0 = "com.liferay.portlet.display.template.PortletDisplayTemplate";

    public static final String CMD_GET_USER_SITES_GROUP = "/group/get-user-sites-groups";
    public static final String CMD_GET_GROUP = "/group/get-group";
    public static final String CMD_GET_COMPANY_GROUP = "/group/get-company-group";

    public static final Map<String, String[]> APPLICATION_DISPLAY_TEMPLATE_TYPES_7_0 = new HashMap<>();
    public static final Map<String, String[]> APPLICATION_DISPLAY_TEMPLATE_TYPES_7_3 = new HashMap<>();
    static {
        APPLICATION_DISPLAY_TEMPLATE_TYPES_7_0.put("asset_category", new String[] {
                "com.liferay.asset.kernel.model.AssetCategory"
        });
        APPLICATION_DISPLAY_TEMPLATE_TYPES_7_0.put("asset_entry", new String[] {
                "com.liferay.asset.kernel.model.AssetEntry"
        });
        APPLICATION_DISPLAY_TEMPLATE_TYPES_7_0.put("asset_tag", new String[] {
                "com.liferay.asset.kernel.model.AssetTag"
        });
        APPLICATION_DISPLAY_TEMPLATE_TYPES_7_0.put("blogs_entry", new String[] {
                "com.liferay.blogs.kernel.model.BlogsEntry",
                "com.liferay.blogs.model.BlogsEntry"
        });
        APPLICATION_DISPLAY_TEMPLATE_TYPES_7_0.put("bread_crumb", new String[] {
                "com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry"
        });
        APPLICATION_DISPLAY_TEMPLATE_TYPES_7_0.put("document_library", new String[] {
                "com.liferay.portal.kernel.repository.model.FileEntry"
        });
        APPLICATION_DISPLAY_TEMPLATE_TYPES_7_0.put("language_entry", new String[] {
                "com.liferay.portal.kernel.servlet.taglib.ui.LanguageEntry"
        });
        APPLICATION_DISPLAY_TEMPLATE_TYPES_7_0.put("media_gallery", new String[] {
                "com.liferay.portal.kernel.repository.model.FileEntry"
        });
        APPLICATION_DISPLAY_TEMPLATE_TYPES_7_0.put("rss_feed", new String[] {
                "com.liferay.rss.web.util.RSSFeed",
                "com.liferay.rss.web.internal.util.RSSFeed"}
        );
        APPLICATION_DISPLAY_TEMPLATE_TYPES_7_0.put("site_map", new String[] {
                "com.liferay.portal.kernel.model.LayoutSet"
        });
        APPLICATION_DISPLAY_TEMPLATE_TYPES_7_0.put("site_navigation", new String[] {
                "com.liferay.site.navigation.theme.SiteNavigationMenuNavItem",
                "com.liferay.portal.kernel.theme.NavItem"
        });
        APPLICATION_DISPLAY_TEMPLATE_TYPES_7_0.put("wiki_page", new String[] {
                "com.liferay.wiki.model.WikiPage"
        });

        APPLICATION_DISPLAY_TEMPLATE_TYPES_7_3.put("category_facet", new String[] {
                "com.liferay.portal.search.web.internal.category.facet.portlet.CategoryFacetPortlet",
                "com.liferay.portal.search.web.internal.facet.display.context.AssetCategoriesSearchFacetDisplayContext"
        });
        APPLICATION_DISPLAY_TEMPLATE_TYPES_7_3.put("custom_facet", new String[] {
                "com.liferay.portal.search.web.internal.custom.facet.portlet.CustomFacetPortlet",
                "com.liferay.portal.search.web.internal.custom.facet.display.context.CustomFacetTermDisplayContext"
        });
        APPLICATION_DISPLAY_TEMPLATE_TYPES_7_3.put("custom_filter", new String[] {
                "com.liferay.portal.search.web.internal.custom.filter.display.context.CustomFilterDisplayContext"
        });
        APPLICATION_DISPLAY_TEMPLATE_TYPES_7_3.put("date_facet", new String[] {
                "com.liferay.portal.search.web.internal.date.facet.portlet.DateFacetPortlet"
        });
        APPLICATION_DISPLAY_TEMPLATE_TYPES_7_3.put("folder_facet", new String[] {
                "com.liferay.portal.search.web.internal.folder.facet.portlet.FolderFacetPortlet",
                "com.liferay.portal.search.web.internal.facet.display.context.FolderSearchFacetTermDisplayContext"
        });
        APPLICATION_DISPLAY_TEMPLATE_TYPES_7_3.put("modified_facet", new String[] {
                "com.liferay.portal.search.web.internal.modified.facet.portlet.ModifiedFacetPortlet",
                "com.liferay.portal.search.web.internal.modified.facet.display.context.ModifiedFacetTermDisplayContext"
        });
        APPLICATION_DISPLAY_TEMPLATE_TYPES_7_3.put("search_bar", new String[] {
                "com.liferay.portal.search.web.internal.search.bar.portlet.SearchBarPortletDisplayContext",
                "com.liferay.portal.search.web.internal.search.bar.portlet.display.context.SearchBarPortletDisplayContext", //since 7.4.3.13
                "com.liferay.portal.search.web.internal.search.bar.portlet.SearchBarPortlet" //since 7.4.3.67
        });
        APPLICATION_DISPLAY_TEMPLATE_TYPES_7_3.put("search_results", new String[] {
                "com.liferay.portal.search.web.internal.result.display.context.SearchResultSummaryDisplayContext"
        });
        APPLICATION_DISPLAY_TEMPLATE_TYPES_7_3.put("site_facet", new String[] {
                "com.liferay.portal.search.web.internal.site.facet.portlet.SiteFacetPortlet",
                "com.liferay.portal.search.web.internal.facet.display.context.ScopeSearchFacetTermDisplayContext"
        });
        APPLICATION_DISPLAY_TEMPLATE_TYPES_7_3.put("sort", new String[] {
                "com.liferay.portal.search.web.internal.sort.display.context.SortDisplayContext"
        });
        APPLICATION_DISPLAY_TEMPLATE_TYPES_7_3.put("tag_facet", new String[] {
                "com.liferay.portal.search.web.internal.tag.facet.portlet.TagFacetPortlet",
                "com.liferay.portal.search.web.internal.facet.display.context.AssetTagsSearchFacetTermDisplayContext"
        });
        APPLICATION_DISPLAY_TEMPLATE_TYPES_7_3.put("type_facet", new String[] {
                "com.liferay.portal.search.web.internal.type.facet.portlet.TypeFacetPortlet",
                "com.liferay.portal.search.web.internal.facet.display.context.AssetEntriesSearchFacetTermDisplayContext"
        });
        APPLICATION_DISPLAY_TEMPLATE_TYPES_7_3.put("user_facet", new String[] {
                "com.liferay.portal.search.web.internal.user.facet.portlet.UserFacetPortlet",
                "com.liferay.portal.search.web.internal.facet.display.context.UserSearchFacetTermDisplayContext"
        });
        APPLICATION_DISPLAY_TEMPLATE_TYPES_7_3.put("similar_results", new String[] {
                "com.liferay.portal.search.similar.results.web.internal.display.context.SimilarResultsDisplayContext"
        });
    }

}
