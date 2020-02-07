package de.dm.intellij.bndtools.parser;

import com.intellij.codeInsight.daemon.JavaErrorMessages;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.JavaClassReferenceProvider;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.ProjectScope;
import com.intellij.psi.util.PsiMethodUtil;
import de.dm.intellij.bndtools.LiferayBndConstants;
import de.dm.intellij.bndtools.psi.BndHeader;
import de.dm.intellij.bndtools.psi.BndHeaderValue;
import de.dm.intellij.bndtools.psi.BndHeaderValuePart;
import de.dm.intellij.bndtools.psi.Clause;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.lang.manifest.ManifestBundle;

public class BndClassReferenceParser extends BndHeaderParser {

    public static final BndClassReferenceParser INSTANCE = new BndClassReferenceParser();

    @NotNull
    @Override
    public PsiReference[] getReferences(@NotNull BndHeaderValuePart bndHeaderValuePart) {
        //TODO add test to resolve reference

        Module module = ModuleUtilCore.findModuleForPsiElement(bndHeaderValuePart);

        JavaClassReferenceProvider javaClassReferenceProvider;

        if (module != null) {
            javaClassReferenceProvider = new JavaClassReferenceProvider() {
                @Override
                public GlobalSearchScope getScope(Project project) {
                    return GlobalSearchScope.moduleWithDependenciesAndLibrariesScope(module);
                }
            };
        } else {
            javaClassReferenceProvider = new JavaClassReferenceProvider();
        }
        return javaClassReferenceProvider.getReferencesByElement(bndHeaderValuePart);
    }

    @Override
    public boolean annotate(@NotNull BndHeader bndHeader, @NotNull AnnotationHolder holder) {
        BndHeaderValue value = bndHeader.getBndHeaderValue();

        BndHeaderValuePart valuePart = null;

        if (value instanceof BndHeaderValuePart) {
            valuePart = (BndHeaderValuePart) value;
        } else if (value instanceof Clause) {
            Clause clause = (Clause)value;
            valuePart = clause.getValue();
        }

        if (valuePart == null) {
            return false;
        }

        String className = valuePart.getUnwrappedText();
        if (StringUtil.isEmptyOrSpaces(className)) {
            //TODO create a test!
            holder.createErrorAnnotation(valuePart.getHighlightingRange(), ManifestBundle.message("header.reference.invalid"));
            return true;
        }

        Project project = bndHeader.getProject();
        Module module = ModuleUtilCore.findModuleForPsiElement(bndHeader);
        GlobalSearchScope globalSearchScope = module != null ? module.getModuleWithDependenciesAndLibrariesScope(false) : ProjectScope.getAllScope(project);
        PsiClass psiClass = JavaPsiFacade.getInstance(project).findClass(className, globalSearchScope);
        if (psiClass == null) {
            //TODO create a test!
            String message = JavaErrorMessages.message("error.cannot.resolve.class", className);
            Annotation annotation = holder.createErrorAnnotation(valuePart.getHighlightingRange(), message);
            annotation.setHighlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL);
            return true;
        }

        return checkClass(valuePart, psiClass, holder);
    }

    protected boolean checkClass(@NotNull BndHeaderValuePart bndHeaderValuePart, @NotNull PsiClass psiClass, @NotNull AnnotationHolder annotationHolder) {
        PsiElement parent = bndHeaderValuePart.getParent();

        if (parent instanceof BndHeader) {
            BndHeader bndHeader = (BndHeader) parent;

            String bndHeaderName = bndHeader.getName();

            if (LiferayBndConstants.MAIN_CLASS.equals(bndHeaderName) && !PsiMethodUtil.hasMainMethod(psiClass)) {
                annotationHolder.createErrorAnnotation(bndHeaderValuePart.getHighlightingRange(), ManifestBundle.message("header.main.class.invalid"));
                return true;
            }

            if (LiferayBndConstants.PREMAIN_CLASS.equals(bndHeaderName) && !hasInstrumenterMethod(psiClass, "premain")) {
                annotationHolder.createErrorAnnotation(bndHeaderValuePart.getHighlightingRange(), ManifestBundle.message("header.pre-main.class.invalid"));
                return true;
            }

            if ((LiferayBndConstants.AGENT_CLASS.equals(bndHeaderName) || LiferayBndConstants.LAUNCHER_AGENT_CLASS.equals(bndHeaderName)) && !hasInstrumenterMethod(psiClass, "agentmain")) {
                annotationHolder.createErrorAnnotation(bndHeaderValuePart.getHighlightingRange(), ManifestBundle.message("header.agent.class.invalid"));
                return true;
            }
        }

        return false;
    }

    private static boolean hasInstrumenterMethod(PsiClass psiClass, String methodName) {
        for (PsiMethod method : psiClass.findMethodsByName(methodName, false)) {
            if (PsiType.VOID.equals(method.getReturnType()) &&
                method.hasModifierProperty(PsiModifier.PUBLIC) &&
                method.hasModifierProperty(PsiModifier.STATIC)) {
                return true;
            }
        }

        return false;
    }
}
