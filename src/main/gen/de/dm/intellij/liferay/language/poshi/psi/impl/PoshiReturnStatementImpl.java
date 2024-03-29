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

public class PoshiReturnStatementImpl extends ASTWrapperPsiElement implements PoshiReturnStatement {

  public PoshiReturnStatementImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PoshiVisitor visitor) {
    visitor.visitReturnStatement(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof PoshiVisitor) accept((PoshiVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<PoshiStringQuotedDouble> getStringQuotedDoubleList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PoshiStringQuotedDouble.class);
  }

  @Override
  @NotNull
  public List<PoshiStringQuotedSingle> getStringQuotedSingleList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PoshiStringQuotedSingle.class);
  }

  @Override
  @NotNull
  public List<PoshiStringQuotedSingleMultiline> getStringQuotedSingleMultilineList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PoshiStringQuotedSingleMultiline.class);
  }

}
