package de.dm.intellij.bndtools.parser;

import com.intellij.lang.PsiParser;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import de.dm.intellij.bndtools.BndLanguage;
import de.dm.intellij.bndtools.psi.impl.BndFileImpl;
import org.jetbrains.lang.manifest.parser.ManifestParserDefinition;

public class BndParserDefinition extends ManifestParserDefinition {

    public static final IFileElementType FILE = new IFileElementType("BndFile", BndLanguage.INSTANCE);

    @Override
    public PsiParser createParser(Project project) {
        return new BndParser();
    }

    @Override
    public IFileElementType getFileNodeType() {
        return FILE;
    }

    @Override
    public PsiFile createFile(FileViewProvider viewProvider) {
        return new BndFileImpl(viewProvider);
    }

}
