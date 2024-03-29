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

public class PoshiInvocationImpl extends ASTWrapperPsiElement implements PoshiInvocation {

  public PoshiInvocationImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PoshiVisitor visitor) {
    visitor.visitInvocation(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof PoshiVisitor) accept((PoshiVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public PoshiMethodCall getMethodCall() {
    return findNotNullChildByClass(PoshiMethodCall.class);
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

  @Override
  @NotNull
  public List<PoshiVariableAssignment> getVariableAssignmentList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PoshiVariableAssignment.class);
  }

  @Override
  @NotNull
  public List<PoshiVariableRef> getVariableRefList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PoshiVariableRef.class);
  }

}
