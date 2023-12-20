package de.dm.intellij.bndtools;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.util.PsiTreeUtil;
import de.dm.intellij.bndtools.psi.BndFile;
import de.dm.intellij.bndtools.psi.BndHeader;
import de.dm.intellij.bndtools.psi.BndSection;

public class BndElementFactory {

    private final Project project;

    public BndElementFactory(Project project) {
        this.project = project;
    }

    public static BndElementFactory getInstance(Project project) {
        return new BndElementFactory(project);
    }

    public BndHeader createHeader(String name) {
        BndFile bndFile = createFile(name);

        BndSection section = PsiTreeUtil.getChildOfType(bndFile, BndSection.class);

        if (section != null) {
            return PsiTreeUtil.getChildOfType(section, BndHeader.class);
        }

        return null;
    }

    private BndFile createFile(String text) {
        PsiFileFactory fileFactory = PsiFileFactory.getInstance(project);

        return (BndFile)fileFactory.createFileFromText("bnd.bnd", BndFileType.INSTANCE, text);
    }
}
