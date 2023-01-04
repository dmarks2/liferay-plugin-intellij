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

public class PoshiCommandBlocksImpl extends ASTWrapperPsiElement implements PoshiCommandBlocks {

  public PoshiCommandBlocksImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PoshiVisitor visitor) {
    visitor.visitCommandBlocks(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof PoshiVisitor) accept((PoshiVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<PoshiAnnotations> getAnnotationsList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PoshiAnnotations.class);
  }

  @Override
  @NotNull
  public List<PoshiCommandBlocks> getCommandBlocksList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PoshiCommandBlocks.class);
  }

  @Override
  @NotNull
  public List<PoshiComments> getCommentsList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PoshiComments.class);
  }

  @Override
  @NotNull
  public List<PoshiControlBlocks> getControlBlocksList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PoshiControlBlocks.class);
  }

  @Override
  @NotNull
  public List<PoshiInvocations> getInvocationsList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PoshiInvocations.class);
  }

  @Override
  @NotNull
  public List<PoshiKeywords> getKeywordsList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PoshiKeywords.class);
  }

  @Override
  @NotNull
  public List<PoshiProperties> getPropertiesList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PoshiProperties.class);
  }

  @Override
  @NotNull
  public List<PoshiStructures> getStructuresList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PoshiStructures.class);
  }

  @Override
  @NotNull
  public List<PoshiVariables> getVariablesList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PoshiVariables.class);
  }

  @Override
  @NotNull
  public PsiElement getIdentifier() {
    return findNotNullChildByType(IDENTIFIER);
  }

}
