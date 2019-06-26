package de.dm.intellij.bndtools.psi;

import com.intellij.psi.PsiNamedElement;

public interface AssignmentExpression extends PsiNamedElement {

    BndHeaderValuePart getNameElement();

    String getValue();

    BndHeaderValuePart getValueElement();

}
