package de.dm.intellij.liferay.language.freemarker;

import com.intellij.codeInsight.intention.preview.IntentionPreviewInfo;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.freemarker.psi.FtlNameValuePair;
import com.intellij.freemarker.psi.directives.FtlMacro;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import de.dm.intellij.liferay.language.jsp.AbstractLiferayDeprecationInspection;
import de.dm.intellij.liferay.util.LiferayInspectionsGroupNames;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static de.dm.intellij.liferay.language.freemarker.LiferayFreemarkerTaglibDeprecationInfoHolder.createAttributes;
import static de.dm.intellij.liferay.language.freemarker.LiferayFreemarkerTaglibDeprecationInfoHolder.createTags;
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
import static de.dm.intellij.liferay.util.LiferayTaglibs.TAGLIB_URI_LIFERAY_EXPANDO;
import static de.dm.intellij.liferay.util.LiferayTaglibs.TAGLIB_URI_LIFERAY_FLAGS;
import static de.dm.intellij.liferay.util.LiferayTaglibs.TAGLIB_URI_LIFERAY_SITE_NAVIGATION;
import static de.dm.intellij.liferay.util.LiferayTaglibs.TAGLIB_URI_LIFERAY_TRASH;

public class LiferayFreemarkerTaglibDeprecationInspection extends AbstractLiferayDeprecationInspection<LiferayFreemarkerTaglibDeprecationInfoHolder> {

	private static final List<LiferayFreemarkerTaglibDeprecationInfoHolder> TAGLIB_DEPRECATIONS = new ArrayList<>();

	static {
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_55886_APP_VIEW_SEARCH_ENTRY).quickfix(removeAttribute()));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_60753_ASSET_CATEGORIES_NAVIGATION).quickfix(renameNamespace(TAGLIB_URI_LIFERAY_ASSET)));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_62922_BUTTON_ITEM).quickfix(renameTag("button")));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_62208_AUI_COLUMN).quickfix(renameTag("col")));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_70442_AUI_TOOL).quickfix(removeTag()));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_69400_LIFERAY_EXPANDO).quickfix(renameNamespace(TAGLIB_URI_LIFERAY_EXPANDO)));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_63101_ICON_BACK).quickfix(removeTag()));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_63106_ENCRYPT).quickfix(removeTag()));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_69383_CAPTCHA).quickfix(renameNamespace(TAGLIB_URI_LIFERAY_CAPTCHA)));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_60779_LIFERAY_TRASH).quickfix(renameNamespace(TAGLIB_URI_LIFERAY_TRASH)));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_69321_JOURNAL_ARTICLE).quickfix(removeTag()));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_60967_FLAGS).quickfix(renameNamespace(TAGLIB_URI_LIFERAY_FLAGS)));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_60328_NAVIGATION).quickfix(renameNamespace(TAGLIB_URI_LIFERAY_SITE_NAVIGATION)));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_54620_PORTLET_ICON).quickfix(removeTag()));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_62935_AUI_LAYOUT).quickfix(removeTag()));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_100146_CONTEXTUAL_SIDEBAR).quickfix(removeTag()));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_106899_CARDS_TREEVIEW).quickfix(removeTag()));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_112464_CLAY_BADGE_CSS_CLASS).quickfix(renameAttribute("cssClass")).version("7.3.3"));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_112464_CLAY_COL_CSS_CLASS).quickfix(renameAttribute("cssClass")).version("7.3.3"));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_112464_CLAY_CONTAINER_CSS_CLASS).quickfix(renameAttribute("cssClass")).version("7.3.3"));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_112464_CLAY_CONTENT_COL_CSS_CLASS).quickfix(renameAttribute("cssClass")).version("7.3.3"));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_112464_CLAY_CONTENT_ROW_CSS_CLASS).quickfix(renameAttribute("cssClass")).version("7.3.3"));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_112464_CLAY_CONTENT_SECTION_CSS_CLASS).quickfix(renameAttribute("cssClass")).version("7.3.3"));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_112464_CLAY_ICON_CSS_CLASS).quickfix(renameAttribute("cssClass")).version("7.3.3"));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_112464_CLAY_LABEL_ITEM_AFTER_CSS_CLASS).quickfix(renameAttribute("cssClass")).version("7.3.3"));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_112464_CLAY_LABEL_ITEM_BEFORE_CSS_CLASS).quickfix(renameAttribute("cssClass")).version("7.3.3"));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_112464_CLAY_LABEL_ITEM_EXPAND_CSS_CLASS).quickfix(renameAttribute("cssClass")).version("7.3.3"));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_112464_CLAY_LABEL_CSS_CLASS).quickfix(renameAttribute("cssClass")).version("7.3.3"));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_112464_CLAY_ROW_CSS_CLASS).quickfix(renameAttribute("cssClass")).version("7.3.3"));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_112464_CLAY_SHEET_FOOTER_CSS_CLASS).quickfix(renameAttribute("cssClass")).version("7.3.3"));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_112464_CLAY_SHEET_HEADER_CSS_CLASS).quickfix(renameAttribute("cssClass")).version("7.3.3"));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_112464_CLAY_SHEET_SECTION_CSS_CLASS).quickfix(renameAttribute("cssClass")).version("7.3.3"));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_112464_CLAY_SHEET_CSS_CLASS).quickfix(renameAttribute("cssClass")).version("7.3.3"));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_112464_CLAY_STICKER_CSS_CLASS).quickfix(renameAttribute("cssClass")).version("7.3.3"));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_125256_CLAY_ALERT).quickfix(removeAttribute()));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_125256_CLAY_BADGE).quickfix(removeAttribute()));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_125256_CLAY_BADGE_STYLE).quickfix(renameAttribute("displayType")));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_125256_CLAY_BUTTON).quickfix(removeAttribute()));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_125256_CLAY_BUTTON_ARIA_LABEL).quickfix(renameAttribute("aria\\-label")));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_125256_CLAY_DROPDOWN_ACTIONS).quickfix(removeAttribute()));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_125256_CLAY_DROPDOWN_MENU).quickfix(removeAttribute()));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_125256_CLAY_ICON).quickfix(removeAttribute()));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_125256_CLAY_LABEL).quickfix(removeAttribute()));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_125256_CLAY_LINK).quickfix(removeAttribute()));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_125256_CLAY_LINK_ARIA_LABEL).quickfix(renameAttribute("aria\\-label")));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_125256_CLAY_NAVIGATION_BAR).quickfix(removeAttribute()));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_125256_CLAY_PROGRESSBAR).quickfix(removeAttribute()));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_125256_CLAY_RADIO).quickfix(removeAttribute()));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_125256_CLAY_STICKER).quickfix(removeAttribute()));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_125256_CLAY_STICKER_STYLE).quickfix(renameAttribute("displayType")));
		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_125256_CLAY_STRIPE).quickfix(removeAttribute()));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_122966_LIFERAY_SOY).quickfix(removeTag()));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_112476_CLAY).quickfix(removeTag()));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_112476_LIFERAY_UI).quickfix(removeTag()));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_112476_LIFERAY_THEME).quickfix(removeTag()));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_121732_FLASH).quickfix(removeTag()));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_168309_LIFERAY_AUI).quickfix(removeTag()));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_168309_LIFERAY_FRONTEND).quickfix(removeTag()));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_158461_LIFERAY_FRONTEND).quickfix(removeTag()));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_166546_AUI_CONTAINER).quickfix(renameNamespace(TAGLIB_URI_LIFERAY_CLAY)));
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_166546_LIFERAY_AUI).quickfix(removeTag()));
	}

	@Nls
	@NotNull
	@Override
	public String getDisplayName() {
		return "Liferay Freemarker taglib deprecations inspection";
	}

	@Override
	public String getStaticDescription() {
		return "Check for deprecated Liferay Freemarker taglibs.";
	}

	@Override
	public String @NotNull [] getGroupPath() {
		return new String[]{
				getGroupDisplayName(),
				LiferayInspectionsGroupNames.FREEMARKER_GROUP_NAME
		};
	}

	@Override
	protected List<LiferayFreemarkerTaglibDeprecationInfoHolder> getInspectionInfoHolders() {
		return TAGLIB_DEPRECATIONS;
	}

	@Override
	protected PsiElementVisitor doBuildVisitor(ProblemsHolder holder, boolean isOnTheFly, List<LiferayFreemarkerTaglibDeprecationInfoHolder> inspectionInfoHolders) {
		return new PsiElementVisitor() {
			@Override
			public void visitElement(@NotNull PsiElement element) {
				if (element instanceof FtlNameValuePair) {
					for (LiferayFreemarkerTaglibDeprecationInfoHolder infoHolder : inspectionInfoHolders) {
						infoHolder.visitFtlNameValuePair(holder, (FtlNameValuePair)element);
					}
				} else if (element instanceof FtlMacro) {
					for (LiferayFreemarkerTaglibDeprecationInfoHolder infoHolder : inspectionInfoHolders) {
						infoHolder.visitFtlMacro(holder, (FtlMacro)element);
					}
				}
			}
		};
	}

	private static LocalQuickFix removeAttribute() {
		return new RemoveFreemarkerAttributeQuickFix();
	}
	private static LocalQuickFix removeTag() {
		return new RemoveFreemarkerTagQuickFix();
	}
	private static LocalQuickFix renameAttribute(String newName) {
		return new RenameFreemarkerAttributeQuickFix(newName);
	}
	private static LocalQuickFix renameTag(String newName) {
		return new RenameFreemarkerTagQuickFix(newName);
	}
	private static LocalQuickFix renameNamespace(String newNamespace) {
		return new RenameFreemarkerNamespaceQuickFix(newNamespace);
	}


	private static class RemoveFreemarkerAttributeQuickFix implements LocalQuickFix {
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

			FtlNameValuePair nameValuePair;

			if (psiElement instanceof FtlNameValuePair) {
				nameValuePair = (FtlNameValuePair) psiElement;
			} else {
				nameValuePair = PsiTreeUtil.getParentOfType(psiElement, FtlNameValuePair.class);
			}

			if (nameValuePair == null) {
				return;
			}

			nameValuePair.delete();
		}
	}
	private static class RemoveFreemarkerTagQuickFix implements LocalQuickFix {
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

			FtlMacro ftlMacro;

			if (psiElement instanceof FtlMacro) {
				ftlMacro = (FtlMacro) psiElement;
			} else {
				ftlMacro = PsiTreeUtil.getParentOfType(psiElement, FtlMacro.class);
			}

			if (ftlMacro == null) {
				return;
			}

			ftlMacro.delete();
		}
	}
	private static class RenameFreemarkerAttributeQuickFix implements LocalQuickFix {
		private String newName;

		public RenameFreemarkerAttributeQuickFix(String newName) {
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

			FtlNameValuePair nameValuePair;

			if (psiElement instanceof FtlNameValuePair) {
				nameValuePair = (FtlNameValuePair) psiElement;
			} else {
				nameValuePair = PsiTreeUtil.getParentOfType(psiElement, FtlNameValuePair.class);
			}

			if (nameValuePair == null) {
				return;
			}

			nameValuePair.setPairName(newName);
		}
	}

	private static class RenameFreemarkerTagQuickFix implements LocalQuickFix {
		private String newName;

		public RenameFreemarkerTagQuickFix(String newName) {
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
		public @NotNull IntentionPreviewInfo generatePreview(@NotNull Project project, @NotNull ProblemDescriptor previewDescriptor) {
			return IntentionPreviewInfo.EMPTY;
		}

		@Override
		public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
			PsiElement psiElement = descriptor.getPsiElement();

			FtlMacro ftlMacro;

			if (psiElement instanceof FtlMacro) {
				ftlMacro = (FtlMacro) psiElement;
			} else {
				ftlMacro = PsiTreeUtil.getParentOfType(psiElement, FtlMacro.class);
			}

			if (ftlMacro == null) {
				return;
			}

			LiferayFreemarkerTaglibs.setLocalName(ftlMacro, newName);
		}
	}
	private static class RenameFreemarkerNamespaceQuickFix implements LocalQuickFix {
		private final String newNamespace;
		public RenameFreemarkerNamespaceQuickFix(String newNamespace) {
			this.newNamespace = newNamespace;
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

			FtlMacro ftlMacro;

			if (psiElement instanceof FtlMacro) {
				ftlMacro = (FtlMacro) psiElement;
			} else {
				ftlMacro = PsiTreeUtil.getParentOfType(psiElement, FtlMacro.class);
			}

			if (ftlMacro == null) {
				return;
			}

			LiferayFreemarkerTaglibs.setNamespace(ftlMacro, newNamespace);
		}
	}
}
