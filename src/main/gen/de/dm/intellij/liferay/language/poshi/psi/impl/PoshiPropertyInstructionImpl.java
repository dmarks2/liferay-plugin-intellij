// This is a generated file. Not intended for manual editing.
package de.dm.intellij.liferay.language.poshi.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static de.dm.intellij.liferay.language.poshi.psi.PoshiTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import de.dm.intellij.liferay.language.poshi.psi.*;

public class PoshiPropertyInstructionImpl extends ASTWrapperPsiElement implements PoshiPropertyInstruction {

  public PoshiPropertyInstructionImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PoshiVisitor visitor) {
    visitor.visitPropertyInstruction(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof PoshiVisitor) accept((PoshiVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public PoshiStringQuotedDouble getStringQuotedDouble() {
    return findChildByClass(PoshiStringQuotedDouble.class);
  }

  @Override
  @Nullable
  public PoshiStringQuotedSingleMultiline getStringQuotedSingleMultiline() {
    return findChildByClass(PoshiStringQuotedSingleMultiline.class);
  }

}
