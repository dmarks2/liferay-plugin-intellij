package de.dm.intellij.liferay.language.java;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.PsiImportList;
import com.intellij.psi.PsiImportStatement;
import com.intellij.psi.PsiImportStatementBase;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiMethodCallExpression;
import com.intellij.psi.PsiReferenceExpression;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.util.PsiTreeUtil;
import de.dm.intellij.liferay.language.jsp.AbstractLiferayInspectionInfoHolder;
import de.dm.intellij.liferay.util.ProjectUtils;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LiferayJavaDeprecationInfoHolder extends AbstractLiferayInspectionInfoHolder<LiferayJavaDeprecationInfoHolder> {

	private String myImportStatement;

	private String myMethodName;

	private static LiferayJavaDeprecationInfoHolder createImportStatement(float majorLiferayVersion, String message, String ticket, String importStatement) {
		return
				new LiferayJavaDeprecationInfoHolder()
						.importStatement(importStatement)
						.majorLiferayVersion(majorLiferayVersion)
						.message(message)
						.ticket(ticket);
	}
	private static LiferayJavaDeprecationInfoHolder createMethodCall(float majorLiferayVersion, String message, String ticket, String methodName) {
		return
				new LiferayJavaDeprecationInfoHolder()
						.methodName(methodName)
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

	public static ListWrapper<LiferayJavaDeprecationInfoHolder> createMethodCalls(LiferayJavaDeprecations.JavaMethodCallDeprecation methodCallDeprecation) {
		List<LiferayJavaDeprecationInfoHolder> result = new ArrayList<>();

		for (int i = 0; i < methodCallDeprecation.methodSignatures().length; i++) {
			LiferayJavaDeprecationInfoHolder deprecationInfoHolder = createMethodCall(methodCallDeprecation.majorLiferayVersion(), methodCallDeprecation.message(), methodCallDeprecation.ticket(), methodCallDeprecation.methodSignatures()[i]);

			if (
				(i < methodCallDeprecation.newNames().length) &&
				(StringUtil.isNotEmpty(methodCallDeprecation.newNames()[i]))
			) {
				deprecationInfoHolder = deprecationInfoHolder.quickfix(renameMethodCall(methodCallDeprecation.newNames()[i]));
			} else {
				deprecationInfoHolder = deprecationInfoHolder.quickfix(removeMethodCall());
			}

			result.add(deprecationInfoHolder);
		}

		return new ListWrapper<>(result);
	}

	public LiferayJavaDeprecationInfoHolder importStatement(String importStatement) {
		this.myImportStatement = importStatement;

		return this;
	}
	public LiferayJavaDeprecationInfoHolder methodName(String methodName) {
		this.myMethodName = methodName;

		return this;
	}

	public void visitImportStatement(ProblemsHolder holder, PsiImportStatement statement) {
		visitImportStatement(holder, statement, statement);
	}
	public void visitImportStatement(ProblemsHolder holder, PsiImportStatement statement, PsiElement problemElement) {
		if (
				(isApplicableLiferayVersion(statement)) &&
				(StringUtil.isNotEmpty(myImportStatement)) &&
				(Objects.equals(myImportStatement, statement.getQualifiedName()))
		) {
			holder.registerProblem(problemElement, getDeprecationMessage(), quickFixes);
		}
	}

	public void visitMethodCallExpression(ProblemsHolder holder, PsiMethodCallExpression methodCallExpression) {
		if (
				(isApplicableLiferayVersion(methodCallExpression)) &&
				(StringUtil.isNotEmpty(myMethodName)) &&
				(Objects.equals(myMethodName, ProjectUtils.getMethodCallSignature(methodCallExpression)))
		) {
			holder.registerProblem(methodCallExpression, getDeprecationMessage(), quickFixes);
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

	private static LocalQuickFix removeMethodCall() {
		return new RemoveMethodCallQuickFix();
	}
	private static class RemoveMethodCallQuickFix implements LocalQuickFix {
		@Nls
		@NotNull
		@Override
		public String getFamilyName() {
			return "Remove Method Call";
		}

		@Nls
		@NotNull
		@Override
		public String getName() {
			return "Remove Method Call";
		}

		@Override
		public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
			PsiElement psiElement = descriptor.getPsiElement();

			PsiMethodCallExpression methodCallExpression;

			if (psiElement instanceof PsiMethodCallExpression) {
				methodCallExpression = (PsiMethodCallExpression) psiElement;
			} else {
				methodCallExpression = PsiTreeUtil.getParentOfType(psiElement, PsiMethodCallExpression.class);
			}

			if (methodCallExpression == null) {
				return;
			}

			methodCallExpression.delete();
		}
	}


	private static LocalQuickFix renameMethodCall(String newName) {
		return new RenameMethodCallQuickFix(newName);
	}
	private static class RenameMethodCallQuickFix implements LocalQuickFix {

		private final String newName;

		private RenameMethodCallQuickFix(String newName) {
			this.newName = newName;
		}

		@Nls
		@NotNull
		@Override
		public String getFamilyName() {
			return "Rename Method Call";
		}

		@Nls
		@NotNull
		@Override
		public String getName() {
			return "Rename Method Call to " + newName;
		}

		@Override
		public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
			PsiElement psiElement = descriptor.getPsiElement();

			PsiMethodCallExpression methodCallExpression;

			if (psiElement instanceof PsiMethodCallExpression) {
				methodCallExpression = (PsiMethodCallExpression) psiElement;
			} else {
				methodCallExpression = PsiTreeUtil.getParentOfType(psiElement, PsiMethodCallExpression.class);
			}

			if (methodCallExpression == null) {
				return;
			}

			PsiReferenceExpression methodExpression = methodCallExpression.getMethodExpression();

			PsiExpression qualifierExpression = methodExpression.getQualifierExpression();

			if (qualifierExpression != null) {
				PsiElementFactory factory = JavaPsiFacade.getInstance(project).getElementFactory();

				PsiMethodCallExpression newMethodCallExpression;

				if (StringUtil.contains(newName, ".")) {
					newMethodCallExpression = (PsiMethodCallExpression) factory.createExpressionFromText(newName + "()", null);
				} else {
					newMethodCallExpression = (PsiMethodCallExpression) factory.createExpressionFromText("var." + newName + "()", null);

					PsiExpression newQualifierExpression = newMethodCallExpression.getMethodExpression().getQualifierExpression();

					if (newQualifierExpression != null) {
						newQualifierExpression.replace(qualifierExpression);
					}
				}

				newMethodCallExpression.getArgumentList().replace(methodCallExpression.getArgumentList());

				methodCallExpression.replace(newMethodCallExpression);
			}
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
