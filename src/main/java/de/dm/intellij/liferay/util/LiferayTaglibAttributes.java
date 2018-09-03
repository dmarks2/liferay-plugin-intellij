package de.dm.intellij.liferay.util;

import javafx.util.Pair;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class LiferayTaglibAttributes {

    public static Map<String, Collection<Pair<String, String>>> TAGLIB_ATTRIBUTES_JAVASCRIPT = new HashMap<String, Collection<Pair<String, String>>>();
    public static Map<String, Collection<Pair<String, String>>> TAGLIB_ATTRIBUTES_RESOURCEBUNDLE = new HashMap<String, Collection<Pair<String, String>>>();
    public static Map<String, Collection<Pair<String, String>>> TAGLIB_ATTRIBUTES_CSS = new HashMap<String, Collection<Pair<String, String>>>();

    //TAGLIB_ATTRIBUTES_JAVASCRIPT
    static {
        TAGLIB_ATTRIBUTES_JAVASCRIPT.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_AUI, Arrays.asList(
                new Pair<String, String>("script", ""),
                new Pair<String, String>("validator", ""),
                new Pair<String, String>("a", "onClick"),
                new Pair<String, String>("button", "onClick"),
                new Pair<String, String>("form", "onSubmit"),
                new Pair<String, String>("input", "onChange"),
                new Pair<String, String>("input", "onClick"),
                new Pair<String, String>("select", "onChange"),
                new Pair<String, String>("select", "onClick")
        ));

        TAGLIB_ATTRIBUTES_JAVASCRIPT.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_FRONTEND, Arrays.asList(
                new Pair<String, String>("edit-form", "onSubmit"),
                new Pair<String, String>("icon-vertical-card", "onClick"),
                new Pair<String, String>("vertical-card", "onClick")
        ));

        TAGLIB_ATTRIBUTES_JAVASCRIPT.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_UI, Arrays.asList(
                new Pair<String, String>("icon", "onClick"),
                new Pair<String, String>("input-checkbox", "onClick"),
                new Pair<String, String>("input-move-boxes", "leftOnChange"),
                new Pair<String, String>("input-move-boxes", "rightOnChange"),
                new Pair<String, String>("page-iterator", "jsCall"),
                new Pair<String, String>("quick-access-entry", "onClick"),
                new Pair<String, String>("tabs", "onClick")
        ));

        TAGLIB_ATTRIBUTES_JAVASCRIPT.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_AUI_OLD, LiferayTaglibAttributes.TAGLIB_ATTRIBUTES_JAVASCRIPT.get(LiferayTaglibs.TAGLIB_URI_LIFERAY_AUI));
    }

    //TAGLIB_ATTRIBUTES_RESOURCEBUNDLE
    static {
        TAGLIB_ATTRIBUTES_RESOURCEBUNDLE.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_UI, Arrays.asList(
                new Pair<String, String>("app-view-search-entry", "containerType"),
                new Pair<String, String>("alert", "message"),
                new Pair<String, String>("asset-addon-entry-selector", "title"),
                new Pair<String, String>("asset-metadata", "metadataField"),
                new Pair<String, String>("asset-tags-summary", "message"),
                new Pair<String, String>("custom-attribute", "name"),
                new Pair<String, String>("diff-html", "infoMessage"),
                new Pair<String, String>("drop-here-info", "message"),
                new Pair<String, String>("empty-result-message", "message"),
                new Pair<String, String>("error", "message"),
                new Pair<String, String>("form-navigator", "categoryLabels"),
                new Pair<String, String>("form-navigator", "categorySectionLabels"),
                new Pair<String, String>("header", "backLabel"),
                new Pair<String, String>("header", "title"),
                new Pair<String, String>("icon", "message"),
                new Pair<String, String>("icon-delete", "confirmation"),
                new Pair<String, String>("icon-delete", "message"),
                new Pair<String, String>("icon-help", "message"),
                new Pair<String, String>("icon-menu", "message"),
                new Pair<String, String>("input-field", "placeholder"),
                new Pair<String, String>("input-localized", "helpMessaage"),
                new Pair<String, String>("input-localized", "placeholder"),
                new Pair<String, String>("input-resource", "title"),
                new Pair<String, String>("input-move-boxes", "leftTitle"),
                new Pair<String, String>("input-move-boxes", "rightTitle"),
                new Pair<String, String>("input-resource", "title"),
                new Pair<String, String>("message", "key"),
                new Pair<String, String>("panel", "helpMessage"),
                new Pair<String, String>("panel", "title"),
                new Pair<String, String>("progress", "message"),
                new Pair<String, String>("quick-access-entry", "label"),
                new Pair<String, String>("search-container", "emptyResultsMessage"),
                new Pair<String, String>("search-container", "headerNames"),
                new Pair<String, String>("search-container-column-button", "name"),
                new Pair<String, String>("search-container-column-date", "name"),
                new Pair<String, String>("search-container-column-text", "name"),
                new Pair<String, String>("search-toggle", "buttonLabel"),
                new Pair<String, String>("success", "message"),
                new Pair<String, String>("tabs", "names"),
                new Pair<String, String>("upload-progress", "message")
        ));

        TAGLIB_ATTRIBUTES_RESOURCEBUNDLE.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_AUI, Arrays.asList(
                new Pair<String, String>("a", "title"),
                new Pair<String, String>("a", "label"),
                new Pair<String, String>("button", "value"),
                new Pair<String, String>("field-wrapper", "helpMessage"),
                new Pair<String, String>("field-wrapper", "label"),
                new Pair<String, String>("fieldset", "helpMessage"),
                new Pair<String, String>("fieldset", "label"),
                new Pair<String, String>("icon", "label"),
                new Pair<String, String>("input", "helpMessage"),
                new Pair<String, String>("input", "label"),
                new Pair<String, String>("input", "labelOff"),
                new Pair<String, String>("input", "labelOn"),
                new Pair<String, String>("input", "title"),
                new Pair<String, String>("input", "placeholder"),
                new Pair<String, String>("input", "prefix"),
                new Pair<String, String>("input", "suffix"),
                new Pair<String, String>("nav-bar", "selectedItemName"),
                new Pair<String, String>("nav-item", "label"),
                new Pair<String, String>("nav-item", "title"),
                new Pair<String, String>("option", "label"),
                new Pair<String, String>("panel", "label"),
                new Pair<String, String>("select", "label"),
                new Pair<String, String>("select", "helpMessage"),
                new Pair<String, String>("select", "prefix"),
                new Pair<String, String>("select", "suffix"),
                new Pair<String, String>("select", "title"),
                new Pair<String, String>("validator", "errorMessage"),
                new Pair<String, String>("workflow-status", "helpMessage"),
                new Pair<String, String>("workflow-status", "statusMessage")
        ));
        TAGLIB_ATTRIBUTES_RESOURCEBUNDLE.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_AUI_OLD, LiferayTaglibAttributes.TAGLIB_ATTRIBUTES_RESOURCEBUNDLE.get(LiferayTaglibs.TAGLIB_URI_LIFERAY_AUI));

        TAGLIB_ATTRIBUTES_RESOURCEBUNDLE.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_ASSET, Arrays.asList(
                new Pair<String, String>("asset-addon-entry-selector", "title"),
                new Pair<String, String>("asset-metadata", "metadataField"),
                new Pair<String, String>("asset-tags-summary", "message")
        ));

        TAGLIB_ATTRIBUTES_RESOURCEBUNDLE.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_EXPANDO, Arrays.asList(
                new Pair<String, String>("custom-attribute", "name")
        ));

        TAGLIB_ATTRIBUTES_RESOURCEBUNDLE.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_FRONTEND, Arrays.asList(
                new Pair<String, String>("email-notification-settings", "bodyLabel"),
                new Pair<String, String>("email-notification-settings", "helpMessage"),
                new Pair<String, String>("management-bar-button", "label"),
                new Pair<String, String>("management-bar-filter", "label")
        ));

        TAGLIB_ATTRIBUTES_RESOURCEBUNDLE.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_TRASH, Arrays.asList(
                new Pair<String, String>("empty", "confirmMessage"),
                new Pair<String, String>("empty", "emptyMessage"),
                new Pair<String, String>("empty", "infoMessage")
        ));

        TAGLIB_ATTRIBUTES_RESOURCEBUNDLE.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_DDM, Arrays.asList(
                new Pair<String, String>("template-selector", "label")
        ));

        TAGLIB_ATTRIBUTES_RESOURCEBUNDLE.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_SITE, Arrays.asList(
                new Pair<String, String>("site-browser", "emptyResultsMessage")
        ));

        TAGLIB_ATTRIBUTES_RESOURCEBUNDLE.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_STAGING, Arrays.asList(
                new Pair<String, String>("checkbox", "description"),
                new Pair<String, String>("checkbox", "label"),
                new Pair<String, String>("checkbox", "popover"),
                new Pair<String, String>("checkbox", "suggestion"),
                new Pair<String, String>("checkbox", "warning"),
                new Pair<String, String>("popover", "text"),
                new Pair<String, String>("popover", "title"),
                new Pair<String, String>("process-date", "labelKey"),
                new Pair<String, String>("process-list", "emptyResultsMessage"),
                new Pair<String, String>("radio", "description"),
                new Pair<String, String>("radio", "label"),
                new Pair<String, String>("radio", "popover")
        ));

    }

    //TAGLIB_ATTRIBUTES_CSS
    static {
        TAGLIB_ATTRIBUTES_CSS.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_AUI, Arrays.asList(
                new Pair<String, String>("a", "cssClass"),
                new Pair<String, String>("a", "iconCssClass"),
                new Pair<String, String>("alert", "cssClass"),
                new Pair<String, String>("button", "cssClass"),
                new Pair<String, String>("button-row", "cssClass"),
                new Pair<String, String>("col", "cssClass"),
                new Pair<String, String>("container", "cssClass"),
                new Pair<String, String>("field-wrapper", "cssClass"),
                new Pair<String, String>("fieldset", "cssClass"),
                new Pair<String, String>("form", "cssClass"),
                new Pair<String, String>("icon", "cssClass"),
                new Pair<String, String>("input", "cssClass"),
                new Pair<String, String>("input", "helpTextCssClass"),
                new Pair<String, String>("input", "wrapperCssClass"),
                new Pair<String, String>("nav", "cssClass"),
                new Pair<String, String>("nav-bar", "cssClass"),
                new Pair<String, String>("nav-bar-search", "cssClass"),
                new Pair<String, String>("nav-item", "anchorCssClass"),
                new Pair<String, String>("nav-item", "cssClass"),
                new Pair<String, String>("nav-item", "iconCssClass"),
                new Pair<String, String>("option", "cssClass"),
                new Pair<String, String>("row", "cssClass"),
                new Pair<String, String>("select", "cssClass"),
                new Pair<String, String>("select", "wrapperCssClass")
        ));

        TAGLIB_ATTRIBUTES_CSS.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_AUI_OLD, TAGLIB_ATTRIBUTES_CSS.get(LiferayTaglibs.TAGLIB_URI_LIFERAY_AUI));

        TAGLIB_ATTRIBUTES_CSS.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_UI, Arrays.asList(
                new Pair<String, String>("alert", "cssClass"),
                new Pair<String, String>("app-view-entry", "cssClass"),
                new Pair<String, String>("app-view-entry", "iconCssClass"),
                new Pair<String, String>("app-view-search-entry", "iconCssClass"),
                new Pair<String, String>("empty-result-message", "cssClass"),
                new Pair<String, String>("header", "cssClass"),
                new Pair<String, String>("icon", "cssClass"),
                new Pair<String, String>("icon", "iconCssClass"),
                new Pair<String, String>("icon", "linkCssClass"),
                new Pair<String, String>("icon-delete", "cssClass"),
                new Pair<String, String>("icon-delete", "linkCssClass"),
                new Pair<String, String>("icon-menu", "cssClass"),
                new Pair<String, String>("icon-menu", "triggerCssClass"),
                new Pair<String, String>("input-checkbox", "cssClass"),
                new Pair<String, String>("input-date", "cssClass"),
                new Pair<String, String>("input-editor", "cssClass"),
                new Pair<String, String>("input-field", "cssClass"),
                new Pair<String, String>("input-localized", "cssClass"),
                new Pair<String, String>("input-move-boxes", "cssClass"),
                new Pair<String, String>("input-repeat", "cssClass"),
                new Pair<String, String>("input-resource", "cssClass"),
                new Pair<String, String>("input-search", "cssClass"),
                new Pair<String, String>("input-select", "cssClass"),
                new Pair<String, String>("input-textarea", "cssClass"),
                new Pair<String, String>("input-time", "cssClass"),
                new Pair<String, String>("input-time-zone", "cssClass"),
                new Pair<String, String>("my-sites", "cssClass"),
                new Pair<String, String>("panel", "cssClass"),
                new Pair<String, String>("panel", "iconCssClass"),
                new Pair<String, String>("panel-container", "cssClass"),
                new Pair<String, String>("search-container", "cssClass"),
                new Pair<String, String>("search-container", "emptyResultsMessageCssClass"),
                new Pair<String, String>("search-container-column-button", "cssClass"),
                new Pair<String, String>("search-container-column-date", "cssClass"),
                new Pair<String, String>("search-container-column-icon", "cssClass"),
                new Pair<String, String>("search-container-column-image", "cssClass"),
                new Pair<String, String>("search-container-column-jsp", "cssClass"),
                new Pair<String, String>("search-container-column-status", "cssClass"),
                new Pair<String, String>("search-container-column-text", "cssClass"),
                new Pair<String, String>("search-container-column-user", "cssClass"),
                new Pair<String, String>("search-container-row", "cssClass"),
                new Pair<String, String>("tabs", "cssClass"),
                new Pair<String, String>("user-display", "imageCssClass"),
                new Pair<String, String>("user-display", "userIconCssClass"),
                new Pair<String, String>("user-portrait", "cssClass"),
                new Pair<String, String>("user-portrait", "imageCssClass")
        ));

        TAGLIB_ATTRIBUTES_CSS.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_FRONTEND, Arrays.asList(
                new Pair<String, String>("horizontal-card", "checkboxCSSClass"),
                new Pair<String, String>("horizontal-card", "cssClass"),
                new Pair<String, String>("icon-vertical-card", "checkboxCSSClass"),
                new Pair<String, String>("icon-vertical-card", "cssClass"),
                new Pair<String, String>("image-card", "cssClass"),
                new Pair<String, String>("image-card", "imageCssClass"),
                new Pair<String, String>("info-bar-button", "cssClass"),
                new Pair<String, String>("info-bar-button", "iconCssClass"),
                new Pair<String, String>("info-bar-sidenav-toggler-button", "cssClass"),
                new Pair<String, String>("info-bar-sidenav-toggler-button", "iconCssClass"),
                new Pair<String, String>("management-bar-button", "cssClass"),
                new Pair<String, String>("management-bar-button", "iconCssClass"),
                new Pair<String, String>("management-bar-sidenav-toggler-button", "cssClass"),
                new Pair<String, String>("management-bar-sidenav-toggler-button", "iconCssClass"),
                new Pair<String, String>("translation-manager", "cssClass"),
                new Pair<String, String>("user-vertical-card", "checkboxCSSClass"),
                new Pair<String, String>("user-vertical-card", "cssClass"),
                new Pair<String, String>("vertical-card", "checkboxCSSClass"),
                new Pair<String, String>("vertical-card", "cssClass"),
                new Pair<String, String>("vertical-card", "imageCSSClass"),
                new Pair<String, String>("vertical-card-small-image", "cssClass")
        ));

        TAGLIB_ATTRIBUTES_CSS.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_ITEM_SELECTOR, Arrays.asList(
                new Pair<String, String>("image-selector", "draggableImage")
        ));

        TAGLIB_ATTRIBUTES_CSS.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_EDITOR, Arrays.asList(
                new Pair<String, String>("editor", "cssClass")
        ));

        TAGLIB_ATTRIBUTES_CSS.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_ADAPTIVE_MEDIA_IMAGE, Arrays.asList(
                new Pair<String, String>("img", "class")
        ));

        TAGLIB_ATTRIBUTES_CSS.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_DDM, Arrays.asList(
                new Pair<String, String>("template-selector", "icon")
        ));

        TAGLIB_ATTRIBUTES_CSS.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_STAGING, Arrays.asList(
                new Pair<String, String>("menu", "cssClass"),
                new Pair<String, String>("permissions", "descriptionCSSClass"),
                new Pair<String, String>("permissions", "labelCSSClass"),
                new Pair<String, String>("process-message-task-details", "linkClass"),
                new Pair<String, String>("process-status", "linkClass"),
                new Pair<String, String>("status", "cssClass")
        ));

        TAGLIB_ATTRIBUTES_CSS.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_CLAY, Arrays.asList(
                new Pair<String, String>("alert", "elementClasses"),
                new Pair<String, String>("badge", "elementClasses"),
                new Pair<String, String>("button", "elementClasses"),
                new Pair<String, String>("button", "icon"),
                new Pair<String, String>("checkbox", "elementClasses"),
                new Pair<String, String>("dropdown-actions", "elementClasses"),
                new Pair<String, String>("dropdown-actions", "triggerCssClasses"),
                new Pair<String, String>("dropdown-menu", "elementClasses"),
                new Pair<String, String>("dropdown-menu", "icon"),
                new Pair<String, String>("dropdown-menu", "triggerCssClasses"),
                new Pair<String, String>("file-card", "elementClasses"),
                new Pair<String, String>("file-card", "icon"),
                new Pair<String, String>("horizontal-card", "elementClasses"),
                new Pair<String, String>("horizontal-card", "icon"),
                new Pair<String, String>("icon", "elementClasses"),
                new Pair<String, String>("icon", "symbol"),
                new Pair<String, String>("image-card", "elementClasses"),
                new Pair<String, String>("image-card", "icon"),
                new Pair<String, String>("label", "elementClasses"),
                new Pair<String, String>("link", "elementClasses"),
                new Pair<String, String>("link", "icon"),
                new Pair<String, String>("management-toolbar", "elementClasses"),
                new Pair<String, String>("navigation-bar", "elementClasses"),
                new Pair<String, String>("progressbar", "elementClasses"),
                new Pair<String, String>("radio", "elementClasses"),
                new Pair<String, String>("select", "elementClasses"),
                new Pair<String, String>("sticker", "elementClasses"),
                new Pair<String, String>("sticker", "icon"),
                new Pair<String, String>("stripe", "elementClasses"),
                new Pair<String, String>("user-card", "elementClasses"),
                new Pair<String, String>("user-card", "icon")
        ));
    }



}
