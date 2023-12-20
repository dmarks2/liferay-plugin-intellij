package de.dm.intellij.liferay.language.jsp;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.jsp.java.JspExpressionStatement;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiMethodCallExpression;
import com.intellij.psi.PsiReferenceExpression;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlTag;
import de.dm.intellij.liferay.language.java.LiferayJavaDeprecations;
import de.dm.intellij.liferay.util.ProjectUtils;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LiferayJspJavaDeprecationInfoHolder extends AbstractLiferayInspectionInfoHolder<LiferayJspJavaDeprecationInfoHolder> {

	private String myImportStatement;

	private String myMethodName;

	private static LiferayJspJavaDeprecationInfoHolder createImportStatement(float majorLiferayVersion, String message, String ticket, String importStatement) {
		return
				new LiferayJspJavaDeprecationInfoHolder()
						.importStatement(importStatement)
						.majorLiferayVersion(majorLiferayVersion)
						.message(message)
						.ticket(ticket);
	}

	private static LiferayJspJavaDeprecationInfoHolder createMethodCall(float majorLiferayVersion, String message, String ticket, String methodName) {
		return
				new LiferayJspJavaDeprecationInfoHolder()
						.methodName(methodName)
						.majorLiferayVersion(majorLiferayVersion)
						.message(message)
						.ticket(ticket);
	}

	public static ListWrapper<LiferayJspJavaDeprecationInfoHolder> createImportStatements(LiferayJavaDeprecations.JavaImportDeprecation importDeprecation) {
		List<LiferayJspJavaDeprecationInfoHolder> result = new ArrayList<>();

		for (int i = 0; i < importDeprecation.importStatements().length; i++) {
			LiferayJspJavaDeprecationInfoHolder deprecationInfoHolder = createImportStatement(importDeprecation.majorLiferayVersion(), importDeprecation.message(), importDeprecation.ticket(), importDeprecation.importStatements()[i]);

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

	public static ListWrapper<LiferayJspJavaDeprecationInfoHolder> createMethodCalls(LiferayJavaDeprecations.JavaMethodCallDeprecation methodCallDeprecation) {
		List<LiferayJspJavaDeprecationInfoHolder> result = new ArrayList<>();

		for (int i = 0; i < methodCallDeprecation.methodSignatures().length; i++) {
			LiferayJspJavaDeprecationInfoHolder deprecationInfoHolder = createMethodCall(methodCallDeprecation.majorLiferayVersion(), methodCallDeprecation.message(), methodCallDeprecation.ticket(), methodCallDeprecation.methodSignatures()[i]);

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

	public LiferayJspJavaDeprecationInfoHolder importStatement(String importStatement) {
		this.myImportStatement = importStatement;

		return this;
	}

	public LiferayJspJavaDeprecationInfoHolder methodName(String methodName) {
		this.myMethodName = methodName;

		return this;
	}

	public void visitImportDirective(ProblemsHolder holder, @NotNull XmlAttribute importDirective) {
		if (
				(isApplicableLiferayVersion(importDirective)) &&
				(StringUtil.isNotEmpty(myImportStatement)) &&
				(importDirective.getValue() != null) &&
				(importDirective.getValueElement() != null) &&
				(Objects.equals(myImportStatement, StringUtil.unquoteString(importDirective.getValue())))
		) {
			holder.registerProblem(importDirective.getValueElement(), getDeprecationMessage(), quickFixes);
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
		return new RenameImportQuickFix(newName);
	}

	private static class RenameImportQuickFix implements LocalQuickFix {
		private final String newName;

		public RenameImportQuickFix(String newName) {
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

			XmlAttributeValue value;

			if (psiElement instanceof XmlAttributeValue) {
				value = (XmlAttributeValue) psiElement;
			} else {
				value = PsiTreeUtil.getParentOfType(psiElement, XmlAttributeValue.class);
			}

			if (value == null) {
				return;
			}

			XmlAttribute xmlAttribute = PsiTreeUtil.getParentOfType(value, XmlAttribute.class);

			if (xmlAttribute != null) {
				xmlAttribute.setValue(newName);
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

			XmlAttributeValue value;

			if (psiElement instanceof XmlAttributeValue) {
				value = (XmlAttributeValue) psiElement;
			} else {
				value = PsiTreeUtil.getParentOfType(psiElement, XmlAttributeValue.class);
			}

			if (value == null) {
				return;
			}

			XmlTag xmlTag = PsiTreeUtil.getParentOfType(value, XmlTag.class);

			if (xmlTag != null) {
				xmlTag.delete();
			}
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

			JspExpressionStatement jspExpressionStatement = PsiTreeUtil.getParentOfType(methodCallExpression, JspExpressionStatement.class);

			if (jspExpressionStatement != null) {
				jspExpressionStatement.delete();
			}
		}
	}

}
