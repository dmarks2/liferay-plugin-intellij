package de.dm.intellij.bndtools.psi.impl;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.AbstractElementManipulator;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.util.IncorrectOperationException;
import de.dm.intellij.bndtools.BndFileType;
import de.dm.intellij.bndtools.psi.BndFile;
import de.dm.intellij.bndtools.psi.BndHeaderValuePart;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.lang.manifest.psi.HeaderValue;

public class BndHeaderValuePartManipulator extends AbstractElementManipulator<BndHeaderValuePart> {

    @Override
    public BndHeaderValuePart handleContentChange(@NotNull BndHeaderValuePart element, @NotNull TextRange range, String newContent) throws IncorrectOperationException {
        String text = "HeaderValuePartManipulator: " + range.replace(element.getText(), newContent);

        PsiFile file = PsiFileFactory.getInstance(element.getProject()).createFileFromText("bnd.bnd", BndFileType.INSTANCE, text);

        HeaderValue value = ((BndFile)file).getHeaders().get(0).getHeaderValue();

        return (BndHeaderValuePart)element.replace(value);
    }
}
