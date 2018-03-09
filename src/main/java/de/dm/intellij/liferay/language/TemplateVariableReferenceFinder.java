package de.dm.intellij.liferay.language;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

public interface TemplateVariableReferenceFinder {

    PsiElement getNavigationalElement(String templateVariable, PsiFile containingFile);

}
