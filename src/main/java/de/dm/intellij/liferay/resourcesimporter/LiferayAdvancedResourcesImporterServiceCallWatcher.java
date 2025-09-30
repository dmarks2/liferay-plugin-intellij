package de.dm.intellij.liferay.resourcesimporter;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.externalSystem.service.project.autoimport.FileChangeListenerBase;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.JavaRecursiveElementVisitor;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiClassType;
import com.intellij.psi.PsiConstantEvaluationHelper;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiExpressionList;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiMethodCallExpression;
import com.intellij.psi.PsiReferenceExpression;
import com.intellij.psi.PsiType;
import de.dm.intellij.liferay.module.LiferayModuleComponent;
import de.dm.intellij.liferay.util.ProjectUtils;
import org.jetbrains.annotations.NotNull;

public class LiferayAdvancedResourcesImporterServiceCallWatcher extends FileChangeListenerBase {

    private final static Logger log = Logger.getInstance(LiferayAdvancedResourcesImporterServiceCallWatcher.class);

    private static final String ADVANCED_RESOURCES_IMPORTER_CLASS_NAME = "de.dm.toolbox.liferay.resources.importer.service.AdvancedResourcesImporterService";

    public static void handleChange(Project project, VirtualFile virtualFile) {
        final Module module = ModuleUtil.findModuleForFile(virtualFile, project);

        if (module != null) {
            handleChange(project, module, virtualFile);
        }
    }

    public static void handleChange(Project project, Module module, VirtualFile virtualFile) {
        final LiferayModuleComponent component = module.getService(LiferayModuleComponent.class);

        if (component != null) {
            ProjectUtils.runDumbAwareLater(project, () -> {
                if (virtualFile.exists() && virtualFile.isValid()) {
                    PsiManager psiManager = PsiManager.getInstance(project);

                    PsiFile psiFile = psiManager.findFile(virtualFile);

                    if (psiFile instanceof PsiJavaFile psiJavaFile) {
                        if (psiJavaFile.isValid()) {
                            PsiClass[] classes = psiJavaFile.getClasses();

                            for (PsiClass psiClass : classes) {
                                PsiMethod[] methods = psiClass.getMethods();

                                for (PsiMethod method : methods) {
                                    method.accept(new JavaRecursiveElementVisitor() {
                                        @Override
                                        public void visitMethodCallExpression(@NotNull PsiMethodCallExpression methodCallExpression) {
											ProjectUtils.runDumbAwareLater(project, () -> {
												PsiReferenceExpression methodExpression = methodCallExpression.getMethodExpression();

												PsiExpression qualifierExpression = methodExpression.getQualifierExpression();

												if (qualifierExpression != null) {
													try {
														if (!qualifierExpression.isValid()) {
															if (log.isDebugEnabled()) {
																log.debug("Qualifier expression is invalid; skipping type resolution.");
															}
															return;
														}
														PsiType type = qualifierExpression.getType();

														if (type instanceof PsiClassType classType) {

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

																		if (result instanceof String advancedResourcesImporterGroup) {
																			if (log.isDebugEnabled()) {
																				log.debug("Found Advanced Resources Importer Group \"" + advancedResourcesImporterGroup + "\" in " + virtualFile.getPath());
																			}

																			component.setResourcesImporterGroupName(advancedResourcesImporterGroup);
																		}
																	}
																}
															}
														}
													} catch (com.intellij.psi.PsiInvalidElementAccessException e) {
														if (log.isDebugEnabled()) {
															log.debug("Qualifier expression became invalid while resolving type.", e);
														}
													}
												}
											});
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
