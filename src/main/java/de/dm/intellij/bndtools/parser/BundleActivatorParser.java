package de.dm.intellij.bndtools.parser;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import de.dm.intellij.bndtools.psi.BndHeaderValuePart;
import de.dm.intellij.bndtools.psi.util.BndPsiUtil;
import org.jetbrains.annotations.NotNull;

public class BundleActivatorParser extends BndClassReferenceParser {

    public static final BundleActivatorParser INSTANCE = new BundleActivatorParser();

    private BundleActivatorParser() {
    }

    @Override
    protected boolean checkClass(@NotNull BndHeaderValuePart bndHeaderValuePart, @NotNull PsiClass psiClass, @NotNull AnnotationHolder annotationHolder) {
        boolean result = super.checkClass(bndHeaderValuePart, psiClass, annotationHolder);

        PsiClass bundleActivatorClass = BndPsiUtil.getBundleActivatorClass(bndHeaderValuePart);

        if (bundleActivatorClass != null) {
            if (! psiClass.isInheritor(bundleActivatorClass, true)) {
                annotationHolder.createErrorAnnotation(
                    bndHeaderValuePart.getHighlightingRange(),
                    "Activator class does not inherit from BundleActivator"
                );

                return true;
            }
        }

        return result;
    }
}


