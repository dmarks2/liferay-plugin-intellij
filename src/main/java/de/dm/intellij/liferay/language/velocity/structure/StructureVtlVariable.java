package de.dm.intellij.liferay.language.velocity.structure;

import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.velocity.psi.VtlLightVariable;
import com.intellij.velocity.psi.files.VtlFile;
import de.dm.intellij.liferay.language.TemplateVariable;
import de.dm.intellij.liferay.util.Icons;
import org.jetbrains.annotations.NotNull;

import javax.swing.Icon;

public class StructureVtlVariable extends VtlLightVariable {

    public static final String CLASS_NAME = "com.liferay.portal.kernel.templateparser.TemplateNode";

    private final TemplateVariable templateVariable;

    public StructureVtlVariable(TemplateVariable templateVariable) {
        super(templateVariable.getName(), (VtlFile) templateVariable.getParentFile(), CLASS_NAME);

        this.templateVariable = templateVariable;
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

    public boolean processDeclarations(@NotNull PsiScopeProcessor processor, @NotNull ResolveState state, PsiElement lastParent, @NotNull PsiElement place) {
        if (! templateVariable.getNestedVariables().isEmpty()) {
            if (templateVariable.isRepeatable()) {
                //TODO how to create collection type vtl variable ??
                //this.siblingsVariable = new CustomVtlVariable("siblings", this, )

            } else {
                for (TemplateVariable nestedVariable : templateVariable.getNestedVariables()) {
                    StructureVtlVariable nestedStructureVtlVariable = new StructureVtlVariable(nestedVariable);
                    processor.execute(nestedStructureVtlVariable, state);
                }

                return false;
            }
        }

        return super.processDeclarations(processor, state, lastParent, place);
    }
}
