package de.dm.intellij.bndtools.psi.impl;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.util.PsiTreeUtil;
import de.dm.intellij.bndtools.BndFileType;
import de.dm.intellij.bndtools.BndLanguage;
import de.dm.intellij.bndtools.psi.BndFile;
import de.dm.intellij.bndtools.psi.BndHeader;
import de.dm.intellij.bndtools.psi.BndSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BndFileImpl extends PsiFileBase implements BndFile {

    public BndFileImpl(FileViewProvider viewProvider) {
        super(viewProvider, BndLanguage.getInstance());
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return BndFileType.INSTANCE;
    }

    @NotNull
    @Override
    public List<BndSection> getSections() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, BndSection.class);
    }

    @Nullable
    @Override
    public BndSection getMainSection() {
        return findChildByClass(BndSection.class);
    }

    @NotNull
    @Override
    public List<BndHeader> getHeaders() {
        return PsiTreeUtil.getChildrenOfTypeAsList(getFirstChild(), BndHeader.class);
    }

    @Nullable
    @Override
    public BndHeader getHeader(@NotNull String name) {
        BndHeader child = PsiTreeUtil.findChildOfType(getFirstChild(), BndHeader.class);
        while (child != null) {
            if (name.equals(child.getName())) {
                return child;
            }
            child = PsiTreeUtil.getNextSiblingOfType(child, BndHeader.class);
        }
        return null;
    }

    @Override
    public String toString() {
        return "BndFile:" + getName();
    }

}
