package de.dm.intellij.liferay.resourcesimporter;

import com.intellij.openapi.externalSystem.service.project.autoimport.FileChangeListenerBase;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import com.intellij.psi.*;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
import de.dm.intellij.liferay.util.ProjectUtils;

public class LiferayAdvancedResourcesImporterServiceCallWatcher extends FileChangeListenerBase {

    private static final String ADVANCED_RESOURCES_IMPORTER_CLASS_NAME = "de.dm.toolbox.liferay.resources.importer.service.AdvancedResourcesImporterService";

    public static void handleChange(Project project, VirtualFile virtualFile) {
        final Module module = ModuleUtil.findModuleForFile(virtualFile, project);

        if (module != null) {
            final LiferayModuleComponent component = module.getService(LiferayModuleComponent.class);

            if (component != null) {
                ProjectUtils.runDumbAwareLater(project, () -> {
                    if (virtualFile.exists() && virtualFile.isValid()) {
                        PsiManager psiManager = PsiManager.getInstance(project);

                        PsiFile psiFile = psiManager.findFile(virtualFile);

                        if (psiFile instanceof PsiJavaFile) {
                            PsiJavaFile psiJavaFile = (PsiJavaFile) psiFile;

                            if (psiJavaFile.isValid()) {
                                PsiClass[] classes = psiJavaFile.getClasses();

                                for (PsiClass psiClass : classes) {
                                    PsiMethod[] methods = psiClass.getMethods();

                                    for (PsiMethod method : methods) {
                                        method.accept(new JavaRecursiveElementVisitor() {
                                            @Override
                                            public void visitMethodCallExpression(PsiMethodCallExpression methodCallExpression) {
                                                PsiReferenceExpression methodExpression = methodCallExpression.getMethodExpression();

                                                PsiExpression qualifierExpression = methodExpression.getQualifierExpression();

                                                if (qualifierExpression != null) {
                                                    PsiType type = qualifierExpression.getType();

                                                    if (type instanceof PsiClassType) {
                                                        PsiClassType classType = (PsiClassType) type;

                                                        PsiClass clazz = classType.resolve();

                                                        if (clazz != null) {
                                                            String qualifiedName = clazz.getQualifiedName();

                                                            if (ADVANCED_RESOURCES_IMPORTER_CLASS_NAME.equals(qualifiedName)) {
                                                                PsiExpressionList argumentList = methodCallExpression.getArgumentList();

                                                                PsiExpression[] expressions = argumentList.getExpressions();

                                                                if (expressions.length > 2) {
                                                                    PsiExpression groupKeyExpression = expressions[2];

                                                                    JavaPsiFacade javaPsiFacade = JavaPsiFacade.getInstance(psiClass.getProject());

                                                                    PsiConstantEvaluationHelper constantEvaluationHelper = javaPsiFacade.getConstantEvaluationHelper();

                                                                    Object result = constantEvaluationHelper.computeConstantExpression(groupKeyExpression);

                                                                    if (result instanceof String) {
                                                                        String advancedResourcesImporterGroup = (String) result;

                                                                        component.setResourcesImporterGroupName(advancedResourcesImporterGroup);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        }
                    }
                });
            }
        }
    }

    public static boolean isRelevantFile(String path) {
        return path.endsWith(".java");
    }

    @Override
    protected boolean isRelevant(String path) {
        return isRelevantFile(path);
    }

    @Override
    protected void updateFile(VirtualFile virtualFile, VFileEvent event) {
        Project project = ProjectUtil.guessProjectForFile(virtualFile);
        if (project != null) {
            handleChange(project, virtualFile);
        }
    }

    @Override
    protected void deleteFile(VirtualFile file, VFileEvent event) {

    }

    @Override
    protected void apply() {

    }
}
