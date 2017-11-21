package de.dm.intellij.liferay.language;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.PsiType;

import java.util.Collection;

public interface TemplateVariableProcessor<F extends PsiFile, T extends PsiNamedElement> {

    T createVariable(String name, F parent, String typeText, PsiElement navigationalElement, Collection<T> nestedVariables);

    String[] getAdditionalLanguageSpecificResources(float liferayVersion);

}
