package de.dm.intellij.liferay.client;

import java.util.HashMap;
import java.util.Map;

public class Constants {

    public static final String ENDPOINT_JSONWS = "/api/jsonws";
    public static final String PATH_INVOKE = "invoke";

    public static final String CLASS_NAME_JOURNAL_ARTICLE_7_0 = "com.liferay.journal.model.JournalArticle";
    public static final String CLASS_NAME_DDM_STRUCTURE_7_0 = "com.liferay.dynamic.data.mapping.model.DDMStructure";
    public static final String CLASS_NAME_PORTLET_DISPLAY_TEMPLATE_7_0 = "com.liferay.portlet.display.template.PortletDisplayTemplate";

    public static final String CMD_GET_USER_SITES_GROUP = "/group/get-user-sites-groups";
    public static final String CMD_GET_GROUP = "/group/get-group";

    public static final Map<String, String> APPLICATION_DISPLAY_TEMPLATE_TYPES_7_0 = new HashMap<String, String>();
    static {
        APPLICATION_DISPLAY_TEMPLATE_TYPES_7_0.put("asset_category", "com.liferay.asset.kernel.model.AssetCategory");
        APPLICATION_DISPLAY_TEMPLATE_TYPES_7_0.put("asset_entry", "com.liferay.asset.kernel.model.AssetEntry");
        APPLICATION_DISPLAY_TEMPLATE_TYPES_7_0.put("asset_tag", "com.liferay.asset.kernel.model.AssetTag");
        APPLICATION_DISPLAY_TEMPLATE_TYPES_7_0.put("blogs_entry", "com.liferay.blogs.kernel.model.BlogsEntry");
        APPLICATION_DISPLAY_TEMPLATE_TYPES_7_0.put("bread_crumb", "com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry");
        APPLICATION_DISPLAY_TEMPLATE_TYPES_7_0.put("document_library", "com.liferay.portal.kernel.repository.model.FileEntry");
        APPLICATION_DISPLAY_TEMPLATE_TYPES_7_0.put("language_entry", "com.liferay.portal.kernel.servlet.taglib.ui.LanguageEntry");
        APPLICATION_DISPLAY_TEMPLATE_TYPES_7_0.put("rss_feed", "com.liferay.rss.web.util.RSSFeed");
        APPLICATION_DISPLAY_TEMPLATE_TYPES_7_0.put("site_map", "com.liferay.portal.kernel.model.LayoutSet");
        APPLICATION_DISPLAY_TEMPLATE_TYPES_7_0.put("site_navigation", "com.liferay.portal.kernel.theme.NavItem");
        APPLICATION_DISPLAY_TEMPLATE_TYPES_7_0.put("wiki_page", "com.liferay.wiki.model.WikiPage");
    }

}
