// This is a generated file. Not intended for manual editing.
package de.dm.intellij.liferay.language.poshi.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;

public interface PoshiCommandBlock extends PsiNamedElement {

  @NotNull
  List<PoshiAnnotation> getAnnotationList();

  @NotNull
  List<PoshiComments> getCommentsList();

  @NotNull
  List<PoshiControlBlock> getControlBlockList();

  @NotNull
  List<PoshiInvocation> getInvocationList();

  @NotNull
  List<PoshiPropertyInstruction> getPropertyInstructionList();

  @NotNull
  List<PoshiVariable> getVariableList();

  @NotNull
  PsiElement getIdentifier();

  String getName();

  PsiElement setName(String newName);

}
