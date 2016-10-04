import java_cup.runtime.*;
%%
%cup
%public
%class Lexer
%type Token
%line
%column
%type Symbol

%{

    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }

    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
%}
%eofval{
  return null;
%eofval};

LineTerminator = \r|\n|\r\n

WhiteSpace     = {LineTerminator} | [ \t\f]
digit = [0-9]
number = {digit}+
comment = "llll"

letter = [a-zA-Z]
identifier = {letter}+

%%


"if"               {  return symbol(sym.IF); }
"int"              {  return symbol(sym.INT); }
"return"           {  return symbol(sym.RETURN); }
"void"             {  return symbol(sym.VOID); }
"while"            {  return symbol(sym.WHILE); }
"else"             {  return symbol(sym.ELSE); }
"!="               {  return symbol(sym.NEQ); }
"="                {  return symbol(sym.ASSIGN); }
"=="               {  return symbol(sym.EQ); }
"<"                { return symbol(sym.LT); }
"<="               { return symbol(sym.LTE); }
">"                { return symbol(sym.GT); }
">="               { return symbol(sym.GTE); }
"+"                { return symbol(sym.PLUS); }
"-"                { return symbol(sym.MINUS); }
"*"                { return symbol(sym.TIMES); }
"/"                { return symbol(sym.DIV); }
"("                { return symbol(sym.LPAREN); }
")"                { return symbol(sym.RPAREN); }
"["                { return symbol(sym.LSQUARE); }
"]"                { return symbol(sym.RSQUARE); }
"{"                { return symbol(sym.LBRACE); }
"}"                { return symbol(sym.RBRACE); }
";"                { return symbol(sym.SEMI); }
","                { return symbol(sym.COMMA); }
{number}           { return symbol(sym.NUM, yytext()); }
{identifier}       { return symbol(sym.ID, yytext()); }
{WhiteSpace}*      { /* skip whitespace */ }
[/][*][^*]*[*]+([^/*][^*]*[*]+)*[/]      { /* skip comments */}
.                  {  return symbol(sym.ERROR); }
