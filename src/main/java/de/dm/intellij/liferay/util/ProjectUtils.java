package de.dm.intellij.liferay.util;

import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.module.ModifiableModuleModel;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.LibraryOrderEntry;
import com.intellij.openapi.roots.ModifiableModelsProvider;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.OrderRootType;
import com.intellij.openapi.roots.impl.OrderEntryUtil;
import com.intellij.openapi.roots.libraries.Library;
import com.intellij.openapi.roots.libraries.LibraryTable;
import com.intellij.openapi.roots.libraries.LibraryTablesRegistrar;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiClassType;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiImportList;
import com.intellij.psi.PsiImportStatement;
import com.intellij.psi.PsiImportStaticStatement;
import com.intellij.psi.PsiJavaCodeReferenceElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiMethodCallExpression;
import com.intellij.psi.PsiModifier;
import com.intellij.psi.PsiModifierList;
import com.intellij.psi.PsiPackage;
import com.intellij.psi.PsiPackageStatement;
import com.intellij.psi.PsiParameter;
import com.intellij.psi.PsiParameterList;
import com.intellij.psi.PsiReferenceExpression;
import com.intellij.psi.PsiType;
import com.intellij.psi.impl.JavaConstantExpressionEvaluator;
import com.intellij.psi.impl.compiled.ClsFieldImpl;
import com.intellij.psi.impl.source.PsiClassReferenceType;
import com.intellij.psi.impl.source.PsiFieldImpl;
import com.intellij.psi.impl.source.tree.JavaSourceUtil;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ArrayUtil;
import com.intellij.util.DisposeAwareRunnable;
import com.intellij.util.PathUtilRt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProjectUtils {

    public static final String REFERENCE_PLACEHOLDER = "+";

    public static void runDumbAware(final Project project, final Runnable r) {
        if (DumbService.isDumbAware(r)) {
            r.run();
        } else {
            DumbService.getInstance(project).runWhenSmart(DisposeAwareRunnable.create(r, project));
        }
    }

    public static void runDumbAwareLater(final Project project, final Runnable r) {
        if (DumbService.isDumbAware(r)) {
            ApplicationManager.getApplication().invokeLater(r);
        } else {
            DumbService.getInstance(project).smartInvokeLater(DisposeAwareRunnable.create(r, project));
        }
    }

    public static void runWriteAction(@NotNull final Project project, @NotNull final Runnable writeAction) {
        Application application = ApplicationManager.getApplication();

        if (application.isDispatchThread()) {
            application.runWriteAction(writeAction);
        } else {
            runDumbAwareLater(project, writeAction);
        }
    }

    public static Collection<Library> findLibrariesByName(final String name, Module module) {
        final Collection<Library> result = new ArrayList<Library>();

        ModuleRootManager.getInstance(module).orderEntries().forEachLibrary(
                library -> {
                    if (library.getName() != null && library.getName().contains(name)) {
                        result.add(library);
                    }
                    return true;
                }
        );

        return result;
    }

    private static LibraryOrderEntry getLibraryIntern(String name, ModifiableRootModel model) {
        return OrderEntryUtil.findLibraryOrderEntry(model, name);
    }

    private static Library createLibraryIntern(@NotNull Project project, String name) {
        LibraryTable libraryTable = LibraryTablesRegistrar.getInstance().getLibraryTable(project);
        Library result = libraryTable.getLibraryByName(name);
        if (result == null) {
            result = libraryTable.createLibrary(name);
        }
        return result;
    }

    public static void removeLibrary(@NotNull final Module module, String name) {
        if (!module.isDisposed()) {
            final ModifiableModelsProvider modelsProvider = ModifiableModelsProvider.getInstance();
            final ModifiableRootModel model = modelsProvider.getModuleModifiableModel(module);

            final LibraryOrderEntry libraryEntry = getLibraryIntern(name, model);

            if (libraryEntry != null) {
                ApplicationManager.getApplication().runWriteAction(
                        () -> {
                            Library library = libraryEntry.getLibrary();
                            if (library != null) {
                                LibraryTable table = library.getTable();
                                if (table != null) {
                                    table.removeLibrary(library);
                                    model.removeOrderEntry(libraryEntry);
                                    modelsProvider.commitModuleModifiableModel(model);
                                }
                            } else {
                                modelsProvider.disposeModuleModifiableModel(model);
                            }
                        }
                );
            } else {
                ApplicationManager.getApplication().runWriteAction(
                        () -> modelsProvider.disposeModuleModifiableModel(model)
                );
            }
        }
    }

    private static void fillLibraryIntern(@NotNull Module module, @NotNull Library library, @NotNull Collection<? extends VirtualFile> importRoots) {
        Library.ModifiableModel libraryModel = library.getModifiableModel();

        //remove old entries
        for (String root : library.getUrls(OrderRootType.CLASSES)) {
            libraryModel.removeRoot(root, OrderRootType.CLASSES);
        }

        for (VirtualFile importRoot : importRoots) {
            if (!ModuleUtilCore.projectContainsFile(module.getProject(), importRoot, false)) {
                libraryModel.addRoot(importRoot.getUrl(), OrderRootType.CLASSES);
            }
        }
        libraryModel.commit();
    }

    public static void updateLibrary(@NotNull final Module module, final String name, @NotNull final Collection<? extends VirtualFile> importRoots) {
        if (!module.isDisposed()) {
            final ModifiableModelsProvider modelsProvider = ModifiableModelsProvider.getInstance();
            final ModifiableRootModel model = modelsProvider.getModuleModifiableModel(module);
            final LibraryOrderEntry libraryEntry = getLibraryIntern(name, model);
            ApplicationManager.getApplication().runWriteAction(
                    () -> {
                        if (libraryEntry != null) {
                            Library library = libraryEntry.getLibrary();
                            if (library != null) {
                                fillLibraryIntern(module, library, importRoots);
                            } else {
                                model.removeOrderEntry(libraryEntry);
                                library = createLibraryIntern(module.getProject(), name);
                                fillLibraryIntern(module, library, importRoots);
                            }
                        } else {
                            Library library = createLibraryIntern(module.getProject(), name);
                            fillLibraryIntern(module, library, importRoots);
                            model.addLibraryEntry(library);
                        }

                        modelsProvider.commitModuleModifiableModel(model);
                    }
            );
        }
    }

    public static PsiClass getClassByName(Project project, String className, PsiElement context) {
        PsiType psiType = JavaPsiFacade.getInstance(project).getElementFactory().createTypeFromText(className, context);

        if (psiType instanceof PsiClassType) {
            PsiClassType psiClassType = (PsiClassType) psiType;

            return psiClassType.resolve();
        }

        return null;
    }

    @NotNull
    public static Collection<PsiField> getClassPublicStaticFields(@Nullable PsiClass psiClass) {
        Collection<PsiField> result = new ArrayList<>();

        if (psiClass != null) {
            for (PsiField psiField : psiClass.getFields()) {
                PsiModifierList modifierList = psiField.getModifierList();
                if (modifierList != null) {
                    if (
                            modifierList.hasModifierProperty(PsiModifier.PUBLIC) &&
                                    modifierList.hasModifierProperty(PsiModifier.STATIC)
                    ) {
                        result.add(psiField);
                    }
                }
            }
        }

        return result;
    }


    @Nullable
    public static PsiField getClassPublicStaticField(@Nullable PsiClass psiClass, @NotNull String fieldName) {
        Collection<PsiField> fields = getClassPublicStaticFields(psiClass);
        for (PsiField field : fields) {
            if (fieldName.equals(field.getName())) {
                return field;
            }
        }

        return null;
    }

    public static List<String> getMethodParameterQualifiedNames(@NotNull PsiMethod psiMethod) {
        List<String> result = new ArrayList<>();

        PsiParameterList parameterList = psiMethod.getParameterList();

        for (PsiParameter psiParameter : parameterList.getParameters()) {
            PsiType psiType = psiParameter.getType();

            if (psiType instanceof PsiClassReferenceType) {
                PsiClassReferenceType psiClassReferenceType = (PsiClassReferenceType)psiType;

                PsiJavaCodeReferenceElement psiJavaCodeReferenceElement = psiClassReferenceType.getReference();

                String qualifiedName = getQualifiedNameWithoutResolve(psiJavaCodeReferenceElement, false);

                result.add(qualifiedName);
            }
        }

        return result;
    }


    @NotNull
    public static String getMatchFromPackageStatementOrImports(@NotNull PsiFile psiFile, @NotNull String className) {
        PsiPackageStatement packageStatement = PsiTreeUtil.getChildOfType(psiFile, PsiPackageStatement.class);
        if (packageStatement != null) {
            //search for own class and all inner classes
            PsiClass[] psiClasses = PsiTreeUtil.getChildrenOfType(psiFile, PsiClass.class);
            if (psiClasses != null) {
                for (PsiClass psiClass : psiClasses) {
                    if (className.equals(psiClass.getName())) {
                        return StringUtil.getQualifiedName(packageStatement.getPackageName(), className);
                    }
                }
            }
        }

        PsiImportList psiImportList = PsiTreeUtil.getChildOfType(psiFile, PsiImportList.class);
        if (psiImportList != null) {
            //search for import statements
            PsiImportStatement[] psiImportStatements = PsiTreeUtil.getChildrenOfType(psiImportList, PsiImportStatement.class);
            if (psiImportStatements != null) {
                for (PsiImportStatement psiImportStatement : psiImportStatements) {
                    String qualifiedName = psiImportStatement.getQualifiedName();
                    if (qualifiedName != null) {
                        if (
                                (className.equals(qualifiedName)) ||
                                        (className.equals(StringUtil.getShortName(qualifiedName)))
                        ) {
                            return qualifiedName;
                        }
                    }
                }
            }
        }

        //if not found in import statements and not found in own class and not found in inner classes, it is probably a class in the same package (and implicitly imported)
        if (packageStatement != null) {
            return StringUtil.getQualifiedName(packageStatement.getPackageName(), className);
        }

        return className;
    }

    @NotNull
    public static String getMatchFromStaticImports(@NotNull PsiFile psiFile, @NotNull String memberName) {
        PsiImportList psiImportList = PsiTreeUtil.getChildOfType(psiFile, PsiImportList.class);
        if (psiImportList != null) {
            PsiImportStaticStatement[] psiImportStaticStatements = PsiTreeUtil.getChildrenOfType(psiImportList, PsiImportStaticStatement.class);
            if (psiImportStaticStatements != null) {
                for (PsiImportStaticStatement psiImportStaticStatement : psiImportStaticStatements) {
                    PsiJavaCodeReferenceElement importReference = psiImportStaticStatement.getImportReference();
                    if (importReference != null) {
                        String qualifiedName = importReference.getQualifiedName();
                        if (qualifiedName != null) {
                            String importMemberName = StringUtil.getShortName(qualifiedName);
                            if (importMemberName.equals(memberName)) {
                                return qualifiedName;
                            }
                        }
                    }
                }
            }
        }

        return memberName;
    }

    @NotNull
    public static String getQualifiedNameWithoutResolve(@NotNull PsiJavaCodeReferenceElement psiJavaCodeReferenceElement, boolean stripMember) {
        if (psiJavaCodeReferenceElement.getNode() == null) {
            return "";
        }

        String referenceText = JavaSourceUtil.getReferenceText(psiJavaCodeReferenceElement);

        String memberText = null;

        boolean resolvedFromStaticImports = false;

        String qualifiedName = referenceText;

        if (stripMember) {
            if (StringUtil.contains(referenceText, ".")) {
                memberText = StringUtil.getShortName(referenceText);
                referenceText = StringUtil.getPackageName(referenceText);
            } else {
                qualifiedName = getMatchFromStaticImports(psiJavaCodeReferenceElement.getContainingFile(), referenceText);
                resolvedFromStaticImports = true;
            }
        }

        if (!resolvedFromStaticImports) {
            qualifiedName = ProjectUtils.getMatchFromPackageStatementOrImports(psiJavaCodeReferenceElement.getContainingFile(), referenceText);

            if (stripMember) {
                qualifiedName = StringUtil.getQualifiedName(qualifiedName, memberText);
            }
        }

        return qualifiedName;
    }

    @Nullable
    public static String getConstantFieldValue(String fullQualifiedReference, Project project, GlobalSearchScope scope) {
        String className = StringUtil.getPackageName(fullQualifiedReference);
        String fieldMemberName = StringUtil.getShortName(fullQualifiedReference);

        //PsiClass psiClass = JavaPsiFacade.getInstance(project).findClass(className, scope);
        PsiClass psiClass = getClassWithoutResolve(className, project, scope);

        if (psiClass != null) {
            PsiField psiField = ProjectUtils.getClassPublicStaticField(psiClass, fieldMemberName);
            if (psiField != null) {

                Object value = null;
                if (psiField instanceof PsiFieldImpl) {
                    value = ((PsiFieldImpl) psiField).computeConstantValue(null);

                    if (value == null) {
                        value = JavaConstantExpressionEvaluator.computeConstantExpression(PsiFieldImpl.getDetachedInitializer(psiField), false);
                    }
                } else if (psiField instanceof ClsFieldImpl) {
                    value = ((ClsFieldImpl)psiField).computeConstantValue(null);
                }

                if (value instanceof String) {
                    return (String)value;
                }
            }
        }

        return null;
    }

    @Nullable
    public static PsiClass getClassWithoutResolve(@NotNull String qualifiedName, @NotNull Project project, @NotNull GlobalSearchScope scope) {
        JavaPsiFacade javaPsiFacade = JavaPsiFacade.getInstance(project);

        String packageName = StringUtil.getPackageName(qualifiedName);
        PsiPackage pkg = javaPsiFacade.findPackage(packageName);
        String className = StringUtil.getShortName(qualifiedName);

        if (pkg != null) {
            PsiClass[] classes = pkg.findClassByShortName(className, scope);
            if (classes.length > 0) {
                return classes[0];
            }
        }

        return null;
    }

    public static String resolveReferencePlaceholder(String referencePlaceholder, Project project, GlobalSearchScope scope) {
        if (referencePlaceholder.startsWith(REFERENCE_PLACEHOLDER)) {
            String reference = referencePlaceholder.substring(1);
            String propertyValue = ProjectUtils.getConstantFieldValue(reference, project, scope);
            if (propertyValue != null) {
                return propertyValue;
            }
        }

        return referencePlaceholder;
    }

    @Nullable
    public static Module getParentModule(Module module) {
        ModuleManager moduleManager = ModuleManager.getInstance(module.getProject());

        ModifiableModuleModel modifiableModuleModel = moduleManager.getModifiableModel();

        String[] groupPaths = modifiableModuleModel.getModuleGroupPath(module);

        String path = ArrayUtil.getLastElement(groupPaths);

        if (path != null) {
            for (Module modifiableModule : modifiableModuleModel.getModules()) {
                if (path.equals(modifiableModule.getName())) {
                    return modifiableModule;
                }
            }
        } else {
            String moduleDirPath = ModuleUtilCore.getModuleDirPath(module);
            for (Module modifiableModule : modifiableModuleModel.getModules()) {
                if (! module.equals(modifiableModule)) {
                    String otherModuleDirPath = ModuleUtilCore.getModuleDirPath(modifiableModule);

                    if (otherModuleDirPath.endsWith(".idea")) {
                        otherModuleDirPath = PathUtilRt.getParentPath(moduleDirPath);
                    }

                    if (isParentDirectory(moduleDirPath, otherModuleDirPath)) {
                        return modifiableModule;
                    }
                }
            }
        }

        return null;
    }

    public static boolean isParentDirectory(@NotNull String childDirectory, @NotNull String parentDirectory) {
        int index = childDirectory.lastIndexOf('/');
        if (index > -1) {
            String calulatedParent = childDirectory.substring(0, childDirectory.lastIndexOf('/'));
            return (calulatedParent.equals(parentDirectory));
        }
        return false;
    }

    public static <T extends PsiElement> List<T> getPreviousSiblingsOrParentOfType(@NotNull PsiElement psiElement, Class<T> type) {
        List<T> result = new ArrayList<>();

        T prevElement = PsiTreeUtil.getPrevSiblingOfType(psiElement, type);

        while (prevElement != null) {
            result.add(prevElement);

            prevElement = PsiTreeUtil.getPrevSiblingOfType(prevElement, type);
        }

        PsiElement parentElement = psiElement.getParent();

        if (parentElement != null) {
            result.addAll(getPreviousSiblingsOrParentOfType(parentElement, type));
        }

        return result;
    }

    public static String getMethodCallSignature(PsiMethodCallExpression methodCallExpression) {
        PsiReferenceExpression methodExpression = methodCallExpression.getMethodExpression();

        PsiExpression qualifierExpression = methodExpression.getQualifierExpression();

        if (qualifierExpression != null) {
            PsiType type = qualifierExpression.getType();

            if (type != null) {
                return type.getCanonicalText() + "." + methodExpression.getReferenceName() + "()";
            }
        }

        return null;
    }

}
