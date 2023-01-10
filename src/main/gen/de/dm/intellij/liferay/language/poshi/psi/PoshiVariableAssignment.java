// This is a generated file. Not intended for manual editing.
package de.dm.intellij.liferay.language.poshi.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;

public interface PoshiVariableAssignment extends PsiNamedElement {

  @Nullable
  PoshiInvocation getInvocation();

  @Nullable
  PoshiStringQuotedDouble getStringQuotedDouble();

  @NotNull
  PsiElement getIdentifier();

  String getName();

  PsiElement setName(String newName);

}
