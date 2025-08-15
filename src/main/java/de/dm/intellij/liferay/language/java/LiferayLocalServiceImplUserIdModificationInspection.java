package de.dm.intellij.liferay.language.java;

import com.intellij.codeInspection.AbstractBaseJavaLocalInspectionTool;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.codeInspection.util.IntentionFamilyName;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.JavaElementVisitor;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiClassType;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiMethodCallExpression;
import com.intellij.psi.PsiStatement;
import com.intellij.psi.PsiType;
import com.intellij.psi.util.InheritanceUtil;
import com.intellij.psi.util.PsiTreeUtil;
import de.dm.intellij.liferay.util.LiferayInspectionsGroupNames;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class LiferayLocalServiceImplUserIdModificationInspection extends AbstractBaseJavaLocalInspectionTool {

	@Override
	public boolean isEnabledByDefault() {
		return true;
	}

	@Nls
	@NotNull
	@Override
	public String getDisplayName() {
		return "Potentially dangerous modifications of the userId property in LocalServiceImpl update methods.";
	}

	@Override
	public String @NotNull [] getGroupPath() {
		return new String[]{
				getGroupDisplayName(),
				LiferayInspectionsGroupNames.JAVA_GROUP_NAME
		};
	}

	@Nls
	@NotNull
	@Override
	public String getGroupDisplayName() {
		return LiferayInspectionsGroupNames.LIFERAY_GROUP_NAME;
	}

	@Override
	public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
		return new JavaElementVisitor() {
			@Override
			public void visitMethodCallExpression(@NotNull PsiMethodCallExpression expression) {
				super.visitMethodCallExpression(expression);

				if (isSetUserIdCall(expression)) {
					PsiMethod psiMethod = PsiTreeUtil.getParentOfType(expression, PsiMethod.class);

					if (isLocalServiceCall(psiMethod) && isUpdateMethod(psiMethod)) {
						PsiExpression qualifierExpression = expression.getMethodExpression().getQualifierExpression();

						if (qualifierExpression != null) {
							PsiType type = qualifierExpression.getType();

							if (isLiferayEntity(type)) {
								holder.registerProblem(
										expression,
										"Modifying userId in update method may change entity ownership and permissions",
										ProblemHighlightType.WARNING,
										new LocalQuickFix() {
											@Override
											public @IntentionFamilyName @NotNull String getFamilyName() {
												return "Remove setUserId call";
											}

											@Override
											public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor problemDescriptor) {
												PsiElement element = problemDescriptor.getPsiElement();

												if (element instanceof PsiMethodCallExpression) {
													PsiStatement statement = PsiTreeUtil.getParentOfType(element, PsiStatement.class);

													if (statement != null) {
														statement.delete();
													}
												}
											}
										}
								);
							}
						}
					}
				}
			}
		};
	}

	private boolean isLiferayEntity(PsiType type) {
		if (type instanceof PsiClassType psiClassType) {
			PsiClass psiClass = psiClassType.resolve();

			if (psiClass != null) {
				return 	extendsBaseModel(psiClass);
			}
		}

		return false;
	}


	private boolean extendsBaseModel(@Nullable PsiClass psiClass) {
		if (psiClass == null) {
			return false;
		}

		return InheritanceUtil.isInheritor(psiClass, "com.liferay.portal.kernel.model.BaseModel") ||
			   InheritanceUtil.isInheritor(psiClass, "com.liferay.portal.model.BaseModel");
	}

	private boolean isSetUserIdCall(PsiMethodCallExpression expression) {
		String methodName = expression.getMethodExpression().getReferenceName();

		return Objects.equals("setUserId", methodName);
	}

	private boolean isUpdateMethod(@Nullable PsiMethod method) {
		if (method == null) {
			return false;
		}

		String methodName = method.getName();

		return StringUtil.startsWith(methodName, "update");
	}

	private boolean isLocalServiceCall(@Nullable PsiMethod method) {
		if (method == null) {
			return false;
		}

		PsiClass containingClass = method.getContainingClass();

		if (containingClass == null) {
			return false;
		}

		String className = containingClass.getName();

		return className != null && StringUtil.endsWith(className, "LocalServiceImpl");
	}
}
