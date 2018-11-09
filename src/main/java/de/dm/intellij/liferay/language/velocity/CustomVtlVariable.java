package de.dm.intellij.liferay.language.velocity;

import com.intellij.freemarker.psi.variables.FtlVariable;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiType;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.intellij.velocity.psi.VtlLightVariable;
import com.intellij.velocity.psi.VtlVariable;
import com.intellij.velocity.psi.files.VtlFile;
import de.dm.intellij.liferay.util.Icons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Collection;

public class CustomVtlVariable extends VtlLightVariable {

    private PsiElement navigationElement;
    private VtlVariable siblingsVariable;
    private Collection<VtlVariable> nestedVariables;

    public CustomVtlVariable(@NotNull String name, @NotNull VtlFile parent, @NotNull String typeText) {
        this(name, parent, typeText, null);
    }

    public CustomVtlVariable(@NotNull String name, @NotNull VtlFile parent, @NotNull String typeText, PsiElement navigationElement) {
        this(name, parent, typeText, navigationElement, null, false);
    }

    public CustomVtlVariable(@NotNull String name, @NotNull VtlFile parent, @NotNull String typeText, PsiElement navigationElement, Collection<VtlVariable> nestedVariables, boolean repeatable) {
        super(name, parent, typeText);
        this.navigationElement = navigationElement;
        if (repeatable) {
            //TODO how to create collection type vtl variable ??
            //this.siblingsVariable = new CustomVtlVariable("siblings", this, )
        } else {
            this.nestedVariables = nestedVariables;
        }
    }

    @NotNull
    public PsiElement getNavigationElement() {
        if (navigationElement != null) {
            return navigationElement;
        }

        return super.getNavigationElement();
    }

    @Override
    public Icon getIcon(boolean open) {
        return Icons.LIFERAY_ICON;
    }

    public boolean processDeclarations(@NotNull PsiScopeProcessor processor, @NotNull ResolveState state, PsiElement lastParent, @NotNull PsiElement place) {
        if (nestedVariables != null) {
            for (VtlVariable variable : nestedVariables) {
                processor.execute(variable, state);
            }

            return false;
        }

        return super.processDeclarations(processor, state, lastParent, place);
    }

}


