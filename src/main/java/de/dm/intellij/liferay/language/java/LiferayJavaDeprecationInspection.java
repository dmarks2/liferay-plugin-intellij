package de.dm.intellij.liferay.language.java;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaElementVisitor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.PsiImportList;
import com.intellij.psi.PsiImportStatement;
import com.intellij.psi.PsiImportStatementBase;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.util.PsiTreeUtil;
import de.dm.intellij.liferay.language.jsp.AbstractLiferayDeprecationInspection;
import de.dm.intellij.liferay.util.LiferayInspectionsGroupNames;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static de.dm.intellij.liferay.language.java.LiferayJavaDeprecationInfoHolder.createImportStatements;

public class LiferayJavaDeprecationInspection extends AbstractLiferayDeprecationInspection<LiferayJavaDeprecationInfoHolder> {

	private static final List<LiferayJavaDeprecationInfoHolder> JAVA_DEPRECATIONS = new ArrayList<>();

	static {
		JAVA_DEPRECATIONS.addAll(createImportStatements(7.0f, "The classes from package com.liferay.util.bridges.mvc in util-bridges.jar were moved to a new package com.liferay.portal.kernel.portlet.bridges.mvc in portal-service.jar.", "LPS-50156",
				"com.liferay.util.bridges.mvc.ActionCommand").quickfix(renameImport("com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand")));
	}

	@Nls
	@NotNull
	@Override
	public String getDisplayName() {
		return "Liferay Java deprecations inspection";
	}

	@Override
	public String getStaticDescription() {
		return "Check for deprecated Liferay Java instructions.";
	}

	@Override
	public String @NotNull [] getGroupPath() {
		return new String[]{
				getGroupDisplayName(),
				LiferayInspectionsGroupNames.JAVA_GROUP_NAME
		};
	}

	@Override
	protected List<LiferayJavaDeprecationInfoHolder> getInspectionInfoHolders() {
		return JAVA_DEPRECATIONS;
	}

	@Override
	protected PsiElementVisitor doBuildVisitor(ProblemsHolder holder, boolean isOnTheFly, List<LiferayJavaDeprecationInfoHolder> inspectionInfoHolders) {
		return new JavaElementVisitor() {
			@Override
			public void visitImportStatement(@NotNull PsiImportStatement statement) {
				for (LiferayJavaDeprecationInfoHolder infoHolder : inspectionInfoHolders) {
					infoHolder.visitImportStatement(holder, statement);
				}
			}
		};
	}

	private static LocalQuickFix renameImport(String newName) {
		return new RenameImportStatementQuickFix(newName);
	}

	private static class RenameImportStatementQuickFix implements LocalQuickFix {
		private final String newName;

		public RenameImportStatementQuickFix(String newName) {
			this.newName = newName;
		}

		@Nls
		@NotNull
		@Override
		public String getFamilyName() {
			return "Rename Import Statement";
		}

		@Nls
		@NotNull
		@Override
		public String getName() {
			return "Rename Import Statement to " + newName;
		}

		@Override
		public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
			PsiElement psiElement = descriptor.getPsiElement();

			PsiImportStatement statement;

			if (psiElement instanceof PsiImportStatement) {
				statement = (PsiImportStatement) psiElement;
			} else {
				statement = PsiTreeUtil.getParentOfType(psiElement, PsiImportStatement.class);
			}

			if (statement == null) {
				return;
			}

			PsiJavaFile aFile = createDummyJavaFile(project, "import " + newName + ";");

			PsiImportStatementBase newStatement = extractImport(aFile);

			if (newStatement != null) {
				CodeStyleManager codeStyleManager = CodeStyleManager.getInstance(project);

				newStatement = (PsiImportStatement) codeStyleManager.reformat(newStatement);

				statement.replace(newStatement);
			}
		}
	}

	protected static PsiJavaFile createDummyJavaFile(Project project, String text) {
		return (PsiJavaFile) PsiFileFactory.getInstance(project).createFileFromText("_Dummy_." + JavaFileType.INSTANCE.getDefaultExtension(), JavaFileType.INSTANCE, text);
	}

	private static PsiImportStatementBase extractImport(PsiJavaFile aFile) {
		PsiImportList importList = aFile.getImportList();

		if (importList != null) {
			PsiImportStatementBase[] statements = importList.getImportStatements();

			if (statements.length > 0) {
				return statements[0];
			}
		}

		return null;
	}

}
