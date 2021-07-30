package de.dm.intellij.liferay.util;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class LiferayTaglibAttributes {

    public static Map<String, Collection<AbstractMap.SimpleEntry<String, String>>> TAGLIB_ATTRIBUTES_JAVASCRIPT = new HashMap<>();
    public static Map<String, Collection<AbstractMap.SimpleEntry<String, String>>> TAGLIB_ATTRIBUTES_RESOURCEBUNDLE = new HashMap<>();
    public static Map<String, Collection<AbstractMap.SimpleEntry<String, String>>> TAGLIB_ATTRIBUTES_CSS = new HashMap<>();
    public static Map<String, Collection<AbstractMap.SimpleEntry<String, String>>> TAGLIB_ATTRIBUTES_CLASS_NAME = new HashMap<>();
    public static Map<String, Collection<AbstractMap.SimpleImmutableEntry<String, String>>> TAGLIB_SUGGESTED_PARENTS = new HashMap<>();

    //TAGLIB_ATTRIBUTES_JAVASCRIPT
    static {
        TAGLIB_ATTRIBUTES_JAVASCRIPT.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_AUI, Arrays.asList(
                new AbstractMap.SimpleEntry<String, String>("script", ""),
                new AbstractMap.SimpleEntry<String, String>("validator", ""),
                new AbstractMap.SimpleEntry<String, String>("a", "onClick"),
                new AbstractMap.SimpleEntry<String, String>("button", "onClick"),
                new AbstractMap.SimpleEntry<String, String>("form", "onSubmit"),
                new AbstractMap.SimpleEntry<String, String>("input", "onChange"),
                new AbstractMap.SimpleEntry<String, String>("input", "onClick"),
                new AbstractMap.SimpleEntry<String, String>("select", "onChange"),
                new AbstractMap.SimpleEntry<String, String>("select", "onClick")
        ));

        TAGLIB_ATTRIBUTES_JAVASCRIPT.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_FRONTEND, Arrays.asList(
                new AbstractMap.SimpleEntry<String, String>("edit-form", "onSubmit"),
                new AbstractMap.SimpleEntry<String, String>("html-vertical-card", "onClick"),
                new AbstractMap.SimpleEntry<String, String>("icon-vertical-card", "onClick"),
                new AbstractMap.SimpleEntry<String, String>("vertical-card", "onClick")
        ));

        TAGLIB_ATTRIBUTES_JAVASCRIPT.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_UI, Arrays.asList(
                new AbstractMap.SimpleEntry<String, String>("icon", "onClick"),
                new AbstractMap.SimpleEntry<String, String>("input-checkbox", "onClick"),
                new AbstractMap.SimpleEntry<String, String>("input-move-boxes", "leftOnChange"),
                new AbstractMap.SimpleEntry<String, String>("input-move-boxes", "rightOnChange"),
                new AbstractMap.SimpleEntry<String, String>("page-iterator", "jsCall"),
                new AbstractMap.SimpleEntry<String, String>("quick-access-entry", "onClick"),
                new AbstractMap.SimpleEntry<String, String>("tabs", "onClick")
        ));

        TAGLIB_ATTRIBUTES_JAVASCRIPT.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_AUI_OLD, LiferayTaglibAttributes.TAGLIB_ATTRIBUTES_JAVASCRIPT.get(LiferayTaglibs.TAGLIB_URI_LIFERAY_AUI));
    }

    //TAGLIB_ATTRIBUTES_RESOURCEBUNDLE
    static {
        TAGLIB_ATTRIBUTES_RESOURCEBUNDLE.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_ASSET, Arrays.asList(
                new AbstractMap.SimpleEntry<String, String>("asset-addon-entry-selector", "title"),
                new AbstractMap.SimpleEntry<String, String>("asset-metadata", "metadataField"),
                new AbstractMap.SimpleEntry<String, String>("asset-tags-summary", "message")
        ));

        TAGLIB_ATTRIBUTES_RESOURCEBUNDLE.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_AUI, Arrays.asList(
                new AbstractMap.SimpleEntry<String, String>("a", "title"),
                new AbstractMap.SimpleEntry<String, String>("a", "label"),
                new AbstractMap.SimpleEntry<String, String>("button", "value"),
                new AbstractMap.SimpleEntry<String, String>("field-wrapper", "helpMessage"),
                new AbstractMap.SimpleEntry<String, String>("field-wrapper", "label"),
                new AbstractMap.SimpleEntry<String, String>("fieldset", "helpMessage"),
                new AbstractMap.SimpleEntry<String, String>("fieldset", "label"),
                new AbstractMap.SimpleEntry<String, String>("icon", "label"),
                new AbstractMap.SimpleEntry<String, String>("input", "helpMessage"),
                new AbstractMap.SimpleEntry<String, String>("input", "label"),
                new AbstractMap.SimpleEntry<String, String>("input", "labelOff"),
                new AbstractMap.SimpleEntry<String, String>("input", "labelOn"),
                new AbstractMap.SimpleEntry<String, String>("input", "title"),
                new AbstractMap.SimpleEntry<String, String>("input", "placeholder"),
                new AbstractMap.SimpleEntry<String, String>("input", "prefix"),
                new AbstractMap.SimpleEntry<String, String>("input", "suffix"),
                new AbstractMap.SimpleEntry<String, String>("nav-bar", "selectedItemName"),
                new AbstractMap.SimpleEntry<String, String>("nav-item", "label"),
                new AbstractMap.SimpleEntry<String, String>("nav-item", "title"),
                new AbstractMap.SimpleEntry<String, String>("option", "label"),
                new AbstractMap.SimpleEntry<String, String>("panel", "label"),
                new AbstractMap.SimpleEntry<String, String>("select", "label"),
                new AbstractMap.SimpleEntry<String, String>("select", "helpMessage"),
                new AbstractMap.SimpleEntry<String, String>("select", "prefix"),
                new AbstractMap.SimpleEntry<String, String>("select", "suffix"),
                new AbstractMap.SimpleEntry<String, String>("select", "title"),
                new AbstractMap.SimpleEntry<String, String>("validator", "errorMessage"),
                new AbstractMap.SimpleEntry<String, String>("workflow-status", "helpMessage"),
                new AbstractMap.SimpleEntry<String, String>("workflow-status", "statusMessage")
        ));
        TAGLIB_ATTRIBUTES_RESOURCEBUNDLE.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_AUI_OLD, LiferayTaglibAttributes.TAGLIB_ATTRIBUTES_RESOURCEBUNDLE.get(LiferayTaglibs.TAGLIB_URI_LIFERAY_AUI));

        TAGLIB_ATTRIBUTES_RESOURCEBUNDLE.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_CLAY, Arrays.asList(
                new AbstractMap.SimpleEntry<String, String>("alert", "message"),
                new AbstractMap.SimpleEntry<String, String>("button", "label"),
                new AbstractMap.SimpleEntry<String, String>("checkbox", "label"),
                new AbstractMap.SimpleEntry<String, String>("dropdown-menu", "label"),
                new AbstractMap.SimpleEntry<String, String>("link", "label"),
                new AbstractMap.SimpleEntry<String, String>("stripe", "message")
        ));

        TAGLIB_ATTRIBUTES_RESOURCEBUNDLE.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_DDM, Arrays.asList(
                new AbstractMap.SimpleEntry<String, String>("template-selector", "label")
        ));

        TAGLIB_ATTRIBUTES_RESOURCEBUNDLE.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_EXPANDO, Arrays.asList(
                new AbstractMap.SimpleEntry<String, String>("custom-attribute", "name")
        ));

        TAGLIB_ATTRIBUTES_RESOURCEBUNDLE.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_FRONTEND, Arrays.asList(
                new AbstractMap.SimpleEntry<String, String>("add-menu-item", "title"),
                new AbstractMap.SimpleEntry<String, String>("email-notification-settings", "bodyLabel"),
                new AbstractMap.SimpleEntry<String, String>("email-notification-settings", "helpMessage"),
                new AbstractMap.SimpleEntry<String, String>("fieldset", "helpMessage"),
                new AbstractMap.SimpleEntry<String, String>("fieldset", "label"),
                new AbstractMap.SimpleEntry<String, String>("management-bar-button", "label"),
                new AbstractMap.SimpleEntry<String, String>("management-bar-filter", "label")
        ));
        TAGLIB_ATTRIBUTES_RESOURCEBUNDLE.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_ITEM_SELECTOR, Arrays.asList(
                new AbstractMap.SimpleEntry<>("repository-entry-browser", "emptyResultsMessage")
        ));
        TAGLIB_ATTRIBUTES_RESOURCEBUNDLE.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_PRODUCT_NAVIGATION, Arrays.asList(
                new AbstractMap.SimpleEntry<>("personal-menu", "label")
        ));

        TAGLIB_ATTRIBUTES_RESOURCEBUNDLE.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_SITE, Arrays.asList(
                new AbstractMap.SimpleEntry<String, String>("site-browser", "emptyResultsMessage")
        ));

        TAGLIB_ATTRIBUTES_RESOURCEBUNDLE.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_STAGING, Arrays.asList(
                new AbstractMap.SimpleEntry<String, String>("checkbox", "description"),
                new AbstractMap.SimpleEntry<String, String>("checkbox", "label"),
                new AbstractMap.SimpleEntry<String, String>("checkbox", "popover"),
                new AbstractMap.SimpleEntry<String, String>("checkbox", "suggestion"),
                new AbstractMap.SimpleEntry<String, String>("checkbox", "warning"),
                new AbstractMap.SimpleEntry<String, String>("configuration-header", "label"),
                new AbstractMap.SimpleEntry<String, String>("popover", "text"),
                new AbstractMap.SimpleEntry<String, String>("popover", "title"),
                new AbstractMap.SimpleEntry<String, String>("process-date", "labelKey"),
                new AbstractMap.SimpleEntry<String, String>("process-list", "emptyResultsMessage"),
                new AbstractMap.SimpleEntry<String, String>("radio", "description"),
                new AbstractMap.SimpleEntry<String, String>("radio", "label"),
                new AbstractMap.SimpleEntry<String, String>("radio", "popover")
        ));

        TAGLIB_ATTRIBUTES_RESOURCEBUNDLE.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_TRASH, Arrays.asList(
                new AbstractMap.SimpleEntry<String, String>("empty", "confirmMessage"),
                new AbstractMap.SimpleEntry<String, String>("empty", "emptyMessage"),
                new AbstractMap.SimpleEntry<String, String>("empty", "infoMessage")
        ));

        TAGLIB_ATTRIBUTES_RESOURCEBUNDLE.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_UI, Arrays.asList(
                new AbstractMap.SimpleEntry<String, String>("app-view-search-entry", "containerType"),
                new AbstractMap.SimpleEntry<String, String>("alert", "message"),
                new AbstractMap.SimpleEntry<String, String>("asset-addon-entry-selector", "title"),
                new AbstractMap.SimpleEntry<String, String>("asset-metadata", "metadataField"),
                new AbstractMap.SimpleEntry<String, String>("asset-tags-summary", "message"),
                new AbstractMap.SimpleEntry<String, String>("custom-attribute", "name"),
                new AbstractMap.SimpleEntry<String, String>("diff-html", "infoMessage"),
                new AbstractMap.SimpleEntry<String, String>("drop-here-info", "message"),
                new AbstractMap.SimpleEntry<String, String>("empty-result-message", "message"),
                new AbstractMap.SimpleEntry<String, String>("error", "message"),
                new AbstractMap.SimpleEntry<String, String>("form-navigator", "categoryLabels"),
                new AbstractMap.SimpleEntry<String, String>("form-navigator", "categorySectionLabels"),
                new AbstractMap.SimpleEntry<String, String>("header", "backLabel"),
                new AbstractMap.SimpleEntry<String, String>("header", "title"),
                new AbstractMap.SimpleEntry<String, String>("icon", "message"),
                new AbstractMap.SimpleEntry<String, String>("icon-delete", "confirmation"),
                new AbstractMap.SimpleEntry<String, String>("icon-delete", "message"),
                new AbstractMap.SimpleEntry<String, String>("icon-help", "message"),
                new AbstractMap.SimpleEntry<String, String>("icon-menu", "message"),
                new AbstractMap.SimpleEntry<String, String>("icon-menu", "triggerLabel"),
                new AbstractMap.SimpleEntry<String, String>("input-date", "dateTogglerCheckboxLabel"),
                new AbstractMap.SimpleEntry<String, String>("input-field", "placeholder"),
                new AbstractMap.SimpleEntry<String, String>("input-localized", "helpMessaage"),
                new AbstractMap.SimpleEntry<String, String>("input-localized", "placeholder"),
                new AbstractMap.SimpleEntry<String, String>("input-resource", "title"),
                new AbstractMap.SimpleEntry<String, String>("input-move-boxes", "leftTitle"),
                new AbstractMap.SimpleEntry<String, String>("input-move-boxes", "rightTitle"),
                new AbstractMap.SimpleEntry<String, String>("input-resource", "title"),
                new AbstractMap.SimpleEntry<String, String>("message", "key"),
                new AbstractMap.SimpleEntry<String, String>("panel", "helpMessage"),
                new AbstractMap.SimpleEntry<String, String>("panel", "title"),
                new AbstractMap.SimpleEntry<String, String>("progress", "message"),
                new AbstractMap.SimpleEntry<String, String>("quick-access-entry", "label"),
                new AbstractMap.SimpleEntry<String, String>("search-container", "emptyResultsMessage"),
                new AbstractMap.SimpleEntry<String, String>("search-container", "headerNames"),
                new AbstractMap.SimpleEntry<String, String>("search-container-column-button", "name"),
                new AbstractMap.SimpleEntry<String, String>("search-container-column-date", "name"),
                new AbstractMap.SimpleEntry<String, String>("search-container-column-text", "name"),
                new AbstractMap.SimpleEntry<String, String>("search-container-column-text", "helpMessage"),
                new AbstractMap.SimpleEntry<String, String>("search-toggle", "buttonLabel"),
                new AbstractMap.SimpleEntry<String, String>("success", "message"),
                new AbstractMap.SimpleEntry<String, String>("tabs", "names"),
                new AbstractMap.SimpleEntry<String, String>("upload-progress", "message")
        ));

    }

    //TAGLIB_ATTRIBUTES_CSS
    static {
        TAGLIB_ATTRIBUTES_CSS.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_ADAPTIVE_MEDIA_IMAGE, Arrays.asList(
                new AbstractMap.SimpleEntry<String, String>("img", "class")
        ));

        TAGLIB_ATTRIBUTES_CSS.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_AUI, Arrays.asList(
                new AbstractMap.SimpleEntry<String, String>("a", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("a", "iconCssClass"),
                new AbstractMap.SimpleEntry<String, String>("alert", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("button", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("button-row", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("col", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("container", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("field-wrapper", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("fieldset", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("form", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("icon", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("input", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("input", "helpTextCssClass"),
                new AbstractMap.SimpleEntry<String, String>("input", "wrapperCssClass"),
                new AbstractMap.SimpleEntry<String, String>("nav", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("nav-bar", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("nav-bar-search", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("nav-item", "anchorCssClass"),
                new AbstractMap.SimpleEntry<String, String>("nav-item", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("nav-item", "iconCssClass"),
                new AbstractMap.SimpleEntry<String, String>("option", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("row", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("select", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("select", "wrapperCssClass")
        ));

        TAGLIB_ATTRIBUTES_CSS.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_AUI_OLD, TAGLIB_ATTRIBUTES_CSS.get(LiferayTaglibs.TAGLIB_URI_LIFERAY_AUI));

        TAGLIB_ATTRIBUTES_CSS.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_CLAY, Arrays.asList(
                new AbstractMap.SimpleEntry<String, String>("alert", "elementClasses"),
                new AbstractMap.SimpleEntry<String, String>("badge", "elementClasses"),
                new AbstractMap.SimpleEntry<String, String>("button", "elementClasses"),
                new AbstractMap.SimpleEntry<String, String>("button", "icon"),
                new AbstractMap.SimpleEntry<String, String>("checkbox", "elementClasses"),
                new AbstractMap.SimpleEntry<String, String>("col", "className"),
                new AbstractMap.SimpleEntry<String, String>("container", "className"),
                new AbstractMap.SimpleEntry<String, String>("container-fluid", "className"),
                new AbstractMap.SimpleEntry<String, String>("content-col", "className"),
                new AbstractMap.SimpleEntry<String, String>("content-row", "className"),
                new AbstractMap.SimpleEntry<String, String>("content-section", "className"),
                new AbstractMap.SimpleEntry<String, String>("dropdown-actions", "elementClasses"),
                new AbstractMap.SimpleEntry<String, String>("dropdown-actions", "triggerCssClasses"),
                new AbstractMap.SimpleEntry<String, String>("dropdown-menu", "elementClasses"),
                new AbstractMap.SimpleEntry<String, String>("dropdown-menu", "icon"),
                new AbstractMap.SimpleEntry<String, String>("dropdown-menu", "triggerCssClasses"),
                new AbstractMap.SimpleEntry<String, String>("file-card", "elementClasses"),
                new AbstractMap.SimpleEntry<String, String>("file-card", "icon"),
                new AbstractMap.SimpleEntry<String, String>("file-card", "stickerCssClass"),
                new AbstractMap.SimpleEntry<String, String>("horizontal-card", "elementClasses"),
                new AbstractMap.SimpleEntry<String, String>("horizontal-card", "icon"),
                new AbstractMap.SimpleEntry<String, String>("icon", "elementClasses"),
                new AbstractMap.SimpleEntry<String, String>("icon", "symbol"),
                new AbstractMap.SimpleEntry<String, String>("image-card", "elementClasses"),
                new AbstractMap.SimpleEntry<String, String>("image-card", "icon"),
                new AbstractMap.SimpleEntry<String, String>("image-card", "stickerCssClass"),
                new AbstractMap.SimpleEntry<String, String>("label", "elementClasses"),
                new AbstractMap.SimpleEntry<String, String>("link", "elementClasses"),
                new AbstractMap.SimpleEntry<String, String>("link", "icon"),
                new AbstractMap.SimpleEntry<String, String>("management-toolbar", "elementClasses"),
                new AbstractMap.SimpleEntry<String, String>("multi-select", "elementClasses"),
                new AbstractMap.SimpleEntry<String, String>("navigation-bar", "elementClasses"),
                new AbstractMap.SimpleEntry<String, String>("progressbar", "elementClasses"),
                new AbstractMap.SimpleEntry<String, String>("radio", "elementClasses"),
                new AbstractMap.SimpleEntry<String, String>("row", "className"),
                new AbstractMap.SimpleEntry<String, String>("select", "elementClasses"),
                new AbstractMap.SimpleEntry<String, String>("sheet", "className"),
                new AbstractMap.SimpleEntry<String, String>("sheet-footer", "className"),
                new AbstractMap.SimpleEntry<String, String>("sheet-header", "className"),
                new AbstractMap.SimpleEntry<String, String>("sheet-section", "className"),
                new AbstractMap.SimpleEntry<String, String>("sticker", "elementClasses"),
                new AbstractMap.SimpleEntry<String, String>("sticker", "icon"),
                new AbstractMap.SimpleEntry<String, String>("stripe", "elementClasses"),
                new AbstractMap.SimpleEntry<String, String>("table", "tableClasses"),
                new AbstractMap.SimpleEntry<String, String>("user-card", "elementClasses"),
                new AbstractMap.SimpleEntry<String, String>("user-card", "icon"),
                new AbstractMap.SimpleEntry<String, String>("user-card", "userColorClass"),
                new AbstractMap.SimpleEntry<String, String>("vertical-card", "elementClasses"),
                new AbstractMap.SimpleEntry<String, String>("vertical-card", "stickerCssClass")
        ));
        TAGLIB_ATTRIBUTES_CSS.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_DOCUMENT_LIBRARY, Arrays.asList(
                new AbstractMap.SimpleEntry<>("mime-type-sticker", "cssClass")
        ));
        TAGLIB_ATTRIBUTES_CSS.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_DDM, Arrays.asList(
                new AbstractMap.SimpleEntry<String, String>("template-selector", "icon")
        ));

        TAGLIB_ATTRIBUTES_CSS.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_EDITOR, Arrays.asList(
                new AbstractMap.SimpleEntry<String, String>("editor", "cssClass")
        ));

        TAGLIB_ATTRIBUTES_CSS.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_FRONTEND, Arrays.asList(
                new AbstractMap.SimpleEntry<String, String>("add-menu-item", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("contextual-sidebar", "bodyClasses"),
                new AbstractMap.SimpleEntry<String, String>("contextual-sidebar", "elementClasses"),
                new AbstractMap.SimpleEntry<String, String>("contextual-sidebar", "headerClasses"),
                new AbstractMap.SimpleEntry<String, String>("edit-form", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("empty-result-message", "buttonCssClass"),
                new AbstractMap.SimpleEntry<String, String>("fieldset", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("form-navigator", "fieldSetCssClass"),
                new AbstractMap.SimpleEntry<String, String>("horizontal-card", "cardCssClass"),
                new AbstractMap.SimpleEntry<String, String>("horizontal-card", "checkboxCSSClass"),
                new AbstractMap.SimpleEntry<String, String>("horizontal-card", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("html-vertical-card", "cardCssClass"),
                new AbstractMap.SimpleEntry<String, String>("html-vertical-card", "checkboxCSSClass"),
                new AbstractMap.SimpleEntry<String, String>("html-vertical-card", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("icon-vertical-card", "cardCssClass"),
                new AbstractMap.SimpleEntry<String, String>("icon-vertical-card", "checkboxCSSClass"),
                new AbstractMap.SimpleEntry<String, String>("icon-vertical-card", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("image-card", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("image-card", "imageCssClass"),
                new AbstractMap.SimpleEntry<String, String>("info-bar-button", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("info-bar-button", "iconCssClass"),
                new AbstractMap.SimpleEntry<String, String>("info-bar-sidenav-toggler-button", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("info-bar-sidenav-toggler-button", "iconCssClass"),
                new AbstractMap.SimpleEntry<String, String>("management-bar-button", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("management-bar-button", "iconCssClass"),
                new AbstractMap.SimpleEntry<String, String>("management-bar-sidenav-toggler-button", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("management-bar-sidenav-toggler-button", "iconCssClass"),
                new AbstractMap.SimpleEntry<String, String>("screen-navigation", "containerCssClass"),
                new AbstractMap.SimpleEntry<String, String>("screen-navigation", "containerWrapperCssClass"),
                new AbstractMap.SimpleEntry<String, String>("screen-navigation", "menubarCssClass"),
                new AbstractMap.SimpleEntry<String, String>("screen-navigation", "navCssClass"),
                new AbstractMap.SimpleEntry<String, String>("translation-manager", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("user-vertical-card", "cardCssClass"),
                new AbstractMap.SimpleEntry<String, String>("user-vertical-card", "checkboxCSSClass"),
                new AbstractMap.SimpleEntry<String, String>("user-vertical-card", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("vertical-card", "cardCssClass"),
                new AbstractMap.SimpleEntry<String, String>("vertical-card", "checkboxCSSClass"),
                new AbstractMap.SimpleEntry<String, String>("vertical-card", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("vertical-card", "imageCSSClass"),
                new AbstractMap.SimpleEntry<String, String>("vertical-card-small-image", "cssClass")
        ));

        TAGLIB_ATTRIBUTES_CSS.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_ITEM_SELECTOR, Arrays.asList(
                new AbstractMap.SimpleEntry<String, String>("image-selector", "draggableImage")
        ));

        TAGLIB_ATTRIBUTES_CSS.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_JOURNAL, Arrays.asList(
                new AbstractMap.SimpleEntry<>("journal-article", "wrapperCssClass"),
                new AbstractMap.SimpleEntry<>("journal-article-display", "wrapperCssClass")
        ));

        TAGLIB_ATTRIBUTES_CSS.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_STAGING, Arrays.asList(
                new AbstractMap.SimpleEntry<String, String>("menu", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("permissions", "descriptionCSSClass"),
                new AbstractMap.SimpleEntry<String, String>("permissions", "labelCSSClass"),
                new AbstractMap.SimpleEntry<String, String>("process-message-task-details", "linkClass"),
                new AbstractMap.SimpleEntry<String, String>("process-status", "linkClass"),
                new AbstractMap.SimpleEntry<String, String>("status", "cssClass")
        ));

        TAGLIB_ATTRIBUTES_CSS.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_UI, Arrays.asList(
                new AbstractMap.SimpleEntry<String, String>("alert", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("app-view-entry", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("app-view-entry", "iconCssClass"),
                new AbstractMap.SimpleEntry<String, String>("app-view-search-entry", "iconCssClass"),
                new AbstractMap.SimpleEntry<String, String>("empty-result-message", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("header", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("icon", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("icon", "iconCssClass"),
                new AbstractMap.SimpleEntry<String, String>("icon", "linkCssClass"),
                new AbstractMap.SimpleEntry<String, String>("icon-delete", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("icon-delete", "linkCssClass"),
                new AbstractMap.SimpleEntry<String, String>("icon-menu", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("icon-menu", "triggerCssClass"),
                new AbstractMap.SimpleEntry<String, String>("input-checkbox", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("input-date", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("input-editor", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("input-field", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("input-localized", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("input-move-boxes", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("input-repeat", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("input-resource", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("input-search", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("input-select", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("input-textarea", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("input-time", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("input-time-zone", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("my-sites", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("panel", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("panel", "iconCssClass"),
                new AbstractMap.SimpleEntry<String, String>("panel-container", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("search-container", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("search-container", "emptyResultsMessageCssClass"),
                new AbstractMap.SimpleEntry<String, String>("search-container-column-button", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("search-container-column-date", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("search-container-column-icon", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("search-container-column-image", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("search-container-column-jsp", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("search-container-column-status", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("search-container-column-text", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("search-container-column-user", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("search-container-row", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("search-iterator", "searchResultCssClass"),
                new AbstractMap.SimpleEntry<String, String>("tabs", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("user-display", "imageCssClass"),
                new AbstractMap.SimpleEntry<String, String>("user-display", "userIconCssClass"),
                new AbstractMap.SimpleEntry<String, String>("user-portrait", "cssClass"),
                new AbstractMap.SimpleEntry<String, String>("user-portrait", "imageCssClass")
        ));
    }

    //TAGLIB_ATTRIBUTES_CLASS_NAME
    static {
        TAGLIB_ATTRIBUTES_CLASS_NAME.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_ASSET, Arrays.asList(
            new AbstractMap.SimpleEntry<>("asset-categories-available", "className"),
            new AbstractMap.SimpleEntry<>("asset-categories-selector", "className"),
            new AbstractMap.SimpleEntry<>("asset-categories-summary", "className"),
            new AbstractMap.SimpleEntry<>("asset-display", "className"),
            new AbstractMap.SimpleEntry<>("asset-entry-usages", "className"),
            new AbstractMap.SimpleEntry<>("asset-links", "className"),
            new AbstractMap.SimpleEntry<>("asset-metadata", "className"),
            new AbstractMap.SimpleEntry<>("asset-tags-available", "className"),
            new AbstractMap.SimpleEntry<>("asset-tags-selector", "className"),
            new AbstractMap.SimpleEntry<>("asset-tags-summary", "className"),
            new AbstractMap.SimpleEntry<>("asset-view-usages", "className"),
            new AbstractMap.SimpleEntry<>("input-asset-links", "className")
        ));
        TAGLIB_ATTRIBUTES_CLASS_NAME.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_AUI, Arrays.asList(
            new AbstractMap.SimpleEntry<>("model-context", "model"),
            new AbstractMap.SimpleEntry<>("input", "model"),
            new AbstractMap.SimpleEntry<>("select", "model"),
            new AbstractMap.SimpleEntry<>("workflow-status", "model")
        ));
        TAGLIB_ATTRIBUTES_CLASS_NAME.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_COMMENT, Arrays.asList(
            new AbstractMap.SimpleEntry<>("discussion", "className")
        ));
        TAGLIB_ATTRIBUTES_CLASS_NAME.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_EXPANDO, Arrays.asList(
            new AbstractMap.SimpleEntry<>("custom-attribute", "className"),
            new AbstractMap.SimpleEntry<>("custom-attribute-list", "className"),
            new AbstractMap.SimpleEntry<>("custom-attributes-available", "className")
        ));
        TAGLIB_ATTRIBUTES_CLASS_NAME.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_EXPORT_IMPORT_CHANGESET, Arrays.asList(
            new AbstractMap.SimpleEntry<>("export-entity", "className"),
            new AbstractMap.SimpleEntry<>("publish-entity-menu-item", "className")
        ));
        TAGLIB_ATTRIBUTES_CLASS_NAME.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_FLAGS, Arrays.asList(
            new AbstractMap.SimpleEntry<>("flags", "className")
        ));
        TAGLIB_ATTRIBUTES_CLASS_NAME.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_PORTLET, Arrays.asList(
            new AbstractMap.SimpleEntry<>("runtime", "portletProviderClassName")
        ));
        TAGLIB_ATTRIBUTES_CLASS_NAME.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_SECURITY, Arrays.asList(
            new AbstractMap.SimpleEntry<>("permissionsURL", "modelResource")
        ));
        TAGLIB_ATTRIBUTES_CLASS_NAME.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_SHARING, Arrays.asList(
            new AbstractMap.SimpleEntry<>("button", "className")
        ));
        TAGLIB_ATTRIBUTES_CLASS_NAME.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_UI, Arrays.asList(
            new AbstractMap.SimpleEntry<>("app-view-entry", "assetCategoryClassName"),
            new AbstractMap.SimpleEntry<>("app-view-entry", "assetTagClassName"),
            new AbstractMap.SimpleEntry<>("asset-categories-available", "className"),
            new AbstractMap.SimpleEntry<>("asset-categories-selector", "className"),
            new AbstractMap.SimpleEntry<>("asset-categories-summary", "className"),
            new AbstractMap.SimpleEntry<>("asset-display", "className"),
            new AbstractMap.SimpleEntry<>("asset-links", "className"),
            new AbstractMap.SimpleEntry<>("asset-metadata", "className"),
            new AbstractMap.SimpleEntry<>("asset-tags-available", "className"),
            new AbstractMap.SimpleEntry<>("asset-tags-selector", "className"),
            new AbstractMap.SimpleEntry<>("asset-tags-summary", "className"),
            new AbstractMap.SimpleEntry<>("discussion", "className"),
            new AbstractMap.SimpleEntry<>("error", "exception"),  //extends Exception?
            new AbstractMap.SimpleEntry<>("input-asset-links", "className"),
            new AbstractMap.SimpleEntry<>("input-field", "model"),
            new AbstractMap.SimpleEntry<>("input-permissions", "modelName"),
            new AbstractMap.SimpleEntry<>("input-permissions-params", "modelName"),
            new AbstractMap.SimpleEntry<>("ratings", "className"),
            new AbstractMap.SimpleEntry<>("search-container-row", "className"),
            new AbstractMap.SimpleEntry<>("social-activities", "className")
        ));
    }

    //TAGLIB_SUGGESTED_PARENTS
    static {
        //param inside any of urls, etc.
        //validator inside any input field
        TAGLIB_SUGGESTED_PARENTS.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_AUI, Arrays.asList(
            new AbstractMap.SimpleImmutableEntry<>("fieldset", "fieldset-group"),
            new AbstractMap.SimpleImmutableEntry<>("input", "form"),
            new AbstractMap.SimpleImmutableEntry<>("input", LiferayTaglibs.TAGLIB_URI_LIFERAY_FRONTEND + "|edit-form"),
            new AbstractMap.SimpleImmutableEntry<>("nav-bar-search", "nav-bar"),
            new AbstractMap.SimpleImmutableEntry<>("nav-item", "nav-bar"),
            new AbstractMap.SimpleImmutableEntry<>("nav", "nav-bar")
        ));

        TAGLIB_SUGGESTED_PARENTS.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_AUI_OLD, TAGLIB_SUGGESTED_PARENTS.get(LiferayTaglibs.TAGLIB_URI_LIFERAY_AUI));

        TAGLIB_SUGGESTED_PARENTS.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_UI, Arrays.asList(
            new AbstractMap.SimpleImmutableEntry<>("organization-search-container-results", "search-container"),
            new AbstractMap.SimpleImmutableEntry<>("panel", "panel-container"),
            new AbstractMap.SimpleImmutableEntry<>("search-container-column-date", "search-container-row"),
            new AbstractMap.SimpleImmutableEntry<>("search-container-column-icon", "search-container-row"),
            new AbstractMap.SimpleImmutableEntry<>("search-container-column-image", "search-container-row"),
            new AbstractMap.SimpleImmutableEntry<>("search-container-column-jsp", "search-container-row"),
            new AbstractMap.SimpleImmutableEntry<>("search-container-column-status", "search-container-row"),
            new AbstractMap.SimpleImmutableEntry<>("search-container-column-text", "search-container-row"),
            new AbstractMap.SimpleImmutableEntry<>("search-container-column-user", "search-container-row"),
            new AbstractMap.SimpleImmutableEntry<>("search-form", "search-container"),
            new AbstractMap.SimpleImmutableEntry<>("section", "tabs"),
            new AbstractMap.SimpleImmutableEntry<>("user-group-search-container-results", "search-container"),
            new AbstractMap.SimpleImmutableEntry<>("user-search-container-results", "search-container")
        ));

        TAGLIB_SUGGESTED_PARENTS.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_PORTLET, Arrays.asList(
            new AbstractMap.SimpleImmutableEntry<>("param", LiferayTaglibs.TAGLIB_URI_LIFERAY_SOY + "|template-renderer"),
            new AbstractMap.SimpleImmutableEntry<>("param", LiferayTaglibs.TAGLIB_URI_LIFERAY_READING_TIME + "|reading-time"),
            new AbstractMap.SimpleImmutableEntry<>("param", LiferayTaglibs.TAGLIB_URI_LIFERAY_ADAPTIVE_MEDIA_IMAGE + "|img"),
            new AbstractMap.SimpleImmutableEntry<>("param", LiferayTaglibs.TAGLIB_URI_LIFERAY_UTIL + "|include"),
            new AbstractMap.SimpleImmutableEntry<>("param", LiferayTaglibs.TAGLIB_URI_LIFERAY_SOCIAL_BOOKMARKS + "|social-bookmarks"),
            new AbstractMap.SimpleImmutableEntry<>("param", "actionURL"),
            new AbstractMap.SimpleImmutableEntry<>("param", LiferayTaglibs.TAGLIB_URI_JAVAX_PORTLET_2_0 + "|actionURL"),
            new AbstractMap.SimpleImmutableEntry<>("param", LiferayTaglibs.TAGLIB_URI_JCP_PORTLET_APP_3_0 + "|actionURL"),
            new AbstractMap.SimpleImmutableEntry<>("param", "resourceURL"),
            new AbstractMap.SimpleImmutableEntry<>("param", LiferayTaglibs.TAGLIB_URI_JAVAX_PORTLET_2_0 + "|resourceURL"),
            new AbstractMap.SimpleImmutableEntry<>("param", LiferayTaglibs.TAGLIB_URI_JCP_PORTLET_APP_3_0 + "|resourceURL"),
            new AbstractMap.SimpleImmutableEntry<>("param", "renderURL"),
            new AbstractMap.SimpleImmutableEntry<>("param", LiferayTaglibs.TAGLIB_URI_JAVAX_PORTLET_2_0 + "|renderURL"),
            new AbstractMap.SimpleImmutableEntry<>("param", LiferayTaglibs.TAGLIB_URI_JCP_PORTLET_APP_3_0 + "|renderURL"),
            new AbstractMap.SimpleImmutableEntry<>("param", LiferayTaglibs.TAGLIB_URI_LIFERAY_UI + "|search-container-row")
        ));

        //c.tld -> when inside choose
    }

}
