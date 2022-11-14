package de.dm.intellij.liferay.language.osgi;

import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.codeInspection.util.IntentionFamilyName;
import com.intellij.codeInspection.util.IntentionName;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.refactoring.rename.RenameProcessor;
import de.dm.intellij.liferay.util.LiferayInspectionsGroupNames;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

public class MetaConfigurationInvalidIdInspection extends LocalInspectionTool {

    @Override
    public boolean isEnabledByDefault() {
        return true;
    }

    @Nls
    @NotNull
    @Override
    public String getDisplayName() {
        return "Meta Configuration with a wrong ID";
    }

    @Override
    public String getStaticDescription() {
        return "Checks that the ID of a Meta Configuration matches the full qualified class name.";
    }

    @Nls
    @NotNull
    @Override
    public String getGroupDisplayName() {
        return LiferayInspectionsGroupNames.LIFERAY_GROUP_NAME;
    }

    @NotNull
    @Override
    public String[] getGroupPath() {
        return new String[] {
                getGroupDisplayName(),
                LiferayInspectionsGroupNames.OSGI_GROUP_NAME
        };
    }

    @Override
    public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
        return new PsiElementVisitor() {
            @Override
            public void visitElement(@NotNull PsiElement element) {
                if (element instanceof PsiLiteralExpression) {
                    if (MetaConfigurationReference.isMetaConfigurationIdElement(element)) {
                        String id = StringUtil.unquoteString(element.getText());

                        PsiClass psiClass = PsiTreeUtil.getParentOfType(element, PsiClass.class);

                        if (psiClass != null) {
                            if (! StringUtil.equals(id, psiClass.getQualifiedName())) {
                                holder.registerProblem(element,
                                        "ID does not match full qualified class name",
                                        ProblemHighlightType.WARNING,
                                        new LocalQuickFix() {

                                            @Override
                                            public @IntentionName @NotNull String getName() {
                                                return "Rename to '" + psiClass.getQualifiedName() + "'";
                                            }

                                            @Override
                                            public @IntentionFamilyName @NotNull String getFamilyName() {
                                                return "Rename";
                                            }

                                            @Override
                                            public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
                                                PsiElement element = descriptor.getPsiElement();

                                                DumbService dumbService = DumbService.getInstance(project);

                                                dumbService.smartInvokeLater(() -> {
                                                    if (psiClass.getQualifiedName() != null) {
                                                        new RenameProcessor(element.getProject(), element, psiClass.getQualifiedName(), false, false).run();
                                                    }
                                                });
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
}
