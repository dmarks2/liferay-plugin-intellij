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
import de.dm.intellij.bndtools.psi.BndHeader;
import de.dm.intellij.bndtools.psi.BndHeaderValue;
import de.dm.intellij.bndtools.psi.BndHeaderValuePart;
import de.dm.intellij.bndtools.psi.Clause;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.lang.manifest.ManifestBundle;

public class BndClassReferenceParser extends BndHeaderParser /*extends ClassReferenceParser*/ {

    public static final BndClassReferenceParser INSTANCE = new BndClassReferenceParser();

    public static final String MAIN_CLASS = "Main-Class";
    public static final String PREMAIN_CLASS = "Premain-Class";
    public static final String AGENT_CLASS = "Agent-Class";
    public static final String LAUNCHER_AGENT_CLASS = "Launcher-Agent-Class";

    @NotNull
    @Override
    public PsiReference[] getReferences(@NotNull BndHeaderValuePart bndHeaderValuePart) {
        Module module = ModuleUtilCore.findModuleForPsiElement(bndHeaderValuePart);

        JavaClassReferenceProvider provider;

        if (module != null) {
            provider = new JavaClassReferenceProvider() {
                @Override
                public GlobalSearchScope getScope(Project project) {
                    return GlobalSearchScope.moduleWithDependenciesAndLibrariesScope(module);
                }
            };
        }
        else {
            provider = new JavaClassReferenceProvider();
        }
        return provider.getReferencesByElement(bndHeaderValuePart);
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
            holder.createErrorAnnotation(valuePart.getHighlightingRange(), ManifestBundle.message("header.reference.invalid"));
            return true;
        }

        Project project = bndHeader.getProject();
        Module module = ModuleUtilCore.findModuleForPsiElement(bndHeader);
        GlobalSearchScope scope = module != null ? module.getModuleWithDependenciesAndLibrariesScope(false) : ProjectScope.getAllScope(project);
        PsiClass aClass = JavaPsiFacade.getInstance(project).findClass(className, scope);
        if (aClass == null) {
            String message = JavaErrorMessages.message("error.cannot.resolve.class", className);
            Annotation anno = holder.createErrorAnnotation(valuePart.getHighlightingRange(), message);
            anno.setHighlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL);
            return true;
        }

        return checkClass(valuePart, aClass, holder);
    }

    protected boolean checkClass(@NotNull BndHeaderValuePart valuePart, @NotNull PsiClass aClass, @NotNull AnnotationHolder holder) {
        PsiElement parent = valuePart.getParent();

        if (parent instanceof BndHeader) {
            BndHeader bndHeader = (BndHeader) parent;

            String bndHeaderName = bndHeader.getName();

            if (MAIN_CLASS.equals(bndHeaderName) && !PsiMethodUtil.hasMainMethod(aClass)) {
                holder.createErrorAnnotation(valuePart.getHighlightingRange(), ManifestBundle.message("header.main.class.invalid"));
                return true;
            }

            if (PREMAIN_CLASS.equals(bndHeaderName) && !hasInstrumenterMethod(aClass, "premain")) {
                holder.createErrorAnnotation(valuePart.getHighlightingRange(), ManifestBundle.message("header.pre-main.class.invalid"));
                return true;
            }

            if ((AGENT_CLASS.equals(bndHeaderName) || LAUNCHER_AGENT_CLASS.equals(bndHeaderName)) && !hasInstrumenterMethod(aClass, "agentmain")) {
                holder.createErrorAnnotation(valuePart.getHighlightingRange(), ManifestBundle.message("header.agent.class.invalid"));
                return true;
            }
        }

        return false;
    }

    private static boolean hasInstrumenterMethod(PsiClass aClass, String methodName) {
        for (PsiMethod method : aClass.findMethodsByName(methodName, false)) {
            if (PsiType.VOID.equals(method.getReturnType()) &&
                method.hasModifierProperty(PsiModifier.PUBLIC) &&
                method.hasModifierProperty(PsiModifier.STATIC)) {
                return true;
            }
        }

        return false;
    }
}
