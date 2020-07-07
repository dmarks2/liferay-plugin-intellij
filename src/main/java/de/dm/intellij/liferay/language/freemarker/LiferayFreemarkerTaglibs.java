package de.dm.intellij.liferay.language.freemarker;

import com.intellij.freemarker.psi.directives.FtlMacro;
import com.intellij.openapi.util.text.StringUtil;
import de.dm.intellij.liferay.util.LiferayTaglibs;

import java.util.HashMap;
import java.util.Map;

public class LiferayFreemarkerTaglibs {

    public static final Map<String, String> FTL_MACRO_PREFIXES = new HashMap<String, String>();

    public static final Map<String, String> FTL_TAGLIB_MAPPINGS = new HashMap<>();

    static {
        FTL_MACRO_PREFIXES.put("liferay_aui", LiferayTaglibs.TAGLIB_URI_LIFERAY_AUI);
        FTL_MACRO_PREFIXES.put("liferay_ui", LiferayTaglibs.TAGLIB_URI_LIFERAY_UI);
        FTL_MACRO_PREFIXES.put("liferay_frontend", LiferayTaglibs.TAGLIB_URI_LIFERAY_FRONTEND);
    }

    static {
        // https://github.com/liferay/liferay-portal/blob/master/modules/apps/frontend-taglib/frontend-taglib-util-freemarker-contributor/src/main/resources/META-INF/taglib-mappings.properties
        FTL_TAGLIB_MAPPINGS.put("liferay-aui.tld", "liferay_aui");
        FTL_TAGLIB_MAPPINGS.put("liferay-portlet-ext.tld", "liferay_portlet");
        FTL_TAGLIB_MAPPINGS.put("liferay-portlet.tld", "portlet");
        FTL_TAGLIB_MAPPINGS.put("liferay-security.tld", "liferay_security");
        FTL_TAGLIB_MAPPINGS.put("liferay-theme.tld", "liferay_theme");
        FTL_TAGLIB_MAPPINGS.put("liferay-ui.tld", "liferay_ui");
        FTL_TAGLIB_MAPPINGS.put("liferay-util.tld", "liferay_util");

        // https://github.com/liferay/liferay-portal/blob/master/modules/apps/adaptive-media/adaptive-media-image-taglib/src/main/resources/META-INF/taglib-mappings.properties
        FTL_TAGLIB_MAPPINGS.put("liferay-adaptive-media.tld", "adaptive_media_image");
        // https://github.com/liferay/liferay-portal/blob/master/modules/apps/asset/asset-taglib/src/main/resources/META-INF/taglib-mappings.properties
        FTL_TAGLIB_MAPPINGS.put("liferay-asset.tld", "liferay_asset");
        // https://github.com/liferay/liferay-portal/blob/master/modules/apps/frontend-taglib/frontend-taglib-chart/src/main/resources/META-INF/taglib-mappings.properties
        FTL_TAGLIB_MAPPINGS.put("liferay-chart.tld", "chart");
        // https://github.com/liferay/liferay-portal/blob/master/modules/apps/frontend-taglib/frontend-taglib-clay/src/main/resources/META-INF/taglib-mappings.properties
        FTL_TAGLIB_MAPPINGS.put("liferay-clay.tld", "clay");
        // https://github.com/liferay/liferay-portal/blob/master/modules/apps/comment/comment-taglib/src/main/resources/META-INF/taglib-mappings.properties
        FTL_TAGLIB_MAPPINGS.put("liferay-comment.tld", "liferay_comment");
        // https://github.com/liferay/liferay-portal/blob/master/modules/apps/document-library/document-library-taglib/src/main/resources/META-INF/taglib-mappings.properties
        FTL_TAGLIB_MAPPINGS.put("liferay-document-library.tld", "liferay_document_library");
        // https://github.com/liferay/liferay-portal/blob/master/modules/apps/frontend-editor/frontend-editor-taglib/src/main/resources/META-INF/taglib-mappings.properties
        FTL_TAGLIB_MAPPINGS.put("liferay-editor.tld", "liferay_editor");
        // https://github.com/liferay/liferay-portal/blob/master/modules/apps/expando/expando-taglib/src/main/resources/META-INF/taglib-mappings.properties
        FTL_TAGLIB_MAPPINGS.put("liferay-expando.tld", "liferay_expando");
        //https://github.com/liferay/liferay-portal/blob/master/modules/apps/flags/flags-taglib/src/main/resources/META-INF/taglib-mappings.properties
        FTL_TAGLIB_MAPPINGS.put("liferay-flags.tld", "liferay_flags");
        // https://github.com/liferay/liferay-portal/blob/master/modules/apps/fragment/fragment-taglib/src/main/resources/META-INF/taglib-mappings.properties
        FTL_TAGLIB_MAPPINGS.put("liferay-fragment.tld", "liferay-fragment");
        // https://github.com/liferay/liferay-portal/blob/master/modules/apps/frontend-taglib/frontend-taglib/src/main/resources/META-INF/taglib-mappings.properties
        FTL_TAGLIB_MAPPINGS.put("liferay-frontend.tld", "liferay_frontend");
        // https://github.com/liferay/liferay-portal/blob/master/modules/apps/item-selector/item-selector-taglib/src/main/resources/META-INF/taglib-mappings.properties
        FTL_TAGLIB_MAPPINGS.put("liferay-item-selector.tld", "liferay_item_selector");
        // https://github.com/liferay/liferay-portal/blob/master/modules/apps/journal/journal-taglib/src/main/resources/META-INF/taglib-mappings.properties
        FTL_TAGLIB_MAPPINGS.put("liferay-journal.tld", "liferay_journal");
        // https://github.com/liferay/liferay-portal/blob/master/modules/apps/layout/layout-taglib/src/main/resources/META-INF/taglib-mappings.properties
        FTL_TAGLIB_MAPPINGS.put("liferay-layout.tld", "liferay_layout");
        // https://github.com/liferay/liferay-portal/blob/master/modules/apps/map/map-taglib/src/main/resources/META-INF/taglib-mappings.properties
        FTL_TAGLIB_MAPPINGS.put("liferay-map.tld", "liferay_map");
        // https://github.com/liferay/liferay-portal/blob/master/modules/apps/product-navigation/product-navigation-taglib/src/main/resources/META-INF/taglib-mappings.properties
        FTL_TAGLIB_MAPPINGS.put("liferay-product-navigation.tld", "liferay_product_navigation");
        // https://github.com/liferay/liferay-portal/blob/master/modules/apps/reading-time/reading-time-taglib/src/main/resources/META-INF/taglib-mappings.properties
        FTL_TAGLIB_MAPPINGS.put("liferay-reading-time.tld", "liferay_reading_time");
        // https://github.com/liferay/liferay-portal/blob/master/modules/apps/rss/rss-taglib/src/main/resources/META-INF/taglib-mappings.properties
        FTL_TAGLIB_MAPPINGS.put("liferay-rss.tld", "liferay_rss");
        // https://github.com/liferay/liferay-portal/blob/master/modules/apps/sharing/sharing-taglib/src/main/resources/META-INF/taglib-mappings.properties
        FTL_TAGLIB_MAPPINGS.put("liferay-sharing.tld", "liferay_sharing");
        // https://github.com/liferay/liferay-portal/blob/master/modules/apps/site/site-taglib/src/main/resources/META-INF/taglib-mappings.properties
        FTL_TAGLIB_MAPPINGS.put("liferay-site.tld", "liferay_site");
        // https://github.com/liferay/liferay-portal/blob/master/modules/apps/site-navigation/site-navigation-taglib/src/main/resources/META-INF/taglib-mappings.properties
        FTL_TAGLIB_MAPPINGS.put("liferay-site-navigation.tld", "liferay_site_navigation");
        // https://github.com/liferay/liferay-portal/blob/master/modules/apps/social/social-activities-taglib/src/main/resources/META-INF/taglib-mappings.properties
        FTL_TAGLIB_MAPPINGS.put("liferay-social-activities.tld", "liferay_social_activities");
        // https://github.com/liferay/liferay-portal/blob/master/modules/apps/social/social-bookmarks-taglib/src/main/resources/META-INF/taglib-mappings.properties
        FTL_TAGLIB_MAPPINGS.put("liferay-social-bookmarks.tld", "liferay_social_bookmarks");
        // https://github.com/liferay/liferay-portal/blob/master/modules/apps/trash/trash-taglib/src/main/resources/META-INF/taglib-mappings.properties
        FTL_TAGLIB_MAPPINGS.put("liferay-trash.tld", "liferay_trash");

    }

    public static String getNamespace(FtlMacro ftlMacro) {
        String directiveName = ftlMacro.getDirectiveName();

        String prefix = null;

        if (directiveName.contains(".")) {
            String[] split = directiveName.split("\\.");
            if (split.length == 2) {
                prefix = split[0];
            }
        } else if (directiveName.contains("[")) {
            String[] split = directiveName.split("\\[");
            if (split.length == 2) {
                prefix = split[0];
            }
        }
        if (prefix != null) {
            if (LiferayFreemarkerTaglibs.FTL_MACRO_PREFIXES.containsKey(prefix)) {
                return LiferayFreemarkerTaglibs.FTL_MACRO_PREFIXES.get(prefix);
            }
        }

        return "";
    }

    public static String getLocalName(FtlMacro ftlMacro) {
        String directiveName = ftlMacro.getDirectiveName();
        if (directiveName.contains(".")) {
            String[] split = directiveName.split("\\.");
            if (split.length == 2) {
                return split[1];
            }
        } else if (directiveName.contains("[")) {
            String[] split = directiveName.split("\\[");
            if (split.length == 2) {
                String localName = split[1];
                if (localName.endsWith("]")) {
                    localName = localName.substring(0, localName.length() -1);
                }

                localName = StringUtil.unquoteString(localName);

                return localName;
            }
        }

        return "";
    }
}
