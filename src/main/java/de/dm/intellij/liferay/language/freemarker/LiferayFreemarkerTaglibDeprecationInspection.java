package de.dm.intellij.liferay.language.freemarker;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.freemarker.psi.FtlNameValuePair;
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
import static de.dm.intellij.liferay.util.LiferayTaglibAttributes.LPS_125256_CLAY_ALERT;

public class LiferayFreemarkerTaglibDeprecationInspection extends AbstractLiferayDeprecationInspection<LiferayFreemarkerTaglibDeprecationInfoHolder> {

	private static final List<LiferayFreemarkerTaglibDeprecationInfoHolder> TAGLIB_DEPRECATIONS = new ArrayList<>();

	static {
		TAGLIB_DEPRECATIONS.addAll(
				createAttributes(LPS_125256_CLAY_ALERT
				).quickfix(removeAttribute())
		);
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
				}
			}
		};
	}

	private static LocalQuickFix removeAttribute() {
		return new RemoveFreemarkerAttributeQuickFix();
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
}
