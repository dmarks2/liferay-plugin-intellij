package de.dm.intellij.liferay.language.jsp;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlTag;
import de.dm.intellij.liferay.language.java.LiferayJavaDeprecations;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LiferayJspJavaDeprecationInfoHolder extends AbstractLiferayInspectionInfoHolder<LiferayJspJavaDeprecationInfoHolder> {

	private String myImportStatement;

	private static LiferayJspJavaDeprecationInfoHolder createImportStatement(float majorLiferayVersion, String message, String ticket, String importStatement) {
		return
				new LiferayJspJavaDeprecationInfoHolder()
						.importStatement(importStatement)
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

	public LiferayJspJavaDeprecationInfoHolder importStatement(String importStatement) {
		this.myImportStatement = importStatement;

		return this;
	}

	public void visitImportDirective(ProblemsHolder holder, @NotNull XmlAttribute importDirective) {
		if (
				(StringUtil.isNotEmpty(myImportStatement)) &&
				(importDirective.getValue() != null) &&
				(importDirective.getValueElement() != null) &&
				(Objects.equals(myImportStatement, StringUtil.unquoteString(importDirective.getValue())))
		) {
			holder.registerProblem(importDirective.getValueElement(), getDeprecationMessage(), quickFixes);
		}
	}

	private static LocalQuickFix renameImport(String newName) {
		return new RenameImportQuickFix(newName);
	}

	private static class RenameImportQuickFix implements LocalQuickFix {
		private String newName;

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


}
