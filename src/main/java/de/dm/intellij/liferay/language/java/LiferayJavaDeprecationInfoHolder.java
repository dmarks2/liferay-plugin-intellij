package de.dm.intellij.liferay.language.java;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiImportStatement;
import de.dm.intellij.liferay.language.jsp.AbstractLiferayInspectionInfoHolder;

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

	public static ListWrapper<LiferayJavaDeprecationInfoHolder> createImportStatements(float majorLiferayVersion, String message, String ticket, String... importStatements) {
		List<LiferayJavaDeprecationInfoHolder> result = new ArrayList<>();

		for (String importStatement : importStatements) {
			result.add(createImportStatement(majorLiferayVersion, message, ticket, importStatement));
		}

		return new ListWrapper<>(result);
	}

	public LiferayJavaDeprecationInfoHolder importStatement(String importStatement) {
		this.myImportStatement = importStatement;

		return this;
	}

	public void visitImportStatement(ProblemsHolder holder, PsiImportStatement statement) {
		if (
				(StringUtil.isNotEmpty(myImportStatement)) &&
				(Objects.equals(myImportStatement, statement.getQualifiedName()))
		) {
			holder.registerProblem(statement, getDeprecationMessage(), quickFixes);
		}
	}
}
