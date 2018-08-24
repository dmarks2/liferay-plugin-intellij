package de.dm.intellij.liferay.util;

import javafx.util.Pair;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class LiferayTaglibAttributes {

    public static Map<String, Collection<Pair<String, String>>> TAGLIB_ATTRIBUTES_JAVASCRIPT = new HashMap<String, Collection<Pair<String, String>>>();
    public static Map<String, Collection<Pair<String, String>>> TAGLIB_ATTRIBUTES_RESOURCEBUNDLE = new HashMap<String, Collection<Pair<String, String>>>();

    //TAGLIB_ATTRIBUTES_JAVASCRIPT
    static {
        TAGLIB_ATTRIBUTES_JAVASCRIPT.put(LiferayTaglibs.TAGLIB_URI_AUI, Arrays.asList(
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

        TAGLIB_ATTRIBUTES_JAVASCRIPT.put(LiferayTaglibs.TAGLIB_URI_AUI_OLD, LiferayTaglibAttributes.TAGLIB_ATTRIBUTES_JAVASCRIPT.get(LiferayTaglibs.TAGLIB_URI_AUI));
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

        TAGLIB_ATTRIBUTES_RESOURCEBUNDLE.put(LiferayTaglibs.TAGLIB_URI_AUI, Arrays.asList(
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
        TAGLIB_ATTRIBUTES_RESOURCEBUNDLE.put(LiferayTaglibs.TAGLIB_URI_AUI_OLD, LiferayTaglibAttributes.TAGLIB_ATTRIBUTES_RESOURCEBUNDLE.get(LiferayTaglibs.TAGLIB_URI_AUI));

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

    }



}
