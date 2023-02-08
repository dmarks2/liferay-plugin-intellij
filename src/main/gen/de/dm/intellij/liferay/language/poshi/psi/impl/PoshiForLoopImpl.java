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

public class PoshiForLoopImpl extends ASTWrapperPsiElement implements PoshiForLoop {

  public PoshiForLoopImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PoshiVisitor visitor) {
    visitor.visitForLoop(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof PoshiVisitor) accept((PoshiVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<PoshiComments> getCommentsList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PoshiComments.class);
  }

  @Override
  @NotNull
  public List<PoshiControlBlock> getControlBlockList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PoshiControlBlock.class);
  }

  @Override
  @NotNull
  public List<PoshiForLoop> getForLoopList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PoshiForLoop.class);
  }

  @Override
  @NotNull
  public List<PoshiInvocation> getInvocationList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PoshiInvocation.class);
  }

  @Override
  @NotNull
  public List<PoshiPropertyInstruction> getPropertyInstructionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PoshiPropertyInstruction.class);
  }

  @Override
  @NotNull
  public List<PoshiReturnStatement> getReturnStatementList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PoshiReturnStatement.class);
  }

  @Override
  @Nullable
  public PoshiStringQuotedDouble getStringQuotedDouble() {
    return findChildByClass(PoshiStringQuotedDouble.class);
  }

  @Override
  @Nullable
  public PoshiStringQuotedSingle getStringQuotedSingle() {
    return findChildByClass(PoshiStringQuotedSingle.class);
  }

  @Override
  @Nullable
  public PoshiStringQuotedSingleMultiline getStringQuotedSingleMultiline() {
    return findChildByClass(PoshiStringQuotedSingleMultiline.class);
  }

  @Override
  @NotNull
  public List<PoshiVariable> getVariableList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PoshiVariable.class);
  }

  @Override
  @NotNull
  public PsiElement getIdentifier() {
    return findNotNullChildByType(IDENTIFIER);
  }

}
