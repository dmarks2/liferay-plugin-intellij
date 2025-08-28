package de.dm.intellij.liferay.language.osgi.config;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

public class LiferayConfigPsiElement extends ASTWrapperPsiElement {
    public LiferayConfigPsiElement(@NotNull ASTNode node) {
        super(node);
    }
}
