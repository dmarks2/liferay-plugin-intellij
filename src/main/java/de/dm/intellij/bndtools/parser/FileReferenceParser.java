package de.dm.intellij.bndtools.parser;

import com.intellij.psi.PsiReference;
import de.dm.intellij.bndtools.psi.BndHeaderValuePart;
import de.dm.intellij.bndtools.psi.Clause;
import de.dm.intellij.bndtools.psi.util.BndPsiUtil;
import org.jetbrains.annotations.NotNull;

public class FileReferenceParser extends OsgiHeaderParser {

    public static final FileReferenceParser INSTANCE = new FileReferenceParser();

    @NotNull
    @Override
    public PsiReference[] getReferences(@NotNull BndHeaderValuePart bndHeaderValuePart) {
        if (bndHeaderValuePart.getParent() instanceof Clause) {
            return BndPsiUtil.getFileReferences(bndHeaderValuePart);
        }

        return PsiReference.EMPTY_ARRAY;
    }

}
