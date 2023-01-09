// This is a generated file. Not intended for manual editing.
package de.dm.intellij.liferay.language.poshi.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static de.dm.intellij.liferay.language.poshi.psi.PoshiTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
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
  public static boolean annotations(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "annotations")) return false;
    if (!nextTokenIs(b, ANNOTATION_NAME)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ANNOTATION_NAME);
    r = r && annotations_1(b, l + 1);
    exit_section_(b, m, ANNOTATIONS, r);
    return r;
  }

  // [EQUALS string-quoted-double]
  private static boolean annotations_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "annotations_1")) return false;
    annotations_1_0(b, l + 1);
    return true;
  }

  // EQUALS string-quoted-double
  private static boolean annotations_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "annotations_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, EQUALS);
    r = r && string_quoted_double(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // (FUNCTION | MACRO | TEST) IDENTIFIER CURLY_LBRACE [poshi-code*] CURLY_RBRACE
  public static boolean command_blocks(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "command_blocks")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, COMMAND_BLOCKS, "<command blocks>");
    r = command_blocks_0(b, l + 1);
    r = r && consumeTokens(b, 0, IDENTIFIER, CURLY_LBRACE);
    r = r && command_blocks_3(b, l + 1);
    r = r && consumeToken(b, CURLY_RBRACE);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // FUNCTION | MACRO | TEST
  private static boolean command_blocks_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "command_blocks_0")) return false;
    boolean r;
    r = consumeToken(b, FUNCTION);
    if (!r) r = consumeToken(b, MACRO);
    if (!r) r = consumeToken(b, TEST);
    return r;
  }

  // [poshi-code*]
  private static boolean command_blocks_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "command_blocks_3")) return false;
    command_blocks_3_0(b, l + 1);
    return true;
  }

  // poshi-code*
  private static boolean command_blocks_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "command_blocks_3_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!poshi_code(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "command_blocks_3_0", c)) break;
    }
    return true;
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
  // invocations | string-quoted-double | variable-assignment | COMPARISION_OPERATOR
  static boolean control_block_inner(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "control_block_inner")) return false;
    boolean r;
    r = invocations(b, l + 1);
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
  // (control-block-with-condition | control-block-without-condition) CURLY_LBRACE [poshi-code*] CURLY_RBRACE
  public static boolean control_blocks(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "control_blocks")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, CONTROL_BLOCKS, "<control blocks>");
    r = control_blocks_0(b, l + 1);
    r = r && consumeToken(b, CURLY_LBRACE);
    r = r && control_blocks_2(b, l + 1);
    r = r && consumeToken(b, CURLY_RBRACE);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // control-block-with-condition | control-block-without-condition
  private static boolean control_blocks_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "control_blocks_0")) return false;
    boolean r;
    r = control_block_with_condition(b, l + 1);
    if (!r) r = control_block_without_condition(b, l + 1);
    return r;
  }

  // [poshi-code*]
  private static boolean control_blocks_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "control_blocks_2")) return false;
    control_blocks_2_0(b, l + 1);
    return true;
  }

  // poshi-code*
  private static boolean control_blocks_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "control_blocks_2_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!poshi_code(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "control_blocks_2_0", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // [variable-assignment | IDENTIFIER | string-quoted-double]
  static boolean invocation_inner(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "invocation_inner")) return false;
    invocation_inner_0(b, l + 1);
    return true;
  }

  // variable-assignment | IDENTIFIER | string-quoted-double
  private static boolean invocation_inner_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "invocation_inner_0")) return false;
    boolean r;
    r = variable_assignment(b, l + 1);
    if (!r) r = consumeToken(b, IDENTIFIER);
    if (!r) r = string_quoted_double(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // invocation-inner (COMMA invocation-inner)*
  static boolean invocation_inner_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "invocation_inner_list")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = invocation_inner(b, l + 1);
    r = r && invocation_inner_list_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (COMMA invocation-inner)*
  private static boolean invocation_inner_list_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "invocation_inner_list_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!invocation_inner_list_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "invocation_inner_list_1", c)) break;
    }
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
  // IDENTIFIER [PERIOD IDENTIFIER] ROUND_LBRACE [invocation-inner-list] ROUND_RBRACE
  public static boolean invocations(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "invocations")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    r = r && invocations_1(b, l + 1);
    r = r && consumeToken(b, ROUND_LBRACE);
    r = r && invocations_3(b, l + 1);
    r = r && consumeToken(b, ROUND_RBRACE);
    exit_section_(b, m, INVOCATIONS, r);
    return r;
  }

  // [PERIOD IDENTIFIER]
  private static boolean invocations_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "invocations_1")) return false;
    parseTokens(b, 0, PERIOD, IDENTIFIER);
    return true;
  }

  // [invocation-inner-list]
  private static boolean invocations_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "invocations_3")) return false;
    invocation_inner_list(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // SEMICOLON | COMPARISION_OPERATOR | RETURN
  public static boolean keywords(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "keywords")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, KEYWORDS, "<keywords>");
    r = consumeToken(b, SEMICOLON);
    if (!r) r = consumeToken(b, COMPARISION_OPERATOR);
    if (!r) r = consumeToken(b, RETURN);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // annotations | command-blocks | comments | control-blocks | invocations | keywords | properties | structures | variables
  static boolean poshi_code(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "poshi_code")) return false;
    boolean r;
    r = annotations(b, l + 1);
    if (!r) r = command_blocks(b, l + 1);
    if (!r) r = comments(b, l + 1);
    if (!r) r = control_blocks(b, l + 1);
    if (!r) r = invocations(b, l + 1);
    if (!r) r = keywords(b, l + 1);
    if (!r) r = properties(b, l + 1);
    if (!r) r = structures(b, l + 1);
    if (!r) r = variables(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // poshi-code*
  static boolean poshi_file(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "poshi_file")) return false;
    while (true) {
      int c = current_position_(b);
      if (!poshi_code(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "poshi_file", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // PROPERTY IDENTIFIER EQUALS (string-quoted-double)
  public static boolean properties(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "properties")) return false;
    if (!nextTokenIs(b, PROPERTY)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, PROPERTY, IDENTIFIER, EQUALS);
    r = r && properties_3(b, l + 1);
    exit_section_(b, m, PROPERTIES, r);
    return r;
  }

  // (string-quoted-double)
  private static boolean properties_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "properties_3")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = string_quoted_double(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
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
  // DEFINITION | SET_UP | TEAR_DOWN
  public static boolean structure_keywords(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "structure_keywords")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, STRUCTURE_KEYWORDS, "<structure keywords>");
    r = consumeToken(b, DEFINITION);
    if (!r) r = consumeToken(b, SET_UP);
    if (!r) r = consumeToken(b, TEAR_DOWN);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // structure-keywords CURLY_LBRACE [poshi-code*] CURLY_RBRACE
  public static boolean structures(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "structures")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, STRUCTURES, "<structures>");
    r = structure_keywords(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, consumeToken(b, CURLY_LBRACE));
    r = p && report_error_(b, structures_2(b, l + 1)) && r;
    r = p && consumeToken(b, CURLY_RBRACE) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // [poshi-code*]
  private static boolean structures_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "structures_2")) return false;
    structures_2_0(b, l + 1);
    return true;
  }

  // poshi-code*
  private static boolean structures_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "structures_2_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!poshi_code(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "structures_2_0", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // IDENTIFIER EQUALS (invocations | string-quoted-double | VARIABLE_REFERENCE | ARITHMETIC_OPERATOR | NUMERIC_CONSTANT)*
  public static boolean variable_assignment(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variable_assignment")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, IDENTIFIER, EQUALS);
    r = r && variable_assignment_2(b, l + 1);
    exit_section_(b, m, VARIABLE_ASSIGNMENT, r);
    return r;
  }

  // (invocations | string-quoted-double | VARIABLE_REFERENCE | ARITHMETIC_OPERATOR | NUMERIC_CONSTANT)*
  private static boolean variable_assignment_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variable_assignment_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!variable_assignment_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "variable_assignment_2", c)) break;
    }
    return true;
  }

  // invocations | string-quoted-double | VARIABLE_REFERENCE | ARITHMETIC_OPERATOR | NUMERIC_CONSTANT
  private static boolean variable_assignment_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variable_assignment_2_0")) return false;
    boolean r;
    r = invocations(b, l + 1);
    if (!r) r = string_quoted_double(b, l + 1);
    if (!r) r = consumeToken(b, VARIABLE_REFERENCE);
    if (!r) r = consumeToken(b, ARITHMETIC_OPERATOR);
    if (!r) r = consumeToken(b, NUMERIC_CONSTANT);
    return r;
  }

  /* ********************************************************** */
  // [STATIC] VAR variable-assignment
  public static boolean variables(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variables")) return false;
    if (!nextTokenIs(b, "<variables>", STATIC, VAR)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, VARIABLES, "<variables>");
    r = variables_0(b, l + 1);
    r = r && consumeToken(b, VAR);
    r = r && variable_assignment(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // [STATIC]
  private static boolean variables_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "variables_0")) return false;
    consumeToken(b, STATIC);
    return true;
  }

}
