package de.dm.intellij.liferay.language.osgi;

import com.intellij.codeInspection.AbstractBaseJavaLocalInspectionTool;
import com.intellij.codeInspection.InspectionManager;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.compiler.inspection.ChangeSuperClassFix;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiAnnotationParameterList;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiIdentifier;
import com.intellij.psi.PsiTypeParameter;
import com.intellij.psi.StubBasedPsiElement;
import com.intellij.psi.impl.java.stubs.PsiClassStub;
import com.intellij.psi.impl.java.stubs.impl.PsiClassStubImpl;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.SearchScope;
import com.intellij.psi.search.searches.ClassInheritorsSearch;
import com.intellij.util.Query;
import de.dm.intellij.liferay.util.LiferayInspectionsGroupNames;
import de.dm.intellij.liferay.util.ProjectUtils;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ComponentServiceInheritanceInspection extends AbstractBaseJavaLocalInspectionTool {

    @Override
    public boolean isEnabledByDefault() {
        return true;
    }

    @Nls
    @NotNull
    @Override
    public String getDisplayName() {
        return "Component service class inheritance check";
    }

    @Override
    public String getStaticDescription() {
        return "Check if the class inherits from the component service(s).";
    }

    @Nls
    @NotNull
    @Override
    public String getGroupDisplayName() {
        return LiferayInspectionsGroupNames.LIFERAY_GROUP_NAME;
    }

    @Override
    public String @NotNull [] getGroupPath() {
        return new String[] {
                getGroupDisplayName(),
                LiferayInspectionsGroupNames.OSGI_GROUP_NAME
        };
    }

    @Nullable
    @Override
    public ProblemDescriptor[] checkClass(@NotNull PsiClass aClass, @NotNull InspectionManager manager, boolean isOnTheFly) {
        List<ProblemDescriptor> problemDescriptors = new ArrayList<>();

        if (!(aClass instanceof PsiTypeParameter) && !aClass.isEnum()) {
            PsiAnnotation annotation = aClass.getAnnotation("org.osgi.service.component.annotations.Component");
            if (annotation != null) {
                PsiAnnotationParameterList annotationParameterList = annotation.getParameterList();

                List<String> serviceClassNames = ComponentPropertiesCompletionContributor.getServiceClassNames(annotationParameterList);

                Project project = aClass.getProject();

                for (String serviceClassName : serviceClassNames) {
                    PsiClass serviceClass = ProjectUtils.getClassByName(project, serviceClassName, aClass);

                    if (serviceClass != null) {
                        if (!isInheritorOrSelf(project, serviceClass, aClass, true)) {
                            List<LocalQuickFix> quickFixes = new ArrayList<>();

                            PsiClass superClass = aClass.getSuperClass();

                            if (superClass != null) {
                                LocalQuickFix quickFix = new ChangeSuperClassFix(
                                        serviceClass,
                                        superClass,
                                        0,
                                        serviceClass.isInterface() && !aClass.isInterface()
                                );

                                quickFixes.add(quickFix);

                                SearchScope scope = GlobalSearchScope.allScope(project);
                                Query<PsiClass> query = ClassInheritorsSearch.search(serviceClass, scope, false);
                                int i = 1;

                                PsiClass[] psiClasses = query.toArray(new PsiClass[0]);
                                for (PsiClass psiClass : psiClasses) {
                                    if (psiClass.getSuperClass() != null) {
                                        if ((!isAnonymousClass(psiClass)) && (!isLocalClass(psiClass))) {
                                            quickFixes.add(
                                                    new ChangeSuperClassFix(
                                                            psiClass,
                                                            superClass,
                                                            i++,
                                                            psiClass.isInterface()
                                                    )
                                            );
                                        }
                                    }
                                }
                            }

                            PsiIdentifier nameIdentifier = aClass.getNameIdentifier();

                            if (nameIdentifier != null) {
                                ProblemDescriptor problemDescriptor = manager.createProblemDescriptor(
                                        nameIdentifier,
                                        "Class " + aClass.getQualifiedName() + " is not assignable to specified service " + serviceClass.getQualifiedName(),
                                        isOnTheFly,
                                        quickFixes.toArray(new LocalQuickFix[0]),
                                        ProblemHighlightType.GENERIC_ERROR
                                );

                                problemDescriptors.add(problemDescriptor);
                            }
                        }
                    }
                }
            }
        }

        return problemDescriptors.toArray(new ProblemDescriptor[0]);
    }

    private static boolean isInheritorOrSelf(Project project, PsiClass baseClass, PsiClass currentClass, boolean checkDeep) {
        if (baseClass.equals(currentClass)) {
            return true;
        }

        SearchScope scope = GlobalSearchScope.allScope(project);
        Query<PsiClass> query = ClassInheritorsSearch.search(baseClass, scope, checkDeep);

        AtomicBoolean result = new AtomicBoolean();

        query.forEach(psiClass -> {
            if (psiClass.equals(currentClass)) {
                result.set(true);
            }
        });

        return result.get();
    }

    private static boolean isLocalClass(PsiClass psiClass) {
        if (psiClass instanceof StubBasedPsiElement stubBasedPsiElement) {

			PsiClassStub<?> stub = (PsiClassStub) stubBasedPsiElement.getStub();

            return stub instanceof PsiClassStubImpl && ((PsiClassStubImpl) stub).isLocalClassInner();
        }

        return false;
    }

    private static boolean isAnonymousClass(PsiClass psiClass) {
        if (psiClass instanceof StubBasedPsiElement stubBasedPsiElement) {

			PsiClassStub<?> stub = (PsiClassStub) stubBasedPsiElement.getStub();
            return stub instanceof PsiClassStubImpl && ((PsiClassStubImpl) stub).isAnonymousInner();
        }
        return false;
    }
}
