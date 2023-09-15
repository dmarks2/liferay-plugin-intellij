package de.dm.intellij.liferay.language.java;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.PsiImportList;
import com.intellij.psi.PsiImportStatement;
import com.intellij.psi.PsiImportStatementBase;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.util.PsiTreeUtil;
import de.dm.intellij.liferay.language.jsp.AbstractLiferayInspectionInfoHolder;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LiferayJavaDeprecationInfoHolder extends AbstractLiferayInspectionInfoHolder<LiferayJavaDeprecationInfoHolder> {

	private String myImportStatement;

	private static LiferayJavaDeprecationInfoHolder createImportStatement(float majorLiferayVersion, String message, String ticket, String importStatement) {
		return
				new LiferayJavaDeprecationInfoHolder()
						.importStatement(importStatement)
						.majorLiferayVersion(majorLiferayVersion)
						.message(message)
						.ticket(ticket);
	}

	public static ListWrapper<LiferayJavaDeprecationInfoHolder> createImportStatements(LiferayJavaDeprecations.JavaImportDeprecation importDeprecation) {
		List<LiferayJavaDeprecationInfoHolder> result = new ArrayList<>();

		for (int i = 0; i < importDeprecation.importStatements().length; i++) {
			LiferayJavaDeprecationInfoHolder deprecationInfoHolder = createImportStatement(importDeprecation.majorLiferayVersion(), importDeprecation.message(), importDeprecation.ticket(), importDeprecation.importStatements()[i]);

			if (
				(i < importDeprecation.newImportStatements().length) &&
				(StringUtil.isNotEmpty(importDeprecation.newImportStatements()[i]))
			) {
				deprecationInfoHolder = deprecationInfoHolder.quickfix(renameImport(importDeprecation.newImportStatements()[i]));
			} else {
				deprecationInfoHolder = deprecationInfoHolder.quickfix(removeImport());
			}

			result.add(deprecationInfoHolder);
		}

		return new ListWrapper<>(result);
	}

	public LiferayJavaDeprecationInfoHolder importStatement(String importStatement) {
		this.myImportStatement = importStatement;

		return this;
	}

	public void visitImportStatement(ProblemsHolder holder, PsiImportStatement statement) {
		visitImportStatement(holder, statement, statement);
	}
	public void visitImportStatement(ProblemsHolder holder, PsiImportStatement statement, PsiElement problemElement) {
		if (
				(StringUtil.isNotEmpty(myImportStatement)) &&
				(Objects.equals(myImportStatement, statement.getQualifiedName()))
		) {
			holder.registerProblem(problemElement, getDeprecationMessage(), quickFixes);
		}
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

	private static LocalQuickFix removeImport() {
		return new RemoveImportStatementQuickFix();
	}

	private static class RemoveImportStatementQuickFix implements LocalQuickFix {

		@Nls
		@NotNull
		@Override
		public String getFamilyName() {
			return "Remove Import Statement";
		}

		@Nls
		@NotNull
		@Override
		public String getName() {
			return "Remove Import Statement";
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

			statement.delete();
		}
	}

	private static PsiJavaFile createDummyJavaFile(Project project, String text) {
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
