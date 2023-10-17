package de.dm.intellij.liferay.language.groovy;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.PsiMethodCallExpression;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiType;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.util.PsiTreeUtil;
import de.dm.intellij.liferay.language.java.LiferayJavaDeprecationInfoHolder;
import de.dm.intellij.liferay.language.java.LiferayJavaDeprecations;
import de.dm.intellij.liferay.language.jsp.AbstractLiferayInspectionInfoHolder;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.groovy.GroovyFileType;
import org.jetbrains.plugins.groovy.lang.psi.GroovyFile;
import org.jetbrains.plugins.groovy.lang.psi.GroovyPsiElementFactory;
import org.jetbrains.plugins.groovy.lang.psi.api.GroovyReference;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.expressions.GrExpression;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.expressions.GrReferenceExpression;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.expressions.path.GrMethodCallExpression;
import org.jetbrains.plugins.groovy.lang.psi.api.toplevel.imports.GrImportStatement;
import org.jetbrains.plugins.groovy.lang.resolve.api.GroovyMethodCallReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LiferayGroovyDeprecationInfoHolder extends AbstractLiferayInspectionInfoHolder<LiferayGroovyDeprecationInfoHolder> {

	private String myImportStatement;

	private String myMethodName;

	private static LiferayGroovyDeprecationInfoHolder createImportStatement(float majorLiferayVersion, String message, String ticket, String importStatement) {
		return
				new LiferayGroovyDeprecationInfoHolder()
						.importStatement(importStatement)
						.majorLiferayVersion(majorLiferayVersion)
						.message(message)
						.ticket(ticket);
	}

	private static LiferayGroovyDeprecationInfoHolder createMethodCall(float majorLiferayVersion, String message, String ticket, String methodName) {
		return
				new LiferayGroovyDeprecationInfoHolder()
						.methodName(methodName)
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

	public static ListWrapper<LiferayGroovyDeprecationInfoHolder> createMethodCalls(LiferayJavaDeprecations.JavaMethodCallDeprecation methodCallDeprecation) {
		List<LiferayGroovyDeprecationInfoHolder> result = new ArrayList<>();

		for (int i = 0; i < methodCallDeprecation.methodSignatures().length; i++) {
			LiferayGroovyDeprecationInfoHolder deprecationInfoHolder = createMethodCall(methodCallDeprecation.majorLiferayVersion(), methodCallDeprecation.message(), methodCallDeprecation.ticket(), methodCallDeprecation.methodSignatures()[i]);

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

	public LiferayGroovyDeprecationInfoHolder importStatement(String importStatement) {
		this.myImportStatement = importStatement;

		return this;
	}

	public LiferayGroovyDeprecationInfoHolder methodName(String methodName) {
		this.myMethodName = methodName;

		return this;
	}

	public void visitImportStatement(ProblemsHolder holder, GrImportStatement statement) {
		if (
				(isApplicableLiferayVersion(statement)) &&
				(StringUtil.isNotEmpty(myImportStatement)) &&
				(Objects.equals(myImportStatement, statement.getImportFqn()))
		) {
			holder.registerProblem(statement, getDeprecationMessage(), quickFixes);
		}
	}

	public void visitMethodCallExpression(ProblemsHolder holder, GrMethodCallExpression methodCallExpression) {
		if (
				(isApplicableLiferayVersion(methodCallExpression)) &&
				(StringUtil.isNotEmpty(myMethodName)) &&
				(Objects.equals(myMethodName, getMethodCallSignature(methodCallExpression)))
		) {
			holder.registerProblem(methodCallExpression, getDeprecationMessage(), quickFixes);
		}
	}

	private static String getMethodCallSignature(GrMethodCallExpression methodCallExpression) {
		GrExpression invokedExpression = methodCallExpression.getInvokedExpression();

		PsiReference reference = invokedExpression.getReference();

		if (reference instanceof GrReferenceExpression referenceExpression) {
			GrExpression qualifierExpression = referenceExpression.getQualifierExpression();

			if (qualifierExpression instanceof GrReferenceExpression qualifierReferenceExpression) {
				GroovyReference staticReference = qualifierReferenceExpression.getStaticReference();

				PsiElement resolve = staticReference.resolve();

				GroovyMethodCallReference callReference = methodCallExpression.getCallReference();

				if (callReference != null) {
					if (resolve instanceof PsiClass psiClass) {
						return psiClass.getQualifiedName() + "." + callReference.getMethodName() + "()";
					} else {
						PsiType type = qualifierReferenceExpression.getType();

						if (type != null) {
							return type.getCanonicalText() + "." + callReference.getMethodName() + "()";
						}

						return GroovyUtil.getMatchFromImports(methodCallExpression.getContainingFile(), staticReference.getCanonicalText()) + "." + callReference.getMethodName() + "()";
					}
				}
			}
		}

		return null;
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

			GrMethodCallExpression methodCallExpression;

			if (psiElement instanceof GrMethodCallExpression) {
				methodCallExpression = (GrMethodCallExpression) psiElement;
			} else {
				methodCallExpression = PsiTreeUtil.getParentOfType(psiElement, GrMethodCallExpression.class);
			}

			if (methodCallExpression == null) {
				return;
			}

			GrExpression invokedExpression = methodCallExpression.getInvokedExpression();

			PsiReference reference = invokedExpression.getReference();

			if (reference instanceof GrReferenceExpression referenceExpression) {
				GrExpression qualifierExpression = referenceExpression.getQualifierExpression();

				if (qualifierExpression instanceof GrReferenceExpression) {
					GroovyPsiElementFactory factory = GroovyPsiElementFactory.getInstance(project);

					GrMethodCallExpression newMethodCallExpression;

					if (StringUtil.contains(newName, ".")) {
						newMethodCallExpression = (GrMethodCallExpression) factory.createExpressionFromText(newName + "()", null);
					} else {
						newMethodCallExpression = (GrMethodCallExpression) factory.createExpressionFromText("var." + newName + "()", null);

						PsiReference newReference = newMethodCallExpression.getInvokedExpression().getReference();

						if (newReference instanceof GrReferenceExpression newReferenceExpression) {
							GrExpression newQualifierExpression = newReferenceExpression.getQualifierExpression();

							if (newQualifierExpression != null) {
								newQualifierExpression.replace(qualifierExpression);
							}
						}
					}

					newMethodCallExpression.getArgumentList().replace(methodCallExpression.getArgumentList());

					methodCallExpression.replace(newMethodCallExpression);
				}
			}
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

			GrMethodCallExpression methodCallExpression;

			if (psiElement instanceof GrMethodCallExpression) {
				methodCallExpression = (GrMethodCallExpression) psiElement;
			} else {
				methodCallExpression = PsiTreeUtil.getParentOfType(psiElement, GrMethodCallExpression.class);
			}

			if (methodCallExpression == null) {
				return;
			}

			methodCallExpression.delete();
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
