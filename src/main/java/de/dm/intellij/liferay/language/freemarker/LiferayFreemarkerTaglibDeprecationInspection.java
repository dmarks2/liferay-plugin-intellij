package de.dm.intellij.liferay.language.freemarker;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.freemarker.psi.FtlNameValuePair;
import com.intellij.freemarker.psi.directives.FtlMacro;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlTag;
import de.dm.intellij.liferay.language.jsp.AbstractLiferayDeprecationInspection;
import de.dm.intellij.liferay.language.jsp.LiferayJspTaglibDeprecationInfoHolder;
import de.dm.intellij.liferay.util.LiferayInspectionsGroupNames;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static de.dm.intellij.liferay.language.freemarker.LiferayFreemarkerTaglibDeprecationInfoHolder.createAttributes;
import static de.dm.intellij.liferay.language.freemarker.LiferayFreemarkerTaglibDeprecationInfoHolder.createTags;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_125256_CLAY_ALERT;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_125256_CLAY_BUTTON_ARIA_LABEL;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_60753_ASSET_CATEGORIES_NAVIGATION;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_62922_BUTTON_ITEM;
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_70442_AUI_TOOL;
import static de.dm.intellij.liferay.util.LiferayTaglibs.TAGLIB_URI_LIFERAY_ASSET;

public class LiferayFreemarkerTaglibDeprecationInspection extends AbstractLiferayDeprecationInspection<LiferayFreemarkerTaglibDeprecationInfoHolder> {

	private static final List<LiferayFreemarkerTaglibDeprecationInfoHolder> TAGLIB_DEPRECATIONS = new ArrayList<>();

	static {
		TAGLIB_DEPRECATIONS.addAll(
				createAttributes(LPS_125256_CLAY_ALERT
				).quickfix(removeAttribute())
		);
		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_70442_AUI_TOOL).quickfix(removeTag()));

		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_60753_ASSET_CATEGORIES_NAVIGATION).quickfix(renameNamespace(TAGLIB_URI_LIFERAY_ASSET)));

		TAGLIB_DEPRECATIONS.addAll(createAttributes(LPS_125256_CLAY_BUTTON_ARIA_LABEL).quickfix(renameAttribute("aria\\-label")));

		TAGLIB_DEPRECATIONS.addAll(createTags(LPS_62922_BUTTON_ITEM).quickfix(renameTag("button")));
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
