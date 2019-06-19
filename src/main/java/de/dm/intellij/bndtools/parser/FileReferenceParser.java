package de.dm.intellij.bndtools.parser;

import com.intellij.psi.PsiReference;
import de.dm.intellij.bndtools.psi.Clause;
import de.dm.intellij.bndtools.psi.util.BndPsiUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.lang.manifest.psi.HeaderValuePart;

public class FileReferenceParser extends OsgiHeaderParser {

    public static final FileReferenceParser INSTANCE = new FileReferenceParser();

    @NotNull
    @Override
    public PsiReference[] getReferences(@NotNull HeaderValuePart headerValuePart) {
        if (headerValuePart.getParent() instanceof Clause) {
            return BndPsiUtil.getFileReferences(headerValuePart);
        }

        return PsiReference.EMPTY_ARRAY;
    }

}
