// This is a generated file. Not intended for manual editing.
package de.dm.intellij.liferay.language.poshi.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface PoshiCommandBlock extends PsiElement {

  @NotNull
  List<PoshiAnnotation> getAnnotationList();

  @NotNull
  List<PoshiComments> getCommentsList();

  @NotNull
  List<PoshiControlBlock> getControlBlockList();

  @NotNull
  PoshiDefinitionBase getDefinitionBase();

  @NotNull
  List<PoshiForLoop> getForLoopList();

  @NotNull
  List<PoshiInvocation> getInvocationList();

  @NotNull
  List<PoshiPropertyInstruction> getPropertyInstructionList();

  @NotNull
  List<PoshiReturnStatement> getReturnStatementList();

  @NotNull
  List<PoshiVariable> getVariableList();

}
