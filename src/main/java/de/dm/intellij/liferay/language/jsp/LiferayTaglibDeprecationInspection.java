package de.dm.intellij.liferay.language.jsp;

import com.intellij.codeInsight.intention.preview.IntentionPreviewInfo;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.codeInspection.XmlSuppressableInspectionTool;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiFile;
import com.intellij.psi.XmlElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.xml.XmlNamespaceHelper;
import de.dm.intellij.liferay.util.LiferayInspectionsGroupNames;
import de.dm.intellij.liferay.util.ProjectUtils;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static de.dm.intellij.liferay.language.jsp.LiferayTaglibDeprecationInfoHolder.createAttribute;
import static de.dm.intellij.liferay.language.jsp.LiferayTaglibDeprecationInfoHolder.createAttributes;
import static de.dm.intellij.liferay.language.jsp.LiferayTaglibDeprecationInfoHolder.createTag;
import static de.dm.intellij.liferay.language.jsp.LiferayTaglibDeprecationInfoHolder.createTags;
import static de.dm.intellij.liferay.util.LiferayTaglibs.TAGLIB_URI_LIFERAY_ASSET;
import static de.dm.intellij.liferay.util.LiferayTaglibs.TAGLIB_URI_LIFERAY_AUI;
import static de.dm.intellij.liferay.util.LiferayTaglibs.TAGLIB_URI_LIFERAY_CAPTCHA;
import static de.dm.intellij.liferay.util.LiferayTaglibs.TAGLIB_URI_LIFERAY_CLAY;
import static de.dm.intellij.liferay.util.LiferayTaglibs.TAGLIB_URI_LIFERAY_EXPANDO;
import static de.dm.intellij.liferay.util.LiferayTaglibs.TAGLIB_URI_LIFERAY_FLAGS;
import static de.dm.intellij.liferay.util.LiferayTaglibs.TAGLIB_URI_LIFERAY_FRONTEND;
import static de.dm.intellij.liferay.util.LiferayTaglibs.TAGLIB_URI_LIFERAY_PORTLET;
import static de.dm.intellij.liferay.util.LiferayTaglibs.TAGLIB_URI_LIFERAY_SECURITY;
import static de.dm.intellij.liferay.util.LiferayTaglibs.TAGLIB_URI_LIFERAY_SITE_NAVIGATION;
import static de.dm.intellij.liferay.util.LiferayTaglibs.TAGLIB_URI_LIFERAY_SOY;
import static de.dm.intellij.liferay.util.LiferayTaglibs.TAGLIB_URI_LIFERAY_THEME;
import static de.dm.intellij.liferay.util.LiferayTaglibs.TAGLIB_URI_LIFERAY_TRASH;
import static de.dm.intellij.liferay.util.LiferayTaglibs.TAGLIB_URI_LIFERAY_UI;

public class LiferayTaglibDeprecationInspection extends XmlSuppressableInspectionTool {

	private static final List<LiferayTaglibDeprecationInfoHolder> TAGLIB_DEPRECATIONS = new ArrayList<>();

	static {
		TAGLIB_DEPRECATIONS.addAll(
				createAttributes(7.0f, TAGLIB_URI_LIFERAY_UI, "app-view-search-entry", "Removed mbMessages and fileEntryTuples Attributes from app-view-search-entry Tag.", "LPS-55886",
						"mbMessages", "fileEntryTuples"
				)
		);
		TAGLIB_DEPRECATIONS.add(
				createTag(7.0f, TAGLIB_URI_LIFERAY_UI, "Removed the liferay-ui:asset-categories-navigation Tag and Replaced with liferay-asset:asset-categories-navigation.", "LPS-60753",
						"asset-categories-navigation"
				).quickfix(renameXmlNamespace(TAGLIB_URI_LIFERAY_ASSET, "liferay-asset"))
		);
		TAGLIB_DEPRECATIONS.add(
				createTag(7.0f, TAGLIB_URI_LIFERAY_AUI, "Removed the aui:button-item Tag and Replaced with aui:button.", "LPS-62922",
						"button-item"
				).quickfix(renameXmlTag("button"))
		);
		TAGLIB_DEPRECATIONS.add(
				createTag(7.0f, TAGLIB_URI_LIFERAY_AUI, "The aui:column taglib has been removed and replaced with aui:col taglib.", "LPS-62208",
						"column"
				).quickfix(renameXmlTag("col"))
		);
		TAGLIB_DEPRECATIONS.addAll(
				createTags(7.0f, TAGLIB_URI_LIFERAY_AUI, "Deprecated the aui:tool Tag with No Direct Replacement.", "LPS-70422",
						"tool"
				)
		);
		TAGLIB_DEPRECATIONS.add(
				createTag(7.0f, TAGLIB_URI_LIFERAY_UI, "Moved the Expando Custom Field Tags to liferay-expando Taglib.", "LPS-69400",
						"custom-attribute"
				).quickfix(renameXmlNamespace(TAGLIB_URI_LIFERAY_EXPANDO, "liferay-expando"))
		);
		TAGLIB_DEPRECATIONS.add(
				createTag(7.0f, TAGLIB_URI_LIFERAY_UI, "Moved the Expando Custom Field Tags to liferay-expando Taglib.", "LPS-69400",
						"custom-attribute-list"
				).quickfix(renameXmlNamespace(TAGLIB_URI_LIFERAY_EXPANDO, "liferay-expando"))
		);
		TAGLIB_DEPRECATIONS.add(
				createTag(7.0f, TAGLIB_URI_LIFERAY_UI, "Moved the Expando Custom Field Tags to liferay-expando Taglib.", "LPS-69400",
						 "custom-attributes-available"
				).quickfix(renameXmlNamespace(TAGLIB_URI_LIFERAY_EXPANDO, "liferay-expando"))
		);
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
		TAGLIB_DEPRECATIONS.add(
				createTag(7.0f, TAGLIB_URI_LIFERAY_UI, "Deprecated the liferay-ui:captcha Tag and Replaced with liferay-captcha:captcha.", "LPS-69383",
						"captcha"
				).quickfix(renameXmlNamespace(TAGLIB_URI_LIFERAY_CAPTCHA, "liferay-captcha"))
		);
		TAGLIB_DEPRECATIONS.add(
				createTag(7.0f, TAGLIB_URI_LIFERAY_UI, "Removed the liferay-ui:trash-empty Tag and Replaced with liferay-trash:empty.", "LPS-60779",
						"trash-empty"
				).quickfix(renameXmlNamespace(TAGLIB_URI_LIFERAY_TRASH, "liferay-trash"))
		);
		TAGLIB_DEPRECATIONS.add(
				createTag(7.0f, TAGLIB_URI_LIFERAY_UI, "Removed the liferay-ui:trash-undo Tag and Replaced with liferay-trash:undo.", "LPS-60779",
						"trash-undo"
				).quickfix(renameXmlNamespace(TAGLIB_URI_LIFERAY_TRASH, "liferay-trash"))
		);
		TAGLIB_DEPRECATIONS.addAll(
				createTags(7.0f, TAGLIB_URI_LIFERAY_UI, "Removed the liferay-ui:journal-article Tag.", "LPS-69321",
						"journal-article"
				)
		);
		TAGLIB_DEPRECATIONS.add(
				createTag(7.0f, TAGLIB_URI_LIFERAY_UI, "Deprecated the liferay-ui:flags Tag and Replaced with liferay-flags:flags.", "LPS-60967",
						"flags"
				).quickfix(renameXmlNamespace(TAGLIB_URI_LIFERAY_FLAGS, "liferay-flags"))
		);
		TAGLIB_DEPRECATIONS.add(
				createTag(7.0f, TAGLIB_URI_LIFERAY_UI, "Removed the liferay-ui:navigation Tag and Replaced with liferay-site-navigation:navigation Tag.", "LPS-60328",
						"navigation"
				).quickfix(renameXmlNamespace(TAGLIB_URI_LIFERAY_SITE_NAVIGATION, "liferay-site-navigation"))
		);
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
				createAttributes(7.4f, TAGLIB_URI_LIFERAY_CLAY, "button", "The attributes componentId, contributorKey, data, defaultEventHandler, disabled, elementClasses, iconAlignment, name, size, spritemap, style, title, type, value in `clay:button` tag was removed.", "LPS-125256",
						 "componentId", "contributorKey", "data", "defaultEventHandler", "disabled", "elementClasses",
						"iconAlignment", "name", "size", "spritemap", "style", "title", "type", "value"
				)
		);
		TAGLIB_DEPRECATIONS.add(
				createAttribute(7.4f, TAGLIB_URI_LIFERAY_CLAY, "button", "The attribute ariaLabel in `clay:button` tag was removed, use aria-label instead", "LPS-125256",
						"ariaLabel"
				).quickfix(renameXmlAttribute("aria-label"))
		);


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
		TAGLIB_DEPRECATIONS.addAll(
				createTags(7.4f, TAGLIB_URI_LIFERAY_AUI, "The tags `<aui:fieldset-group>` and `<liferay-frontend:fieldset-group>` added unnecessary markup to the page and caused accessibility issues, so they have been removed", "LPS-168309",
						"fieldset-group"
				)
		);
		TAGLIB_DEPRECATIONS.addAll(
				createTags(7.4f, TAGLIB_URI_LIFERAY_FRONTEND, "The tags `<aui:fieldset-group>` and `<liferay-frontend:fieldset-group>` added unnecessary markup to the page and caused accessibility issues, so they have been removed", "LPS-168309",
						"fieldset-group"
				)
		);
		TAGLIB_DEPRECATIONS.addAll(
				createTags(7.4f, TAGLIB_URI_LIFERAY_FRONTEND, "Obsolete frontend taglibs that don't follow lexicon patterns and have an obsolete markup have been removed", "LPS-158461",
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
						"vertical-card-sticker-bottom"
				)
		);
		TAGLIB_DEPRECATIONS.add(
				createTag(7.4f, TAGLIB_URI_LIFERAY_AUI, "The tags <aui:container> is deprecated, use <clay:container> instead", "LPS-166546",
						"container"
				).quickfix(renameXmlNamespace(TAGLIB_URI_LIFERAY_CLAY, "clay"))
		);
		TAGLIB_DEPRECATIONS.addAll(
				createTags(7.4f, TAGLIB_URI_LIFERAY_AUI, "The tags <aui:component>, <aui:spacer> and <aui:panel> are obsolete and have been removed", "LPS-166546",
						"component", "spacer", "panel"
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

	private static LocalQuickFix renameXmlAttribute(String newName) {
		return new RenameXmlAttributeQuickFix(newName);
	}

	private static LocalQuickFix renameXmlTag(String newName) {
		return new RenameXmlTagQuickFix(newName);
	}

	private static LocalQuickFix renameXmlNamespace(String newNamespace, String newPrefix) {
		return new RenameXmlNamespaceQuickFix(newNamespace, newPrefix);
	}

	private static class RenameXmlAttributeQuickFix implements LocalQuickFix {
		private String newName;

		public RenameXmlAttributeQuickFix(String newName) {
			this.newName = newName;
		}

		@Nls
		@NotNull
		@Override
		public String getFamilyName() {
			return "Rename Attribute";
		}

		@Nls
		@NotNull
		@Override
		public String getName() {
			return "Rename Attribute to " + newName;
		}

		@Override
		public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
			PsiElement psiElement = descriptor.getPsiElement();

			XmlAttribute attribute;

			if (psiElement instanceof XmlAttribute) {
				attribute = (XmlAttribute) psiElement;
			} else {
				attribute = PsiTreeUtil.getParentOfType(psiElement, XmlAttribute.class);
			}

			if (attribute == null) {
				return;
			}

			attribute.setName(newName);
		}
	}
	private static class RenameXmlTagQuickFix implements LocalQuickFix {
		private String newName;

		public RenameXmlTagQuickFix(String newName) {
			this.newName = newName;
		}

		@Nls
		@NotNull
		@Override
		public String getFamilyName() {
			return "Rename Tag";
		}

		@Nls
		@NotNull
		@Override
		public String getName() {
			return "Rename Tag to " + newName;
		}

		@Override
		public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
			PsiElement psiElement = descriptor.getPsiElement();

			XmlTag currentTag;

			if (psiElement instanceof XmlTag) {
				currentTag = (XmlTag) psiElement;
			} else {
				currentTag = PsiTreeUtil.getParentOfType(psiElement, XmlTag.class, false);
			}

			if (currentTag == null) {
				return;
			}

			XmlTag parentTag = currentTag.getParentTag();

			if (parentTag == null) {
				return;
			}

			XmlTag newTag = parentTag.createChildTag(newName, currentTag.getNamespace(), currentTag.isEmpty() ? null : currentTag.getValue().getText(),true);

			for (XmlAttribute attr : currentTag.getAttributes()) {
				newTag.setAttribute(attr.getLocalName(), attr.getNamespace(), attr.getValue());
			}

			currentTag.replace(newTag);
		}
	}

	private static class RenameXmlNamespaceQuickFix implements LocalQuickFix {
		private String newNamespace;
		private String newPrefix;

		public RenameXmlNamespaceQuickFix(String newNamespace, String newPrefix) {
			this.newNamespace = newNamespace;
			this.newPrefix = newPrefix;
		}

		@Nls
		@NotNull
		@Override
		public String getFamilyName() {
			return "Rename Namespace";
		}

		@Nls
		@NotNull
		@Override
		public String getName() {
			return "Change Namespace to " + newNamespace;
		}

		@Override
		public @NotNull IntentionPreviewInfo generatePreview(@NotNull Project project, @NotNull ProblemDescriptor previewDescriptor) {
			return IntentionPreviewInfo.EMPTY;
		}

		@Override
		public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
			PsiElement psiElement = descriptor.getPsiElement();

			XmlTag currentTag;

			if (psiElement instanceof XmlTag) {
				currentTag = (XmlTag) psiElement;
			} else {
				currentTag = PsiTreeUtil.getParentOfType(psiElement, XmlTag.class, false);
			}

			if (currentTag == null) {
				return;
			}

			XmlTag parentTag = currentTag.getParentTag();

			if (parentTag == null) {
				return;
			}

			String namespacePrefix = currentTag.getPrefixByNamespace(newNamespace);

			ProjectUtils.runWriteAction(
					project,
					() -> {
						if (namespacePrefix == null) {
							PsiFile file = currentTag.getContainingFile();

							file = file.getOriginalFile();

							XmlNamespaceHelper extension = XmlNamespaceHelper.getHelper(file);

							XmlFile xmlFile = (XmlFile)file;

							extension.insertNamespaceDeclaration(xmlFile, null, Collections.singleton(newNamespace), newPrefix, null);
						}

						XmlTag newTag = parentTag.createChildTag(currentTag.getLocalName(), newNamespace, currentTag.isEmpty() ? null : currentTag.getValue().getText(),true);

						for (XmlAttribute attr : currentTag.getAttributes()) {
							newTag.setAttribute(attr.getLocalName(), attr.getNamespace(), attr.getValue());
						}

						currentTag.replace(newTag);
					}
			);
		}
	}

}
