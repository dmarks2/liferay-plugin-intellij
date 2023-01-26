// This is a generated file. Not intended for manual editing.
package de.dm.intellij.liferay.language.poshi.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface PoshiInvocation extends PsiElement {

  @NotNull
  PoshiMethodCall getMethodCall();

  @NotNull
  List<PoshiStringQuotedDouble> getStringQuotedDoubleList();

  @NotNull
  List<PoshiStringQuotedSingle> getStringQuotedSingleList();

  @NotNull
  List<PoshiStringQuotedSingleMultiline> getStringQuotedSingleMultilineList();

  @NotNull
  List<PoshiVariableAssignment> getVariableAssignmentList();

}
