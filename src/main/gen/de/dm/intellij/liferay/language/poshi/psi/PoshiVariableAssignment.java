// This is a generated file. Not intended for manual editing.
package de.dm.intellij.liferay.language.poshi.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface PoshiVariableAssignment extends PsiElement {

  @Nullable
  PoshiInvocations getInvocations();

  @Nullable
  PoshiStringQuotedDouble getStringQuotedDouble();

  @Nullable
  PsiElement getArithmeticOperator();

  @NotNull
  PsiElement getIdentifier();

  @Nullable
  PsiElement getNumericConstant();

  @Nullable
  PsiElement getVariableReference();

}
