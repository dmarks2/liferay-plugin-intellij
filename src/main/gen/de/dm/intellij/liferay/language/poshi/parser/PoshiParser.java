// This is a generated file. Not intended for manual editing.
package de.dm.intellij.liferay.language.poshi.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static de.dm.intellij.liferay.language.poshi.psi.PoshiTypes.*;
import static de.dm.intellij.liferay.language.poshi.parser.PoshiParserUtil.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class PoshiParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    r = parse_root_(t, b);
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b) {
    return parse_root_(t, b, 0);
  }

  static boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return poshi_file(b, l + 1);
  }

  /* ********************************************************** */
  // ANNOTATION_NAME [EQUALS string-quoted-double]
  public static boolean annotation(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "annotation")) return false;
    if (!nextTokenIs(b, ANNOTATION_NAME)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ANNOTATION_NAME);
    r = r && annotation_1(b, l + 1);
    exit_section_(b, m, ANNOTATION, r);
    return r;
  }

  // [EQUALS string-quoted-double]
  private static boolean annotation_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "annotation_1")) return false;
    annotation_1_0(b, l + 1);
    return true;
  }

  // EQUALS string-quoted-double
  private static boolean annotation_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "annotation_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, EQUALS);
    r = r && string_quoted_double(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // [annotation*] (FUNCTION | MACRO | TEST) IDENTIFIER [CURLY_LBRACE {invocation | property-instruction | variable | control-block | comments}* CURLY_RBRACE]
  public static boolean command_block(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "command_block")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, COMMAND_BLOCK, "<command block>");
    r = command_block_0(b, l + 1);
    r = r && command_block_1(b, l + 1);
    r = r && consumeToken(b, IDENTIFIER);
    r = r && command_block_3(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // [annotation*]
  private static boolean command_block_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "command_block_0")) return false;
    command_block_0_0(b, l + 1);
    return true;
  }

  // annotation*
  private static boolean command_block_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "command_block_0_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!annotation(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "command_block_0_0", c)) break;
    }
    return true;
  }

  // FUNCTION | MACRO | TEST
  private static boolean command_block_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "command_block_1")) return false;
    boolean r;
    r = consumeToken(b, FUNCTION);
    if (!r) r = consumeToken(b, MACRO);
    if (!r) r = consumeToken(b, TEST);
    return r;
  }

  // [CURLY_LBRACE {invocation | property-instruction | variable | control-block | comments}* CURLY_RBRACE]
  private static boolean command_block_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "command_block_3")) return false;
    command_block_3_0(b, l + 1);
    return true;
  }

  // CURLY_LBRACE {invocation | property-instruction | variable | control-block | comments}* CURLY_RBRACE
  private static boolean command_block_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "command_block_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, CURLY_LBRACE);
    r = r && command_block_3_0_1(b, l + 1);
    r = r && consumeToken(b, CURLY_RBRACE);
    exit_section_(b, m, null, r);
    return r;
  }

  // {invocation | property-instruction | variable | control-block | comments}*
  private static boolean command_block_3_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "command_block_3_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!command_block_3_0_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "command_block_3_0_1", c)) break;
    }
    return true;
  }

  // invocation | property-instruction | variable | control-block | comments
  private static boolean command_block_3_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "command_block_3_0_1_0")) return false;
    boolean r;
    r = invocation(b, l + 1);
    if (!r) r = property_instruction(b, l + 1);
    if (!r) r = variable(b, l + 1);
    if (!r) r = control_block(b, l + 1);
    if (!r) r = comments(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // BLOCK_COMMENT
  public static boolean comment_block(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "comment_block")) return false;
    if (!nextTokenIs(b, BLOCK_COMMENT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, BLOCK_COMMENT);
    exit_section_(b, m, COMMENT_BLOCK, r);
    return r;
  }

  /* ********************************************************** */
  // comment-block | LINE_COMMENT
  public static boolean comments(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "comments")) return false;
    if (!nextTokenIs(b, "<comments>", BLOCK_COMMENT, LINE_COMMENT)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, COMMENTS, "<comments>");
    r = comment_block(b, l + 1);
    if (!r) r = consumeToken(b, LINE_COMMENT);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // (control-block-with-condition | control-block-without-condition) CURLY_LBRACE {invocation | property-instruction | variable | control-block | comments}* CURLY_RBRACE
  public static boolean control_block(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "control_block")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, CONTROL_BLOCK, "<control block>");
    r = control_block_0(b, l + 1);
    r = r && consumeToken(b, CURLY_LBRACE);
    r = r && control_block_2(b, l + 1);
    r = r && consumeToken(b, CURLY_RBRACE);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // control-block-with-condition | control-block-without-condition
  private static boolean control_block_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "control_block_0")) return false;
    boolean r;
    r = control_block_with_condition(b, l + 1);
    if (!r) r = control_block_without_condition(b, l + 1);
    return r;
  }

  // {invocation | property-instruction | variable | control-block | comments}*
  private static boolean control_block_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "control_block_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!control_block_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "control_block_2", c)) break;
    }
    return true;
  }

  // invocation | property-instruction | variable | control-block | comments
  private static boolean control_block_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "control_block_2_0")) return false;
    boolean r;
    r = invocation(b, l + 1);
    if (!r) r = property_instruction(b, l + 1);
    if (!r) r = variable(b, l + 1);
    if (!r) r = control_block(b, l + 1);
    if (!r) r = comments(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // invocation | string-quoted-double | variable-assignment | COMPARISION_OPERATOR
  static boolean control_block_inner(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "control_block_inner")) return false;
    boolean r;
    r = invocation(b, l + 1);
    if (!r) r = string_quoted_double(b, l + 1);
    if (!r) r = variable_assignment(b, l + 1);
    if (!r) r = consumeToken(b, COMPARISION_OPERATOR);
    return r;
  }

  /* ********************************************************** */
  // (IF | ELSE_IF | TASK | WHILE) ROUND_LBRACE control-block-inner* ROUND_RBRACE
  static boolean control_block_with_condition(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "control_block_with_condition")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = control_block_with_condition_0(b, l + 1);
    r = r && consumeToken(b, ROUND_LBRACE);
    r = r && control_block_with_condition_2(b, l + 1);
    r = r && consumeToken(b, ROUND_RBRACE);
    exit_section_(b, m, null, r);
    return r;
  }

  // IF | ELSE_IF | TASK | WHILE
  private static boolean control_block_with_condition_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "control_block_with_condition_0")) return false;
    boolean r;
    r = consumeToken(b, IF);
    if (!r) r = consumeToken(b, ELSE_IF);
    if (!r) r = consumeToken(b, TASK);
    if (!r) r = consumeToken(b, WHILE);
    return r;
  }

  // control-block-inner*
  private static boolean control_block_with_condition_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "control_block_with_condition_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!control_block_inner(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "control_block_with_condition_2", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // (ELSE)
  static boolean control_block_without_condition(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "control_block_without_condition")) return false;
    if (!nextTokenIs(b, ELSE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ELSE);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // [annotation*] DEFINITION [CURLY_LBRACE {structure-block | command-block | property-instruction | variable | comments}* CURLY_RBRACE]
  public static boolean definition_block(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "definition_block")) return false;
    if (!nextTokenIs(b, "<definition block>", ANNOTATION_NAME, DEFINITION)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, DEFINITION_BLOCK, "<definition block>");
    r = definition_block_0(b, l + 1);
    r = r && consumeToken(b, DEFINITION);
    r = r && definition_block_2(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // [annotation*]
  private static boolean definition_block_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "definition_block_0")) return false;
    definition_block_0_0(b, l + 1);
    return true;
  }

  // annotation*
  private static boolean definition_block_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "definition_block_0_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!annotation(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "definition_block_0_0", c)) break;
    }
    return true;
  }

  // [CURLY_LBRACE {structure-block | command-block | property-instruction | variable | comments}* CURLY_RBRACE]
  private static boolean definition_block_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "definition_block_2")) return false;
    definition_block_2_0(b, l + 1);
    return true;
  }

  // CURLY_LBRACE {structure-block | command-block | property-instruction | variable | comments}* CURLY_RBRACE
  private static boolean definition_block_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "definition_block_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, CURLY_LBRACE);
    r = r && definition_block_2_0_1(b, l + 1);
    r = r && consumeToken(b, CURLY_RBRACE);
    exit_section_(b, m, null, r);
    return r;
  }

  // {structure-block | command-block | property-instruction | variable | comments}*
  private static boolean definition_block_2_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "definition_block_2_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!definition_block_2_0_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "definition_block_2_0_1", c)) break;
    }
    return true;
  }

  // structure-block | command-block | property-instruction | variable | comments
  private static boolean definition_block_2_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "definition_block_2_0_1_0")) return false;
    boolean r;
    r = structure_block(b, l + 1);
    if (!r) r = command_block(b, l + 1);
    if (!r) r = property_instruction(b, l + 1);
    if (!r) r = variable(b, l + 1);
    if (!r) r = comments(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // IDENTIFIER {PERIOD IDENTIFIER}* [ROUND_LBRACE [invocation-inner-list] ROUND_RBRACE] [SEMICOLON]
  public static boolean invocation(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "invocation")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    r = r && invocation_1(b, l + 1);
    r = r && invocation_2(b, l + 1);
    r = r && invocation_3(b, l + 1);
    exit_section_(b, m, INVOCATION, r);
    return r;
  }

  // {PERIOD IDENTIFIER}*
  private static boolean invocation_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "invocation_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!invocation_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "invocation_1", c)) break;
    }
    return true;
  }

  // PERIOD IDENTIFIER
  private static boolean invocation_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "invocation_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, PERIOD, IDENTIFIER);
    exit_section_(b, m, null, r);
    return r;
  }

  // [ROUND_LBRACE [invocation-inner-list] ROUND_RBRACE]
  private static boolean invocation_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "invocation_2")) return false;
    invocation_2_0(b, l + 1);
    return true;
  }

  // ROUND_LBRACE [invocation-inner-list] ROUND_RBRACE
  private static boolean invocation_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "invocation_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ROUND_LBRACE);
    r = r && invocation_2_0_1(b, l + 1);
    r = r && consumeToken(b, ROUND_RBRACE);
    exit_section_(b, m, null, r);
    return r;
  }

  // [invocation-inner-list]
  private static boolean invocation_2_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "invocation_2_0_1")) return false;
    invocation_inner_list(b, l + 1);
    return true;
  }

  // [SEMICOLON]
  private static boolean invocation_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "invocation_3")) return false;
    consumeToken(b, SEMICOLON);
    return true;
  }

  /* ********************************************************** */
  // variable-assignment | IDENTIFIER | string-quoted-double
  static boolean invocation_inner(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "invocation_inner")) return false;
    if (!nextTokenIs(b, "", DOUBLE_QUOTED_STRING, IDENTIFIER)) return false;
    boolean r;
    r = variable_assignment(b, l + 1);
    if (!r) r = consumeToken(b, IDENTIFIER);
    if (!r) r = string_quoted_double(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // invocation-inner [COMMA invocation-inner]
  static boolean invocation_inner_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "invocation_inner_list")) return false;
    if (!nextTokenIs(b, "", DOUBLE_QUOTED_STRING, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = invocation_inner(b, l + 1);
    r = r && invocation_inner_list_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [COMMA invocation-inner]
  private static boolean invocation_inner_list_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "invocation_inner_list_1")) return false;
    invocation_inner_list_1_0(b, l + 1);
    return true;
  }

  // COMMA invocation-inner
  private static boolean invocation_inner_list_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "invocation_inner_list_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && invocation_inner(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // definition-block | comments
  static boolean poshi_file(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "poshi_file")) return false;
    boolean r;
    r = definition_block(b, l + 1);
    if (!r) r = comments(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // PROPERTY IDENTIFIER {PERIOD IDENTIFIER}* EQUALS (string-quoted-double) [SEMICOLON]
  public static boolean property_instruction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "property_instruction")) return false;
    if (!nextTokenIs(b, PROPERTY)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, PROPERTY, IDENTIFIER);
    r = r && property_instruction_2(b, l + 1);
    r = r && consumeToken(b, EQUALS);
    r = r && property_instruction_4(b, l + 1);
    r = r && property_instruction_5(b, l + 1);
    exit_section_(b, m, PROPERTY_INSTRUCTION, r);
    return r;
  }

  // {PERIOD IDENTIFIER}*
  private static boolean property_instruction_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "property_instruction_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!property_instruction_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "property_instruction_2", c)) break;
    }
    return true;
  }

  // PERIOD IDENTIFIER
  private static boolean property_instruction_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "property_instruction_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, PERIOD, IDENTIFIER);
    exit_section_(b, m, null, r);
    return r;
  }

  // (string-quoted-double)
  private static boolean property_instruction_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "property_instruction_4")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = string_quoted_double(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // [SEMICOLON]
  private static boolean property_instruction_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "property_instruction_5")) return false;
    consumeToken(b, SEMICOLON);
    return true;
  }

  /* ********************************************************** */
  // DOUBLE_QUOTED_STRING
  public static boolean string_quoted_double(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "string_quoted_double")) return false;
    if (!nextTokenIs(b, DOUBLE_QUOTED_STRING)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DOUBLE_QUOTED_STRING);
    exit_section_(b, m, STRING_QUOTED_DOUBLE, r);
    return r;
  }

  /* ********************************************************** */
  // (SET_UP | TEAR_DOWN) [CURLY_LBRACE {invocation | property-instruction | variable | control-block | comments}* CURLY_RBRACE]
  public static boolean structure_block(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "structure_block")) return false;
    if (!nextTokenIs(b, "<structure block>", SET_UP, TEAR_DOWN)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, STRUCTURE_BLOCK, "<structure block>");
    r = structure_block_0(b, l + 1);
    r = r && structure_block_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // SET_UP | TEAR_DOWN
  private static boolean structure_block_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "structure_block_0")) return false;
    boolean r;
    r = consumeToken(b, SET_UP);
    if (!r) r = consumeToken(b, TEAR_DOWN);
    return r;
  }

  // [CURLY_LBRACE {invocation | property-instruction | variable | control-block | comments}* CURLY_RBRACE]
  private static boolean structure_block_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "structure_block_1")) return false;
    structure_block_1_0(b, l + 1);
    return true;
  }

  // CURLY_LBRACE {invocation | property-instruction | variable | control-block | comments}* CURLY_RBRACE
  private static boolean structure_block_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "structure_block_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, CURLY_LBRACE);
    r = r && structure_block_1_0_1(b, l + 1);
    r = r && consumeToken(b, CURLY_RBRACE);
    exit_section_(b, m, null, r);
    return r;
  }

  // {invocation | property-instruction | variable | control-block | comments}*
  private static boolean structure_block_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "structure_block_1_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!structure_block_1_0_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "structure_block_1_0_1", c)) break;
    }
    return true;
  }

  // invocation | property-instruction | variable | control-block | comments
  private static boolean structure_block_1_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "structure_block_1_0_1_0")) return false;
    boolean r;
    r = invocation(b, l + 1);
    if (!r) r = property_instruction(b, l + 1);
    if (!r) r = variable(b, l + 1);
    if (!r) r = control_block(b, l + 1);
    if (!r) r = comments(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // [STATIC] VAR variable-assignment [SEMICOLON]
  public static boolean variable(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variable")) return false;
    if (!nextTokenIs(b, "<variable>", STATIC, VAR)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, VARIABLE, "<variable>");
    r = variable_0(b, l + 1);
    r = r && consumeToken(b, VAR);
    r = r && variable_assignment(b, l + 1);
    r = r && variable_3(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // [STATIC]
  private static boolean variable_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variable_0")) return false;
    consumeToken(b, STATIC);
    return true;
  }

  // [SEMICOLON]
  private static boolean variable_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variable_3")) return false;
    consumeToken(b, SEMICOLON);
    return true;
  }

  /* ********************************************************** */
  // IDENTIFIER EQUALS variable-assignment-inner {ARITHMETIC_OPERATOR variable-assignment-inner}*
  public static boolean variable_assignment(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variable_assignment")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, IDENTIFIER, EQUALS);
    r = r && variable_assignment_inner(b, l + 1);
    r = r && variable_assignment_3(b, l + 1);
    exit_section_(b, m, VARIABLE_ASSIGNMENT, r);
    return r;
  }

  // {ARITHMETIC_OPERATOR variable-assignment-inner}*
  private static boolean variable_assignment_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variable_assignment_3")) return false;
    while (true) {
      int c = current_position_(b);
      if (!variable_assignment_3_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "variable_assignment_3", c)) break;
    }
    return true;
  }

  // ARITHMETIC_OPERATOR variable-assignment-inner
  private static boolean variable_assignment_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variable_assignment_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ARITHMETIC_OPERATOR);
    r = r && variable_assignment_inner(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // string-quoted-double | invocation | NUMERIC_CONSTANT
  static boolean variable_assignment_inner(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variable_assignment_inner")) return false;
    boolean r;
    r = string_quoted_double(b, l + 1);
    if (!r) r = invocation(b, l + 1);
    if (!r) r = consumeToken(b, NUMERIC_CONSTANT);
    return r;
  }

}
