package de.dm.intellij.liferay.language;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TemplateVariable {

    private String name;
    private boolean repeatable;

    private PsiElement parentFile;

    private PsiFile originalFile;

    private PsiElement navigationalElement;

    private String defaultLanguageId;

    private final Map<String, String> labels = new HashMap<>();

    private final Map<String, String> tips = new HashMap<>();

    private String type;

    private final List<TemplateVariable> nestedVariables = new ArrayList<>();

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

    public PsiFile getOriginalFile() {
        return originalFile;
    }

    public void setOriginalFile(PsiFile originalFile) {
        this.originalFile = originalFile;
    }

    public String getDefaultLanguageId() {
        return defaultLanguageId;
    }

    public void setDefaultLanguageId(String defaultLanguageId) {
        this.defaultLanguageId = defaultLanguageId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getLabels() {
        return labels;
    }

    public Map<String, String> getTips() {
        return tips;
    }
}
