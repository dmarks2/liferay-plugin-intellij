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

public class PoshiDefinitionBlockImpl extends ASTWrapperPsiElement implements PoshiDefinitionBlock {

  public PoshiDefinitionBlockImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PoshiVisitor visitor) {
    visitor.visitDefinitionBlock(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof PoshiVisitor) accept((PoshiVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<PoshiAnnotation> getAnnotationList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PoshiAnnotation.class);
  }

  @Override
  @NotNull
  public List<PoshiCommandBlock> getCommandBlockList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PoshiCommandBlock.class);
  }

  @Override
  @NotNull
  public List<PoshiComments> getCommentsList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PoshiComments.class);
  }

  @Override
  @NotNull
  public List<PoshiPropertyInstruction> getPropertyInstructionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PoshiPropertyInstruction.class);
  }

  @Override
  @NotNull
  public List<PoshiStructureBlock> getStructureBlockList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PoshiStructureBlock.class);
  }

}
