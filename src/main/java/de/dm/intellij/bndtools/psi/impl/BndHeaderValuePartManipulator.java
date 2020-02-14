package de.dm.intellij.bndtools.psi.impl;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.AbstractElementManipulator;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.util.IncorrectOperationException;
import de.dm.intellij.bndtools.BndFileType;
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
        String text = "HeaderValuePartManipulator: " + textRange.replace(element.getText(), newContent);

        PsiFileFactory psiFileFactory = PsiFileFactory.getInstance(element.getProject());

        PsiFile psiFile = psiFileFactory.createFileFromText("bnd.bnd", BndFileType.INSTANCE, text);

        BndFile bndFile = (BndFile)psiFile;

        List<BndHeader> bndHeaders = bndFile.getHeaders();

        BndHeader bndHeader = bndHeaders.get(0);

        BndHeaderValue value = bndHeader.getBndHeaderValue();

        if (value != null) {
            BndHeaderValue bndHeaderValueReplacement = (BndHeaderValue) element.replace(value);
            if (bndHeaderValueReplacement instanceof BndHeaderValuePart) {
                return (BndHeaderValuePart) bndHeaderValueReplacement;
            } else {
                Clause clause = (Clause) bndHeaderValueReplacement;

                return clause.getValue();
            }
        }

        return element;
    }
}
