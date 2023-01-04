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

ANNOTATION_NAME=(@[\s]*[a-z0-9]+)
IDENTIFIER=([A-z][\w-]*)+
VARIABLE_REFERENCE=(\$\{[\w-]+\})
BLOCK_COMMENT="/"\*.*?\*"/"
LINE_COMMENT="//".*
DOUBLE_QUOTED_STRING=(\"[^\"]*\")
SINGLE_QUOTED_STRING=('.*?')
COMPARISION_OPERATOR=[!=]=
ARITHMETIC_OPERATOR=(\-|\+|\*"/")
NUMERIC_CONSTANT=[0-9]+
TESTING_WHITESPACE=[ \n\t\r\f]+

%%
<YYINITIAL> {
  {WHITE_SPACE}               { return WHITE_SPACE; }

  "definition"                { return DEFINITION; }
  "setUp"                     { return SET_UP; }
  "tearDown"                  { return TEAR_DOWN; }
  "static"                    { return STATIC; }
  "var"                       { return VAR; }
  "property"                  { return PROPERTY; }
  "function"                  { return FUNCTION; }
  "macro"                     { return MACRO; }
  "test"                      { return TEST; }
  "if"                        { return IF; }
  "else if"                   { return ELSE_IF; }
  "else"                      { return ELSE; }
  "task"                      { return TASK; }
  "while"                     { return WHILE; }
  "return"                    { return RETURN; }
  "{"                         { return CURLY_LBRACE; }
  "}"                         { return CURLY_RBRACE; }
  "("                         { return ROUND_LBRACE; }
  ")"                         { return ROUND_RBRACE; }
  "["                         { return SQUARE_LBRACE; }
  "]"                         { return SQUARE_RBRACE; }
  "."                         { return PERIOD; }
  ";"                         { return SEMICOLON; }
  "="                         { return EQUALS; }
  "\""                        { return DOUBLE_QUOTE; }
  ","                         { return COMMA; }

  {ANNOTATION_NAME}           { return ANNOTATION_NAME; }
  {IDENTIFIER}                { return IDENTIFIER; }
  {VARIABLE_REFERENCE}        { return VARIABLE_REFERENCE; }
  {BLOCK_COMMENT}             { return BLOCK_COMMENT; }
  {LINE_COMMENT}              { return LINE_COMMENT; }
  {DOUBLE_QUOTED_STRING}      { return DOUBLE_QUOTED_STRING; }
  {SINGLE_QUOTED_STRING}      { return SINGLE_QUOTED_STRING; }
  {COMPARISION_OPERATOR}      { return COMPARISION_OPERATOR; }
  {ARITHMETIC_OPERATOR}       { return ARITHMETIC_OPERATOR; }
  {NUMERIC_CONSTANT}          { return NUMERIC_CONSTANT; }
  {TESTING_WHITESPACE}        { return TESTING_WHITESPACE; }

}

[^] { return BAD_CHARACTER; }
