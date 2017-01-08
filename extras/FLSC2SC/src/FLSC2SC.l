%{
#include <string.h>
#include "flsc_types.h"
#include "flsc2sc.h"

int linenum = 1;
%}

%option noyywrap

newline \n
comment ;+[^\n]*
blanks	[ \t]+
parl	\(
parr	\)
brl	\[
brr	\]
amp	\&
nunq	!

nil	nil

define	define
require require

lambda	lambda
patch	patch
module	module

let	let
letrec	letrec
letstar	let\*
nowarp  nowarp

if	if
cond	cond
else	else

symbol	[\+\-\*\/\?]+([a-z_][a-z0-9_\+\-\*\/\?]*)?|[a-z][a-z0-9_\+\-\*\/\?]*
number	\-?[0-9]+(\.[0-9]+)?(e\-?[0-9]+)?(pi)?
string	\"(\\.|[^"])*\"

other	.

%%

{newline}	{ linenum++; }
{comment}	{ /* ignore */ }
{blanks}	{ /* ignore */ }

{parl}		{ return(PARL); }
{parr}		{ return(PARR); }
{brl}		{ return(BRL); }
{brr}		{ return(BRR); }
{amp}		{ return(AMP); }
{nunq}		{ return(NUNQ); }

{nil}		{ return(NIL); }

{define}	{ return(DEFINE); }
{require}	{ return(REQUIRE); }

{lambda}	{ return(LAMBDA); }
{patch}		{ return(PATCH); }
{module}	{ return(MODULE); }

{let}		{ return(LET); }
{letrec}	{ return(LETREC); }
{letstar}	{ return(LETSTAR); }
{nowarp}	{ return(NOWARP); }

{if}		{ return(IF); }
{cond}		{ return(COND); }
{else}		{ return(ELSE); }

{symbol}	{ yylval.str = strdup(yytext); return(SYMB); }
{number}	{ yylval.str = strdup(yytext); return(NUM); }
{string}	{ yylval.str = strdup(yytext); return(STRING); }

{other}		{ return(ERROR); }
