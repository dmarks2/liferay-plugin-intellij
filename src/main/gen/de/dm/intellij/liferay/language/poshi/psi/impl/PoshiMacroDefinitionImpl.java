// This is a generated file. Not intended for manual editing.
package de.dm.intellij.liferay.language.poshi.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static de.dm.intellij.liferay.language.poshi.psi.PoshiTypes.*;
import de.dm.intellij.liferay.language.poshi.psi.*;

public class PoshiMacroDefinitionImpl extends PoshiDefinitionBaseImpl implements PoshiMacroDefinition {

  public PoshiMacroDefinitionImpl(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public void accept(@NotNull PoshiVisitor visitor) {
    visitor.visitMacroDefinition(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof PoshiVisitor) accept((PoshiVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public PsiElement getIdentifier() {
    return findNotNullChildByType(IDENTIFIER);
  }

}