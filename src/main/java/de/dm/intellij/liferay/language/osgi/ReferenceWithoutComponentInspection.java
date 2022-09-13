package de.dm.intellij.liferay.language.osgi;

import com.intellij.codeInspection.AbstractBaseJavaLocalInspectionTool;
import com.intellij.codeInspection.InspectionManager;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.util.PsiUtil;
import de.dm.intellij.liferay.util.LiferayInspectionsGroupNames;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ReferenceWithoutComponentInspection extends AbstractBaseJavaLocalInspectionTool {

    @Override
    public boolean isEnabledByDefault() {
        return true;
    }

    @Nls
    @NotNull
    @Override
    public String getDisplayName() {
        return "Reference injection without component check";
    }

    @Override
    public String getStaticDescription() {
        return "Check if a reference annoation is used without the class being annotated as component.";
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

    public ProblemDescriptor @Nullable [] checkMethod(@NotNull PsiMethod method, @NotNull InspectionManager manager, boolean isOnTheFly) {
        List<ProblemDescriptor> problemDescriptors = new ArrayList<>();

        PsiAnnotation annotation = method.getAnnotation("org.osgi.service.component.annotations.Reference");

        if (annotation != null) {
            PsiClass containingClass = method.getContainingClass();

            if (containingClass != null) {
                if (! (PsiUtil.isAbstractClass(containingClass) || containingClass.isInterface()) ) {
                    PsiAnnotation classAnnotation = containingClass.getAnnotation("org.osgi.service.component.annotations.Component");

                    if (classAnnotation == null) {
                        ProblemDescriptor problemDescriptor = manager.createProblemDescriptor(
                                method.getNameIdentifier(),
                                "Reference annotation on a method where the class " + containingClass.getQualifiedName() + " is not annotated with @Component does not work.",
                                isOnTheFly,
                                null,
                                ProblemHighlightType.GENERIC_ERROR
                        );

                        problemDescriptors.add(problemDescriptor);
                    }
                }
            }

        }

        return problemDescriptors.toArray(new ProblemDescriptor[0]);
    }

    public ProblemDescriptor @Nullable [] checkField(@NotNull PsiField field, @NotNull InspectionManager manager, boolean isOnTheFly) {
        List<ProblemDescriptor> problemDescriptors = new ArrayList<>();

        PsiAnnotation annotation = field.getAnnotation("org.osgi.service.component.annotations.Reference");

        if (annotation != null) {
            PsiClass containingClass = field.getContainingClass();

            if (containingClass != null) {
                if (! (PsiUtil.isAbstractClass(containingClass) || containingClass.isInterface()) ) {
                    PsiAnnotation classAnnotation = containingClass.getAnnotation("org.osgi.service.component.annotations.Component");

                    if (classAnnotation == null) {
                        ProblemDescriptor problemDescriptor = manager.createProblemDescriptor(
                                field.getNameIdentifier(),
                                "Reference annotation on a field where the class " + containingClass.getQualifiedName() + " is not annotated with @Component does not work.",
                                isOnTheFly,
                                null,
                                ProblemHighlightType.GENERIC_ERROR
                        );

                        problemDescriptors.add(problemDescriptor);
                    }
                }
            }
        }

        return problemDescriptors.toArray(new ProblemDescriptor[0]);
    }
}
