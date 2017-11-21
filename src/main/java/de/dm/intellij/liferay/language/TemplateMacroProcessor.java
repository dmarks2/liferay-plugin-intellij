package de.dm.intellij.liferay.language;

import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiNamedElement;

import java.util.Collection;

public interface TemplateMacroProcessor<F extends PsiFile, T extends PsiNamedElement> {

    Collection<T> getMacrosFromFile(PsiFile psiFile);

    String getMacroFileName(float liferayVersion);

}
