package de.dm.intellij.bndtools.psi;

import com.intellij.psi.PsiNamedElement;
import org.jetbrains.lang.manifest.psi.HeaderValuePart;

public interface AssignmentExpression extends PsiNamedElement {

    HeaderValuePart getNameElement();

    String getValue();

    HeaderValuePart getValueElement();

}
