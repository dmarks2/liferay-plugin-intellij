// This is a generated file. Not intended for manual editing.
package de.dm.intellij.liferay.language.poshi.psi;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;

public class PoshiVisitor extends PsiElementVisitor {

  public void visitAnnotation(@NotNull PoshiAnnotation o) {
    visitPsiElement(o);
  }

  public void visitCommandBlock(@NotNull PoshiCommandBlock o) {
    visitPsiElement(o);
  }

  public void visitCommentBlock(@NotNull PoshiCommentBlock o) {
    visitPsiElement(o);
  }

  public void visitComments(@NotNull PoshiComments o) {
    visitPsiElement(o);
  }

  public void visitControlBlock(@NotNull PoshiControlBlock o) {
    visitPsiElement(o);
  }

  public void visitDefinitionBase(@NotNull PoshiDefinitionBase o) {
    visitPsiElement(o);
  }

  public void visitDefinitionBlock(@NotNull PoshiDefinitionBlock o) {
    visitPsiElement(o);
  }

  public void visitFunctionDefinition(@NotNull PoshiFunctionDefinition o) {
    visitDefinitionBase(o);
    // visitPsiNamedElement(o);
  }

  public void visitInvocation(@NotNull PoshiInvocation o) {
    visitPsiElement(o);
  }

  public void visitMacroDefinition(@NotNull PoshiMacroDefinition o) {
    visitDefinitionBase(o);
    // visitPsiNamedElement(o);
  }

  public void visitMethodCall(@NotNull PoshiMethodCall o) {
    visitPsiElement(o);
  }

  public void visitPropertyInstruction(@NotNull PoshiPropertyInstruction o) {
    visitPsiElement(o);
  }

  public void visitStringQuotedDouble(@NotNull PoshiStringQuotedDouble o) {
    visitPsiElement(o);
  }

  public void visitStructureBlock(@NotNull PoshiStructureBlock o) {
    visitPsiElement(o);
  }

  public void visitTestDefinition(@NotNull PoshiTestDefinition o) {
    visitDefinitionBase(o);
    // visitPsiNamedElement(o);
  }

  public void visitVariable(@NotNull PoshiVariable o) {
    visitPsiElement(o);
  }

  public void visitVariableAssignment(@NotNull PoshiVariableAssignment o) {
    visitPsiNamedElement(o);
  }

  public void visitPsiNamedElement(@NotNull PsiNamedElement o) {
    visitElement(o);
  }

  public void visitPsiElement(@NotNull PsiElement o) {
    visitElement(o);
  }

}
