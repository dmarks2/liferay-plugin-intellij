// This is a generated file. Not intended for manual editing.
package de.dm.intellij.liferay.language.poshi.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import de.dm.intellij.liferay.language.poshi.psi.impl.*;

public interface PoshiTypes {

  IElementType ANNOTATION = new PoshiElementType("ANNOTATION");
  IElementType COMMAND_BLOCK = new PoshiElementType("COMMAND_BLOCK");
  IElementType COMMENTS = new PoshiElementType("COMMENTS");
  IElementType COMMENT_BLOCK = new PoshiElementType("COMMENT_BLOCK");
  IElementType CONTROL_BLOCK = new PoshiElementType("CONTROL_BLOCK");
  IElementType DEFINITION_BLOCK = new PoshiElementType("DEFINITION_BLOCK");
  IElementType INVOCATION = new PoshiElementType("INVOCATION");
  IElementType PROPERTY_INSTRUCTION = new PoshiElementType("PROPERTY_INSTRUCTION");
  IElementType STRING_QUOTED_DOUBLE = new PoshiElementType("STRING_QUOTED_DOUBLE");
  IElementType STRUCTURE_BLOCK = new PoshiElementType("STRUCTURE_BLOCK");
  IElementType VARIABLE = new PoshiElementType("VARIABLE");
  IElementType VARIABLE_ASSIGNMENT = new PoshiElementType("VARIABLE_ASSIGNMENT");

  IElementType ANNOTATION_NAME = new PoshiTokenType("ANNOTATION_NAME");
  IElementType ARITHMETIC_OPERATOR = new PoshiTokenType("ARITHMETIC_OPERATOR");
  IElementType BLOCK_COMMENT = new PoshiTokenType("BLOCK_COMMENT");
  IElementType COMMA = new PoshiTokenType(",");
  IElementType COMPARISION_OPERATOR = new PoshiTokenType("COMPARISION_OPERATOR");
  IElementType CURLY_LBRACE = new PoshiTokenType("{");
  IElementType CURLY_RBRACE = new PoshiTokenType("}");
  IElementType DEFINITION = new PoshiTokenType("definition");
  IElementType DOUBLE_QUOTED_STRING = new PoshiTokenType("DOUBLE_QUOTED_STRING");
  IElementType ELSE = new PoshiTokenType("else");
  IElementType ELSE_IF = new PoshiTokenType("else if");
  IElementType EQUALS = new PoshiTokenType("=");
  IElementType FUNCTION = new PoshiTokenType("function");
  IElementType IDENTIFIER = new PoshiTokenType("IDENTIFIER");
  IElementType IF = new PoshiTokenType("if");
  IElementType LINE_COMMENT = new PoshiTokenType("LINE_COMMENT");
  IElementType MACRO = new PoshiTokenType("macro");
  IElementType NUMERIC_CONSTANT = new PoshiTokenType("NUMERIC_CONSTANT");
  IElementType PERIOD = new PoshiTokenType(".");
  IElementType PROPERTY = new PoshiTokenType("property");
  IElementType RETURN = new PoshiTokenType("return");
  IElementType ROUND_LBRACE = new PoshiTokenType("(");
  IElementType ROUND_RBRACE = new PoshiTokenType(")");
  IElementType SEMICOLON = new PoshiTokenType(";");
  IElementType SET_UP = new PoshiTokenType("setUp");
  IElementType SINGLE_QUOTED_STRING = new PoshiTokenType("SINGLE_QUOTED_STRING");
  IElementType SQUARE_LBRACE = new PoshiTokenType("[");
  IElementType SQUARE_RBRACE = new PoshiTokenType("]");
  IElementType STATIC = new PoshiTokenType("static");
  IElementType TASK = new PoshiTokenType("task");
  IElementType TEAR_DOWN = new PoshiTokenType("tearDown");
  IElementType TEST = new PoshiTokenType("test");
  IElementType VAR = new PoshiTokenType("var");
  IElementType WHILE = new PoshiTokenType("while");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == ANNOTATION) {
        return new PoshiAnnotationImpl(node);
      }
      else if (type == COMMAND_BLOCK) {
        return new PoshiCommandBlockImpl(node);
      }
      else if (type == COMMENTS) {
        return new PoshiCommentsImpl(node);
      }
      else if (type == COMMENT_BLOCK) {
        return new PoshiCommentBlockImpl(node);
      }
      else if (type == CONTROL_BLOCK) {
        return new PoshiControlBlockImpl(node);
      }
      else if (type == DEFINITION_BLOCK) {
        return new PoshiDefinitionBlockImpl(node);
      }
      else if (type == INVOCATION) {
        return new PoshiInvocationImpl(node);
      }
      else if (type == PROPERTY_INSTRUCTION) {
        return new PoshiPropertyInstructionImpl(node);
      }
      else if (type == STRING_QUOTED_DOUBLE) {
        return new PoshiStringQuotedDoubleImpl(node);
      }
      else if (type == STRUCTURE_BLOCK) {
        return new PoshiStructureBlockImpl(node);
      }
      else if (type == VARIABLE) {
        return new PoshiVariableImpl(node);
      }
      else if (type == VARIABLE_ASSIGNMENT) {
        return new PoshiVariableAssignmentImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
