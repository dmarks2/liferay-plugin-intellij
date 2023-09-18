package de.dm.intellij.liferay.language.properties;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.lang.properties.psi.impl.PropertyImpl;
import com.intellij.lang.properties.psi.impl.PropertyKeyImpl;
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

public class LiferayPropertiesDeprecationInfoHolder extends AbstractLiferayInspectionInfoHolder<LiferayPropertiesDeprecationInfoHolder> {

	private String myPropertyName;

	private static LiferayPropertiesDeprecationInfoHolder createProperty(float majorLiferayVersion, String message, String ticket, String propertyName) {
		return
				new LiferayPropertiesDeprecationInfoHolder()
						.property(propertyName)
						.majorLiferayVersion(majorLiferayVersion)
						.message(message)
						.ticket(ticket);
	}

	public static ListWrapper<LiferayPropertiesDeprecationInfoHolder> createProperties(float majorLiferayVersion, String message, String ticket, String... properties) {
		List<LiferayPropertiesDeprecationInfoHolder> result = new ArrayList<>();

		for (String property : properties) {
			result.add(createProperty(majorLiferayVersion, message, ticket, property));
		}

		return new ListWrapper<>(result);
	}

	public LiferayPropertiesDeprecationInfoHolder property(String propertyName) {
		this.myPropertyName = propertyName;

		return this;
	}

	public void visitProperty(ProblemsHolder holder, PropertyImpl property) {
		if (
				(StringUtil.isNotEmpty(myPropertyName)) &&
				(
					(Objects.equals(myPropertyName, property.getName())) ||
					(StringUtil.endsWith(myPropertyName, ".*") && Objects.requireNonNull(property.getName()).startsWith(Objects.requireNonNull(StringUtil.substringBefore(myPropertyName, ".*"))))
				)
		) {
			holder.registerProblem(property, getDeprecationMessage(), quickFixes);
		}
	}

}
