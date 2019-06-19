package de.dm.intellij.bndtools.parser;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import de.dm.intellij.bndtools.psi.util.BndPsiUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.lang.manifest.header.impl.ClassReferenceParser;
import org.jetbrains.lang.manifest.psi.HeaderValuePart;

public class BundleActivatorParser extends ClassReferenceParser {

    public static final BundleActivatorParser INSTANCE = new BundleActivatorParser();

    private BundleActivatorParser() {
    }

    @Override
    protected boolean checkClass(@NotNull HeaderValuePart valuePart, @NotNull PsiClass aClass, @NotNull AnnotationHolder holder) {
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


