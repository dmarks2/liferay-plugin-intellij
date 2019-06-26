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
    protected boolean checkClass(@NotNull BndHeaderValuePart valuePart, @NotNull PsiClass aClass, @NotNull AnnotationHolder holder) {
        Project project = valuePart.getProject();

        PsiClass activatorClass = BndPsiUtil.getActivatorClass(project);

        if (activatorClass != null) {
            if (! aClass.isInheritor(activatorClass, true)) {
                holder.createErrorAnnotation(
                    valuePart.getHighlightingRange(),
                    "Activator class does not inherit from BundleActivator"
                );

                return true;
            }
        }

        return false;
    }
}


