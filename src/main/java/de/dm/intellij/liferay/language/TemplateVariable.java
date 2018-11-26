package de.dm.intellij.liferay.language;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TemplateVariable {

    private String name;
    private boolean repeatable;

    private PsiElement parentFile;

    private PsiElement navigationalElement;

    private List<TemplateVariable> nestedVariables = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRepeatable() {
        return repeatable;
    }

    public void setRepeatable(boolean repeatable) {
        this.repeatable = repeatable;
    }

    public PsiElement getParentFile() {
        return parentFile;
    }

    public void setParentFile(PsiElement parentFile) {
        this.parentFile = parentFile;
    }

    public PsiElement getNavigationalElement() {
        return navigationalElement;
    }

    public void setNavigationalElement(PsiElement navigationalElement) {
        this.navigationalElement = navigationalElement;
    }

    @NotNull
    public List<TemplateVariable> getNestedVariables() {
        return nestedVariables;
    }

    public void addNestedVariable(TemplateVariable nestedVariable) {
        this.nestedVariables.add(nestedVariable);
    }
}
