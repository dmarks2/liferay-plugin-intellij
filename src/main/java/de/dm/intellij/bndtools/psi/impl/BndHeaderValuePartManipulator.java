package de.dm.intellij.bndtools.psi.impl;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.AbstractElementManipulator;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.impl.PsiFileFactoryImpl;
import com.intellij.util.IncorrectOperationException;
import de.dm.intellij.bndtools.BndFileType;
import de.dm.intellij.bndtools.BndLanguage;
import de.dm.intellij.bndtools.psi.BndElementType;
import de.dm.intellij.bndtools.psi.BndFile;
import de.dm.intellij.bndtools.psi.BndHeader;
import de.dm.intellij.bndtools.psi.BndHeaderValue;
import de.dm.intellij.bndtools.psi.BndHeaderValuePart;
import de.dm.intellij.bndtools.psi.Clause;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BndHeaderValuePartManipulator extends AbstractElementManipulator<BndHeaderValuePart> {

    @Override
    public BndHeaderValuePart handleContentChange(@NotNull BndHeaderValuePart element, @NotNull TextRange textRange, String newContent) throws IncorrectOperationException {
        PsiFileFactory psiFileFactory = PsiFileFactory.getInstance(element.getProject());

        if (psiFileFactory instanceof PsiFileFactoryImpl) {
            PsiFileFactoryImpl psiFileFactoryImpl = (PsiFileFactoryImpl) psiFileFactory;
            String elementText = textRange.replace(element.getText(), newContent);

            PsiElement baseElement = psiFileFactoryImpl.createElementFromText("HeaderValuePartManipulator: " + elementText, BndLanguage.INSTANCE, BndElementType.HEADER_VALUE_PART, element.getContainingFile());
            if (baseElement != null) {
                PsiElement sectionElement = baseElement.getFirstChild();
                if (sectionElement != null) {
                    PsiElement headerElement = sectionElement.getFirstChild();
                    if (headerElement instanceof BndHeader) {
                        BndHeader bndHeader = (BndHeader)headerElement;

                        BndHeaderValue bndHeaderValue = bndHeader.getBndHeaderValue();
                        if (bndHeaderValue != null) {
                            BndHeaderValue bndHeaderValueReplacement = (BndHeaderValue) element.replace(bndHeaderValue);
                            if (bndHeaderValueReplacement instanceof BndHeaderValuePart) {
                                return (BndHeaderValuePart) bndHeaderValueReplacement;
                            } else {
                                Clause clause = (Clause) bndHeaderValueReplacement;

                                return clause.getValue();
                            }
                        }
                    }
                }
            }
        }

        return element;
    }
}
