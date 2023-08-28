// This is a generated file. Not intended for manual editing.
package de.dm.intellij.liferay.language.poshi.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface PoshiControlBlock extends PsiElement {

  @NotNull
  List<PoshiBreakStatement> getBreakStatementList();

  @NotNull
  List<PoshiComments> getCommentsList();

  @NotNull
  List<PoshiContinueStatement> getContinueStatementList();

  @NotNull
  List<PoshiControlBlock> getControlBlockList();

  @NotNull
  List<PoshiForLoop> getForLoopList();

  @NotNull
  List<PoshiInvocation> getInvocationList();

  @NotNull
  List<PoshiPropertyInstruction> getPropertyInstructionList();

  @NotNull
  List<PoshiReturnStatement> getReturnStatementList();

  @NotNull
  List<PoshiStringQuotedDouble> getStringQuotedDoubleList();

  @NotNull
  List<PoshiVariable> getVariableList();

  @NotNull
  List<PoshiVariableAssignment> getVariableAssignmentList();

  @NotNull
  List<PoshiVariableRef> getVariableRefList();

}
