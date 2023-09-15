package de.dm.intellij.liferay.language.groovy;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.util.PsiTreeUtil;
import de.dm.intellij.liferay.language.java.LiferayJavaDeprecations;
import de.dm.intellij.liferay.language.jsp.AbstractLiferayInspectionInfoHolder;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.groovy.GroovyFileType;
import org.jetbrains.plugins.groovy.lang.psi.GroovyFile;
import org.jetbrains.plugins.groovy.lang.psi.api.toplevel.imports.GrImportStatement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LiferayGroovyDeprecationInfoHolder extends AbstractLiferayInspectionInfoHolder<LiferayGroovyDeprecationInfoHolder> {

	private String myImportStatement;

	private static LiferayGroovyDeprecationInfoHolder createImportStatement(float majorLiferayVersion, String message, String ticket, String importStatement) {
		return
				new LiferayGroovyDeprecationInfoHolder()
						.importStatement(importStatement)
						.majorLiferayVersion(majorLiferayVersion)
						.message(message)
						.ticket(ticket);
	}

	public static ListWrapper<LiferayGroovyDeprecationInfoHolder> createImportStatements(LiferayJavaDeprecations.JavaImportDeprecation importDeprecation) {
		List<LiferayGroovyDeprecationInfoHolder> result = new ArrayList<>();

		for (int i = 0; i < importDeprecation.importStatements().length; i++) {
			LiferayGroovyDeprecationInfoHolder deprecationInfoHolder = createImportStatement(importDeprecation.majorLiferayVersion(), importDeprecation.message(), importDeprecation.ticket(), importDeprecation.importStatements()[i]);

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

	public LiferayGroovyDeprecationInfoHolder importStatement(String importStatement) {
		this.myImportStatement = importStatement;

		return this;
	}

	public void visitImportStatement(ProblemsHolder holder, GrImportStatement statement) {
		if (
				(StringUtil.isNotEmpty(myImportStatement)) &&
				(Objects.equals(myImportStatement, statement.getImportFqn()))
		) {
			holder.registerProblem(statement, getDeprecationMessage(), quickFixes);
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

			GrImportStatement statement;

			if (psiElement instanceof GrImportStatement) {
				statement = (GrImportStatement) psiElement;
			} else {
				statement = PsiTreeUtil.getParentOfType(psiElement, GrImportStatement.class);
			}

			if (statement == null) {
				return;
			}

			GroovyFile aFile = createDummyGroovyFile(project, "import " + newName + ";");

			GrImportStatement newStatement = extractImport(aFile);

			if (newStatement != null) {
				CodeStyleManager codeStyleManager = CodeStyleManager.getInstance(project);

				newStatement = (GrImportStatement) codeStyleManager.reformat(newStatement);

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

			GrImportStatement statement;

			if (psiElement instanceof GrImportStatement) {
				statement = (GrImportStatement) psiElement;
			} else {
				statement = PsiTreeUtil.getParentOfType(psiElement, GrImportStatement.class);
			}

			if (statement == null) {
				return;
			}

			statement.delete();
		}
	}

	protected static GroovyFile createDummyGroovyFile(Project project, String text) {
		return (GroovyFile) PsiFileFactory.getInstance(project).createFileFromText("_Dummy_." + GroovyFileType.GROOVY_FILE_TYPE.getDefaultExtension(), GroovyFileType.GROOVY_FILE_TYPE, text);
	}

	private static GrImportStatement extractImport(GroovyFile aFile) {
		GrImportStatement[] importStatements = aFile.getImportStatements();

		if (importStatements.length > 0) {
			return importStatements[0];
		}

		return null;
	}
}
