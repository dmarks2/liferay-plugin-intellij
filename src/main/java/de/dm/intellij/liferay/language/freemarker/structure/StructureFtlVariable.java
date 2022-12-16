package de.dm.intellij.liferay.language.freemarker.structure;

import com.intellij.freemarker.psi.FtlCollectionType;
import com.intellij.freemarker.psi.FtlType;
import com.intellij.freemarker.psi.FtlVariantsProcessor;
import com.intellij.freemarker.psi.variables.FtlDynamicMember;
import com.intellij.freemarker.psi.variables.FtlLightVariable;
import com.intellij.freemarker.psi.variables.FtlMethodType;
import com.intellij.freemarker.psi.variables.FtlSpecialVariableType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiClassType;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiSubstitutor;
import com.intellij.psi.PsiType;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.NameHint;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PropertyUtil;
import com.intellij.util.IncorrectOperationException;
import de.dm.intellij.liferay.language.TemplateVariable;
import de.dm.intellij.liferay.util.Icons;
import de.dm.intellij.liferay.util.ProjectUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class StructureFtlVariable extends FtlLightVariable {

    public static final String CLASS_NAME = "com.liferay.portal.kernel.templateparser.TemplateNode";

    private TemplateVariable templateVariable;

    private PsiClassType templateNodeClassType;
    private PsiClass templateNodeClass;

    private final FtlCollectionType collectionType = new FtlCollectionType(new FtlSpecialVariableType() {
        @Override
        public boolean processDeclarations(@NotNull PsiScopeProcessor psiScopeProcessor, @NotNull PsiElement psiElement, ResolveState resolveState) {
            if (! templateVariable.getNestedVariables().isEmpty()) {
                for (TemplateVariable nestedVariable : templateVariable.getNestedVariables()) {
                    StructureFtlVariable nestedStructureFtlVariable = new StructureFtlVariable(nestedVariable);
                    psiScopeProcessor.execute(nestedStructureFtlVariable, resolveState);
                }
            }

            if (templateNodeClassType != null) {
                return processTemplateNode(psiScopeProcessor, resolveState, psiElement, templateNodeClassType);
            }

            return false;
        }
    });

    public StructureFtlVariable(TemplateVariable templateVariable) {
        super(templateVariable.getName(), templateVariable.getParentFile(), CLASS_NAME);

        this.templateVariable = templateVariable;

        try {
            Project project = templateVariable.getParentFile().getProject();

            PsiType psiType = JavaPsiFacade.getInstance(project).getElementFactory().createTypeFromText("com.liferay.portal.kernel.templateparser.TemplateNode", templateVariable.getParentFile());
            if (psiType instanceof PsiClassType) {
                templateNodeClassType = (PsiClassType) psiType;

                PsiClass psiClass = ProjectUtils.getClassWithoutResolve(
                        "com.liferay.portal.kernel.templateparser.TemplateNode",
                        project,
                        GlobalSearchScope.allScope(project)
                );

                //PsiClass psiClass = templateNodeClassType.resolve();
                if (psiClass != null) {
                    templateNodeClass = psiClass;
                }
            }
        } catch (IncorrectOperationException e) {
            //unable to resolve TemplateNode
        }
    }

    @NotNull
    @Override
    public PsiElement getNavigationElement() {
        if (this.templateVariable.getNavigationalElement() != null) {
            return this.templateVariable.getNavigationalElement();
        }
        return super.getNavigationElement();
    }

    @Override
    public Icon getIcon(boolean open) {
        return Icons.LIFERAY_ICON;
    }

    @Override
    public boolean processDeclarations(@NotNull PsiScopeProcessor processor, @NotNull ResolveState state, PsiElement lastParent, @NotNull PsiElement place) {
        if ( (! templateVariable.getNestedVariables().isEmpty()) || (templateVariable.isRepeatable()) ) {
            if (templateVariable.isRepeatable()) {
                if (templateNodeClass != null) {
                    for (PsiMethod psiMethod : templateNodeClass.getMethods()) {
                        if ( ("getSiblings".equals(psiMethod.getName()) && (psiMethod.getParameterList().isEmpty())) ) {
                            FtlMethodType ftlMethodType = new FtlMethodType(psiMethod, PsiSubstitutor.EMPTY) {
                                @Override
                                public FtlType getResultType() {
                                    return collectionType;
                                }
                            };


                            FtlDynamicMember siblings = new FtlDynamicMember("siblings", psiMethod, collectionType);
                            FtlDynamicMember getSiblings = new FtlDynamicMember("getSiblings", psiMethod, ftlMethodType);

                            processor.execute(siblings, state);
                            processor.execute(getSiblings, state);

                            break;
                        }
                    }
                } else {
                    FtlLightVariable siblings = new FtlLightVariable("siblings", this, collectionType);
                    processor.execute(siblings, state);
                }
            } else {
                for (TemplateVariable nestedVariable : templateVariable.getNestedVariables()) {
                    StructureFtlVariable nestedStructureFtlVariable = new StructureFtlVariable(nestedVariable);
                    processor.execute(nestedStructureFtlVariable, state);
                }

                return processTemplateNode(processor, state, place, templateNodeClassType);
            }
        } else {
            if (templateNodeClassType != null) {
                return processTemplateNode(processor, state, place, templateNodeClassType);
            }
        }

        return super.processDeclarations(processor, state, lastParent, place);
    }

    private boolean processTemplateNode(@NotNull PsiScopeProcessor processor, @NotNull ResolveState state, @NotNull PsiElement place, PsiClassType classType) {
        //default implementation of FtlPsiType does not resolve members for classes which inherit from java.util.Map (TemplateNode inherits from Map)
        //so resolve members of com.liferay.portal.kernel.templateparser.TemplateNode manually

        PsiClassType.ClassResolveResult resolveResult = classType.resolveGenerics();
        PsiClass psiClass = resolveResult.getElement();
        if (psiClass == null) {
            return true;
        }

        ResolveState newState = state.put(PsiSubstitutor.KEY, resolveResult.getSubstitutor());
        String hint = processor instanceof FtlVariantsProcessor ? ((FtlVariantsProcessor) processor).getReferenceName() : null;
        if (!psiClass.processDeclarations(new StructureFtlVariable.PsiMemberProcessor(processor, hint), newState, (PsiElement) null, place)) {
            return false;
        }

        if (hint != null && !((FtlVariantsProcessor) processor).isMethodCall()) {
            String isAccessor = PropertyUtil.suggestGetterName(hint, PsiType.BOOLEAN);
            if (!psiClass.processDeclarations(new StructureFtlVariable.PsiMemberProcessor(processor, isAccessor), newState, (PsiElement) null, place)) {
                return false;
            }

            String getAccessor = PropertyUtil.suggestGetterName(hint, PsiType.INT);
            if (!psiClass.processDeclarations(new StructureFtlVariable.PsiMemberProcessor(processor, getAccessor), newState, (PsiElement) null, place)) {
                return false;
            }
        }

        return false;
    }

    private class PsiMemberProcessor implements PsiScopeProcessor, NameHint {
        private final PsiScopeProcessor myDelegate;
        private final String myNameHint;

        public PsiMemberProcessor(PsiScopeProcessor delegate, String hint) {
            this.myDelegate = delegate;
            this.myNameHint = hint;
        }

        public boolean execute(@NotNull PsiElement element, @NotNull ResolveState state) {
            return this.myDelegate.execute(element, state);
        }

        @SuppressWarnings("unchecked")
        public <T> T getHint(@NotNull Key<T> hintKey) {
            return hintKey == NameHint.KEY && this.myNameHint != null ? (T) this : null;
        }

        @Nullable
        public String getName(@NotNull ResolveState state) {
            return this.myNameHint;
        }
    }

    public TemplateVariable getTemplateVariable() {
        return templateVariable;
    }
}