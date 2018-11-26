package de.dm.intellij.liferay.language;

import com.intellij.psi.PsiFile;

import java.util.List;

public interface TemplateVariableParser<T extends PsiFile> {

    List<TemplateVariable> getTemplateVariables(T psiFile, PsiFile templateFile);

}
