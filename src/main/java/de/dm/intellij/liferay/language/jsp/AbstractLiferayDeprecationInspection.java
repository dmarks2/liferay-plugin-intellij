package de.dm.intellij.liferay.language.jsp;

import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElementVisitor;
import de.dm.intellij.liferay.util.LiferayInspectionsGroupNames;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class AbstractLiferayDeprecationInspection<T extends AbstractLiferayInspectionInfoHolder<?>> extends LocalInspectionTool {

	@Override
	public boolean isEnabledByDefault() {
		return true;
	}

	@Nls
	@NotNull
	@Override
	public String getGroupDisplayName() {
		return LiferayInspectionsGroupNames.LIFERAY_GROUP_NAME;
	}

	@NotNull
	@Override
	public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
		return doBuildVisitor(holder, isOnTheFly, getInspectionInfoHolders());
	}

	protected abstract List<T> getInspectionInfoHolders();

	protected abstract PsiElementVisitor doBuildVisitor(ProblemsHolder holder, boolean isOnTheFly, List<T> inspectionInfoHolders);
}
