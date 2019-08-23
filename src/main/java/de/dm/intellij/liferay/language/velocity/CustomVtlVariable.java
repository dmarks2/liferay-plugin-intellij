package de.dm.intellij.liferay.language.velocity;

import com.intellij.psi.PsiElement;
import com.intellij.velocity.psi.VtlLightVariable;
import com.intellij.velocity.psi.files.VtlFile;
import de.dm.intellij.liferay.util.Icons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Comparator;
import java.util.Objects;

public class CustomVtlVariable extends VtlLightVariable implements Comparable<CustomVtlVariable>{

    private PsiElement navigationElement;
    private String typeText;

    public CustomVtlVariable(@NotNull String name, @NotNull VtlFile parent, @NotNull String typeText) {
        this(name, parent, typeText, null);
    }

    public CustomVtlVariable(@NotNull String name, @NotNull VtlFile parent, @NotNull String typeText, PsiElement navigationElement) {
        super(name, parent, typeText);
        this.navigationElement = navigationElement;
        this.typeText = typeText;
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

    public String getTypeText() {
        return typeText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomVtlVariable that = (CustomVtlVariable) o;
        return
            Objects.equals(navigationElement, that.navigationElement) &&
            Objects.equals(getName(), that.getName()) &&
            Objects.equals(getParent(), that.getParent()) &&
            Objects.equals(getPsiType(), that.getPsiType());
    }

    @Override
    public int compareTo(@NotNull CustomVtlVariable o) {
        Comparator<CustomVtlVariable> comparator =
            Comparator
                .comparing(CustomVtlVariable::getName)
                .thenComparing(CustomVtlVariable::getTypeText);
        return comparator.compare(this, o);
    }
}


