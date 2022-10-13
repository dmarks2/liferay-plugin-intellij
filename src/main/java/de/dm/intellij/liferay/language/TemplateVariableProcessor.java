package de.dm.intellij.liferay.language;

import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiNamedElement;

public interface TemplateVariableProcessor<F extends PsiFile, T extends PsiNamedElement> {

    T createVariable(String name, F parent, String typeText);

    T createStructureVariable(TemplateVariable templateVariable);

    String[] getAdditionalLanguageSpecificResources(float liferayVersion);

}
