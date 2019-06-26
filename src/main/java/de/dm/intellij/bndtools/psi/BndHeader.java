package de.dm.intellij.bndtools.psi;

import com.intellij.psi.PsiNamedElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface BndHeader extends PsiNamedElement {

    @NotNull
    @Override
    String getName();

    @NotNull
    BndToken getBndNameElement();

    /**
     * Returns a first header value element if exists.
     */
    @Nullable
    BndHeaderValue getBndHeaderValue();

    /**
     * Returns a list of all header value elements.
     */
    @NotNull
    List<BndHeaderValue> getBndHeaderValues();

}
