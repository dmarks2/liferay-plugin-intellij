package de.dm.intellij.liferay.language.velocity;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiType;
import com.intellij.velocity.psi.VtlLightVariable;
import com.intellij.velocity.psi.files.VtlFile;
import de.dm.intellij.liferay.util.Icons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class CustomVtlVariable extends VtlLightVariable {

    private PsiElement navigationElement;
    private PsiType psiType;

    public CustomVtlVariable(@NotNull String name, @NotNull VtlFile parent, @NotNull String typeText) {
        super(name, parent, typeText);
    }

    public CustomVtlVariable(@NotNull String name, @NotNull VtlFile parent, @NotNull String typeText, PsiElement navigationElement) {
        this(name, parent, typeText);
        this.navigationElement = navigationElement;
    }

    @NotNull
    public PsiElement getNavigationElement() {
        if (navigationElement != null) {
            return navigationElement;
        }

        return super.getNavigationElement();
    }

    @Override
    public PsiType getPsiType() {
        return super.getPsiType();
    }

    public void setPsiType(PsiType psiType) {
        this.psiType = psiType;
    }

    @Override
    public Icon getIcon(boolean open) {
        return Icons.LIFERAY_ICON;
    }
}


