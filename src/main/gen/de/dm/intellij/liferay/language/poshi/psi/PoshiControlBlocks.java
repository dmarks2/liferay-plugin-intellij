// This is a generated file. Not intended for manual editing.
package de.dm.intellij.liferay.language.poshi.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface PoshiControlBlocks extends PsiElement {

  @NotNull
  List<PoshiAnnotations> getAnnotationsList();

  @NotNull
  List<PoshiCommandBlocks> getCommandBlocksList();

  @NotNull
  List<PoshiComments> getCommentsList();

  @NotNull
  List<PoshiControlBlocks> getControlBlocksList();

  @NotNull
  List<PoshiInvocations> getInvocationsList();

  @NotNull
  List<PoshiKeywords> getKeywordsList();

  @NotNull
  List<PoshiProperties> getPropertiesList();

  @NotNull
  List<PoshiStringQuotedDouble> getStringQuotedDoubleList();

  @NotNull
  List<PoshiStructures> getStructuresList();

  @NotNull
  List<PoshiVariableAssignment> getVariableAssignmentList();

  @NotNull
  List<PoshiVariables> getVariablesList();

}
