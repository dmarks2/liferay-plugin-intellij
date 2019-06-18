package de.dm.intellij.bndtools.psi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.lang.manifest.psi.HeaderValue;
import org.jetbrains.lang.manifest.psi.HeaderValuePart;

import java.util.List;

public interface Clause extends HeaderValue {

    /**
     * Returns the attribute with the given name.
     */
    @Nullable
    public Attribute getAttribute(@NotNull String name);

    /**
     * Returns all attributes of this clause.
     */
    @NotNull
    public List<Attribute> getAttributes();

    /**
     * Returns the directive with the given name.
     */
    @Nullable
    public Directive getDirective(@NotNull String name);

    /**
     * Returns all directives of this clause.
     */
    @NotNull
    public List<Directive> getDirectives();

    /**
     * Returns the value of this clause.
     */
    @Nullable
    public HeaderValuePart getValue();

}
