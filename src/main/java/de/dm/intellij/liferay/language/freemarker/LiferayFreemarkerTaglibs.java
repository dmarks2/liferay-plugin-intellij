package de.dm.intellij.liferay.language.freemarker;

import com.intellij.freemarker.psi.directives.FtlMacro;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import de.dm.intellij.liferay.util.LiferayTaglibs;

import java.util.HashMap;
import java.util.Map;

public class LiferayFreemarkerTaglibs {

    public static final Map<String, String> FTL_MACRO_PREFIXES = new HashMap<>();

    public static final Map<String, String> FTL_TAGLIB_MAPPINGS = new HashMap<>();

    static {
        FTL_MACRO_PREFIXES.put("adaptive_media_image", LiferayTaglibs.TAGLIB_URI_LIFERAY_ADAPTIVE_MEDIA_IMAGE);
        FTL_MACRO_PREFIXES.put("chart", LiferayTaglibs.TAGLIB_URI_LIFERAY_CHART);
        FTL_MACRO_PREFIXES.put("clay", LiferayTaglibs.TAGLIB_URI_LIFERAY_CLAY);
        FTL_MACRO_PREFIXES.put("liferay_asset", LiferayTaglibs.TAGLIB_URI_LIFERAY_ASSET);
        FTL_MACRO_PREFIXES.put("liferay_aui", LiferayTaglibs.TAGLIB_URI_LIFERAY_AUI);
        FTL_MACRO_PREFIXES.put("liferay_captcha", LiferayTaglibs.TAGLIB_URI_LIFERAY_CAPTCHA);
        FTL_MACRO_PREFIXES.put("liferay_comment", LiferayTaglibs.TAGLIB_URI_LIFERAY_COMMENT);
        FTL_MACRO_PREFIXES.put("liferay_commerce_product", LiferayTaglibs.TAGLIB_URI_LIFERAY_COMMERCE_PRODUCT);
        FTL_MACRO_PREFIXES.put("liferay_document_library", LiferayTaglibs.TAGLIB_URI_LIFERAY_DOCUMENT_LIBRARY);
        FTL_MACRO_PREFIXES.put("liferay_editor", LiferayTaglibs.TAGLIB_URI_LIFERAY_EDITOR);
        FTL_MACRO_PREFIXES.put("liferay_expando", LiferayTaglibs.TAGLIB_URI_LIFERAY_EXPANDO);
        FTL_MACRO_PREFIXES.put("liferay_flags", LiferayTaglibs.TAGLIB_URI_LIFERAY_FLAGS);
        FTL_MACRO_PREFIXES.put("liferay-fragment", LiferayTaglibs.TAGLIB_URI_LIFERAY_FRAGMENT);
        FTL_MACRO_PREFIXES.put("liferay_friendly_url", LiferayTaglibs.TAGLIB_URI_LIFERAY_FRIENDLY_URL);
        FTL_MACRO_PREFIXES.put("liferay_frontend", LiferayTaglibs.TAGLIB_URI_LIFERAY_FRONTEND);
        FTL_MACRO_PREFIXES.put("frontend_data_set", LiferayTaglibs.TAGLIB_URI_LIFERAY_FRONTEND_DATA_SET);
        FTL_MACRO_PREFIXES.put("liferay_item_selector", LiferayTaglibs.TAGLIB_URI_LIFERAY_ITEM_SELECTOR);
        FTL_MACRO_PREFIXES.put("liferay_journal", LiferayTaglibs.TAGLIB_URI_LIFERAY_JOURNAL);
        FTL_MACRO_PREFIXES.put("liferay_layout", LiferayTaglibs.TAGLIB_URI_LIFERAY_LAYOUT);
        FTL_MACRO_PREFIXES.put("liferay_map", LiferayTaglibs.TAGLIB_URI_LIFERAY_MAP);
        FTL_MACRO_PREFIXES.put("liferay_portlet", LiferayTaglibs.TAGLIB_URI_LIFERAY_PORTLET);
        FTL_MACRO_PREFIXES.put("liferay_product_navigation", LiferayTaglibs.TAGLIB_URI_LIFERAY_PRODUCT_NAVIGATION);
        FTL_MACRO_PREFIXES.put("liferay_reading_time", LiferayTaglibs.TAGLIB_URI_LIFERAY_READING_TIME);
        FTL_MACRO_PREFIXES.put("liferay_rss", LiferayTaglibs.TAGLIB_URI_LIFERAY_RSS);
        FTL_MACRO_PREFIXES.put("liferay_security", LiferayTaglibs.TAGLIB_URI_LIFERAY_SECURITY);
        FTL_MACRO_PREFIXES.put("liferay_sharing", LiferayTaglibs.TAGLIB_URI_LIFERAY_SHARING);
        FTL_MACRO_PREFIXES.put("liferay_site", LiferayTaglibs.TAGLIB_URI_LIFERAY_SITE);
        FTL_MACRO_PREFIXES.put("liferay_site_navigation", LiferayTaglibs.TAGLIB_URI_LIFERAY_SITE_NAVIGATION);
        FTL_MACRO_PREFIXES.put("liferay_social_activities", LiferayTaglibs.TAGLIB_URI_LIFERAY_SOCIAL_ACTIVITIES);
        FTL_MACRO_PREFIXES.put("liferay_social_bookmarks", LiferayTaglibs.TAGLIB_URI_LIFERAY_SOCIAL_BOOKMARKS);
        FTL_MACRO_PREFIXES.put("liferay_theme", LiferayTaglibs.TAGLIB_URI_LIFERAY_THEME);
        FTL_MACRO_PREFIXES.put("liferay_trash", LiferayTaglibs.TAGLIB_URI_LIFERAY_TRASH);
        FTL_MACRO_PREFIXES.put("liferay_ui", LiferayTaglibs.TAGLIB_URI_LIFERAY_UI);
        FTL_MACRO_PREFIXES.put("liferay_util", LiferayTaglibs.TAGLIB_URI_LIFERAY_UTIL);
        FTL_MACRO_PREFIXES.put("portlet", LiferayTaglibs.TAGLIB_URI_JAVAX_PORTLET_2_0);
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
        // https://github.com/liferay/liferay-portal/blob/master/modules/apps/commerce/commerce-taglib/src/main/resources/META-INF/taglib-mappings.properties
        FTL_TAGLIB_MAPPINGS.put("liferay-commerce.tld", "liferay_commerce");
        // https://github.com/liferay/liferay-portal/blob/master/modules/apps/commerce/commerce-cart-taglib/src/main/resources/META-INF/taglib-mappings.properties
        FTL_TAGLIB_MAPPINGS.put("liferay-commerce-cart.tld", "liferay_commerce_cart");
        // https://github.com/liferay/liferay-portal/blob/master/modules/apps/commerce/commerce-frontend-taglib/src/main/resources/META-INF/taglib-mappings.properties
        FTL_TAGLIB_MAPPINGS.put("liferay-commerce-ui.tld", "liferay_commerce_ui");
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
        // https://github.com/liferay/liferay-portal/blob/master/modules/apps/info/info-taglib/src/main/resources/META-INF/taglib-mappings.properties
        FTL_TAGLIB_MAPPINGS.put("liferay-info.tld", "liferay_info");
        // https://github.com/liferay/liferay-portal/blob/master/modules/apps/journal/journal-taglib/src/main/resources/META-INF/taglib-mappings.properties
        FTL_TAGLIB_MAPPINGS.put("liferay-journal.tld", "liferay_journal");
        // https://github.com/liferay/liferay-portal/blob/master/modules/apps/layout/layout-taglib/src/main/resources/META-INF/taglib-mappings.properties
        FTL_TAGLIB_MAPPINGS.put("liferay-layout.tld", "liferay_layout");
        // https://github.com/liferay/liferay-portal/blob/master/modules/apps/learn/learn-taglib/src/main/resources/META-INF/taglib-mappings.properties
        FTL_TAGLIB_MAPPINGS.put("liferay-learn.tld", "liferay_learn");
        // https://github.com/liferay/liferay-portal/blob/master/modules/apps/map/map-taglib/src/main/resources/META-INF/taglib-mappings.properties
        FTL_TAGLIB_MAPPINGS.put("liferay-map.tld", "liferay_map");
        // https://github.com/liferay/liferay-portal/blob/master/modules/apps/product-navigation/product-navigation-taglib/src/main/resources/META-INF/taglib-mappings.properties
        FTL_TAGLIB_MAPPINGS.put("liferay-product-navigation.tld", "liferay_product_navigation");
        // https://github.com/liferay/liferay-portal/blob/master/modules/apps/ratings/ratings-taglib/src/main/resources/META-INF/taglib-mappings.properties
        FTL_TAGLIB_MAPPINGS.put("ratings.tld", "liferay_ratings");
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

    public static String getPrefix(FtlMacro ftlMacro) {
        String directiveName = ftlMacro.getDirectiveName();

        if (directiveName.contains(".")) {
            String[] split = directiveName.split("\\.");
            if (split.length == 2) {
                return split[0];
            }
        } else if (directiveName.contains("[")) {
            String[] split = directiveName.split("\\[");
            if (split.length == 2) {
                return split[0];
            }
        }

        return null;
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

    public static void setNamespace(FtlMacro ftlMacro, String newNamespace) {
        String directiveName = ftlMacro.getDirectiveName();

        String styleStart = ".";
        String styleEnd = "";

        int index = directiveName.indexOf('.');

        if (index < 0) {
            index = directiveName.indexOf('[');

            styleStart = "[\"";
            styleEnd = "\"]";
        }

        if (index >= 0) {
            for (Map.Entry<String, String> macroPrefix : LiferayFreemarkerTaglibs.FTL_MACRO_PREFIXES.entrySet()) {
                if (macroPrefix.getValue().equals(newNamespace)) {
                    PsiElement startTagNameElement = ftlMacro.getStartTagNameElement();

                    if (startTagNameElement != null) {
                        directiveName = macroPrefix.getKey() + styleStart + getLocalName(ftlMacro) + styleEnd;

                        PsiFile file = startTagNameElement.getContainingFile();

                        file = file.getOriginalFile();

                        Document document = file.getViewProvider().getDocument();

                        TextRange range = startTagNameElement.getTextRange();

                        document.replaceString(range.getStartOffset(), range.getEndOffset(), directiveName);
                    }
                }
            }
        }
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

    public static void setLocalName(FtlMacro ftlMacro, String newName) {
        String directiveName = ftlMacro.getDirectiveName();

        String styleStart = ".";
        String styleEnd = "";

        int index = directiveName.indexOf('.');

        if (index < 0) {
            index = directiveName.indexOf('[');

            styleStart = "[\"";
            styleEnd = "\"]";
        }

        if (index >= 0) {
            PsiElement startTagNameElement = ftlMacro.getStartTagNameElement();

            if (startTagNameElement != null) {
                directiveName = getPrefix(ftlMacro) + styleStart + newName + styleEnd;

                PsiFile file = startTagNameElement.getContainingFile();

                file = file.getOriginalFile();

                Document document = file.getViewProvider().getDocument();

                TextRange range = startTagNameElement.getTextRange();

                document.replaceString(range.getStartOffset(), range.getEndOffset(), directiveName);
            }
        }
    }
}
