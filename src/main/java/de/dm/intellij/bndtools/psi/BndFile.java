package de.dm.intellij.bndtools.psi;

import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface BndFile extends PsiFile {

    /**
     * Returns all sections of the file.
     */
    @NotNull
    List<BndSection> getSections();

    /**
     * Returns main (first) section if not empty.
     */
    @Nullable
    BndSection getMainSection();

    /**
     * Returns all headers from the main section in this file.
     */
    @NotNull
    List<BndHeader> getHeaders();

    /**
     * Returns the header from the main section with the given name, or null if no such header exists.
     */
    @Nullable
    BndHeader getHeader(@NotNull String name);
}

