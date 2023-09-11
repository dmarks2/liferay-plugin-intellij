package de.dm.intellij.liferay.language.freemarker;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.freemarker.psi.FtlNameValuePair;
import com.intellij.freemarker.psi.directives.FtlMacro;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import de.dm.intellij.liferay.language.jsp.AbstractLiferayDeprecationInspection;
import de.dm.intellij.liferay.language.jsp.LiferayJspTaglibDeprecationInfoHolder;
import de.dm.intellij.liferay.util.LiferayInspectionsGroupNames;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static de.dm.intellij.liferay.language.freemarker.LiferayFreemarkerTaglibDeprecationInfoHolder.createAttributes;
import static de.dm.intellij.liferay.util.LiferayTaglibs.TAGLIB_URI_LIFERAY_CLAY;

public class LiferayFreemarkerTaglibDeprecationInspection extends AbstractLiferayDeprecationInspection<LiferayFreemarkerTaglibDeprecationInfoHolder> {

	private static final List<LiferayFreemarkerTaglibDeprecationInfoHolder> TAGLIB_DEPRECATIONS = new ArrayList<>();

	static {
		TAGLIB_DEPRECATIONS.addAll(
				createAttributes(7.4f, TAGLIB_URI_LIFERAY_CLAY, "alert", "The attributes closeable, componentId, contributorKey, data, defaultEventHandler, destroyOnHide, elementClasses, spritemap, style, type in `clay:alert` tag were removed.", "LPS-125256",
						"closeable", "componentId", "contributorKey", "data", "defaultEventHandler", "destroyOnHide", "elementClasses",
						"spritemap", "style", "type"
				)
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
}
