package de.dm.intellij.bndtools.parser;

import com.intellij.codeInsight.daemon.JavaErrorBundle;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiModifier;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiType;
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

public class ClassReferenceParser extends BndHeaderParser {

    public static final ClassReferenceParser INSTANCE = new ClassReferenceParser();

    @NotNull
    @Override
    public PsiReference[] getReferences(@NotNull BndHeaderValuePart bndHeaderValuePart) {
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
            holder.newAnnotation(
                    HighlightSeverity.ERROR, ManifestBundle.message("header.reference.invalid")
            ).range(
                    valuePart.getHighlightingRange()
            ).create();

            return true;
        }

        Project project = bndHeader.getProject();
        Module module = ModuleUtilCore.findModuleForPsiElement(bndHeader);
        GlobalSearchScope globalSearchScope = module != null ? module.getModuleWithDependenciesAndLibrariesScope(false) : ProjectScope.getAllScope(project);
        PsiClass psiClass = JavaPsiFacade.getInstance(project).findClass(className, globalSearchScope);
        if (psiClass == null) {
            holder.newAnnotation(
                    HighlightSeverity.ERROR, JavaErrorBundle.message("error.cannot.resolve.class", className)
            ).range(
                    valuePart.getHighlightingRange()
            ).highlightType(
                    ProblemHighlightType.LIKE_UNKNOWN_SYMBOL
            ).create();

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
                annotationHolder.newAnnotation(
                        HighlightSeverity.ERROR, ManifestBundle.message("header.main.class.invalid")
                ).range(
                        bndHeaderValuePart.getHighlightingRange()
                ).create();

                return true;
            }

            if (LiferayBndConstants.PREMAIN_CLASS.equals(bndHeaderName) && !hasInstrumenterMethod(psiClass, "premain")) {
                annotationHolder.newAnnotation(
                        HighlightSeverity.ERROR, ManifestBundle.message("header.pre-main.class.invalid")
                ).range(
                        bndHeaderValuePart.getHighlightingRange()
                ).create();

                return true;
            }

            if ((LiferayBndConstants.AGENT_CLASS.equals(bndHeaderName) || LiferayBndConstants.LAUNCHER_AGENT_CLASS.equals(bndHeaderName)) && !hasInstrumenterMethod(psiClass, "agentmain")) {
                annotationHolder.newAnnotation(
                        HighlightSeverity.ERROR, ManifestBundle.message("header.agent.class.invalid")
                ).range(
                        bndHeaderValuePart.getHighlightingRange()
                ).create();

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
