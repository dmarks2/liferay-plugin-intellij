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

public class PoshiVariableAssignmentImpl extends ASTWrapperPsiElement implements PoshiVariableAssignment {

  public PoshiVariableAssignmentImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PoshiVisitor visitor) {
    visitor.visitVariableAssignment(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof PoshiVisitor) accept((PoshiVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public PoshiInvocations getInvocations() {
    return findChildByClass(PoshiInvocations.class);
  }

  @Override
  @Nullable
  public PoshiStringQuotedDouble getStringQuotedDouble() {
    return findChildByClass(PoshiStringQuotedDouble.class);
  }

  @Override
  @Nullable
  public PsiElement getArithmeticOperator() {
    return findChildByType(ARITHMETIC_OPERATOR);
  }

  @Override
  @NotNull
  public PsiElement getIdentifier() {
    return findNotNullChildByType(IDENTIFIER);
  }

  @Override
  @Nullable
  public PsiElement getNumericConstant() {
    return findChildByType(NUMERIC_CONSTANT);
  }

  @Override
  @Nullable
  public PsiElement getVariableReference() {
    return findChildByType(VARIABLE_REFERENCE);
  }

}
