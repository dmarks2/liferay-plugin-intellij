package de.dm.intellij.bndtools.psi.impl;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.util.PsiTreeUtil;
import de.dm.intellij.bndtools.BndFileType;
import de.dm.intellij.bndtools.BndLanguage;
import de.dm.intellij.bndtools.psi.BndFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.lang.manifest.psi.Header;
import org.jetbrains.lang.manifest.psi.Section;

import java.util.List;

public class BndFileImpl extends PsiFileBase implements BndFile {

    public BndFileImpl(FileViewProvider viewProvider) {
        super(viewProvider, BndLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return BndFileType.INSTANCE;
    }

    @NotNull
    @Override
    public List<Section> getSections() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, Section.class);
    }

    @Nullable
    @Override
    public Section getMainSection() {
        return findChildByClass(Section.class);
    }

    @NotNull
    @Override
    public List<Header> getHeaders() {
        return PsiTreeUtil.getChildrenOfTypeAsList(getFirstChild(), Header.class);
    }

    @Nullable
    @Override
    public Header getHeader(@NotNull String name) {
        Header child = PsiTreeUtil.findChildOfType(getFirstChild(), Header.class);
        while (child != null) {
            if (name.equals(child.getName())) {
                return child;
            }
            child = PsiTreeUtil.getNextSiblingOfType(child, Header.class);
        }
        return null;
    }

    @Override
    public String toString() {
        return "BndFile:" + getName();
    }

}
