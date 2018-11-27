package de.dm.intellij.liferay.language.velocity;

import com.intellij.psi.PsiElement;
import com.intellij.velocity.psi.VtlLightVariable;
import com.intellij.velocity.psi.files.VtlFile;
import de.dm.intellij.liferay.util.Icons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class CustomVtlVariable extends VtlLightVariable {

    private PsiElement navigationElement;

    public CustomVtlVariable(@NotNull String name, @NotNull VtlFile parent, @NotNull String typeText) {
        this(name, parent, typeText, null);
    }

    public CustomVtlVariable(@NotNull String name, @NotNull VtlFile parent, @NotNull String typeText, PsiElement navigationElement) {
        super(name, parent, typeText);
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
    public Icon getIcon(boolean open) {
        return Icons.LIFERAY_ICON;
    }


}


