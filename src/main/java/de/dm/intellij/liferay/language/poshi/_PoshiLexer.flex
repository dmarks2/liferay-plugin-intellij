package de.dm.intellij.liferay.language.poshi;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static de.dm.intellij.liferay.language.poshi.psi.PoshiTypes.*;

%%

%{
  public _PoshiLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class _PoshiLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL=\R
WHITE_SPACE=\s+

LINE_COMMENT="//".*
BLOCK_COMMENT="/"\*([^*]|\*+[^*/])*(\*+"/")?
DOUBLE_QUOTED_STRING=\"([^\\\"\r\n]|\\[^\r\n])*\"?
SINGLE_QUOTED_MULTILINE='''(.|\n)*'''
SINGLE_QUOTED_STRING='.*'
ANNOTATION_NAME=(@[\s]*[a-z0-9-]+)
IDENTIFIER=([A-z][\w-]*)+
ARITHMETIC_OPERATOR=(\-|\+|\*"/")
NUMERIC_CONSTANT=[0-9]+
COMPARISION_OPERATOR=(\!|\!=|==)
TESTING_WHITESPACE=[ \n\t\r\f]+

%%
<YYINITIAL> {
  {WHITE_SPACE}                  { return WHITE_SPACE; }

  "definition"                   { return DEFINITION; }
  "setUp"                        { return SET_UP; }
  "tearDown"                     { return TEAR_DOWN; }
  "function"                     { return FUNCTION; }
  "macro"                        { return MACRO; }
  "test"                         { return TEST; }
  "property"                     { return PROPERTY; }
  "static"                       { return STATIC; }
  "var"                          { return VAR; }
  "if"                           { return IF; }
  "else if"                      { return ELSE_IF; }
  "else"                         { return ELSE; }
  "task"                         { return TASK; }
  "while"                        { return WHILE; }
  "return"                       { return RETURN; }
  "{"                            { return CURLY_LBRACE; }
  "}"                            { return CURLY_RBRACE; }
  "("                            { return ROUND_LBRACE; }
  ")"                            { return ROUND_RBRACE; }
  "["                            { return SQUARE_LBRACE; }
  "]"                            { return SQUARE_RBRACE; }
  "."                            { return PERIOD; }
  ","                            { return COMMA; }
  "="                            { return EQUALS; }
  ";"                            { return SEMICOLON; }

  {LINE_COMMENT}                 { return LINE_COMMENT; }
  {BLOCK_COMMENT}                { return BLOCK_COMMENT; }
  {DOUBLE_QUOTED_STRING}         { return DOUBLE_QUOTED_STRING; }
  {SINGLE_QUOTED_MULTILINE}      { return SINGLE_QUOTED_MULTILINE; }
  {SINGLE_QUOTED_STRING}         { return SINGLE_QUOTED_STRING; }
  {ANNOTATION_NAME}              { return ANNOTATION_NAME; }
  {IDENTIFIER}                   { return IDENTIFIER; }
  {ARITHMETIC_OPERATOR}          { return ARITHMETIC_OPERATOR; }
  {NUMERIC_CONSTANT}             { return NUMERIC_CONSTANT; }
  {COMPARISION_OPERATOR}         { return COMPARISION_OPERATOR; }
  {TESTING_WHITESPACE}           { return TESTING_WHITESPACE; }

}

[^] { return BAD_CHARACTER; }
