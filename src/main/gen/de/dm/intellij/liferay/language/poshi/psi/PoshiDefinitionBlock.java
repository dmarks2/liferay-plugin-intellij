// This is a generated file. Not intended for manual editing.
package de.dm.intellij.liferay.language.poshi.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface PoshiDefinitionBlock extends PsiElement {

  @NotNull
  List<PoshiAnnotation> getAnnotationList();

  @NotNull
  List<PoshiCommandBlock> getCommandBlockList();

  @NotNull
  List<PoshiComments> getCommentsList();

  @NotNull
  List<PoshiPropertyInstruction> getPropertyInstructionList();

  @NotNull
  List<PoshiStructureBlock> getStructureBlockList();

}
