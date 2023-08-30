package de.dm.intellij.liferay.language.jsp;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.codeInspection.XmlSuppressableInspectionTool;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.XmlElementVisitor;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlTag;
import de.dm.intellij.liferay.util.LiferayInspectionsGroupNames;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static de.dm.intellij.liferay.language.jsp.LiferayTaglibDeprecationInfoHolder.createAttributes;
import static de.dm.intellij.liferay.language.jsp.LiferayTaglibDeprecationInfoHolder.createTags;
import static de.dm.intellij.liferay.util.LiferayTaglibs.TAGLIB_URI_LIFERAY_AUI;
import static de.dm.intellij.liferay.util.LiferayTaglibs.TAGLIB_URI_LIFERAY_CLAY;
import static de.dm.intellij.liferay.util.LiferayTaglibs.TAGLIB_URI_LIFERAY_FRONTEND;
import static de.dm.intellij.liferay.util.LiferayTaglibs.TAGLIB_URI_LIFERAY_PORTLET;
import static de.dm.intellij.liferay.util.LiferayTaglibs.TAGLIB_URI_LIFERAY_SECURITY;
import static de.dm.intellij.liferay.util.LiferayTaglibs.TAGLIB_URI_LIFERAY_SOY;
import static de.dm.intellij.liferay.util.LiferayTaglibs.TAGLIB_URI_LIFERAY_THEME;
import static de.dm.intellij.liferay.util.LiferayTaglibs.TAGLIB_URI_LIFERAY_UI;

public class LiferayTaglibDeprecationInspection extends XmlSuppressableInspectionTool {

	private static final List<LiferayTaglibDeprecationInfoHolder> TAGLIB_DEPRECATIONS = new ArrayList<>();

	static {
		TAGLIB_DEPRECATIONS.addAll(
				createAttributes(7.0f, TAGLIB_URI_LIFERAY_UI, "app-view-search-entry", "Removed mbMessages and fileEntryTuples Attributes from app-view-search-entry Tag.", "LPS-55886",
						"mbMessages", "fileEntryTuples"
				)
		);
		TAGLIB_DEPRECATIONS.addAll(
				createTags(7.0f, TAGLIB_URI_LIFERAY_UI, "Removed the liferay-ui:asset-categories-navigation Tag and Replaced with liferay-asset:asset-categories-navigation.", "LPS-60753",
						"asset-categories-navigation"
				)
		);//TODO add quickfix rename namespace
		TAGLIB_DEPRECATIONS.addAll(
				createTags(7.0f, TAGLIB_URI_LIFERAY_AUI, "Removed the aui:button-item Tag and Replaced with aui:button.", "LPS-62922",
						"button-item"
				)
		);//TODO add quickfix rename tag
		TAGLIB_DEPRECATIONS.addAll(
				createTags(7.0f, TAGLIB_URI_LIFERAY_AUI, "The aui:column taglib has been removed and replaced with aui:col taglib.", "LPS-62208",
						"column"
				)
		);//TODO add quickfix rename tag
		TAGLIB_DEPRECATIONS.addAll(
				createTags(7.0f, TAGLIB_URI_LIFERAY_AUI, "Deprecated the aui:tool Tag with No Direct Replacement.", "LPS-70422",
						"tool"
				)
		);
		TAGLIB_DEPRECATIONS.addAll(
				createTags(7.0f, TAGLIB_URI_LIFERAY_UI, "Moved the Expando Custom Field Tags to liferay-expando Taglib.", "LPS-69400",
						"custom-attribute", "custom-attribute-list", "custom-attributes-available"
				)
		);//TODO add quickfix rename namespace
		TAGLIB_DEPRECATIONS.addAll(
				createTags(7.0f, TAGLIB_URI_LIFERAY_PORTLET, "Deprecated the liferay-portlet:icon-back Tag with No Direct Replacement.", "LPS-63101",
						"icon-back"
				)
		);
		TAGLIB_DEPRECATIONS.addAll(
				createTags(7.0f, TAGLIB_URI_LIFERAY_SECURITY, "Deprecated the liferay-security:encrypt Tag with No Direct Replacement.", "LPS-63106",
						"encrypt"
				)
		);
		TAGLIB_DEPRECATIONS.addAll(
				createTags(7.0f, TAGLIB_URI_LIFERAY_UI, "Deprecated the liferay-ui:captcha Tag and Replaced with liferay-captcha:captcha.", "LPS-69383",
						"captcha"
				)
		);//TODO add quickfix rename namespace
		TAGLIB_DEPRECATIONS.addAll(
				createTags(7.0f, TAGLIB_URI_LIFERAY_UI, "Removed the liferay-ui:trash-empty Tag and Replaced with liferay-trash:empty.", "LPS-60779",
						"trash-empty"
				)
		);//TODO add quickfix rename namespace
		TAGLIB_DEPRECATIONS.addAll(
				createTags(7.0f, TAGLIB_URI_LIFERAY_UI, "Removed the liferay-ui:trash-undo Tag and Replaced with liferay-trash:undo.", "LPS-60779",
						"trash-undo"
				)
		);//TODO add quickfix rename namespace
		TAGLIB_DEPRECATIONS.addAll(
				createTags(7.0f, TAGLIB_URI_LIFERAY_UI, "Removed the liferay-ui:journal-article Tag.", "LPS-69321",
						"journal-article"
				)
		);
		TAGLIB_DEPRECATIONS.addAll(
				createTags(7.0f, TAGLIB_URI_LIFERAY_UI, "Deprecated the liferay-ui:flags Tag and Replaced with liferay-flags:flags.", "LPS-60967",
						"flags"
				)
		);//TODO add quickfix rename namespace
		TAGLIB_DEPRECATIONS.addAll(
				createTags(7.0f, TAGLIB_URI_LIFERAY_UI, "Removed the liferay-ui:navigation Tag and Replaced with liferay-site-navigation:navigation Tag.", "LPS-60328",
						"navigation"
				)
		);//TODO add quickfix rename namespace
		TAGLIB_DEPRECATIONS.addAll(
				createTags(7.0f, TAGLIB_URI_LIFERAY_PORTLET, "Removed the Tags that Start with portlet:icon-.", "LPS-54620",
						"icon-close", "icon-configuration", "icon-edit",
						"icon-edit-defaults", "icon-edit-guest", "icon-export-import",
						"icon-help", "icon-maximize", "icon-minimize",
						"icon-portlet-css", "icon-print", "icon-refresh",
						"icon-staging"
				)
		);
		TAGLIB_DEPRECATIONS.addAll(
				createTags(7.0f, TAGLIB_URI_LIFERAY_AUI, "Removed the aui:layout Tag with No Direct Replacement.", "LPS-62935",
						"layout"
				)
		);

		TAGLIB_DEPRECATIONS.addAll(
				createTags(7.3f, TAGLIB_URI_LIFERAY_FRONTEND, "Removed liferay-frontend:cards-treeview Tag", "LPS-106899",
						"cards-treeview"
				)
		);
		TAGLIB_DEPRECATIONS.addAll(
				createTags(7.3f, TAGLIB_URI_LIFERAY_FRONTEND, "The `liferay-frontend:contextual-sidebar` tag was removed.", "LPS-100146",
						"contextual-sidebar"
				)
		);
		TAGLIB_DEPRECATIONS.addAll(
				createAttributes(7.4f, TAGLIB_URI_LIFERAY_CLAY, "alert", "The attributes closeable, componentId, contributorKey, data, defaultEventHandler, destroyOnHide, elementClasses, spritemap, style, type in `clay:alert` tag was removed.", "LPS-125256",
						"closeable", "componentId", "contributorKey", "data", "defaultEventHandler", "destroyOnHide", "elementClasses",
						"spritemap", "style", "type"
				)
		);
		TAGLIB_DEPRECATIONS.addAll(
				createAttributes(7.4f, TAGLIB_URI_LIFERAY_CLAY, "badge", "The attributes componentId, contributorKey, data, defaultEventHandler, elementClasses, style in `clay:badge` tag was replaced by displayType.", "LPS-125256",
						"componentId", "contributorKey", "data", "defaultEventHandler", "elementClasses", "style"
				)
		);
		TAGLIB_DEPRECATIONS.addAll(
				createAttributes(7.4f, TAGLIB_URI_LIFERAY_CLAY, "button", "The attributes ariaLabel, componentId, contributorKey, data, defaultEventHandler, disabled, elementClasses, iconAlignment, name, size, spritemap, style, title, type, value in `clay:button` tag was removed.", "LPS-125256",
						"ariaLabel", "componentId", "contributorKey", "data", "defaultEventHandler", "disabled", "elementClasses",
						"iconAlignment", "name", "size", "spritemap", "style", "title", "type", "value"
				)
		);//TODO add quickfix rename ariaLabel to aria-label

		TAGLIB_DEPRECATIONS.addAll(
				createAttributes(7.4f, TAGLIB_URI_LIFERAY_CLAY, "dropdown-actions", "The attributes buttonLabel, buttonStyle, buttonType, componentId, contributorKey, data, defaultEventHandler, elementClasses, expanded, itemsIconAlignment, searchable, showToggleIcon, spritemap, style, triggerCssClasses, triggerTitle, type in `clay:dropdown-actions` tag was removed.", "LPS-125256",
						"buttonLabel", "buttonStyle", "buttonType", "componentId", "contributorKey", "data", "defaultEventHandler",
						"elementClasses", "expanded", "itemsIconAlignment", "searchable", "showToggleIcon", "spritemap", "style",
						"triggerCssClasses", "triggerTitle", "type"
				)
		);
		TAGLIB_DEPRECATIONS.addAll(
				createAttributes(7.4f, TAGLIB_URI_LIFERAY_CLAY, "dropdown-menu", "The attributes buttonLabel, buttonStyle, buttonType, componentId, contributorKey, data, defaultEventHandler, elementClasses, expanded, itemsIconAlignment, searchable, showToggleIcon, spritemap, style, triggerCssClasses, triggerTitle, type in `clay:dropdown-menu` tag was removed.", "LPS-125256",
						"buttonLabel", "buttonStyle", "buttonType", "componentId", "contributorKey", "data", "defaultEventHandler",
						"elementClasses", "expanded", "itemsIconAlignment", "searchable", "showToggleIcon", "spritemap", "style",
						"triggerCssClasses", "triggerTitle", "type"
				)
		);
		TAGLIB_DEPRECATIONS.addAll(
				createAttributes(7.4f, TAGLIB_URI_LIFERAY_CLAY, "icon", "The attributes componentId, contributorKey, data, defaultEventHandler, elementClasses, monospaced, spritemap in `clay:icon` tag was removed.", "LPS-125256",
						"componentId", "contributorKey", "data", "defaultEventHandler", "elementClasses", "monospaced", "spritemap"
				)
		);
		TAGLIB_DEPRECATIONS.addAll(
				createAttributes(7.4f, TAGLIB_URI_LIFERAY_CLAY, "label", "The attributes closeable, componentId, contributorKey, data, defaultEventHandler, elementClasses, href, message, size, spritemap, style in `clay:label` tag was removed.", "LPS-125256",
						"closeable", "componentId", "contributorKey", "data", "defaultEventHandler", "elementClasses", "href",
						"message", "size", "spritemap", "style"
				)
		);
		TAGLIB_DEPRECATIONS.addAll(
				createAttributes(7.4f, TAGLIB_URI_LIFERAY_CLAY, "link", "The attributes ariaLabel, buttonStyle, componentId, contributorKey, data, defaultEventHandler, elementClasses, iconAlignment, spritemap, style, target, title in `clay:link` tag was replaced by aria-label.", "LPS-125256",
						"ariaLabel", "buttonStyle", "componentId", "contributorKey", "data", "defaultEventHandler", "elementClasses",
						"iconAlignment", "spritemap", "style", "target", "title"
				)
		);
		TAGLIB_DEPRECATIONS.addAll(
				createAttributes(7.4f, TAGLIB_URI_LIFERAY_CLAY, "navigation-bar", "The attributes componentId, contributorKey, data, defaultEventHandler, elementClasses, spritemap in `clay:navigation-bar` tag was removed.", "LPS-125256",
						"componentId", "contributorKey", "data", "defaultEventHandler", "elementClasses", "spritemap"
				)
		);
		TAGLIB_DEPRECATIONS.addAll(
				createAttributes(7.4f, TAGLIB_URI_LIFERAY_CLAY, "progressbar", "The attributes componentId, contributorKey, data, defaultEventHandler, elementClasses, spritemap in `clay:navigation-bar` tag was removed.", "LPS-125256",
						"componentId", "contributorKey", "data", "defaultEventHandler", "elementClasses", "spritemap"
				)
		);
		TAGLIB_DEPRECATIONS.addAll(
				createAttributes(7.4f, TAGLIB_URI_LIFERAY_CLAY, "radio", "The attributes componentId, contributorKey, data, defaultEventHandler, elementClasses in `clay:radio` tag was removed.", "LPS-125256",
						"componentId", "contributorKey", "data", "defaultEventHandler", "elementClasses"
				)
		);
		TAGLIB_DEPRECATIONS.addAll(
				createAttributes(7.4f, TAGLIB_URI_LIFERAY_CLAY, "sticker", "The attributes componentId, contributorKey, data, defaultEventHandler, elementClasses, spritemap, style in `clay:sticker` tag was replaced by displayType.", "LPS-125256",
						"componentId", "contributorKey", "data", "defaultEventHandler", "elementClasses", "spritemap", "style"
				)
		);
		TAGLIB_DEPRECATIONS.addAll(
				createAttributes(7.4f, TAGLIB_URI_LIFERAY_CLAY, "stripe", "The attributes closeable, componentId, contributorKey, data, defaultEventHandler, destroyOnHide, elementClasses, spritemap, style, type in `clay:stripe` tag was removed.", "LPS-125256",
						"closeable", "componentId", "contributorKey", "data", "defaultEventHandler", "destroyOnHide", "elementClasses",
						"spritemap", "style", "type"
				)
		);
		TAGLIB_DEPRECATIONS.addAll(
				createTags(7.4f, TAGLIB_URI_LIFERAY_SOY, "Deprecate soy:component and soy:template renderer tags", "LPS-122966",
						"template-renderer", "component-renderer"
				)
		);
		TAGLIB_DEPRECATIONS.addAll(
				createTags(7.4f, TAGLIB_URI_LIFERAY_CLAY, "A series of deprecated and unused JSP tags have been removed and are no longer available", "LPS-112476",
						"table"
				)
		);
		TAGLIB_DEPRECATIONS.addAll(
				createTags(7.4f, TAGLIB_URI_LIFERAY_UI, "A series of deprecated and unused JSP tags have been removed and are no longer available", "LPS-112476",
						"alert", "input-scheduler",
						"organization-search-container-results", "organization-search-form", "ratings",
						"search-speed", "table-iterator", "toggle-area", "toggle",
						"user-search-container-results", "user-search-form"
				)
		);
		TAGLIB_DEPRECATIONS.addAll(
				createTags(7.4f, TAGLIB_URI_LIFERAY_THEME, "A series of deprecated and unused JSP tags have been removed and are no longer available", "LPS-112476",
						"layout-icon", "param"
				)
		);
		TAGLIB_DEPRECATIONS.addAll(
				createTags(7.4f, TAGLIB_URI_LIFERAY_UI, "The tag liferay-ui:flash has been deleted and is no longer available", "LPS-121732",
						"flash"
				)
		);
	}


	@Override
	public boolean isEnabledByDefault() {
		return true;
	}

	@Nls
	@NotNull
	@Override
	public String getDisplayName() {
		return "Liferay taglib deprecations inspection";
	}

	@Override
	public String getStaticDescription() {
		return "Check for deprecated Liferay taglibs.";
	}

	@Nls
	@NotNull
	@Override
	public String getGroupDisplayName() {
		return LiferayInspectionsGroupNames.LIFERAY_GROUP_NAME;
	}

	@Override
	public String @NotNull [] getGroupPath() {
		return new String[]{
				getGroupDisplayName(),
				LiferayInspectionsGroupNames.JSP_GROUP_NAME
		};
	}

	@NotNull
	@Override
	public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
		return new XmlElementVisitor() {
			@Override
			public void visitXmlTag(@NotNull XmlTag xmlTag) {
				for (LiferayTaglibDeprecationInfoHolder infoHolder : TAGLIB_DEPRECATIONS) {
					infoHolder.visitXmlTag(holder, xmlTag);
				}
			}

			public void visitXmlAttribute(@NotNull XmlAttribute attribute) {
				for (LiferayTaglibDeprecationInfoHolder infoHolder : TAGLIB_DEPRECATIONS) {
					infoHolder.visitXmlAttribute(holder, attribute);
				}
			}
		};
	}

}
