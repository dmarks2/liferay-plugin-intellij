package de.dm.intellij.liferay.language.jsp;

import com.intellij.codeInsight.intention.preview.IntentionPreviewInfo;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiDocumentManager;
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
import de.dm.intellij.liferay.util.LiferayVersions;
import de.dm.intellij.liferay.util.ProjectUtils;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static de.dm.intellij.liferay.language.jsp.LiferayJspTaglibDeprecationInfoHolder.createAttributes;
import static de.dm.intellij.liferay.language.jsp.LiferayJspTaglibDeprecationInfoHolder.createTags;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_100146_CONTEXTUAL_SIDEBAR;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_106899_CARDS_TREEVIEW;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_112464_CLAY_BADGE_CSS_CLASS;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_112464_CLAY_COL_CSS_CLASS;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_112464_CLAY_CONTAINER_CSS_CLASS;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_112464_CLAY_CONTENT_COL_CSS_CLASS;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_112464_CLAY_CONTENT_ROW_CSS_CLASS;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_112464_CLAY_CONTENT_SECTION_CSS_CLASS;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_112464_CLAY_ICON_CSS_CLASS;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_112464_CLAY_LABEL_CSS_CLASS;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_112464_CLAY_LABEL_ITEM_AFTER_CSS_CLASS;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_112464_CLAY_LABEL_ITEM_BEFORE_CSS_CLASS;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_112464_CLAY_LABEL_ITEM_EXPAND_CSS_CLASS;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_112464_CLAY_ROW_CSS_CLASS;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_112464_CLAY_SHEET_CSS_CLASS;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_112464_CLAY_SHEET_FOOTER_CSS_CLASS;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_112464_CLAY_SHEET_HEADER_CSS_CLASS;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_112464_CLAY_SHEET_SECTION_CSS_CLASS;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_112464_CLAY_STICKER_CSS_CLASS;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_112476_CLAY;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_112476_LIFERAY_THEME;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_112476_LIFERAY_UI;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_121732_FLASH;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_122966_LIFERAY_SOY;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_125256_CLAY_ALERT;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_125256_CLAY_BADGE;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_125256_CLAY_BADGE_STYLE;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_125256_CLAY_BUTTON;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_125256_CLAY_BUTTON_ARIA_LABEL;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_125256_CLAY_DROPDOWN_ACTIONS;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_125256_CLAY_DROPDOWN_MENU;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_125256_CLAY_ICON;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_125256_CLAY_LABEL;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_125256_CLAY_LINK;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_125256_CLAY_LINK_ARIA_LABEL;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_125256_CLAY_NAVIGATION_BAR;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_125256_CLAY_PROGRESSBAR;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_125256_CLAY_RADIO;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_125256_CLAY_STICKER;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_125256_CLAY_STICKER_STYLE;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_125256_CLAY_STRIPE;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_158461_LIFERAY_FRONTEND;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_166546_AUI_CONTAINER;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_166546_LIFERAY_AUI;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_168309_LIFERAY_AUI;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_168309_LIFERAY_FRONTEND;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_199170_COMMERCE;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_199170_EXPORT_IMPORT_CHANGESET;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_202768_LOGO_SELECTOR;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_202768_UPLOAD_PROGRESS;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_54620_PORTLET_ICON;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_55886_APP_VIEW_SEARCH_ENTRY;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_60328_NAVIGATION;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_60753_ASSET_CATEGORIES_NAVIGATION;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_60779_LIFERAY_TRASH;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_60967_FLAGS;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_62208_AUI_COLUMN;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_62922_BUTTON_ITEM;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_62935_AUI_LAYOUT;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_63101_ICON_BACK;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_63106_ENCRYPT;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_69321_JOURNAL_ARTICLE;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_69383_CAPTCHA;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_69400_LIFERAY_EXPANDO;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_70442_AUI_TOOL;
import static de.dm.intellij.liferay.util.LiferayTaglibs.TAGLIB_URI_LIFERAY_ASSET;
import static de.dm.intellij.liferay.util.LiferayTaglibs.TAGLIB_URI_LIFERAY_CAPTCHA;
import static de.dm.intellij.liferay.util.LiferayTaglibs.TAGLIB_URI_LIFERAY_CLAY;
import static de.dm.intellij.liferay.util.LiferayTaglibs.TAGLIB_URI_LIFERAY_DOCUMENT_LIBRARY;
import static de.dm.intellij.liferay.util.LiferayTaglibs.TAGLIB_URI_LIFERAY_EXPANDO;
import static de.dm.intellij.liferay.util.LiferayTaglibs.TAGLIB_URI_LIFERAY_FLAGS;
import static de.dm.intellij.liferay.util.LiferayTaglibs.TAGLIB_URI_LIFERAY_FRONTEND;
import static de.dm.intellij.liferay.util.LiferayTaglibs.TAGLIB_URI_LIFERAY_SITE_NAVIGATION;
import static de.dm.intellij.liferay.util.LiferayTaglibs.TAGLIB_URI_LIFERAY_TRASH;

public class LiferayJspTaglibDeprecationInspection extends AbstractLiferayDeprecationInspection<LiferayJspTaglibDeprecationInfoHolder> {

	private static final String XML_TEMPLATE_CHILDREN_PLACEHOLDER = "$$children$$";

	private static final List<LiferayJspTaglibDeprecationInfoHolder> TAGLIB_DEPRECATIONS = new ArrayList<>();

	static {
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_55886_APP_VIEW_SEARCH_ENTRY).quickfix(removeXmlAttribute()));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_60753_ASSET_CATEGORIES_NAVIGATION).quickfix(renameXmlNamespace(TAGLIB_URI_LIFERAY_ASSET, "liferay-asset")));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_62922_BUTTON_ITEM).quickfix(renameXmlTag("button")));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_62208_AUI_COLUMN).quickfix(renameXmlTag("col")));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_70442_AUI_TOOL).quickfix(removeXmlTag()));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_69400_LIFERAY_EXPANDO).quickfix(renameXmlNamespace(TAGLIB_URI_LIFERAY_EXPANDO, "liferay-expando")));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_63101_ICON_BACK).quickfix(removeXmlTag()));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_63106_ENCRYPT).quickfix(removeXmlTag()));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_69383_CAPTCHA).quickfix(renameXmlNamespace(TAGLIB_URI_LIFERAY_CAPTCHA, "liferay-captcha")));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_60779_LIFERAY_TRASH).quickfix(renameXmlNamespace(TAGLIB_URI_LIFERAY_TRASH, "liferay-trash")));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_69321_JOURNAL_ARTICLE).quickfix(removeXmlTag()));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_60967_FLAGS).quickfix(renameXmlNamespace(TAGLIB_URI_LIFERAY_FLAGS, "liferay-flags")));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_60328_NAVIGATION).quickfix(renameXmlNamespace(TAGLIB_URI_LIFERAY_SITE_NAVIGATION, "liferay-site-navigation")));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_54620_PORTLET_ICON).quickfix(removeXmlTag()));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_62935_AUI_LAYOUT).quickfix(removeXmlTag()));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_100146_CONTEXTUAL_SIDEBAR).quickfix(removeXmlTag()));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_106899_CARDS_TREEVIEW).quickfix(removeXmlTag()));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_112464_CLAY_BADGE_CSS_CLASS).quickfix(renameXmlAttribute("cssClass")).version("7.3.3"));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_112464_CLAY_COL_CSS_CLASS).quickfix(renameXmlAttribute("cssClass")).version("7.3.3"));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_112464_CLAY_CONTAINER_CSS_CLASS).quickfix(renameXmlAttribute("cssClass")).version("7.3.3"));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_112464_CLAY_CONTENT_COL_CSS_CLASS).quickfix(renameXmlAttribute("cssClass")).version("7.3.3"));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_112464_CLAY_CONTENT_ROW_CSS_CLASS).quickfix(renameXmlAttribute("cssClass")).version("7.3.3"));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_112464_CLAY_CONTENT_SECTION_CSS_CLASS).quickfix(renameXmlAttribute("cssClass")).version("7.3.3"));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_112464_CLAY_ICON_CSS_CLASS).quickfix(renameXmlAttribute("cssClass")).version("7.3.3"));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_112464_CLAY_LABEL_ITEM_AFTER_CSS_CLASS).quickfix(renameXmlAttribute("cssClass")).version("7.3.3"));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_112464_CLAY_LABEL_ITEM_BEFORE_CSS_CLASS).quickfix(renameXmlAttribute("cssClass")).version("7.3.3"));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_112464_CLAY_LABEL_ITEM_EXPAND_CSS_CLASS).quickfix(renameXmlAttribute("cssClass")).version("7.3.3"));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_112464_CLAY_LABEL_CSS_CLASS).quickfix(renameXmlAttribute("cssClass")).version("7.3.3"));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_112464_CLAY_ROW_CSS_CLASS).quickfix(renameXmlAttribute("cssClass")).version("7.3.3"));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_112464_CLAY_SHEET_FOOTER_CSS_CLASS).quickfix(renameXmlAttribute("cssClass")).version("7.3.3"));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_112464_CLAY_SHEET_HEADER_CSS_CLASS).quickfix(renameXmlAttribute("cssClass")).version("7.3.3"));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_112464_CLAY_SHEET_SECTION_CSS_CLASS).quickfix(renameXmlAttribute("cssClass")).version("7.3.3"));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_112464_CLAY_SHEET_CSS_CLASS).quickfix(renameXmlAttribute("cssClass")).version("7.3.3"));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_112464_CLAY_STICKER_CSS_CLASS).quickfix(renameXmlAttribute("cssClass")).version("7.3.3"));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_125256_CLAY_ALERT).quickfix(removeXmlAttribute()));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_125256_CLAY_BADGE).quickfix(removeXmlAttribute()));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_125256_CLAY_BADGE_STYLE).quickfix(renameXmlAttribute("displayType")));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_125256_CLAY_BUTTON).quickfix(removeXmlAttribute()));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_125256_CLAY_BUTTON_ARIA_LABEL).quickfix(renameXmlAttribute("aria-label")));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_125256_CLAY_DROPDOWN_ACTIONS).quickfix(removeXmlAttribute()));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_125256_CLAY_DROPDOWN_MENU).quickfix(removeXmlAttribute()));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_125256_CLAY_ICON).quickfix(removeXmlAttribute()));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_125256_CLAY_LABEL).quickfix(removeXmlAttribute()));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_125256_CLAY_LINK).quickfix(removeXmlAttribute()));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_125256_CLAY_LINK_ARIA_LABEL).quickfix(renameXmlAttribute("aria-label")));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_125256_CLAY_NAVIGATION_BAR).quickfix(removeXmlAttribute()));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_125256_CLAY_PROGRESSBAR).quickfix(removeXmlAttribute()));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_125256_CLAY_RADIO).quickfix(removeXmlAttribute()));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_125256_CLAY_STICKER).quickfix(removeXmlAttribute()));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_125256_CLAY_STICKER_STYLE).quickfix(renameXmlAttribute("displayType")));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_125256_CLAY_STRIPE).quickfix(removeXmlAttribute()));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_122966_LIFERAY_SOY).quickfix(removeXmlTag()));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_112476_CLAY).quickfix(removeXmlTag()));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_112476_LIFERAY_UI).quickfix(removeXmlTag()));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_112476_LIFERAY_THEME).quickfix(removeXmlTag()));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_121732_FLASH).quickfix(removeXmlTag()));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_168309_LIFERAY_AUI).quickfix(
				replaceWithTags(
						"<div class=\"sheet\">\n" +
								"  	<div class=\"panel-group panel-group-flush\">" +
								XML_TEMPLATE_CHILDREN_PLACEHOLDER +
								"  	</div>\n" +
								"</div>"
				)
		));

		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_168309_LIFERAY_FRONTEND).quickfix(removeXmlTag()));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_158461_LIFERAY_FRONTEND).quickfix(removeXmlTag()));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_166546_AUI_CONTAINER).quickfix(renameXmlNamespace(TAGLIB_URI_LIFERAY_CLAY, "clay")));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_166546_LIFERAY_AUI).quickfix(removeXmlTag()));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_199170_COMMERCE).quickfix(removeXmlTag()).version("7.4.3.100"));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_199170_EXPORT_IMPORT_CHANGESET).quickfix(removeXmlTag()).version("7.4.3.100"));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_202768_LOGO_SELECTOR).quickfix(renameXmlNamespace(TAGLIB_URI_LIFERAY_FRONTEND, "liferay-frontend")).version(LiferayVersions.LIFERAY_2024_Q1_CE));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_202768_UPLOAD_PROGRESS).quickfix(renameXmlNamespace(TAGLIB_URI_LIFERAY_DOCUMENT_LIBRARY, "liferay-document-library")).version(LiferayVersions.LIFERAY_2024_Q1_CE));
	}

	@Nls
	@NotNull
	@Override
	public String getDisplayName() {
		return "Liferay JSP taglib deprecations inspection";
	}

	@Override
	public String getStaticDescription() {
		return "Check for deprecated Liferay JSP taglibs.";
	}

	@Override
	public String @NotNull [] getGroupPath() {
		return new String[]{
				getGroupDisplayName(),
				LiferayInspectionsGroupNames.JSP_GROUP_NAME
		};
	}

	@Override
	protected List<LiferayJspTaglibDeprecationInfoHolder> getInspectionInfoHolders() {
		return TAGLIB_DEPRECATIONS;
	}

	@Override
	protected PsiElementVisitor doBuildVisitor(ProblemsHolder holder, boolean isOnTheFly, List<LiferayJspTaglibDeprecationInfoHolder> inspectionInfoHolders) {
		return new XmlElementVisitor() {
			@Override
			public void visitXmlTag(@NotNull XmlTag xmlTag) {
				for (LiferayJspTaglibDeprecationInfoHolder infoHolder : inspectionInfoHolders) {
					infoHolder.visitXmlTag(holder, xmlTag);
				}
			}

			public void visitXmlAttribute(@NotNull XmlAttribute attribute) {
				for (LiferayJspTaglibDeprecationInfoHolder infoHolder : inspectionInfoHolders) {
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

	private static LocalQuickFix removeXmlAttribute() {
		return new RemoveXmlAttributeQuickFix();
	}
	private static LocalQuickFix removeXmlTag() {
		return new RemoveXmlTagQuickFix();
	}

	private static class RenameXmlAttributeQuickFix implements LocalQuickFix {
		private final String newName;

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
		private final String newName;

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
		private final String newNamespace;
		private final String newPrefix;

		public RenameXmlNamespaceQuickFix(String newNamespace, String newPrefix) {
			this.newNamespace = newNamespace;
			this.newPrefix = newPrefix;
		}

		@Nls
		@NotNull
		@Override
		public String getFamilyName() {
			return "Change Namespace";
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

	private static class RemoveXmlAttributeQuickFix implements LocalQuickFix {
		@Nls
		@NotNull
		@Override
		public String getFamilyName() {
			return "Remove Attribute";
		}

		@Nls
		@NotNull
		@Override
		public String getName() {
			return "Remove Attribute";
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

			attribute.getParent().setAttribute(attribute.getName(), null);
		}
	}
	private static class RemoveXmlTagQuickFix implements LocalQuickFix {
		@Nls
		@NotNull
		@Override
		public String getFamilyName() {
			return "Remove Tag";
		}

		@Nls
		@NotNull
		@Override
		public String getName() {
			return "Remove Tag";
		}

		@Override
		public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
			PsiElement psiElement = descriptor.getPsiElement();

			XmlTag tag;

			if (psiElement instanceof XmlTag) {
				tag = (XmlTag) psiElement;
			} else {
				tag = PsiTreeUtil.getParentOfType(psiElement, XmlTag.class);
			}

			if (tag == null) {
				return;
			}

			tag.delete();
		}
	}

	private static LocalQuickFix replaceWithTags(String template) {
		return new ReplaceWithTagsQuickFix(template);
	}
	private static class ReplaceWithTagsQuickFix implements LocalQuickFix {

		private final String template;

		public ReplaceWithTagsQuickFix(String template) {
			this.template = template;
		}

		@Nls
		@NotNull
		@Override
		public String getFamilyName() {
			return "Replace with Tags";
		}

		@Nls
		@NotNull
		@Override
		public String getName() {
			return "Replace with " + template;
		}

		@Override
		public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
			PsiElement psiElement = descriptor.getPsiElement();

			XmlTag tag;

			if (psiElement instanceof XmlTag) {
				tag = (XmlTag) psiElement;
			} else {
				tag = PsiTreeUtil.getParentOfType(psiElement, XmlTag.class);
			}

			if (tag == null) {
				return;
			}

			Document document = PsiDocumentManager.getInstance(project).getDocument(tag.getContainingFile());

			if (document != null) {
				TextRange range = tag.getTextRange();

				String text = document.getText(tag.getValue().getTextRange());

				PsiDocumentManager.getInstance(project).doPostponedOperationsAndUnblockDocument(document);

				document.replaceString(range.getStartOffset(), range.getEndOffset(), StringUtil.replace(template, XML_TEMPLATE_CHILDREN_PLACEHOLDER, text));

				PsiDocumentManager.getInstance(project).commitDocument(document);
			}
		}
	}

}
