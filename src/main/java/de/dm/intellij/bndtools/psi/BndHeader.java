package de.dm.intellij.bndtools.psi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.lang.manifest.psi.Header;

public interface BndHeader extends Header {

    @NotNull
    BndToken getBndNameElement();

}
