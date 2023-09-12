package de.dm.intellij.liferay.util;

import de.dm.intellij.liferay.schema.LiferayDefinitionsResourceProvider;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.dm.intellij.liferay.util.LiferayTaglibs.TAGLIB_URI_LIFERAY_AUI;
import static de.dm.intellij.liferay.util.LiferayTaglibs.TAGLIB_URI_LIFERAY_CLAY;
import static de.dm.intellij.liferay.util.LiferayTaglibs.TAGLIB_URI_LIFERAY_FRONTEND;
import static de.dm.intellij.liferay.util.LiferayTaglibs.TAGLIB_URI_LIFERAY_PORTLET;
import static de.dm.intellij.liferay.util.LiferayTaglibs.TAGLIB_URI_LIFERAY_SOY;
import static de.dm.intellij.liferay.util.LiferayTaglibs.TAGLIB_URI_LIFERAY_THEME;
import static de.dm.intellij.liferay.util.LiferayTaglibs.TAGLIB_URI_LIFERAY_UI;
import static de.dm.intellij.liferay.util.LiferayTaglibs.TAGLIB_URI_LIFERAY_SECURITY;

public class LiferayTaglibAttributes {

	public record TaglibDeprecationAttributes(float majorLiferayVersion, String namespace, String localName,
											  String message, String ticket, String... attributes) {
	}

	public record TaglibDeprecationTags(float majorLiferayVersion, String namespace, String message, String ticket,
										String... localNames) {
	}

	public static Map<String, Collection<AbstractMap.SimpleEntry<String, String>>> TAGLIB_ATTRIBUTES_JAVASCRIPT = new HashMap<>();
	public static Map<String, Collection<AbstractMap.SimpleEntry<String, String>>> TAGLIB_ATTRIBUTES_RESOURCEBUNDLE = new HashMap<>();
	public static Map<String, Collection<AbstractMap.SimpleEntry<String, String>>> TAGLIB_ATTRIBUTES_CSS = new HashMap<>();
	public static Map<String, Collection<AbstractMap.SimpleEntry<String, String>>> TAGLIB_ATTRIBUTES_CLASS_NAME = new HashMap<>();
	public static Map<String, Collection<AbstractMap.SimpleImmutableEntry<String, String>>> TAGLIB_SUGGESTED_PARENTS = new HashMap<>();
	public static Map<String, Collection<AbstractMap.SimpleEntry<String, String>>> TAGLIB_JAVASCRIPT_FILES = new HashMap<>();

	public static TaglibDeprecationTags LPS_54620_PORTLET_ICON = new TaglibDeprecationTags(7.0f, TAGLIB_URI_LIFERAY_PORTLET, "Removed the Tags that start with portlet:icon-.", "LPS-54620",
			"icon-close", "icon-configuration", "icon-edit",
			"icon-edit-defaults", "icon-edit-guest", "icon-export-import",
			"icon-help", "icon-maximize", "icon-minimize",
			"icon-portlet-css", "icon-print", "icon-refresh",
			"icon-staging");

	public static TaglibDeprecationAttributes LPS_55886_APP_VIEW_SEARCH_ENTRY = new TaglibDeprecationAttributes(7.0f, TAGLIB_URI_LIFERAY_UI, "app-view-search-entry", "Removed mbMessages and fileEntryTuples Attributes from app-view-search-entry Tag.", "LPS-55886",
			"mbMessages", "fileEntryTuples");
	public static TaglibDeprecationTags LPS_62935_AUI_LAYOUT = new TaglibDeprecationTags(7.0f, TAGLIB_URI_LIFERAY_AUI, "Removed the aui:layout Tag with no direct Replacement.", "LPS-62935", "layout");
	public static TaglibDeprecationTags LPS_63101_ICON_BACK = new TaglibDeprecationTags(7.0f, TAGLIB_URI_LIFERAY_PORTLET, "Deprecated the liferay-portlet:icon-back Tag with no direct Replacement.", "LPS-63101", "icon-back");
	public static TaglibDeprecationTags LPS_63106_ENCRYPT = new TaglibDeprecationTags(7.0f, TAGLIB_URI_LIFERAY_SECURITY, "Deprecated the liferay-security:encrypt Tag with no direct Replacement.", "LPS-63106", "encrypt");
	public static TaglibDeprecationTags LPS_69321_JOURNAL_ARTICLE = new TaglibDeprecationTags(7.0f, TAGLIB_URI_LIFERAY_UI, "Removed the liferay-ui:journal-article Tag.", "LPS-69321", "journal-article");
	public static TaglibDeprecationTags LPS_70442_AUI_TOOL = new TaglibDeprecationTags(7.0f, TAGLIB_URI_LIFERAY_AUI, "Deprecated the aui:tool Tag with no direct Replacement.", "LPS-70422", "tool");
	public static TaglibDeprecationTags LPS_100146_CONTEXTUAL_SIDEBAR = new TaglibDeprecationTags(7.3f, TAGLIB_URI_LIFERAY_FRONTEND, "The `liferay-frontend:contextual-sidebar` tag was removed.", "LPS-100146", "contextual-sidebar");
	public static TaglibDeprecationTags LPS_106899_CARDS_TREEVIEW = new TaglibDeprecationTags(7.3f, TAGLIB_URI_LIFERAY_FRONTEND, "Removed liferay-frontend:cards-treeview Tag", "LPS-106899","cards-treeview");
	public static TaglibDeprecationAttributes LPS_125256_CLAY_ALERT = new TaglibDeprecationAttributes(7.4f, TAGLIB_URI_LIFERAY_CLAY, "alert", "The attributes closeable, componentId, contributorKey, data, defaultEventHandler, destroyOnHide, elementClasses, spritemap, style, type in `clay:alert` tag were removed.", "LPS-125256",
			"closeable", "componentId", "contributorKey", "data", "defaultEventHandler", "destroyOnHide", "elementClasses",
			"spritemap", "style", "type");
	public static TaglibDeprecationAttributes LPS_125256_CLAY_BADGE = new TaglibDeprecationAttributes(7.4f, TAGLIB_URI_LIFERAY_CLAY, "badge", "The attributes componentId, contributorKey, data, defaultEventHandler, elementClasses in `clay:badge` tag were removed.", "LPS-125256",
			"componentId", "contributorKey", "data", "defaultEventHandler", "elementClasses");
	public static TaglibDeprecationAttributes LPS_125256_CLAY_BUTTON = new TaglibDeprecationAttributes(7.4f, TAGLIB_URI_LIFERAY_CLAY, "button", "The attributes componentId, contributorKey, data, defaultEventHandler, disabled, elementClasses, iconAlignment, name, size, spritemap, style, title, type, value in `clay:button` tag were removed.", "LPS-125256",
			"componentId", "contributorKey", "data", "defaultEventHandler", "disabled", "elementClasses",
			"iconAlignment", "name", "size", "spritemap", "style", "title", "type", "value");
	public static TaglibDeprecationAttributes LPS_125256_CLAY_DROPDOWN_ACTIONS = new TaglibDeprecationAttributes(7.4f, TAGLIB_URI_LIFERAY_CLAY, "dropdown-actions", "The attributes buttonLabel, buttonStyle, buttonType, componentId, contributorKey, data, defaultEventHandler, elementClasses, expanded, itemsIconAlignment, searchable, showToggleIcon, spritemap, style, triggerCssClasses, triggerTitle, type in `clay:dropdown-actions` tag were removed.", "LPS-125256",
			"buttonLabel", "buttonStyle", "buttonType", "componentId", "contributorKey", "data", "defaultEventHandler",
			"elementClasses", "expanded", "itemsIconAlignment", "searchable", "showToggleIcon", "spritemap", "style",
			"triggerCssClasses", "triggerTitle", "type");
	public static TaglibDeprecationAttributes LPS_125256_CLAY_DROPDOWN_MENU = new TaglibDeprecationAttributes(7.4f, TAGLIB_URI_LIFERAY_CLAY, "dropdown-menu", "The attributes buttonLabel, buttonStyle, buttonType, componentId, contributorKey, data, defaultEventHandler, elementClasses, expanded, itemsIconAlignment, searchable, showToggleIcon, spritemap, style, triggerCssClasses, triggerTitle, type in `clay:dropdown-menu` tag were removed.", "LPS-125256",
			"buttonLabel", "buttonStyle", "buttonType", "componentId", "contributorKey", "data", "defaultEventHandler",
			"elementClasses", "expanded", "itemsIconAlignment", "searchable", "showToggleIcon", "spritemap", "style",
			"triggerCssClasses", "triggerTitle", "type");
	public static TaglibDeprecationAttributes LPS_125256_CLAY_ICON = new TaglibDeprecationAttributes(7.4f, TAGLIB_URI_LIFERAY_CLAY, "icon", "The attributes componentId, contributorKey, data, defaultEventHandler, elementClasses, monospaced, spritemap in `clay:icon` tag were removed.", "LPS-125256",
			"componentId", "contributorKey", "data", "defaultEventHandler", "elementClasses", "monospaced", "spritemap");
	public static TaglibDeprecationAttributes LPS_125256_CLAY_LABEL = new TaglibDeprecationAttributes(7.4f, TAGLIB_URI_LIFERAY_CLAY, "label", "The attributes closeable, componentId, contributorKey, data, defaultEventHandler, elementClasses, href, message, size, spritemap, style in `clay:label` tag were removed.", "LPS-125256",
			"closeable", "componentId", "contributorKey", "data", "defaultEventHandler", "elementClasses", "href",
			"message", "size", "spritemap", "style");
	public static TaglibDeprecationAttributes LPS_125256_CLAY_LINK = new TaglibDeprecationAttributes(7.4f, TAGLIB_URI_LIFERAY_CLAY, "link", "The attributes buttonStyle, componentId, contributorKey, data, defaultEventHandler, elementClasses, iconAlignment, spritemap, style, target, title in `clay:link` tag were removed.", "LPS-125256",
			"buttonStyle", "componentId", "contributorKey", "data", "defaultEventHandler", "elementClasses",
			"iconAlignment", "spritemap", "style", "target", "title");
	public static TaglibDeprecationAttributes LPS_125256_CLAY_NAVIGATION_BAR = new TaglibDeprecationAttributes(7.4f, TAGLIB_URI_LIFERAY_CLAY, "navigation-bar", "The attributes componentId, contributorKey, data, defaultEventHandler, elementClasses, spritemap in `clay:navigation-bar` tag were removed.", "LPS-125256",
			"componentId", "contributorKey", "data", "defaultEventHandler", "elementClasses", "spritemap");
	public static TaglibDeprecationAttributes LPS_125256_CLAY_PROGRESSBAR = new TaglibDeprecationAttributes(7.4f, TAGLIB_URI_LIFERAY_CLAY, "progressbar", "The attributes componentId, contributorKey, data, defaultEventHandler, elementClasses, spritemap in `clay:navigation-bar` tag were removed.", "LPS-125256",
			"componentId", "contributorKey", "data", "defaultEventHandler", "elementClasses", "spritemap");
	public static TaglibDeprecationAttributes LPS_125256_CLAY_RADIO = new TaglibDeprecationAttributes(7.4f, TAGLIB_URI_LIFERAY_CLAY, "radio", "The attributes componentId, contributorKey, data, defaultEventHandler, elementClasses in `clay:radio` tag were removed.", "LPS-125256",
			"componentId", "contributorKey", "data", "defaultEventHandler", "elementClasses");
	public static TaglibDeprecationAttributes LPS_125256_CLAY_STICKER = new TaglibDeprecationAttributes(7.4f, TAGLIB_URI_LIFERAY_CLAY, "sticker", "The attributes componentId, contributorKey, data, defaultEventHandler, elementClasses, spritemap in `clay:sticker` tag were removed.", "LPS-125256",
			"componentId", "contributorKey", "data", "defaultEventHandler", "elementClasses", "spritemap");
	public static TaglibDeprecationAttributes LPS_125256_CLAY_STRIPE = new TaglibDeprecationAttributes(7.4f, TAGLIB_URI_LIFERAY_CLAY, "stripe", "The attributes closeable, componentId, contributorKey, data, defaultEventHandler, destroyOnHide, elementClasses, spritemap, style, type in `clay:stripe` tag were removed.", "LPS-125256",
			"closeable", "componentId", "contributorKey", "data", "defaultEventHandler", "destroyOnHide", "elementClasses",
			"spritemap", "style", "type");

	public static TaglibDeprecationTags LPS_122966_LIFERAY_SOY = new TaglibDeprecationTags(7.4f, TAGLIB_URI_LIFERAY_SOY, "Deprecate soy:component and soy:template renderer tags", "LPS-122966",
			"template-renderer", "component-renderer");
	public static TaglibDeprecationTags LPS_112476_CLAY = new TaglibDeprecationTags(7.4f, TAGLIB_URI_LIFERAY_CLAY, "A series of deprecated and unused JSP tags have been removed and are no longer available", "LPS-112476",
			"table");
	public static TaglibDeprecationTags LPS_112476_LIFERAY_UI = new TaglibDeprecationTags(7.4f, TAGLIB_URI_LIFERAY_UI, "A series of deprecated and unused JSP tags have been removed and are no longer available", "LPS-112476",
			"alert", "input-scheduler",
			"organization-search-container-results", "organization-search-form", "ratings",
			"search-speed", "table-iterator", "toggle-area", "toggle",
			"user-search-container-results", "user-search-form");
	public static TaglibDeprecationTags LPS_112476_LIFERAY_THEME = new TaglibDeprecationTags(7.4f, TAGLIB_URI_LIFERAY_THEME, "A series of deprecated and unused JSP tags have been removed and are no longer available", "LPS-112476",
			"layout-icon", "param");
	public static TaglibDeprecationTags LPS_121732_FLASH = new TaglibDeprecationTags(7.4f, TAGLIB_URI_LIFERAY_UI, "The tag liferay-ui:flash has been deleted and is no longer available", "LPS-121732",
			"flash");
	public static TaglibDeprecationTags LPS_168309_LIFERAY_AUI = new TaglibDeprecationTags(7.4f, TAGLIB_URI_LIFERAY_AUI, "The tags `<aui:fieldset-group>` and `<liferay-frontend:fieldset-group>` added unnecessary markup to the page and caused accessibility issues, so they have been removed", "LPS-168309",
			"fieldset-group");
	public static TaglibDeprecationTags LPS_168309_LIFERAY_FRONTEND = new TaglibDeprecationTags(7.4f, TAGLIB_URI_LIFERAY_FRONTEND, "The tags `<aui:fieldset-group>` and `<liferay-frontend:fieldset-group>` added unnecessary markup to the page and caused accessibility issues, so they have been removed", "LPS-168309",
			"fieldset-group");
	public static TaglibDeprecationTags LPS_158461_LIFERAY_FRONTEND = new TaglibDeprecationTags(7.4f, TAGLIB_URI_LIFERAY_FRONTEND, "Obsolete frontend taglibs that don't follow lexicon patterns and have an obsolete markup have been removed", "LPS-158461",
			"add-menu",
			"add-menu-item",
			"info-bar",
			"info-bar-button",
			"horizontal-card",
			"horizontal-card-col",
			"horizontal-card-icon",
			"html-vertical-card",
			"icon-vertical-card",
			"image-card",
			"management-bar",
			"management-bar-action-buttons",
			"management-bar-button",
			"management-bar-buttons",
			"management-bar-display-buttons",
			"management-bar-filter",
			"management-bar-filter-item",
			"management-bar-filters",
			"management-bar-navigation",
			"management-bar-sidenav-toggler-button",
			"management-bar-sort",
			"user-vertical-card",
			"vertical-card",
			"vertical-card-footer",
			"vertical-card-header",
			"vertical-card-small-image",
			"vertical-card-sticker-bottom");
	public static TaglibDeprecationTags LPS_166546_LIFERAY_AUI = new TaglibDeprecationTags(7.4f, TAGLIB_URI_LIFERAY_AUI, "The tags <aui:component>, <aui:spacer> and <aui:panel> are obsolete and have been removed", "LPS-166546",
			"component", "spacer", "panel");
	public static TaglibDeprecationTags LPS_60753_ASSET_CATEGORIES_NAVIGATION = new TaglibDeprecationTags(7.0f, TAGLIB_URI_LIFERAY_UI, "Removed the liferay-ui:asset-categories-navigation Tag and replaced with liferay-asset:asset-categories-navigation.", "LPS-60753",
			"asset-categories-navigation");
	public static TaglibDeprecationTags LPS_62922_BUTTON_ITEM = new TaglibDeprecationTags(7.0f, TAGLIB_URI_LIFERAY_AUI, "Removed the aui:button-item Tag and replaced with aui:button.", "LPS-62922",
			"button-item");
	public static TaglibDeprecationTags LPS_62208_AUI_COLUMN = new TaglibDeprecationTags(7.0f, TAGLIB_URI_LIFERAY_AUI, "The aui:column taglib has been removed and replaced with aui:col taglib.", "LPS-62208",
			"column");
	public static TaglibDeprecationTags LPS_69400_LIFERAY_EXPANDO = new TaglibDeprecationTags(7.0f, TAGLIB_URI_LIFERAY_UI, "Moved the Expando Custom Field Tags to liferay-expando Taglib.", "LPS-69400",
			"custom-attribute", "custom-attribute-list", "custom-attributes-available");
	public static TaglibDeprecationTags LPS_69383_CAPTCHA = new TaglibDeprecationTags(7.0f, TAGLIB_URI_LIFERAY_UI, "Deprecated the liferay-ui:captcha Tag and replaced with liferay-captcha:captcha.", "LPS-69383",
			"captcha");
	public static TaglibDeprecationTags LPS_60779_LIFERAY_TRASH = new TaglibDeprecationTags(7.0f, TAGLIB_URI_LIFERAY_UI, "Removed the liferay-ui:trash-empty Tag and replaced with liferay-trash:empty.", "LPS-60779",
			"trash-empty", "trash-undo");
	public static TaglibDeprecationTags LPS_60967_FLAGS = new TaglibDeprecationTags(7.0f, TAGLIB_URI_LIFERAY_UI, "Deprecated the liferay-ui:flags Tag and replaced with liferay-flags:flags.", "LPS-60967",
			"flags");
	public static TaglibDeprecationTags LPS_60328_NAVIGATION = new TaglibDeprecationTags(7.0f, TAGLIB_URI_LIFERAY_UI, "Removed the liferay-ui:navigation Tag and replaced with liferay-site-navigation:navigation Tag.", "LPS-60328",
			"navigation");
	public static TaglibDeprecationAttributes LPS_125256_CLAY_BADGE_STYLE = new TaglibDeprecationAttributes(7.4f, TAGLIB_URI_LIFERAY_CLAY, "badge", "The attribute style in `clay:badge` tag was replaced by displayType.", "LPS-125256",
			"style");
	public static TaglibDeprecationAttributes LPS_125256_CLAY_BUTTON_ARIA_LABEL = new TaglibDeprecationAttributes(7.4f, TAGLIB_URI_LIFERAY_CLAY, "button", "The attribute ariaLabel in `clay:button` tag was removed, use aria-label instead", "LPS-125256",
			"ariaLabel");
	public static TaglibDeprecationAttributes LPS_125256_CLAY_LINK_ARIA_LABEL = new TaglibDeprecationAttributes(7.4f, TAGLIB_URI_LIFERAY_CLAY, "link", "The attribute ariaLabel `clay:link` tag has been replaced by aria-label.", "LPS-125256",
			"ariaLabel");
	public static TaglibDeprecationAttributes LPS_125256_CLAY_STICKER_STYLE = new TaglibDeprecationAttributes(7.4f, TAGLIB_URI_LIFERAY_CLAY, "sticker", "The attribute style in `clay:sticker` tag was replaced by displayType.", "LPS-125256",
			"style");
	public static TaglibDeprecationTags LPS_166546_AUI_CONTAINER = new TaglibDeprecationTags(7.4f, TAGLIB_URI_LIFERAY_AUI, "The tags <aui:container> is deprecated, use <clay:container> instead", "LPS-166546",
			"container");
	/*
		public static TaglibDeprecationTags LPS_ = new TaglibDeprecationTags();

		public static TaglibDeprecationAttributes LPS_ = new TaglibDeprecationAttributes();
	 */

	//TAGLIB_ATTRIBUTES_JAVASCRIPT
	static {
		TAGLIB_ATTRIBUTES_JAVASCRIPT.put(TAGLIB_URI_LIFERAY_AUI, Arrays.asList(
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

		TAGLIB_ATTRIBUTES_JAVASCRIPT.put(TAGLIB_URI_LIFERAY_FRONTEND, Arrays.asList(
				new AbstractMap.SimpleEntry<String, String>("edit-form", "onSubmit"),
				new AbstractMap.SimpleEntry<String, String>("html-vertical-card", "onClick"),
				new AbstractMap.SimpleEntry<String, String>("icon-vertical-card", "onClick"),
				new AbstractMap.SimpleEntry<String, String>("vertical-card", "onClick")
		));

		TAGLIB_ATTRIBUTES_JAVASCRIPT.put(TAGLIB_URI_LIFERAY_UI, Arrays.asList(
				new AbstractMap.SimpleEntry<String, String>("icon", "onClick"),
				new AbstractMap.SimpleEntry<String, String>("input-checkbox", "onClick"),
				new AbstractMap.SimpleEntry<String, String>("input-move-boxes", "leftOnChange"),
				new AbstractMap.SimpleEntry<String, String>("input-move-boxes", "rightOnChange"),
				new AbstractMap.SimpleEntry<String, String>("page-iterator", "jsCall"),
				new AbstractMap.SimpleEntry<String, String>("quick-access-entry", "onClick"),
				new AbstractMap.SimpleEntry<String, String>("tabs", "onClick")
		));

		TAGLIB_ATTRIBUTES_JAVASCRIPT.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_AUI_OLD, LiferayTaglibAttributes.TAGLIB_ATTRIBUTES_JAVASCRIPT.get(TAGLIB_URI_LIFERAY_AUI));

		TAGLIB_ATTRIBUTES_JAVASCRIPT.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_6_1_0, List.of(
				new AbstractMap.SimpleEntry<String, String>("setting", "")
		));

		TAGLIB_ATTRIBUTES_JAVASCRIPT.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_6_2_0, TAGLIB_ATTRIBUTES_JAVASCRIPT.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_6_1_0));
		TAGLIB_ATTRIBUTES_JAVASCRIPT.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_7_0_0, TAGLIB_ATTRIBUTES_JAVASCRIPT.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_6_1_0));
		TAGLIB_ATTRIBUTES_JAVASCRIPT.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_7_1_0, TAGLIB_ATTRIBUTES_JAVASCRIPT.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_6_1_0));
		TAGLIB_ATTRIBUTES_JAVASCRIPT.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_7_2_0, TAGLIB_ATTRIBUTES_JAVASCRIPT.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_6_1_0));
		TAGLIB_ATTRIBUTES_JAVASCRIPT.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_7_3_0, TAGLIB_ATTRIBUTES_JAVASCRIPT.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_6_1_0));
		TAGLIB_ATTRIBUTES_JAVASCRIPT.put(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_7_4_0, TAGLIB_ATTRIBUTES_JAVASCRIPT.get(LiferayDefinitionsResourceProvider.XML_NAMESPACE_LIFERAY_LOOK_AND_FEEL_6_1_0));

	}

	//TAGLIB_ATTRIBUTES_RESOURCEBUNDLE
	static {
		TAGLIB_ATTRIBUTES_RESOURCEBUNDLE.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_ASSET, Arrays.asList(
				new AbstractMap.SimpleEntry<String, String>("asset-addon-entry-selector", "title"),
				new AbstractMap.SimpleEntry<String, String>("asset-metadata", "metadataField"),
				new AbstractMap.SimpleEntry<String, String>("asset-tags-summary", "message")
		));

		TAGLIB_ATTRIBUTES_RESOURCEBUNDLE.put(TAGLIB_URI_LIFERAY_AUI, Arrays.asList(
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
		TAGLIB_ATTRIBUTES_RESOURCEBUNDLE.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_AUI_OLD, LiferayTaglibAttributes.TAGLIB_ATTRIBUTES_RESOURCEBUNDLE.get(TAGLIB_URI_LIFERAY_AUI));

		TAGLIB_ATTRIBUTES_RESOURCEBUNDLE.put(TAGLIB_URI_LIFERAY_CLAY, Arrays.asList(
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

		TAGLIB_ATTRIBUTES_RESOURCEBUNDLE.put(TAGLIB_URI_LIFERAY_FRONTEND, Arrays.asList(
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

		TAGLIB_ATTRIBUTES_RESOURCEBUNDLE.put(TAGLIB_URI_LIFERAY_UI, Arrays.asList(
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

		TAGLIB_ATTRIBUTES_CSS.put(TAGLIB_URI_LIFERAY_AUI, Arrays.asList(
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

		TAGLIB_ATTRIBUTES_CSS.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_AUI_OLD, TAGLIB_ATTRIBUTES_CSS.get(TAGLIB_URI_LIFERAY_AUI));

		TAGLIB_ATTRIBUTES_CSS.put(TAGLIB_URI_LIFERAY_CLAY, Arrays.asList(
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
		TAGLIB_ATTRIBUTES_CSS.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_COMMERCE, Arrays.asList(
				new AbstractMap.SimpleEntry<>("order-transitions", "cssClass")
		));
		TAGLIB_ATTRIBUTES_CSS.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_COMMERCE_UI, Arrays.asList(
				new AbstractMap.SimpleEntry<>("header", "cssClasses"),
				new AbstractMap.SimpleEntry<>("header", "wrapperCssClasses"),
				new AbstractMap.SimpleEntry<>("modal-content", "contentCssClasses"),
				new AbstractMap.SimpleEntry<>("panel", "bodyClasses"),
				new AbstractMap.SimpleEntry<>("panel", "elementClasses")
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

		TAGLIB_ATTRIBUTES_CSS.put(TAGLIB_URI_LIFERAY_FRONTEND, Arrays.asList(
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

		TAGLIB_ATTRIBUTES_CSS.put(TAGLIB_URI_LIFERAY_UI, Arrays.asList(
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
		TAGLIB_ATTRIBUTES_CLASS_NAME.put(TAGLIB_URI_LIFERAY_AUI, Arrays.asList(
				new AbstractMap.SimpleEntry<>("model-context", "model"),
				new AbstractMap.SimpleEntry<>("input", "model"),
				new AbstractMap.SimpleEntry<>("select", "model"),
				new AbstractMap.SimpleEntry<>("workflow-status", "model")
		));
		TAGLIB_ATTRIBUTES_CLASS_NAME.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_COMMENT, Arrays.asList(
				new AbstractMap.SimpleEntry<>("discussion", "className")
		));
		TAGLIB_ATTRIBUTES_CLASS_NAME.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_DDM, Arrays.asList(
				new AbstractMap.SimpleEntry<>("template-renderer", "className"),
				new AbstractMap.SimpleEntry<>("template-selector", "className")
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
		TAGLIB_ATTRIBUTES_CLASS_NAME.put(TAGLIB_URI_LIFERAY_PORTLET, Arrays.asList(
				new AbstractMap.SimpleEntry<>("runtime", "portletProviderClassName")
		));
		TAGLIB_ATTRIBUTES_CLASS_NAME.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_RATINGS, Arrays.asList(
				new AbstractMap.SimpleEntry<>("ratings", "className")
		));
		TAGLIB_ATTRIBUTES_CLASS_NAME.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_SECURITY, Arrays.asList(
				new AbstractMap.SimpleEntry<>("permissionsURL", "modelResource")
		));
		TAGLIB_ATTRIBUTES_CLASS_NAME.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_SHARING, Arrays.asList(
				new AbstractMap.SimpleEntry<>("button", "className")
		));
		TAGLIB_ATTRIBUTES_CLASS_NAME.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_TEMPLATE, Arrays.asList(
				new AbstractMap.SimpleEntry<>("template-selector", "className")
		));
		TAGLIB_ATTRIBUTES_CLASS_NAME.put(TAGLIB_URI_LIFERAY_UI, Arrays.asList(
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
		TAGLIB_SUGGESTED_PARENTS.put(TAGLIB_URI_LIFERAY_AUI, Arrays.asList(
				new AbstractMap.SimpleImmutableEntry<>("input", "form"),
				new AbstractMap.SimpleImmutableEntry<>("input", TAGLIB_URI_LIFERAY_FRONTEND + "|edit-form"),
				new AbstractMap.SimpleImmutableEntry<>("nav-bar-search", "nav-bar"),
				new AbstractMap.SimpleImmutableEntry<>("nav-item", "nav-bar"),
				new AbstractMap.SimpleImmutableEntry<>("nav", "nav-bar")
		));

		TAGLIB_SUGGESTED_PARENTS.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_AUI_OLD, TAGLIB_SUGGESTED_PARENTS.get(TAGLIB_URI_LIFERAY_AUI));

		TAGLIB_SUGGESTED_PARENTS.put(TAGLIB_URI_LIFERAY_UI, Arrays.asList(
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

		TAGLIB_SUGGESTED_PARENTS.put(TAGLIB_URI_LIFERAY_PORTLET, Arrays.asList(
				new AbstractMap.SimpleImmutableEntry<>("param", TAGLIB_URI_LIFERAY_SOY + "|template-renderer"),
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
				new AbstractMap.SimpleImmutableEntry<>("param", TAGLIB_URI_LIFERAY_UI + "|search-container-row")
		));

		//c.tld -> when inside choose
	}

	static {
		TAGLIB_JAVASCRIPT_FILES.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_REACT, Arrays.asList(
				new AbstractMap.SimpleEntry<>("component", "module")
		));
		TAGLIB_JAVASCRIPT_FILES.put(TAGLIB_URI_LIFERAY_SOY, Arrays.asList(
				new AbstractMap.SimpleEntry<>("template-renderer", "module")
		));
		TAGLIB_JAVASCRIPT_FILES.put(TAGLIB_URI_LIFERAY_FRONTEND, Arrays.asList(
				new AbstractMap.SimpleEntry<>("component", "module"),
				new AbstractMap.SimpleEntry<>("empty-result-message", "buttonPropsTransformer"),
				new AbstractMap.SimpleEntry<>("empty-result-message", "defaultEventHandler"),
				new AbstractMap.SimpleEntry<>("empty-result-message", "propsTransformer")
		));
		TAGLIB_JAVASCRIPT_FILES.put(TAGLIB_URI_LIFERAY_CLAY, Arrays.asList(
				new AbstractMap.SimpleEntry<>("button", "propsTransformer"),
				new AbstractMap.SimpleEntry<>("checkbox", "defaultEventHandler"),
				new AbstractMap.SimpleEntry<>("dropdown-actions", "propsTransformer"),
				new AbstractMap.SimpleEntry<>("dropdown-menu", "propsTransformer"),
				new AbstractMap.SimpleEntry<>("file-card", "defaultEventHandler"),
				new AbstractMap.SimpleEntry<>("file-card", "propsTransformer"),
				new AbstractMap.SimpleEntry<>("horizontal-card", "defaultEventHandler"),
				new AbstractMap.SimpleEntry<>("horizontal-card", "propsTransformer"),
				new AbstractMap.SimpleEntry<>("image-card", "defaultEventHandler"),
				new AbstractMap.SimpleEntry<>("image-card", "propsTransformer"),
				new AbstractMap.SimpleEntry<>("link", "propsTransformer"),
				new AbstractMap.SimpleEntry<>("management-toolbar", "defaultEventHandler"),
				new AbstractMap.SimpleEntry<>("management-toolbar", "propsTransformer"),
				new AbstractMap.SimpleEntry<>("multiselect", "propsTransformer"),
				new AbstractMap.SimpleEntry<>("navigation-card", "propsTransformer"),
				new AbstractMap.SimpleEntry<>("pagination-bar", "propsTransformer"),
				new AbstractMap.SimpleEntry<>("select", "defaultEventHandler"),
				new AbstractMap.SimpleEntry<>("select", "propsTransformer"),
				new AbstractMap.SimpleEntry<>("user-card", "defaultEventHandler"),
				new AbstractMap.SimpleEntry<>("user-card", "propsTransformer"),
				new AbstractMap.SimpleEntry<>("vertical-card", "defaultEventHandler"),
				new AbstractMap.SimpleEntry<>("vertical-card", "propsTransformer")
		));
		TAGLIB_JAVASCRIPT_FILES.put(LiferayTaglibs.TAGLIB_URI_LIFERAY_FRONTEND_DATA_SET, Arrays.asList(
				new AbstractMap.SimpleEntry<>("frontend-data-set", "propsTransformer"),
				new AbstractMap.SimpleEntry<>("headless-display", "propsTransformer")
		));

	}


}
